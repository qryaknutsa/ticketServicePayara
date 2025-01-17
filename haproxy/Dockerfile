# Используем образ Alpine
FROM alpine:3.16

# Устанавливаем необходимые пакеты, включая Go и HAProxy
RUN apk add --no-cache go haproxy

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем файлы приложения
COPY ./go.* .
COPY ./updater.go .

# Собираем Go-приложение
RUN go build -o updater updater.go

# Копируем конфигурацию HAProxy
COPY ./haproxy.cfg /usr/local/etc/haproxy/haproxy.cfg
COPY ./errors/429.http /usr/local/etc/haproxy/429.http
COPY ./errors/503.http /usr/local/etc/haproxy/503.http

RUN echo "" > /usr/local/etc/haproxy/current.txt

ENV SLEEP_TIME=100
ENV SERVICES=ticketService
ENV CONFIG_PATH=/usr/local/etc/haproxy/haproxy.cfg
ENV CONSUL_ADDR=host.docker.internal:8500
ENV RUN_UPDATER=true

# Запускаем HAProxy и Go-приложение
CMD ["sh", "-c", "haproxy -f /usr/local/etc/haproxy/haproxy.cfg -D -p /usr/local/etc/haproxy/current.txt & if [ \"$RUN_UPDATER\" = \"true\" ]; then ./updater; else tail -f /dev/null; fi"]