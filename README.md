# Job Hunter - RESTful API

## Overview

Job Hunter is a RESTful API built with Java Spring Boot, designed to provide a platform for job seekers and employers to interact efficiently. This API allows users to manage job postings, applications, and user profiles, while also offering secure authentication and authorization mechanisms.

## Table of Contents

- [Features](#features)
- [Architecture](#architecture)
- [Tech Stack](#tech-stack)
- [Installation](#installation)
- [Configuration](#configuration)
- [Usage](#usage)
- [Docker Setup](#docker-setup)


## Features

- **User Management**: Registration, login, profile management, and role-based access control (RBAC) for job seekers and employers.
- **Job Management**: CRUD operations for job listings, with filtering and search capabilities.
- **Application Management**: Job application submission, tracking, and management.
- **Security**: JWT-based authentication, OAuth2 resource server, and validation mechanisms.
- **Email Notifications**: Automated email notifications for account verification and application status updates.
- **Swagger Documentation**: API documentation available via SpringDoc OpenAPI.

## Architecture

The project follows a layered architecture:

- **Controller Layer**: Handles incoming HTTP requests and maps them to the appropriate service methods.
- **Service Layer**: Contains business logic and interacts with the data access layer.
- **Repository Layer**: Responsible for data persistence using Spring Data JPA.
- **Security Layer**: Manages authentication and authorization using Spring Security and JWT.

## Tech Stack

- **Java**: Version 17
- **Spring Boot**: Version 3.3.2
  - Spring Boot Starter Web
  - Spring Boot Starter Data JPA
  - Spring Boot Starter Security
  - Spring Boot Starter Thymeleaf
  - Spring Boot Starter Mail
  - Spring Boot Starter Actuator
- **Database**: MySQL
- **Security**: JWT, OAuth2 Resource Server, Spring Security
- **Documentation**: SpringDoc OpenAPI
- **Others**: Lombok, Thymeleaf Extras Spring Security, Turkraft Spring Filter

## Installation

1. **Clone the repository**:
   ```bash
   git clone https://github.com/TranDatk/Java-spring-jobhunter.git
2. Build the project:
   ```bash
   ./gradlew build
3. Run the application:
   ```bash
   ./gradlew bootRun

## Configuration

1. Database Configuration:
   * Update the application.properties file with your MySQL database credentials:
      ```bash
      spring.datasource.url=jdbc:mysql://localhost:3306/jobhunter
      spring.datasource.username=your-username
      spring.datasource.password=your-password
      spring.jpa.hibernate.ddl-auto=update
      spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
      spring.jpa.show-sql: true
2. Mail Server Configuration:
   * Set up email configurations for subcriber:
      ```bash
      spring.mail.host=smtp.your-email-provider.com
      spring.mail.port=587
      spring.mail.username=your-email@example.com
      spring.mail.password=your-email-password
      spring.mail.properties.mail.smtp.auth=true
      spring.mail.properties.mail.smtp.starttls.enable=true
3. JWT Configuration:
      ```bash
      datk.jwt.base64-secret=qoAEABDke07+AVLepXB4aCMtsT0wMAqR5x2VFyldsnx6e75YQkJH2UcZKTjEyoNgG71SBCXfq5N6NVZxWOfsHQ==
      datk.jwt.access-token-validity-in-seconds=86400
      datk.jwt.refresh-token-validity-in-seconds=86400
4. Upload File Configuration:
      ```bash
      #default = 1MB
      spring.servlet.multipart.max-file-size=50MB
      
      #default = 10 MB (form data)
      spring.servlet.multipart.max-request-size=50MB
      
      #base path
      datk.upload-file.base-uri=file:///your-uri
5. Spring Data Web Properties:
      ```bash
      # Default page size.
      spring.data.web.pageable.default-page-size=5
      # Maximum page size to be accepted.
      spring.data.web.pageable.max-page-size=20
      # Whether to expose and assume 1-based page number indexes.
      spring.data.web.pageable.one-indexed-parameters=true
      # Page index parameter name.
      #spring.data.web.pageable.page-parameter=currentPage
      # Page size parameter name.
      #spring.data.web.pageable.size-parameter=pageSize
      # Sort parameter name.
      spring.data.web.sort.sort-parameter=sort
6. App Configuration:
      ```bash
      spring.application.name=jobhunter
      server.port=8080
      apiPrefix=/api/v1
      
## Usage

* API Documentation: Once the application is running, you can access the API documentation at http://localhost:8080/swagger-ui.html.
* Sample Data: You can use the provided sample data to populate the database for testing purposes.

## Docker Setup

The project includes a Dockerfile for containerizing the application.

### Dockerfile Overview

The Dockerfile is divided into two stages:

1. **Build Stage**: 
   - Uses a Gradle image with JDK 17 to build the application.
   - Copies the source code into the container and builds the application, skipping the tests for efficiency.

   ```dockerfile
   # Stage 1: Build the application
   FROM gradle:8.7-jdk17 AS build
   COPY --chown=gradle:gradle . /datk/jobhunter
   WORKDIR /datk/jobhunter

   # Skip task: test
   RUN gradle clean build -x test --no-daemon
2. **Run Stage**:
   - Uses a slim OpenJDK 17 image to run the application.
   - Exposes port 8080 and runs the application as a jar file.
   ```dockerfile
   # Stage 2: Run the application
   FROM openjdk:17-slim
   EXPOSE 8080
   COPY --from=build /datk/jobhunter/build/libs/*.jar /datk/spring-boot-job-hunter.jar
   ENTRYPOINT ["java", "-jar", "/datk/spring-boot-job-hunter.jar"]
   
### Building and Running with Docker

1. Build the Docker Image:
   ```bash
   docker build -t job-hunter .
2. Run the Docker Container:
   ```bash
   docker run -p 8080:8080 job-hunter
3. Access the Application:
   - Once the container is running, the application will be accessible at http://localhost:8080.
