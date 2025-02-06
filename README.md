## Сервис по управлению билетами
# 2 лаба
Запускается на payara-micro-6.2024.9.jar (для helios) и payara-6.2024.9 (локально)

## payara-6.2024.9:
1. Чтобы настроить бд надо создать JDBC Resource и JDBC Connection pool
2. Название JDBC Resource надо указать в имени Persistence unit в persistence.xml
3. Деплой находится в Applications

## payara-micro-6.2024.9.jar + helios:
0. На хелиосе мало место, придется его расширить, чтобы влез хотя бы один инстанс payara-micro:
```shell
export _JAVA_OPTIONS="-XX:MaxHeapSize=1G -XX:MaxMetaspaceSize=512m"
```
1. Загружаем payara-micro-6.2024.9.jar, драйвер postgresql и db-config.asadmin (лежит в payara-config)
2. В db-config.asadmin меняем настройки бд под свои
3. Команда запуска:
```shell
java -jar payara-micro-6.2024.9.jar --addlibs payara-config/postgresql-42.7.4.jar --postbootcommandfile payara-config/db-config-helios.asadmin --deploy target/ticketservicepayara.war --port 8090
```
4. Не забываем пробросить порт:
```shell
ssh -p 2222 -L 8090:helios.cs.ifmo.ru:8090 s336385@helios.cs.ifmo.ru
```

## SSL
Надо создать свой сертификат:
```shell
keytool -genkey -alias truststore -keyalg RSA -keysize 2048 -keystore truststore.jks -storepass changeit -validity 365
```

Потом его надо загрузить в хелиос и запустить:
```shell
java -Djavax.net.ssl.trustStore=truststore.jks -Djavax.net.ssl.trustStorePassword=changeit -jar payara-micro-6.2024.9.jar --sslport 9011 --addlibs payara-config/postgresql-42.7.4.jar --postbootcommandfile payara-config/db-config-helios.asadmin --deploy target/ticketservicepayara.war --port 9012
```
## Второй сервис
Второй сервис я запускала только локально, вот репозиторий:
https://github.com/qryaknutsa/bookingServicePayara