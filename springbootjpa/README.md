# Каркас на Spring Boot 

## Локальный запуск

### Подключить модуль к БД

Обновить файл application.properties исходя из ваших настроек бд
```
spring.datasource.driver-class-name=org.postgresql.Driver
spring.datasource.url=jdbc:postgresql://localhost:5432/postgres
spring.datasource.username=postgres
spring.datasource.password=postgres
```
        
Патч на бд можно не накатывать так как в application.properties есть параметр
```
spring.jpa.hibernate.ddl-auto=update
```
### Кодманда для запуска приложения
```
./gradlew springbootjpa:bootrun
```
### Команда для запуска тестов
```
./gradlew springbootjpa:test
```
