# file_reader_app
<h3>Данный сервис загружает данные из файла с расширением (CSV). Далее по наименованию заголовка извлечь из БД все имеющиеся значения для данного заголовка и возвращает в формате: имя файла, номер строки в файле, значение.</h3>

Основной стек технологий:
    
    Java 8
    Docker
    Spring Boot 2
    PostgreSQL 11
    Flyway
    MyBatis
    
    JUnit
    Mockito

### запуск приложения
Указать необходимые креды для запуска 
   
Приложение по дефолту поднимается на 8080 порт

Application.yml содержит сепаратор используемый в csv
        
### примеры валидных запросов:
Примеры валидных запросов:

    //загрузка данных из файла
    curl --location --request POST 'http://localhost:8080/api/v1/files' \
         --header 'Content-Type: application/json' \
         --form 'file=@"path-to-file"'
          
        
    //получение данных по заголовку
    curl --location --request GET 'http://localhost:8080/api/v1/contents?fileHeader=Period' \
       

 
