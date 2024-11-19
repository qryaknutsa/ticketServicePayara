## URGENT
- [x] запустить на payara micro с подключением бд


## Big tasks
- [ ] Depoly on helios
  - [x] запуск payara
  - [x] db connection
  - [ ] final deploy
- [ ] Frontend


## General
- [x] pagination, sorting, filter
- [x] return ErrorResponse or ErrorResponseArray
- [x] 500, 404, 400, etc errors
- [x] difference between 400 nd 422
- [ ] validation of enums
- [ ] добавить округление значений с точкой
- [x] изменить валидацию на максимальные значения
- [x] поменять Event под создание нескольких билетов
- [ ] handleHttpMessageNotReadable уточнить нечитаемое поле **ЧО ЭТО**


## GET /enums
- [ ] проверить

## GET /tickets
- [ ] проверить, что ошибка неверного способа фильтрации не путается с ошибкой неправильного поля
- [x] не работает фильтрация для полей объектов, говорит что такого поля нет (coordinates.x)
- [ ] не работает фильтрация на '='
- [ ] для значений с плавающей точкой нужно при фильтрации добавлять '.' (не 'discount=43', а 'discount=43.0')
- [ ] contains не работает...........

## POST /tickets
- [x] Поменять @NotNull кидать 400 статус

## GET /tickets/{id}
- [x] проверка id корректен
- [x] проверка id валиден
- [x] 404 если не найден

## PATCH /tickets/{id} 
- [ ] проверка id корректен
- [ ] проверка id валиден, 
- [ ] 404 если не найден
- [ ] filter fields in update


## DELETE /tickets/{id}
- [x] проверка id корректен
- [x] проверка id валиден
- [x] 404 если не найден


