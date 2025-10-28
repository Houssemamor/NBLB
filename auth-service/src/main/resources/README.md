# Authentication Service

This is a Spring Boot project for an authentication service. It provides functionalities for user registration and login, along with token generation for secure access.

## Project Structure

```
auth-service
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com
│   │   │       └── example
│   │   │           └── authservice
│   │   │               ├── AuthServiceApplication.java
│   │   │               ├── controller
│   │   │               │   └── AuthController.java
│   │   │               ├── service
│   │   │               │   └── AuthService.java
│   │   │               └── model
│   │   │                   └── User.java
│   │   └── resources
│   │       ├── application.properties
│   │       └── README.md
│   └── test
│       └── java
│           └── com
│               └── example
│                   └── authservice
│                       └── AuthServiceApplicationTests.java
├── pom.xml
└── README.md
```

## Features

- User Registration
- User Login
- Token Generation for secure access

## Setup Instructions

1. Clone the repository.
2. Navigate to the project directory.
3. Build the project using Maven:
   ```
   mvn clean install
   ```
4. Run the application:
   ```
   mvn spring-boot:run
   ```

## Usage

- Use the `/register` endpoint to register a new user.
- Use the `/login` endpoint to authenticate a user and receive a token.

## Dependencies

This project uses the following dependencies:

- Spring Boot Starter Web
- Spring Boot Starter Security
- Spring Boot Starter Test

## License

This project is licensed under the MIT License.