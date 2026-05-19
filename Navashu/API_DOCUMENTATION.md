# API Documentation - Navashu Authentication Service

## Base URL
```
http://localhost:8080/api/auth
```

## Headers
All requests should include:
```
Content-Type: application/json
```

---

## Endpoints

### 1. Sign Up - Create New User Account

**Endpoint:** `POST /api/auth/signup`

**Purpose:** Register a new user with email and password

**Request Body:**
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

**Request Fields:**
| Field | Type | Required | Validation |
|-------|------|----------|-----------|
| firstName | String | Yes | 2-50 characters |
| lastName | String | No | Max 50 characters |
| email | String | Yes | Valid email format, unique |
| phoneNumber | String | Yes | 10-15 digits |
| password | String | Yes | Min 8 characters |
| confirmPassword | String | Yes | Must match password |

**Success Response (201 Created):**
```json
{
  "success": true,
  "message": "Account created successfully. Please log in.",
  "data": 1
}
```

**Error Responses:**

400 - Email already registered:
```json
{
  "success": false,
  "message": "Email already registered"
}
```

400 - Passwords don't match:
```json
{
  "success": false,
  "message": "Passwords do not match"
}
```

400 - Validation failed:
```json
{
  "success": false,
  "message": "Validation failed",
  "errors": {
    "email": "Email should be valid",
    "password": "Password must be at least 8 characters long"
  }
}
```

**cURL Example:**
```bash
curl -X POST http://localhost:8080/api/auth/signup \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "John",
    "lastName": "Doe",
    "email": "john@example.com",
    "phoneNumber": "1234567890",
    "password": "SecurePassword123!",
    "confirmPassword": "SecurePassword123!"
  }'
```

**Postman Example:**
```
URL: POST http://localhost:8080/api/auth/signup
Headers: Content-Type: application/json
Body (raw):
{
  "firstName": "John",
  "lastName": "Doe",
  "email": "john@example.com",
  "phoneNumber": "1234567890",
  "password": "SecurePassword123!",
  "confirmPassword": "SecurePassword123!"
}
```

---

### 2. Sign In - Authenticate User

**Endpoint:** `POST /api/auth/signin`

**Purpose:** Authenticate user and receive JWT token

**Request Body:**
```json
{
  "email": "john@example.com",
  "password": "SecurePassword123!"
}
```

**Request Fields:**
| Field | Type | Required | Validation |
|-------|------|----------|-----------|
| email | String | Yes | Valid email format |
| password | String | Yes | Min 8 characters |

**Success Response (200 OK):**
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

**Error Response (401 Unauthorized):**
```json
{
  "success": false,
  "message": "Invalid email or password"
}
```

**Response Fields:**
| Field | Type | Description |
|-------|------|-------------|
| userId | Long | User ID |
| firstName | String | User's first name |
| lastName | String | User's last name |
| email | String | User's email |
| phoneNumber | String | User's phone number |
| token | String | JWT authentication token |
| success | Boolean | Operation status |
| message | String | Status message |

**cURL Example:**
```bash
curl -X POST http://localhost:8080/api/auth/signin \
  -H "Content-Type: application/json" \
  -d '{
    "email": "john@example.com",
    "password": "SecurePassword123!"
  }'
```

**Important:** Save the `token` from the response for use in authenticated endpoints.

---

### 3. Forgot Password - Request Password Reset

**Endpoint:** `POST /api/auth/forgot-password`

**Purpose:** Request password reset link via email

**Request Body:**
```json
{
  "email": "john@example.com"
}
```

**Request Fields:**
| Field | Type | Required | Validation |
|-------|------|----------|-----------|
| email | String | Yes | Valid email format |

**Success Response (200 OK):**
```json
{
  "success": true,
  "message": "If the email exists in our system, you will receive a password reset link"
}
```

**Note:** Response is always positive for security (doesn't reveal if email exists)

**cURL Example:**
```bash
curl -X POST http://localhost:8080/api/auth/forgot-password \
  -H "Content-Type: application/json" \
  -d '{
    "email": "john@example.com"
  }'
```

**Email Contains:**
- Reset link with token
- Token expiration time (24 hours)
- Instructions for resetting password

---

### 4. Reset Password - Reset Password with Token

**Endpoint:** `POST /api/auth/reset-password`

**Purpose:** Reset password using token from email

**Request Body:**
```json
{
  "token": "reset-token-from-email",
  "newPassword": "NewPassword123!",
  "confirmPassword": "NewPassword123!"
}
```

**Request Fields:**
| Field | Type | Required | Validation |
|-------|------|----------|-----------|
| token | String | Yes | Valid reset token from email |
| newPassword | String | Yes | Min 8 characters |
| confirmPassword | String | Yes | Must match newPassword |

**Success Response (200 OK):**
```json
{
  "success": true,
  "message": "Password reset successfully. Please log in with your new password"
}
```

**Error Responses:**

400 - Passwords don't match:
```json
{
  "success": false,
  "message": "Passwords do not match"
}
```

400 - Token expired:
```json
{
  "success": false,
  "message": "Reset token has expired"
}
```

400 - Invalid token:
```json
{
  "success": false,
  "message": "Invalid or expired reset token"
}
```

**cURL Example:**
```bash
curl -X POST http://localhost:8080/api/auth/reset-password \
  -H "Content-Type: application/json" \
  -d '{
    "token": "the-token-from-email",
    "newPassword": "NewPassword123!",
    "confirmPassword": "NewPassword123!"
  }'
```

---

### 5. Change Password - Change Password for Authenticated User

**Endpoint:** `PUT /api/auth/change-password/{userId}`

**Purpose:** Change password for logged-in user

**URL Parameters:**
| Parameter | Type | Required | Description |
|-----------|------|----------|-------------|
| userId | Long | Yes | User ID (from login response) |

**Request Body:**
```json
{
  "currentPassword": "SecurePassword123!",
  "newPassword": "NewPassword456!",
  "confirmPassword": "NewPassword456!"
}
```

**Request Fields:**
| Field | Type | Required | Validation |
|-------|------|----------|-----------|
| currentPassword | String | Yes | Current user password |
| newPassword | String | Yes | Min 8 characters |
| confirmPassword | String | Yes | Must match newPassword |

**Success Response (200 OK):**
```json
{
  "success": true,
  "message": "Password changed successfully"
}
```

**Error Responses:**

400 - Passwords don't match:
```json
{
  "success": false,
  "message": "Passwords do not match"
}
```

400 - Current password incorrect:
```json
{
  "success": false,
  "message": "Current password is incorrect"
}
```

404 - User not found:
```json
{
  "success": false,
  "message": "User not found"
}
```

**cURL Example:**
```bash
# Replace 1 with actual user ID
curl -X PUT http://localhost:8080/api/auth/change-password/1 \
  -H "Content-Type: application/json" \
  -d '{
    "currentPassword": "SecurePassword123!",
    "newPassword": "NewPassword456!",
    "confirmPassword": "NewPassword456!"
  }'
```

---

### 6. Health Check - Service Status

**Endpoint:** `GET /api/auth/health`

**Purpose:** Verify authentication service is running

**Request Body:** None

**Success Response (200 OK):**
```json
{
  "success": true,
  "message": "Authentication service is running"
}
```

**cURL Example:**
```bash
curl http://localhost:8080/api/auth/health
```

---

## Common Error Codes

| Code | Error | Description |
|------|-------|-------------|
| 200 | OK | Successful request |
| 201 | Created | Resource created successfully |
| 400 | Bad Request | Invalid input or validation error |
| 401 | Unauthorized | Authentication failed |
| 404 | Not Found | Resource not found |
| 500 | Internal Server Error | Server error |

## Response Status Guide

**2xx Success**
- 200: Request successful with response
- 201: Resource created successfully

**4xx Client Error**
- 400: Invalid input, validation failed
- 401: Authentication failed, wrong credentials
- 404: User or resource not found

**5xx Server Error**
- 500: Internal server error

---

## Authentication & Security

### Password Requirements
- Minimum 8 characters
- Can contain letters, numbers, and special characters
- Passwords are encrypted with BCrypt before storage
- Never stored in plain text

### Token Management
- JWT tokens are generated on successful login
- Tokens are valid for 24 hours
- Include token in Authorization header for future authenticated requests
- Token format: Bearer [token]

### Password Reset Token
- Generated when 'Forgot Password' is requested
- Valid for 24 hours only
- One-time use only
- Sent via email

---

## Request/Response Examples

### Complete Sign Up Flow

```bash
# 1. Sign Up
curl -X POST http://localhost:8080/api/auth/signup \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "Alice",
    "lastName": "Johnson",
    "email": "alice@example.com",
    "phoneNumber": "9876543210",
    "password": "SecurePass123!",
    "confirmPassword": "SecurePass123!"
  }'

# 2. Sign In
curl -X POST http://localhost:8080/api/auth/signin \
  -H "Content-Type: application/json" \
  -d '{
    "email": "alice@example.com",
    "password": "SecurePass123!"
  }'
# Note: Save the token from response

# 3. Change Password
curl -X PUT http://localhost:8080/api/auth/change-password/1 \
  -H "Content-Type: application/json" \
  -d '{
    "currentPassword": "SecurePass123!",
    "newPassword": "NewSecurePass456!",
    "confirmPassword": "NewSecurePass456!"
  }'
```

### Complete Password Reset Flow

```bash
# 1. Request Password Reset
curl -X POST http://localhost:8080/api/auth/forgot-password \
  -H "Content-Type: application/json" \
  -d '{
    "email": "alice@example.com"
  }'
# Note: Check email for reset token

# 2. Reset Password with Token (use token from email)
curl -X POST http://localhost:8080/api/auth/reset-password \
  -H "Content-Type: application/json" \
  -d '{
    "token": "token-from-email",
    "newPassword": "FinalPassword789!",
    "confirmPassword": "FinalPassword789!"
  }'

# 3. Sign In with New Password
curl -X POST http://localhost:8080/api/auth/signin \
  -H "Content-Type: application/json" \
  -d '{
    "email": "alice@example.com",
    "password": "FinalPassword789!"
  }'
```

---

## Rate Limiting

Currently no rate limiting is implemented. Consider implementing rate limiting for production:
- Maximum login attempts: 5 per 15 minutes
- Maximum password reset requests: 3 per hour
- API requests: Unlimited (implement as needed)

---

## Data Validation Rules

### Email Validation
- Must be unique in the system
- Must follow standard email format (user@domain.com)
- Case-insensitive matching

### Phone Number
- Minimum 10 digits
- Maximum 15 digits
- Must contain only digits

### Name Fields
- First name: 2-50 characters, required
- Last name: 0-50 characters, optional

### Password Constraints
- Minimum 8 characters
- Should contain mix of uppercase, lowercase, numbers
- Special characters recommended

---

## Testing Scenarios

### Scenario 1: New User Registration
1. Call Sign Up endpoint with unique email
2. Verify response contains user ID
3. Check database for new user record

### Scenario 2: User Login
1. Call Sign In with registered email and password
2. Verify response contains valid JWT token
3. Save token for future requests

### Scenario 3: Forgot Password Flow
1. Call Forgot Password with email
2. Check email inbox for reset link
3. Extract token from email
4. Call Reset Password with token
5. Sign in with new password

### Scenario 4: Change Password
1. Login user
2. Call Change Password with old and new passwords
3. Verify password changed
4. Login again with new password

---

## Additional Resources

- Base URL: `http://localhost:8080`
- API Documentation Version: 1.0
- Last Updated: May 18, 2024
- Contact: support@navashu.com

---

**End of API Documentation**

