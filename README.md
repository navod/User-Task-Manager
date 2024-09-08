# Task Manager

## Overview

This is a Spring Boot project that uses Gradle as the build tool and MySQL as the database. The project includes various dependencies for building a robust and scalable application.

## Spring Boot Version

This project is using Spring Boot version `3.3.3`.

## Dependencies

The following dependencies are included in this project:

- `spring-boot-starter-data-jpa` - Provides Spring Data JPA support.
- `spring-boot-starter-web` - Includes Spring MVC, RESTful web services, and Tomcat.
- `lombok` - Reduces boilerplate code through annotations.
- `modelmapper` - A library for object mapping.
- `spring-boot-starter-validation` - Provides support for Bean Validation with JSR-380.
- `spring-boot-starter-oauth2-resource-server` - Provides support for OAuth2 resource server.

## Database

This project uses MySQL as the database. Make sure you have MySQL installed and a database created for this project.

## Build and Run

### Prerequisites

- Java 17 or higher
- Gradle
- MySQL

### Building the Project

To build the project, run the following command:

```bash
./gradlew build
