# Quick Start Guide - Navashu Authentication Microservice

## Step 1: Prerequisites Installation

### Install Java 22
- Download from: https://www.oracle.com/java/technologies/downloads/
- Verify installation: `java -version`
- Expected output: openjdk version "22" or higher

### Install Maven
- Download from: https://maven.apache.org/download.cgi
- Extract to a location (e.g., C:\Maven)
- Add to System PATH: `C:\Maven\bin`
- Verify installation: `mvn -version`

### Install MySQL 8.0+
- Download from: https://dev.mysql.com/downloads/mysql/
- Install and remember your root password
- Verify installation: Connect to MySQL command line

### Install Git (Optional but recommended)
- Download from: https://git-scm.com/download/win

## Step 2: Database Setup

### Using MySQL Command Line:
```bash
mysql -u root -p
```

Enter your password, then execute:
```sql
CREATE DATABASE IF NOT EXISTS navashu_db;
USE navashu_db;

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

Or use MySQL Workbench to run the schema.sql file.

### Verify Database Creation:
```sql
SHOW DATABASES;
USE navashu_db;
SHOW TABLES;
DESCRIBE users;
```

## Step 3: Update Application Configuration

Edit `src/main/resources/application.properties`:

### Database Configuration (Update if needed):
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/navashu_db?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
spring.datasource.username=root
spring.datasource.password=root
```

### Email Configuration (Optional):
For Gmail:
1. Enable 2-factor authentication
2. Generate App Password at https://myaccount.google.com/apppasswords
3. Update in application.properties:
```properties
spring.mail.username=your-email@gmail.com
spring.mail.password=your-app-password
```

## Step 4: Build the Application

```bash
# Navigate to project directory
cd "C:\RDWS\Robo WS\Navashu"

# Clean and build
mvn clean package -DskipTests

# Or just clean install
mvn clean install
```

## Step 5: Run the Application

### Option 1: Using Maven
```bash
mvn spring-boot:run
```

### Option 2: Using JAR file
```bash
java -jar target/Navashu-1.0-SNAPSHOT.jar
```

### Expected Output:
```
...
2024-05-18 10:30:00.000  INFO 1234 --- [main] o.example.Main: Starting Main v1.0-SNAPSHOT
2024-05-18 10:30:05.000  INFO 1234 --- [main] o.example.Main: Started Main in 5.123 seconds
```

The application will be running at: **http://localhost:8080**

## Step 6: Test API Endpoints

### Using Postman:

#### 1. Sign Up
```
POST http://localhost:8080/api/auth/signup
Content-Type: application/json

{
  "firstName": "John",
  "lastName": "Doe",
  "email": "john@example.com",
  "phoneNumber": "1234567890",
  "password": "Test@1234567",
  "confirmPassword": "Test@1234567"
}
```

#### 2. Sign In
```
POST http://localhost:8080/api/auth/signin
Content-Type: application/json

{
  "email": "john@example.com",
  "password": "Test@1234567"
}
```

#### 3. Health Check
```
GET http://localhost:8080/api/auth/health
```

#### 4. Change Password
```
PUT http://localhost:8080/api/auth/change-password/1
Content-Type: application/json

{
  "currentPassword": "Test@1234567",
  "newPassword": "NewTest@1234567",
  "confirmPassword": "NewTest@1234567"
}
```

#### 5. Forgot Password
```
POST http://localhost:8080/api/auth/forgot-password
Content-Type: application/json

{
  "email": "john@example.com"
}
```

### Using cURL:

```bash
# Test Health
curl http://localhost:8080/api/auth/health

# Sign Up
curl -X POST http://localhost:8080/api/auth/signup \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "Jane",
    "lastName": "Smith",
    "email": "jane@example.com",
    "phoneNumber": "9876543210",
    "password": "SecurePass@123",
    "confirmPassword": "SecurePass@123"
  }'

# Sign In
curl -X POST http://localhost:8080/api/auth/signin \
  -H "Content-Type: application/json" \
  -d '{
    "email": "jane@example.com",
    "password": "SecurePass@123"
  }'
```

## Step 7: Verify Database Records

After signing up users, verify in MySQL:

```sql
USE navashu_db;
SELECT * FROM users;
```

You should see the registered users with encrypted passwords.

## Troubleshooting

### Problem: Port 8080 already in use
**Solution**: Change port in application.properties
```properties
server.port=8081
```

### Problem: Cannot connect to database
**Solution**:
1. Verify MySQL is running
2. Check credentials in application.properties
3. Ensure database exists: `SHOW DATABASES;`
4. Check firewall settings

### Problem: Email not sending
**Solution**:
1. Configure SMTP settings correctly
2. For Gmail, use app-specific password, not regular password
3. Enable "Less secure app access" if not using 2FA
4. Add email configuration to application.properties

### Problem: Maven command not found
**Solution**:
1. Download Maven from official site
2. Add Maven bin to System PATH
3. Restart terminal/IDE
4. Verify: `mvn -version`

### Problem: Build fails with Java compilation errors
**Solution**:
1. Verify Java 22 is installed: `java -version`
2. Set JAVA_HOME environment variable
3. Run: `mvn clean install`

## Project Structure Overview

```
Navashu/
├── pom.xml                      # Maven dependencies & build config
├── README.md                    # Detailed documentation
├── QUICKSTART.md               # This file
├── .env.example                # Environment variables template
├── src/
│   ├── main/
│   │   ├── java/org/example/
│   │   │   ├── Main.java                    # Application entry point
│   │   │   ├── controller/
│   │   │   │   └── AuthController.java      # REST API endpoints
│   │   │   ├── service/
│   │   │   │   └── AuthService.java         # Business logic
│   │   │   ├── entity/
│   │   │   │   └── User.java                # JPA entity
│   │   │   ├── model/                       # DTOs for requests/responses
│   │   │   │   ├── SignUpRequest.java
│   │   │   │   ├── SignInRequest.java
│   │   │   │   ├── ForgotPasswordRequest.java
│   │   │   │   ├── ChangePasswordRequest.java
│   │   │   │   ├── ResetPasswordRequest.java
│   │   │   │   ├── SignInResponse.java
│   │   │   │   ├── ApiResponse.java
│   │   │   │   └── UserDTO.java
│   │   │   ├── repository/
│   │   │   │   └── UserRepository.java      # Database queries
│   │   │   ├── util/
│   │   │   │   ├── PasswordUtil.java        # Password encryption
│   │   │   │   ├── JwtUtil.java             # JWT token handling
│   │   │   │   └── EmailService.java        # Email notifications
│   │   │   ├── exception/
│   │   │   │   ├── GlobalExceptionHandler.java
│   │   │   │   ├── ResourceNotFoundException.java
│   │   │   │   └── UnauthorizedException.java
│   │   │   └── config/
│   │   │       └── SecurityConfig.java      # Spring Security settings
│   │   └── resources/
│   │       ├── application.properties       # Application config
│   │       └── schema.sql                   # Database schema
│   └── test/                   # Test files (to be implemented)
└── target/                     # Build output (created after build)
```

## Key Features Implemented

✅ User Registration (Sign Up)
✅ User Login (Sign In)
✅ Password Change (for authenticated users)
✅ Forgot Password (with email reset)
✅ Password Reset (using token)
✅ Email Notifications
✅ JWT Token Authentication
✅ Password Encryption (BCrypt)
✅ Input Validation
✅ Error Handling
✅ CORS Support
✅ MySQL Database Integration

## API Response Format

All endpoints return JSON with this format:

**Success Response:**
```json
{
  "success": true,
  "message": "Operation successful",
  "data": {}
}
```

**Error Response:**
```json
{
  "success": false,
  "message": "Error description",
  "data": null
}
```

## Next Steps

1. ✅ Setup database and application
2. ✅ Run the application
3. ✅ Test API endpoints
4. ⬜ Implement Frontend UI
5. ⬜ Add Unit Tests
6. ⬜ Add Integration Tests
7. ⬜ Deploy to Production

## Additional Resources

- Spring Boot Documentation: https://spring.io/projects/spring-boot
- Spring Data JPA: https://spring.io/projects/spring-data-jpa
- MySQL Documentation: https://dev.mysql.com/doc/
- Postman: https://www.postman.com/

## Support

For detailed API documentation, see: `README.md`
For project architecture details, check the individual class documentation.

## Version Info

- Spring Boot: 3.2.0
- Java: 22
- MySQL: 8.0+
- Maven: 3.8.0+

---

**Happy Coding! 🚀**

