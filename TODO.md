## URGENT
- [x] запустить на payara micro с подключением бд


## Big tasks
- [x] Depoly on helios
  - [x] запуск payara
  - [x] db connection
  - [x] final deploy
- [ ] Frontend


## General
- [x] pagination, sorting, filter
- [x] return ErrorResponse or ErrorResponseArray
- [x] 500, 404, 400, etc errors
- [x] difference between 400 nd 422
- [x] validation of enums - проверка всех string
- [ ] добавить округление значений с точкой
- [x] изменить валидацию на максимальные значения
- [x] поменять Event под создание нескольких билетов
- [ ] handleHttpMessageNotReadable уточнить нечитаемое поле **ЧО ЭТО**


## GET /enums
- [x] проверить

## GET /tickets
- [ ] проверить, что ошибка неверного способа фильтрации не путается с ошибкой неправильного поля
- [x] не работает фильтрация для полей объектов, говорит что такого поля нет (coordinates.x)
- [x] не работает фильтрация на = и != для чисел
- [x] contains не работает
- [x] фильтрация String значений не работает
- [ ] фильтрация creationDate значений не работает
- [x] фильтрация enum значений не работает
- [ ] refundable не работает на null


## Тестирование
- [x] "discount": "43ю23". Может конвертировать числа в виде строки, но если строка не конвертируется в число - ошибка 400, но не моя
- [x] строка name не влезает в бд, надо поменять бд
- [x] price = 2147483647, не срабатывает MAX_VALUE. Надо обработать Numeric value (2147483648) out of range of int (-2147483648 - 2147483647)
- [x] http://localhost:8090/ticketservicepayara/TMA/api/v2/tickets?filter=person.height=123 - jakarta.servlet.ServletException: Request processing failed: java.lang.NullPointerException: Cannot load from object array because "values" is null
- [x] в delete сделать проверку, что можно удалить
- [ ] убрать ограничение на количество знаков после запятой
- [ ] нет сортировки внутри объектов

## POST /tickets
- [x] Поменять @NotNull кидать 400 статус

## GET /tickets/{id}
- [x] проверка id корректен
- [x] проверка id валиден
- [x] 404 если не найден

## PATCH /tickets/{id} 
- [x] проверка id корректен
- [x] проверка id валиден, 
- [x] 404 если не найден
- [x] filter fields in update
- [ ] надо ли изменять eventId?


## DELETE /tickets/{id}
- [x] проверка id корректен
- [x] проверка id валиден
- [x] 404 если не найден


