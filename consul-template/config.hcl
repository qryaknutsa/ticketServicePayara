consul {
  address = "127.0.0.1:8500"  # Адрес вашего Consul агента (может быть другим)
}

template {
  source      = "/mnt/c/Users/toric/IdeaProjects/rbdip/ticketService/consul-template/haproxy.cfg.ctmpl"
  destination = "/etc/haproxy/haproxy.cfg"
  command     = "sudo systemctl reload haproxy"
  command_timeout = "60s"
}