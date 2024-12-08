Products and Category Management Project

Project Context
  As a Full Stack Developer, the goal is to secure and deploy a web application.
  Main Entities

    Product

    designation (String)
    price (Double)
    quantity (Integer)

    Category

    name (String)
    description (String)

    User

    login (String)
    password (String)
    active (Boolean)
    roles (Collection of Role)
  
    Role

    name (String)
Relationships

    A category can have multiple products.
    A product belongs to one category.
Here is the translated and reformulated README in English:

Product and Category Management Project
Project Context

As a Full Stack Developer, the goal is to secure and deploy a web application. The developer must:

    Secure the APIs using stateful authentication (based on sessions).
    Containerize and deploy the application using Docker and Jenkins.
    Maintain technical documentation and train teams.

Main Entities

The project includes the following entities:

    Product
        designation (String)
        price (Double)
        quantity (Integer)

    Category
        name (String)
        description (String)

    User
        login (String)
        password (String)
        active (Boolean)
        roles (Collection of Role)

    Role
        name (String)

Relationships

    A category can have multiple products.
    A product belongs to one category.

Required Features
Product Management

    List products with pagination (for USER or ADMIN).
    Search products by designation with pagination and sorting (for USER or ADMIN).
    Search products by category (for USER or ADMIN).
    Filter products by category with pagination and sorting (for USER or ADMIN).
    Add a new product (only ADMIN).
    Modify an existing product (only ADMIN).
    Delete a product (only ADMIN).

Endpoints:

    /api/user/products/**
    /api/admin/products/**

Category Management

    List categories with pagination (for USER or ADMIN).
    Search categories by name with pagination and sorting (for USER or ADMIN).
    List products of a category with pagination and sorting (for USER or ADMIN).
    Add a new category (only ADMIN).
    Modify an existing category (only ADMIN).
    Delete a category (only ADMIN).

Endpoints:

    /api/user/categories/**
    /api/admin/categories/**

User Management

    Authentication: /api/auth/login (POST)
    Account creation: /api/auth/register (POST)
    List users: /api/admin/users (GET, only ADMIN)
    Manage user roles: /api/admin/users/{id}/roles (PUT, only ADMIN)

Security

    Implement Spring Security with stateful authentication (based on sessions) using JdbcAuthentication.
    URLs /api/admin/* require the ADMIN role.
    URLs /api/user/* require the USER role.
    Use BCryptPasswordEncoder or another strong encoder for password encryption.
Application Layers

The project follows a layered architecture with the following components:

    Controller: Handles HTTP requests and redirects them to the service.
    Service: Contains business logic.
    Repository: Accesses data via Spring Data JPA.
    DTO (Data Transfer Objects): Used for transferring data between layers.
    Mapper: Converts entities to DTOs and vice versa.
    Exception: Manages custom business exceptions via @ControllerAdvice.
    Validation: Uses Bean Validation (@Valid, @NotNull, etc.) and custom validators.
    Utils: Contains utility classes (optional).
    Tests: Unit and integration tests.
Technologies and Concepts to Use

    Spring Boot for application development.
    REST API for communication between components.
    Spring Data JPA for data management.
    Exception Handling: Use @ControllerAdvice and custom business exceptions.
    Validation: Use Bean Validation and custom validators.
    Databases: MariaDB or OracleXE.
    CI/CD with Jenkins: Continuous integration and deployment.
    Containerization with Docker:
        Structure of the Dockerfile.
        Startup scripts.
Author and Contact Information

    Termoussi Lamiaa 
    Email: lamiaa3105@gmail.com
