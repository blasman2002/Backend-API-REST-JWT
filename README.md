*HOW TO RUN PROJECT:

to run this project first your need:  
-Docker desktop  
-Docker compose (included with Docker desktop)  

You do not need to install Java, Maven, or MySQL locally, as all services run inside Docker containers.

1. Clone repository

"git clone https://github.com/blasman2002/Backend-API-REST-JWT cd Backend-API-REST-JWT"

2. Configure environment variables

Create a .env file in the project root directory, alongside compose.yml.

Add the following variables:

MYSQL_ROOT_PASSWORD=your_root_password  
DB_NAME=db_social  
DB_USERNAME=socialmedia_user  
DB_PASSWORD=your_password  
JWT_SECRET=your_jwt_secret  

-Replace example variables with your own credentials.   
-JWT_SECRET must contain a secure key compatible with the application JWT configuration (Base64 key).

3. Build and start services:

from the project root directory, run:

"docker compose up -d --build"

this command builds the Spring Boot application image and starts the following services:

-Spring boot API - Port 8092    
-MySql - Port 3307

4. Verify container status

"docker compose ps"

both services should display UP status, MySQL should display healthy.

5. Access the application

once the services are running, you can access:

Swagger UI: http://localhost:8092/swagger-ui/index.html   
OpenAPI JSON: http://localhost:8092/v3/api-docs

