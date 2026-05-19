# Navashu Project Structure

## Complete Directory Tree

```
SkillTest/
│
├── src/
│   ├── main/
│   │   ├── java/org/example/
│   │   │   ├── (Other existing classes)
│   │   │   └── navashu/
│   │   │       ├── NavaashuApplication.java          # Main Spring Boot Application
│   │   │       │
│   │   │       ├── config/
│   │   │       │   └── SecurityConfig.java           # Security configuration (BCrypt)
│   │   │       │
│   │   │       ├── controller/
│   │   │       │   └── AuthController.java           # REST API Controller
│   │   │       │       ├── POST /api/auth/sign-up
│   │   │       │       ├── POST /api/auth/sign-in
│   │   │       │       ├── POST /api/auth/forgot-password
│   │   │       │       ├── POST /api/auth/reset-password
│   │   │       │       ├── POST /api/auth/change-password/{userId}
│   │   │       │       └── GET  /api/auth/health
│   │   │       │
│   │   │       ├── dto/
│   │   │       │   ├── SignUpRequest.java            # Registration DTO
│   │   │       │   ├── SignInRequest.java            # Login DTO
│   │   │       │   ├── ForgotPasswordRequest.java    # Password recovery request
│   │   │       │   ├── ResetPasswordRequest.java     # Password reset DTO
│   │   │       │   ├── ChangePasswordRequest.java    # Change password DTO
│   │   │       │   ├── UserResponse.java             # User response DTO
│   │   │       │   └── ApiResponse.java              # Generic API response wrapper
│   │   │       │
│   │   │       ├── entity/
│   │   │       │   └── User.java                     # JPA Entity for users table
│   │   │       │       ├── id (PK)
│   │   │       │       ├── email (UNIQUE)
│   │   │       │       ├── firstName
│   │   │       │       ├── lastName
│   │   │       │       ├── password (encrypted)
│   │   │       │       ├── phoneNumber
│   │   │       │       ├── isActive
│   │   │       │       ├── createdAt
│   │   │       │       ├── updatedAt
│   │   │       │       ├── passwordResetToken
│   │   │       │       └── passwordResetTokenExpiry
│   │   │       │
│   │   │       ├── exception/
│   │   │       │   ├── UserAlreadyExistsException.java      # Custom exception
│   │   │       │   ├── UserNotFoundException.java           # Custom exception
│   │   │       │   ├── InvalidTokenException.java           # Custom exception
│   │   │       │   └── GlobalExceptionHandler.java          # Global exception handler
│   │   │       │
│   │   │       ├── repository/
│   │   │       │   └── UserRepository.java           # JPA Repository
│   │   │       │       ├── findByEmail()
│   │   │       │       ├── existsByEmail()
│   │   │       │       └── findByPasswordResetToken()
│   │   │       │
│   │   │       ├── service/
│   │   │       │   ├── AuthService.java              # Service Interface
│   │   │       │   │   ├── signUp()
│   │   │       │   │   ├── signIn()
│   │   │       │   │   ├── forgotPassword()
│   │   │       │   │   ├── resetPassword()
│   │   │       │   │   ├── changePassword()
│   │   │       │   │   ├── getUserByEmail()
│   │   │       │   │   └── getUserById()
│   │   │       │   │
│   │   │       │   └── impl/
│   │   │       │       └── AuthServiceImpl.java       # Service Implementation
│   │   │       │
│   │   │       └── util/
│   │   │           └── JwtUtil.java                  # JWT Token utility
│   │   │               ├── generateToken()
│   │   │               └── generatePasswordResetToken()
│   │   │
│   │   └── resources/
│   │       └── application.properties                # Configuration file
│   │           ├── Server settings
│   │           ├── MySQL connection
│   │           ├── JPA/Hibernate settings
│   │           ├── JWT configuration
│   │           └── Logging configuration
│   │
│   └── test/
│       └── java/org/example/
│           └── navashu/
│               └── service/
│                   └── AuthServiceTest.java          # Unit tests for AuthService
│
├── docker-compose.yml                               # Docker Compose configuration
├── init.sql                                         # Database initialization script
├── pom.xml                                          # Maven configuration
│
├── NAVASHU_README.md                                # Project overview and setup
├── NAVASHU_SETUP_GUIDE.md                           # Detailed setup instructions
├── NAVASHU_API_DOCUMENTATION.md                     # Complete API documentation
└── NAVASHU_DOCKER_GUIDE.md                          # Docker setup guide
```

## Database Schema

### Users Table
```sql
CREATE TABLE users (
  id                           BIGINT (PK)
  email                        VARCHAR(100) UNIQUE NOT NULL
  first_name                   VARCHAR(100) NOT NULL
  last_name                    VARCHAR(100) NOT NULL
  password                     VARCHAR(255) NOT NULL
  phone_number                 VARCHAR(15) NOT NULL
  is_active                    BOOLEAN DEFAULT true
  created_at                   TIMESTAMP
  updated_at                   TIMESTAMP
  password_reset_token         VARCHAR(500)
  password_reset_token_expiry  TIMESTAMP
)
```

## Maven Dependencies

```
├── Spring Boot Starters
│   ├── spring-boot-starter-web        # Web and REST support
│   ├── spring-boot-starter-data-jpa   # Database JPA support
│   ├── spring-boot-starter-security   # Security support
│   ├── spring-boot-starter-mail       # Email support
│   └── spring-boot-starter-validation # Input validation
│
├── Database
│   └── mysql-connector-java          # MySQL driver
│
├── JWT
│   ├── jjwt-api                      # JWT API
│   ├── jjwt-impl                     # JWT implementation
│   └── jjwt-jackson                  # JWT Jackson support
│
├── Development
│   └── lombok                        # Boilerplate reduction
│
└── Testing
    ├── junit
    ├── junit-jupiter
    └── spring-boot-starter-test
```

## Application Layers

### 1. Controller Layer (REST Endpoints)
- `AuthController.java` - Handles all HTTP requests
- Validates input using DTOs
- Returns standardized API responses

### 2. Service Layer (Business Logic)
- `AuthService.java` - Interface defining operations
- `AuthServiceImpl.java` - Implementation of business logic
- Handles authentication, registration, and password management

### 3. Repository Layer (Data Access)
- `UserRepository.java` - JPA repository for database operations
- Custom Query Methods:
  - `findByEmail()` - Find user by email
  - `existsByEmail()` - Check if email exists
  - `findByPasswordResetToken()` - Find user by reset token

### 4. Entity Layer (Database Models)
- `User.java` - JPA entity representing users table
- Annotations for column mapping and constraints

### 5. DTO Layer (Data Transfer)
- Request DTOs: SignUpRequest, SignInRequest, etc.
- Response DTOs: UserResponse, ApiResponse
- Validation annotations for input validation

### 6. Exception Layer (Error Handling)
- Custom Exceptions: UserAlreadyExistsException, UserNotFoundException, InvalidTokenException
- `GlobalExceptionHandler.java` - Centralized exception handling

### 7. Configuration Layer
- `SecurityConfig.java` - Security and password encoding configuration

### 8. Utility Layer
- `JwtUtil.java` - JWT token generation and validation

## API Endpoints Summary

| Method | Endpoint | Purpose |
|--------|----------|---------|
| POST | /api/auth/sign-up | User registration |
| POST | /api/auth/sign-in | User login |
| POST | /api/auth/forgot-password | Initiate password reset |
| POST | /api/auth/reset-password | Reset password with token |
| POST | /api/auth/change-password/{userId} | Change password (authenticated) |
| GET | /api/auth/health | Health check |

## Technology Stack

```
Framework: Spring Boot 3.2.0
├── Web: Spring Web
├── Data: Spring Data JPA
├── Security: Spring Security + JWT
├── Validation: Jakarta Bean Validation
└── ORM: Hibernate

Database: MySQL 8.0+

Build: Maven 3.6+

Language: Java 17+

Development: Lombok

Testing: JUnit 5, Mockito

Containers: Docker, Docker Compose

IDE: JetBrains IntelliJ IDEA (recommended)
```

## Authentication Flow

```
User Registration Flow:
┌─────────┐
│ Sign Up │ POST /api/auth/sign-up
└────┬────┘
     │ SignUpRequest (email, firstName, lastName, password, phone)
     ▼
┌─────────────────────────┐
│ AuthController          │
└────┬────────────────────┘
     │
     ▼
┌─────────────────────────┐
│ AuthService.signUp()    │
└────┬────────────────────┘
     │ Check if user exists
     │ Encode password (BCrypt)
     │ Save to database
     │ Generate JWT token
     ▼
┌─────────────────────────┐
│ User Response           │ UserResponse (id, email, token, ...)
└─────────────────────────┘

User Login Flow:
┌──────────┐
│ Sign In  │ POST /api/auth/sign-in
└────┬─────┘
     │ SignInRequest (email, password)
     ▼
┌─────────────────────────┐
│ AuthController          │
└────┬────────────────────┘
     │
     ▼
┌─────────────────────────┐
│ AuthService.signIn()    │
└────┬────────────────────┘
     │ Find user by email
     │ Verify password (BCrypt match)
     │ Check if active
     │ Generate JWT token
     ▼
┌─────────────────────────┐
│ User Response           │ UserResponse (id, email, token, ...)
└─────────────────────────┘

Password Reset Flow:
┌───────────────────────┐
│ Forgot Password       │ POST /api/auth/forgot-password
└────┬──────────────────┘
     │ ForgotPasswordRequest (email)
     ▼
┌─────────────────────────┐
│ AuthService             │
└────┬────────────────────┘
     │ Find user
     │ Generate reset token (1 hour expiry)
     │ Save token to database
     │ Send email (optional)
     ▼
┌─────────────────────────┐
│ Reset Link Response     │
└─────────────────────────┘
     │
     ▼
┌───────────────────────┐
│ Reset Password        │ POST /api/auth/reset-password
└────┬──────────────────┘
     │ ResetPasswordRequest (token, newPassword, confirmPassword)
     ▼
┌─────────────────────────┐
│ AuthService             │
└────┬────────────────────┘
     │ Verify token
     │ Check expiry
     │ Validate passwords match
     │ Encode new password
     │ Update database
     ▼
┌─────────────────────────┐
│ Success Response        │
└─────────────────────────┘
```

## Security Features

1. **Password Encryption**: BCrypt with strength 12
2. **JWT Tokens**: 24-hour expiry for authentication
3. **Reset Tokens**: 1-hour expiry for password recovery
4. **Input Validation**: Jakarta Bean Validation on all DTOs
5. **Exception Handling**: Consistent error response format
6. **CORS**: Enabled for cross-origin requests

## Configuration Files

### application.properties
- Server port configuration
- MySQL connection details
- JPA/Hibernate settings
- JWT configuration
- Email configuration (optional)
- Logging levels

### docker-compose.yml
- MySQL 8.0 service
- PhpMyAdmin for database management
- Environment variables
- Volume mounting
- Network configuration

### pom.xml
- Spring Boot parent POM
- All project dependencies
- Maven plugins configuration
- Build configuration

### init.sql
- Database initialization script
- Table creation
- Index creation

## File Statistics

```
Total Java Classes: 16
├── Entities: 1
├── DTOs: 6
├── Controllers: 1
├── Services: 2
├── Repositories: 1
├── Exceptions: 4
├── Configs: 1
├── Utils: 1
└── Tests: 1

Configuration Files: 4
├── application.properties
├── pom.xml
├── docker-compose.yml
└── init.sql

Documentation Files: 4
├── NAVASHU_README.md
├── NAVASHU_SETUP_GUIDE.md
├── NAVASHU_API_DOCUMENTATION.md
└── NAVASHU_DOCKER_GUIDE.md
```

## Development Guidelines

### Coding Standards
- Use Spring Boot best practices
- Follow REST API conventions
- Implement proper exception handling
- Use meaningful variable and method names
- Add Javadoc for public methods

### Testing
- Write unit tests for services
- Use Mockito for mocking
- Test edge cases and exceptions
- Achieve high code coverage

### Git Workflow
- Create feature branches for new features
- Use meaningful commit messages
- Create pull requests for review
- Follow semantic versioning

## Project Phases

### Phase 1: Core Setup ✅
- Spring Boot project setup
- MySQL database configuration
- Basic entity and repository

### Phase 2: Authentication ✅
- User registration (Sign Up)
- User login (Sign In)
- JWT token generation

### Phase 3: Password Management ✅
- Forgot password functionality
- Password reset with token
- Change password for logged-in users

### Phase 4: Future Enhancements (Optional)
- Email notifications
- OAuth2 integration
- Multi-factor authentication
- Role-based access control
- Audit logging

## Performance Considerations

- Database indexing on frequently queried columns
- Connection pooling with HikariCP
- JWT for stateless authentication
- Caching strategies
- Query optimization

## Security Checklist

- ✅ Passwords encrypted with BCrypt
- ✅ JWT tokens with expiry
- ✅ Input validation on all endpoints
- ✅ CORS configuration
- ✅ Exception handling
- ⚠️ HTTPS in production (not configured)
- ⚠️ Rate limiting (future)
- ⚠️ Audit logging (optional)

---

**Last Updated**: May 2024  
**Version**: 1.0.0  
**Status**: Production Ready (with security enhancements for production)

