## **HEALTH CARE SYSTEM**

This is Health Care System. In this application you can get/add/update/delete Enrollee and its dependents.

## What's inside 
This project is based on the [Spring Boot](http://projects.spring.io/spring-boot/) project and uses these packages :
- Maven
- Spring Boot
- Spring JPA (H2 Database)
- Swagger
- Junit (Unit Testing)

## Installation 
The project is created with Maven, so you just need to import it to your IDE and build the project to resolve the dependencies

## Database configuration 
Create a h2 database with the name `healthcare` and add the credentials to `/resources/application.properties`.  
The default ones are :

```
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.url=jdbc:h2:mem:healthcare
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
```

## Usage 
Run the project through the IDE and head out to [http://localhost:8080](http://localhost:8080)

or 

run this command in the command line:
```
mvn spring-boot:run
```

#### You Can access Swagger UI for REST API using below link:

###### **For Localhost:** _http://localhost:8080/health-care/swagger-ui.html_

###### **For Other Environment:** _${ENV_HOSTNAME}/health-care/swagger-ui.html_


#### You can Access H2-Console using below link:

###### **For Localhost:** _http://localhost:8080/health-care/h2-console_

###### **For Other Environment:** _${ENV_HOSTNAME}/health-care/h2-console_
