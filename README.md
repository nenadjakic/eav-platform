# eav-platform

**eav-platform** is an application designed for managing data using the Entity-Attribute-Value (EAV) model. This application allows adding, editing, and deleting entities, attributes, and values in accordance with the EAV data model. It utilizes JWT with refresh token for authentication and access control for users, and also implements security at the attribute level.

## Features
- EAV Model Management
- JWT Authentication with Refresh Token
- Email Confirmation Mechanism
- Security at row-Level

## Features (details)

### Security: Authentication & Authorization
- Uses JWT tokens for user authentication and authorization.
- Implements refresh tokens to extend JWT validity without requiring reauthentication.
- Provides enhanced security by reducing token exposure and improving session management.
- Role-Based Access Control (RBAC)
- Row-Level Access Control (RLAC)
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
- **_Role-Based Access Control (RBAC) with row-level access control (RLAC)_**:
  - Application leverages Role-Based Access Control (RBAC) to manage user permissions and access levels effectively
  - application implements Row-Level Access Control (RLAC) to protect individual rows or records in the database based on specific attributes.
### Data Management with Spring Data JPA
- Data model is implemented using Spring JPA (Java Persistence API).
- Repository interfaces extend `JpaRepository`, providing methods for common database operations.
- For enhanced performance and optimized data retrieval, `Entity graphs` is leveraged in certain scenarios.

## Requirements
- **Java Development Kit (JDK):** While the project may work with JDK versions 21 or higher, it is recommended to use the latest stable version of JDK for optimal compatibility and performance.
- **Gradle Build Tool** is used for project management and dependency resolution.

## Build and Run
1. **Clone this repository:**

   `git clone https://github.com/nenadjakic/eav-platform.git`

   `cd eav-platform`
2. **Create a new database for the project.**

    `eav-platform` uses ORM (`Hibernate`) so you can choose any relational database engine which is supported by `Hibernate` (e.g., MySQL, PostgreSQL, Oracle).
3. **Configure database connection:**
   - Open the `application.properties` file located in src/main/resources.
   - Update the database connection settings (URL, username, password, dialect).
4. **Open a terminal and navigate to the project directory.**
5. **Build the project using Gradle:**

   `./gradlew clean build`
6. **Run the project using Gradle:**

   `./gradlew bootRun`

## Dockerize Application
Follow these steps to dockerize and run your application using Docker and Docker Compose:

1. **Installing Docker and Docker Compose:**

   First, make sure Docker and Docker Compose are installed on your machine. If not, you can follow these guides:
    - [Docker Install Guide](https://docs.docker.com/get-docker/)
    - [Docker Compose Install Guide](https://docs.docker.com/compose/install/)
2. **Running Application with Docker Compose:**

   Use Docker Compose to build and run your application in a container:

   `docker compose up`
