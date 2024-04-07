# eav-platform

**eav-platform** is an application designed for managing data using the Entity-Attribute-Value (EAV) model. This application allows adding, editing, and deleting entities, attributes, and values in accordance with the EAV data model. It utilizes JWT with refresh token for authentication and access control for users, and also implements security at the attribute level.

## Features
- EAV Model Management
- JWT Authentication with Refresh Token
- Email Confirmation Mechanism
- Security at Attribute Level

## Features (details)

### JWT Authentication
- Uses JWT tokens for user authentication and authorization.
- Implements refresh tokens to extend JWT validity without requiring reauthentication.
- Provides enhanced security by reducing token exposure and improving session management.
#### Description
- **_User Registration:_**
  - Users can register using their email and password.
  - Upon successful registration, an email is sent to the user with a confirmation link.
- **_Email Confirmation:_**
  - After receiving the email, the user clicks on the confirmation link to verify their email address.
  - Once the email is confirmed, the registration process is complete, and the user can proceed to log in to the application.
- **_User Login:_**
  - Users log in using their email (username) and password.
  - Upon successful authentication, the user receives a JWT access token and a refresh token.

### Data Management with Spring Data JPA
- Data model is implemented using Spring JPA (Java Persistence API).
- Repository interfaces extend `JpaRepository`, providing methods for common database operations.

## Requirements
- **Java Development Kit (JDK):** While the project may work with JDK versions 21 or higher, it is recommended to use the latest stable version of JDK for optimal compatibility and performance.
- **Gradle Build Tool:** is used for project management and dependency resolution.
