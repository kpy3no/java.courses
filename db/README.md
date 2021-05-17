# Скрипты базы данных

## Локальный запуск

### Обновление схемы базы данных

Обновить БД:
```
./gradlew liquibaseUpdate
```
        
Откатить 10-ть последних изменений:
```
./gradlew liquibaseRollbackCount -PliquibaseCommandValue=10
```
Очистить БД:
```
./gradlew liquibaseDropAll
```
    
> Настройки соединения с БД хранятся в файле **gradle.properties** и могут быть переопределены

Обновить БД с переопределением параметров подключения:
```
./gradlew liquibaseUpdate -Puser=myuser -Ppass=mypassword -Phost=myhost -Pport=myport -Pdatabase=mydatabase
```
