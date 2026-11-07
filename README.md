<h2> SOCIALMEDIA-API </h2>

This repository contains a social media REST API developed in Java using Spring Boot.

This project was created for learning and practice, 
focusing on applying software development best practices, architecture, and 
design patterns. It is an ongoing project and may undergo further changes and improvements.

<h3> FEATURES: </h3>

- Users can create posts, comment on posts, like posts, and give stars to other users.    
- Admin functionalities, including banning users and deleting posts and comments.    
- API documentation with OpenAPI and Swagger UI.    
- Layered Architecture.    
- DTO Pattern and object mapping using MapStruct.    
- Pagination for endpoints such as user posts and received stars.    
- Security layer implemented with Spring Security and JWT-based authentication.    
- Unit, Integration and Security testing with JUnit 5 and Mockito.  

<h3> TECHNOLOGIES: </h3>

- Java 21  
- Spring Boot  
- Spring Security  
- Spring data JPA / Hibernate  
- MySql  
- JWT   
- MapStruct  
- JUnit 5 & Mockito  
- Docker  
- Swagger OpenAPI  
- GitHub actions  

<h3> HOW TO RUN PROJECT: </h3>

to run this project you will need:  
- Docker desktop  
- Docker compose (included with Docker desktop)  

You do not need to install Java, Maven, or MySQL locally, as all services run inside Docker containers.

**1. Clone repository**

"git clone https://github.com/blasman2002/Backend-API-REST-JWT.git cd Backend-API-REST-JWT"

**2. Configure environment variables**

Create a .env file in the project root directory, alongside compose.yml.

Add the following variables:

MYSQL_ROOT_PASSWORD=your_root_password  
DB_NAME=db_social  
DB_USERNAME=socialmedia_user  
DB_PASSWORD=your_password  
JWT_SECRET=your_jwt_secret  

- Replace example variables with your own credentials.   
- JWT_SECRET must contain a secure Base64-encoded key compatible with the application's JWT configuration.

**3. Build and start services:**

from the project root directory, run:

"docker compose up -d --build"

this command builds the Spring Boot application image and starts the following services:

- Spring boot API - Port 8092    
- MySql - Port 3307

**4. Verify container status**

"docker compose ps"

both services should display UP status, MySQL should display healthy status.

**5. Access the application**

once the services are running, you can access:

Swagger UI: http://localhost:8092/swagger-ui/index.html   
OpenAPI JSON: http://localhost:8092/v3/api-docs

