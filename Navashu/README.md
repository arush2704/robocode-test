# Navashu - Spring Boot Authentication Microservice

## Project Description

Navashu is a Spring Boot microservice application that provides RESTful API endpoints for user authentication and account management. It supports user sign-up, sign-in, password reset, and password change functionalities with MySQL database integration.

## Features

- **User Sign Up**: Register new users with email validation
- **User Sign In**: Authenticate users with email and password
- **Forgot Password**: Send password reset email with token
- **Reset Password**: Reset password using reset token
- **Change Password**: Change password for authenticated users
- **JWT Token based Authentication**: Secure token generation and validation
- **Email Notifications**: Send welcome and password reset emails
- **Password Encryption**: BCrypt password encoding
- **Error Handling**: Comprehensive exception handling
- **Input Validation**: Request validation with detailed error messages
- **MySQL Database**: Persistent data storage with JPA/Hibernate

## Technology Stack

- **Java**: 22
- **Spring Boot**: 3.2.0
- **Spring Security**: For security
- **Spring Data JPA**: For database operations
- **Hibernate**: ORM framework
- **MySQL**: Database
- **Lombok**: For reducing boilerplate code
- **JWT**: For token-based authentication
- **Maven**: Build tool

## Project Structure

```
Navashu/
├── pom.xml                          # Maven configuration
├── src/
│   ├── main/
│   │   ├── java/org/example/
│   │   │   ├── Main.java           # Spring Boot Application
│   │   │   ├── controller/
│   │   │   │   └── AuthController.java      # REST Endpoints
│   │   │   ├── service/
│   │   │   │   └── AuthService.java         # Business Logic
│   │   │   ├── entity/
│   │   │   │   └── User.java                # JPA Entity
│   │   │   ├── model/                       # DTOs
│   │   │   │   ├── SignUpRequest.java
│   │   │   │   ├── SignInRequest.java
│   │   │   │   ├── ForgotPasswordRequest.java
│   │   │   │   ├── ChangePasswordRequest.java
│   │   │   │   ├── ResetPasswordRequest.java
│   │   │   │   ├── SignInResponse.java
│   │   │   │   └── ApiResponse.java
│   │   │   ├── repository/
│   │   │   │   └── UserRepository.java       # JPA Repository
│   │   │   ├── util/
│   │   │   │   ├── PasswordUtil.java         # Password Encoding
│   │   │   │   ├── JwtUtil.java              # JWT Token Management
│   │   │   │   └── EmailService.java         # Email Notifications
│   │   │   ├── exception/
│   │   │   │   ├── GlobalExceptionHandler.java
│   │   │   │   ├── ResourceNotFoundException.java
│   │   │   │   └── UnauthorizedException.java
│   │   │   └── config/
│   │   │       └── SecurityConfig.java       # Spring Security Configuration
│   │   └── resources/
│   │       ├── application.properties         # Application Configuration
│   │       └── schema.sql                     # Database Schema
│   └── test/                        # Test files
└── README.md                        # This file
```

## Prerequisites

- Java 22 installed
- MySQL Server 8.0+ installed and running
- Maven 3.8.0+ installed
- IDE (IntelliJ IDEA, VS Code, etc.)

## Setup Instructions

### 1. Database Setup

1. Open MySQL command line or MySQL Workbench
2. Execute the SQL script from `src/main/resources/schema.sql`:

```sql
-- Create Database
CREATE DATABASE IF NOT EXISTS navashu_db;
USE navashu_db;

-- Create Users Table
CREATE TABLE IF NOT EXISTS users (
    user_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50),
    email VARCHAR(100) UNIQUE NOT NULL,
    phone_number VARCHAR(15),
    password VARCHAR(255) NOT NULL,
    is_active BOOLEAN DEFAULT TRUE,
    is_email_verified BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    last_login TIMESTAMP,
    password_reset_token VARCHAR(255),
    password_reset_token_expiry TIMESTAMP,
    INDEX idx_email (email),
    INDEX idx_created_at (created_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
```

### 2. Application Configuration

Edit `src/main/resources/application.properties`:

```properties
# MySQL Configuration (Update these if needed)
spring.datasource.url=jdbc:mysql://localhost:3306/navashu_db?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
spring.datasource.username=root
spring.datasource.password=root

# Email Configuration (Optional - Update with your email service)
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=your-email@gmail.com
spring.mail.password=your-app-password
```

### 3. Build and Run

```bash
# Navigate to project directory
cd C:\RDWS\Robo WS\Navashu

# Build the project
mvn clean package

# Run the application
mvn spring-boot:run

# Or run the JAR file
java -jar target/Navashu-1.0-SNAPSHOT.jar
```

The application will start on `http://localhost:8080`

## API Endpoints

### 1. Sign Up
- **URL**: `POST /api/auth/signup`
- **Description**: Register a new user
- **Request Body**:
```json
{
  "firstName": "John",
  "lastName": "Doe",
  "email": "john@example.com",
  "phoneNumber": "1234567890",
  "password": "SecurePassword123!",
  "confirmPassword": "SecurePassword123!"
}
```
- **Response** (Success - 201):
```json
{
  "success": true,
  "message": "Account created successfully. Please log in.",
  "data": 1
}
```
- **Response** (Error - 400):
```json
{
  "success": false,
  "message": "Email already registered"
}
```

### 2. Sign In
- **URL**: `POST /api/auth/signin`
- **Description**: Authenticate user and get JWT token
- **Request Body**:
```json
{
  "email": "john@example.com",
  "password": "SecurePassword123!"
}
```
- **Response** (Success - 200):
```json
{
  "success": true,
  "message": "Login successful",
  "data": {
    "userId": 1,
    "firstName": "John",
    "lastName": "Doe",
    "email": "john@example.com",
    "phoneNumber": "1234567890",
    "token": "MTo2OmpvaG5AZXhhbXBsZS5jb206MTcxNjEyMzQ1Njc4OQ==",
    "success": true,
    "message": "Login successful"
  }
}
```
- **Response** (Error - 401):
```json
{
  "success": false,
  "message": "Invalid email or password"
}
```

### 3. Forgot Password
- **URL**: `POST /api/auth/forgot-password`
- **Description**: Request password reset email
- **Request Body**:
```json
{
  "email": "john@example.com"
}
```
- **Response** (200):
```json
{
  "success": true,
  "message": "If the email exists in our system, you will receive a password reset link"
}
```

### 4. Reset Password
- **URL**: `POST /api/auth/reset-password`
- **Description**: Reset password using token from email
- **Request Body**:
```json
{
  "token": "reset-token-from-email",
  "newPassword": "NewPassword123!",
  "confirmPassword": "NewPassword123!"
}
```
- **Response** (Success - 200):
```json
{
  "success": true,
  "message": "Password reset successfully. Please log in with your new password"
}
```
- **Response** (Error - 400):
```json
{
  "success": false,
  "message": "Reset token has expired"
}
```

### 5. Change Password
- **URL**: `PUT /api/auth/change-password/{userId}`
- **Description**: Change password for authenticated user
- **Path Parameter**: `userId` - The user ID
- **Request Body**:
```json
{
  "currentPassword": "SecurePassword123!",
  "newPassword": "NewPassword456!",
  "confirmPassword": "NewPassword456!"
}
```
- **Response** (Success - 200):
```json
{
  "success": true,
  "message": "Password changed successfully"
}
```
- **Response** (Error - 400):
```json
{
  "success": false,
  "message": "Current password is incorrect"
}
```

### 6. Health Check
- **URL**: `GET /api/auth/health`
- **Description**: Check if the authentication service is running
- **Response** (200):
```json
{
  "success": true,
  "message": "Authentication service is running"
}
```

## Testing with Postman

1. **Import API requests into Postman**
2. **Sign Up**: Create a new user
3. **Sign In**: Get JWT token
4. **Change Password**: Use the token and user ID
5. **Forgot Password**: Request reset
6. **Reset Password**: Use the token from email

## Entity Details

### User Entity
The User table contains the following fields:
- `user_id` (BIGINT): Primary key, auto-increment
- `first_name` (VARCHAR): User's first name
- `last_name` (VARCHAR): User's last name
- `email` (VARCHAR): User's email (unique)
- `phone_number` (VARCHAR): User's phone number
- `password` (VARCHAR): Encrypted password
- `is_active` (BOOLEAN): Account status
- `is_email_verified` (BOOLEAN): Email verification status
- `created_at` (TIMESTAMP): Account creation time
- `updated_at` (TIMESTAMP): Last update time
- `last_login` (TIMESTAMP): Last login time
- `password_reset_token` (VARCHAR): Password reset token
- `password_reset_token_expiry` (TIMESTAMP): Token expiry time

## Security Features

- **Password Encryption**: All passwords are encrypted using BCrypt
- **JWT Token Authentication**: Secure token-based authentication
- **Input Validation**: All inputs are validated
- **CORS Enabled**: Cross-Origin requests allowed
- **Email Verification**: Optional email verification for new accounts
- **Token Expiry**: Password reset tokens expire after 24 hours
- **SQL Injection Prevention**: Parameterized queries using JPA

## Error Handling

The application provides comprehensive error handling with meaningful error messages. Common errors include:

- `Email already registered` (400): User tries to register with existing email
- `Invalid email or password` (401): Wrong credentials
- `Reset token has expired` (400): Password reset token expired
- `Passwords do not match` (400): Confirmation password doesn't match
- `Validation failed` (400): Invalid input parameters

## Configuration Properties

### Database Properties
```properties
spring.datasource.url              # Database URL
spring.datasource.username         # Database username
spring.datasource.password         # Database password
spring.jpa.hibernate.ddl-auto      # Hibernate DDL mode (create/update/validate)
```

### Email Properties
```properties
spring.mail.host                   # SMTP host
spring.mail.port                   # SMTP port
spring.mail.username               # Email username
spring.mail.password               # Email password
```

## Dependencies

All dependencies are managed by Maven. Key dependencies:
- Spring Boot Starter Web
- Spring Boot Starter Data JPA
- Spring Boot Starter Security
- Spring Boot Starter Mail
- MySQL Connector Java
- Lombok
- JWT (jjwt)

## Future Enhancements

- OAuth2 integration
- Two-factor authentication
- Email verification confirmation
- User profile management
- Admin dashboard
- User roles and permissions
- API documentation with Swagger

## Troubleshooting

### Issue: Cannot connect to MySQL
**Solution**: 
- Verify MySQL server is running
- Check database URL, username, and password in `application.properties`
- Ensure database exists: `CREATE DATABASE navashu_db;`

### Issue: Email not sending
**Solution**:
- Configure SMTP settings in `application.properties`
- For Gmail, use app-specific password
- Enable "Less secure app access" (if not using app password)

### Issue: Cannot run the application
**Solution**:
- Ensure Java 22 is installed: `java -version`
- Verify Maven is installed: `mvn -version`
- Run: `mvn clean install` before `mvn spring-boot:run`

## Support

For issues or questions, please refer to the Spring Boot documentation:
- https://spring.io/projects/spring-boot
- https://spring.io/projects/spring-data-jpa

## License

This project is provided as-is for educational purposes.

## Author

Navashu Development Team

