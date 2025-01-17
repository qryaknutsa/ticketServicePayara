package main

import (
	"bufio"
	"encoding/json"
	"fmt"
	"log"
	"net/http"
	"os"
	"os/exec"
	"strconv"
	"strings"
	"time"
)

type Node struct {
	ID         string `json:"ID"`
	Node       string `json:"Node"`
	Address    string `json:"Address"`
	Datacenter string `json:"Datacenter"`
}

type Service struct {
	ID      string `json:"ID"`
	Service string `json:"Service"`
	Address string `json:"Address"`
	Port    int    `json:"Port"`
}

type Check struct {
	Status string `json:"Status"`
}

type HealthResponse struct {
	Node    Node    `json:"Node"`
	Service Service `json:"Service"`
	Checks  []Check `json:"Checks"`
}

const (
	serviceLine   = 36
	passingStatus = "passing"
)

var (
	fileID  uint64
	prevPID int
	prevIPs map[string]struct{}
)

func main() {
	sleepTime := os.Getenv("SLEEP_TIME")
	services := os.Getenv("SERVICES")
	configPath := os.Getenv("CONFIG_PATH")
	consulAddr := os.Getenv("CONSUL_ADDR")

	sleepTimeInt, err := strconv.ParseInt(sleepTime, 10, 64)
	if err != nil {
		log.Fatalf("Invalid SLEEP_TIME: %v", err)
	}
	duration := time.Duration(sleepTimeInt) * time.Millisecond
	if err != nil {
		log.Fatalf("Invalid SLEEP_TIME: %v", err)
	}

	serviceList := strings.Split(services, ",")

	for {
		time.Sleep(duration)
		results, ips := getConsulPODs(serviceList, consulAddr)
		config := generateHAProxyConfig(results)
		if compareMaps(prevIPs, ips) {
			continue
		}
		prevPID = readPid("/usr/local/etc/haproxy/current.txt")
		filename := writeToFile(config, configPath)
		reloadConfig(filename, fmt.Sprintf("%d", prevPID))
		go func(filename string) {
			f := filename
			time.Sleep(duration)
			os.Remove(f)
		}(filename)
		prevIPs = ips
	}
}

func readPid(f string) int {
	data, _ := os.ReadFile(f)
	numberStr := string(data)
	n, e := strconv.Atoi(strings.TrimSpace(numberStr))
	if e != nil {
		log.Printf("Error reading PID file: %v\n", e)
	}
	return n
}

func reloadConfig(filename string, prevPID string) {
	cmd := exec.Command("sh", "-c", "haproxy -f "+filename+" -D -p /usr/local/etc/haproxy/current.txt -sf "+prevPID)
	cmd.Start()
	log.Println(cmd.Args)
	log.Println("HAProxy config reloaded")
}

func getConsulPODs(serviceList []string, consulAddr string) (map[string]map[string]map[string]string, map[string]struct{}) {
	results := make(map[string]map[string]map[string]string, len(serviceList))
	ips := make(map[string]struct{}, len(serviceList))
	for {
		for _, serviceName := range serviceList {
			url := fmt.Sprintf("http://%s/v1/health/service/%s", consulAddr, serviceName)
			resp, err := http.Get(url)

			if err != nil {
				log.Printf("Error fetching health for service %s: %v", serviceName, err)
				continue
			}
			defer resp.Body.Close()

			if resp.StatusCode != http.StatusOK {
				log.Printf("Service %s returned non-200 status: %d", serviceName, resp.StatusCode)
				continue
			}

			var healthResponses []HealthResponse
			if err := json.NewDecoder(resp.Body).Decode(&healthResponses); err != nil {
				log.Printf("Error decoding JSON response for service %s: %v", serviceName, err)
				continue
			}

			for _, health := range healthResponses {
				id := health.Service.ID
				address := health.Service.Address
				port := health.Service.Port

				allPassing := true
				for _, check := range health.Checks {
					if check.Status != passingStatus {
						allPassing = false
						break
					}
				}

				if allPassing {
					if _, ok := results[serviceName]; !ok {
						results[serviceName] = make(map[string]map[string]string)
					}
					results[serviceName][id] = map[string]string{
						"ID":      id,
						"Address": address,
						"Port":    fmt.Sprintf("%d", port),
					}
					ips[address] = struct{}{}
				} else {
					delete(results, id)
				}
			}
		}

		return results, ips
	}
}

func generateHAProxyConfig(results map[string]map[string]map[string]string) []string {
	var config []string

	for name, info := range results {
		config = append(config, fmt.Sprintf("backend %s_back", name))
		config = append(config, "    balance roundrobin")
		for _, instance := range info {
// 			config = append(config, fmt.Sprintf("    server %s %s:%s ssl verify none maxconn 10", instance["ID"], instance["Address"], instance["Port"]))
			config = append(config, fmt.Sprintf("    server %s host.docker.internal:%s check", instance["ID"], instance["Port"]))

		}
		config = append(config, "")
	}

	return config
}

func writeToFile(config []string, path string) string {
	file, err := os.Open(path)
	if err != nil {
		log.Printf("Error opening file: %s, err: %v", path, err)
		return ""
	}
	defer file.Close()

	var lines []string
	scanner := bufio.NewScanner(file)
	lineNumber := 0
	for scanner.Scan() {
		lineNumber++
		if lineNumber <= serviceLine {
			lines = append(lines, scanner.Text())
		}
	}

	if err := scanner.Err(); err != nil {
		log.Printf("Error reading file: %s, err: %v", path, err)
		return ""
	}

	fileID++
	filename := path + fmt.Sprintf("%d", fileID)
	file, err = os.OpenFile(filename, os.O_CREATE|os.O_WRONLY, 0777)
	if err != nil {
		log.Printf("Error opening file: %s, err: %v", path, err)
		return ""
	}
	defer file.Close()

	for _, line := range lines {
		_, err := file.WriteString(line + "\n")
		if err != nil {
			log.Printf("Error writing file: %s, err: %v", path, err)
			return ""
		}
	}

	for _, line := range config {
		_, err := file.WriteString(line + "\n")
		if err != nil {
			log.Printf("Error writing file: %s, err: %v", path, err)
			return ""
		}
	}

	log.Printf("File updated new config: %s", strings.Join(config, "\n"))
	return filename
}

func compareMaps(a, b map[string]struct{}) bool {
	if len(a) != len(b) {
		return false
	}
	for key := range a {
		if _, exists := b[key]; !exists {
			return false
		}
	}
	for key := range b {
		if _, exists := a[key]; !exists {
			return false
		}
	}
	return true
}
