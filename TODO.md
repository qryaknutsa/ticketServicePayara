- [x] pagination, sorting, filter
- [x] return ErrorResponse or ErrorResponseArray
- [x] 500, 404, 400, etc errors
- [x] difference between 400 nd 422
- [ ] validation of enums
- [ ] добавить округление значений с точкой
- [x] изменить валидацию на максимальные значения
- [x] поменять Event под создание нескольких билетов
- [ ] handleHttpMessageNotReadable уточнить нечитаемое поле

## Ticket Service 

### GET /enums
- [ ] проверить

### GET /tickets
- [ ] проверить, что ошибка неверного способа фильтрации не путается с ошибкой неправильного поля
- [ ] не работает фильтрация для полей объектов, говорит что такого поля нет (coordinates.x)

### POST /tickets
- [x] Поменять @NotNull кидать 400 статус

### GET /tickets/{id}
- [x] проверка id корректен
- [x] проверка id валиден
- [x] 404 если не найден

### PATCH /tickets/{id} 
- [ ] проверка id корректен
- [ ] проверка id валиден, 
- [ ] 404 если не найден
- [ ] filter fields in update


### DELETE /tickets/{id}
- [x] проверка id корректен
- [x] проверка id валиден
- [x] 404 если не найден




## Big tasks
- [ ] Depoly on helios 
  - [ ] запуск payara
  - [ ] db connection
- [ ] Frontend
