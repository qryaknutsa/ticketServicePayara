Ошибки
- [ ] сделать проверку repo.findById().isPresent() для поиска сущностей
- [ ] и создать ошибки notFound для всех сущностей



~~- [ ] у меня консул в Докере, как его запускать на хелиосе? Что вообще дал консул?~~
- [x] добавить авторизацию через keycloak
- [ ] haproxy
- [ ] ошибки 422 не бывает для url значений



data
: 
detail
: 
"Access Denied"
instance
: 
"http://localhost/TMA/api/v2/tickets"
title
: 
"Внутренняя ошибка сервера"

2025-01-17T09:58:47.893+03:00 DEBUG 24424 --- [ticketService] [io-54739-exec-7] horizationManagerBeforeMethodInterceptor : Authorizing method invocation ReflectiveMetho
dInvocation: public org.springframework.http.ResponseEntity org.example.ticketservice.controller.TicketController.saveTicket(org.example.ticketservice.dto.TicketWrite); target is of class [org.example.ticketservice.controller.TicketController]
