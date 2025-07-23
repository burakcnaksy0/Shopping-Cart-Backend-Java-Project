# Shopping Card Backend Project

This project is a backend application for a shopping cart system, developed using Java and the Spring Boot framework.

## Table of Contents
- [About the Project](#about-the-project)
- [Technologies Used](#technologies-used)
- [Features](#features)
- [Getting Started](#getting-started)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Project Structure](#project-structure)

## About the Project

This application provides the necessary backend services for an e-commerce platform's shopping cart functionality. It handles user authentication, product management, and shopping cart operations through a RESTful API.

## Technologies Used

- **Java 17**
- **Spring Boot 3**
- **Spring Web**: For creating RESTful APIs.
- **Spring Security**: For authentication and authorization.
- **Spring Data JPA**: For data persistence.
- **PostgreSQL**: As the relational database.
- **Maven**: For project dependency management.
- **JWT (JSON Web Tokens)**: For secure API communication.
- **Lombok**: To reduce boilerplate code.
- **ModelMapper**: For object mapping.


## Features

- User registration and login system.
- Secure token-based authentication (JWT).
- Role-based access control.
- REST endpoints for managing products, categories, and shopping carts.
- Adding, updating, and removing items from the cart.
- Viewing cart contents and total price.

## Getting Started

Follow these instructions to get a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

- **JDK 17** or later.
- **Maven 3.2+**.
- **PostgreSQL** database server.

### Installation

1. **Clone the repository:**
   ```sh
   git clone https://github.com/burakcnaksy0/Shopping-Cart-Backend-Java-Project
   cd shopping-card-backend/shopcard
   ```

2. **Configure the database:**
   - Create a new PostgreSQL database.
   - Open the `src/main/resources/application.properties` file.
   - Update the `spring.datasource.url`, `spring.datasource.username`, and `spring.datasource.password` properties with your database connection details.

3. **Build and run the application:**
   ```sh
   mvn spring-boot:run
   ```
   The application will start on `http://localhost:8080`.

## Project Structure

The project follows a standard layered architecture:

```
├── src
│   ├── main
│   │   ├── java
│   │   │   └── b
│   │   │       └── aksoy
│   │   │           └── shopcard
│   │   │               ├── config      # Spring configurations (Security, etc.)
│   │   │               ├── controller  # REST API controllers
│   │   │               ├── dto         # Data Transfer Objects
│   │   │               ├── entity      # JPA entities
│   │   │               ├── exception   # Custom exception handlers
│   │   │               ├── repository  # Spring Data JPA repositories
│   │   │               ├── request     # Request body models
│   │   │               ├── response    # Response body models
│   │   │               ├── security    # JWT and security components
│   │   │               └── service     # Business logic services
│   │   └── resources
│   │       └── application.properties # Application configuration
│   └── test
├── .gitignore
├── pom.xml         # Maven project configuration
└── README.md
```
