## Сервис по управлению билетами
### Как запустить на хелиосе

Чтобы запустить, надо:
1. Собрать заново проект, если надо
`   mvn clean install
`
2. Подрубиться к хелиос
`ssh -p 2222 s336385@se.ifmo.ru
`
3. Расширить пространства
`export _JAVA_OPTIONS="-XX:MaxHeapSize=1G -XX:MaxMetaspaceSize=512m"
`
4. Перетащить в директорию soa/target полученный war файл

5. Перейти в директорию soa
`   cd ticket
`
6. запустить:
`   java -jar payara-micro-6.2024.9.jar --addlibs payara-config/postgresql-42.7.4.jar --postbootcommandfile payara-config/db-config-helios.asadmin --deploy target/ticketservicepayara.war --port 8080
`

7. Пробросить порт:
`   ssh -p 2222 -L 8080:helios.cs.ifmo.ru:8080 s336385@helios.cs.ifmo.ru
`