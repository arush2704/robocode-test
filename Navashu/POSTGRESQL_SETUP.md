# PostgreSQL Setup Guide

## 📋 Prerequisites

- PostgreSQL 12+ installed
- pgAdmin or any PostgreSQL client (optional)
- Maven 3.6+
- Java 22

---

## 🔧 Setup Steps

### Step 1: Install PostgreSQL

#### Windows
1. Download from https://www.postgresql.org/download/windows/
2. Run the installer
3. Set superuser password (remember it)
4. Keep default port: **5432**
5. Complete installation

#### Mac
```bash
brew install postgresql@15
brew services start postgresql@15
```

#### Linux
```bash
sudo apt-get install postgresql postgresql-contrib
sudo systemctl start postgresql
```

---

### Step 2: Create Database and User

#### Using psql Terminal (Recommended)

```sql
-- Connect to PostgreSQL
psql -U postgres

-- Create database
CREATE DATABASE navashu_db;

-- Create user with credentials
CREATE USER postgres WITH PASSWORD 'Navneit#21';

-- Grant privileges
ALTER ROLE postgres SUPERUSER CREATEDB CREATEROLE CREATEUSER;

-- List databases to verify
\l

-- Quit
\q
```

#### Using pgAdmin (GUI)

1. Open pgAdmin
2. Right-click **Databases** → **Create** → **Database**
3. Name: `navashu_db`
4. Save
5. Verify in Object Explorer

---

### Step 3: Initialize Database Schema

#### Option A: Using Command Line

```bash
# Navigate to project
cd "C:\RDWS\Robo WS\Navashu"

# Run schema file
psql -U postgres -d navashu_db -f src/main/resources/schema.sql
```

#### Option B: Using pgAdmin

1. Open pgAdmin
2. Connect to `navashu_db`
3. Click **Tools** → **Query Tool**
4. Copy-paste contents of `src/main/resources/schema.sql`
5. Execute (F5 or Execute button)

---

### Step 4: Verify Database Setup

```bash
# Connect to database
psql -U postgres -d navashu_db

# Check tables
\dt

# Check data
SELECT * FROM users;

# Verify indexes
\d users

# Quit
\q
```

---

### Step 5: Update Application Configuration

The `application.properties` file is already configured with:
- **Host**: localhost
- **Port**: 5432
- **Database**: navashu_db
- **Username**: postgres
- **Password**: Navneit#21

No changes needed! ✅

---

### Step 6: Build and Run Application

```bash
# Navigate to project
cd "C:\RDWS\Robo WS\Navashu"

# Clean and build
mvn clean package -DskipTests

# Run application
mvn spring-boot:run
```

Or run from IDE with `Alt+Shift+F10`

---

## 🧪 Test Connection

Once application is running:

```bash
# Test health endpoint
curl http://localhost:8080/api/auth/health

# Expected response
# {"status":"UP","message":"Application is running","timestamp":"2026-05-20T..."}
```

---

## 📊 Database Details

| Property | Value |
|----------|-------|
| **Host** | localhost |
| **Port** | 5432 |
| **Database** | navashu_db |
| **Username** | postgres |
| **Password** | Navneit#21 |
| **Driver** | org.postgresql.Driver |

---

## 🔐 Security Notes

⚠️ **IMPORTANT**: Change the password in production!

1. Update password in PostgreSQL:
```sql
ALTER USER postgres WITH PASSWORD 'new-secure-password';
```

2. Update `application.properties`:
```properties
spring.datasource.password=new-secure-password
```

---

## 🐛 Troubleshooting

### Connection Refused
- Verify PostgreSQL is running
- Check port 5432 is open
```bash
# Check if PostgreSQL is running
psql -U postgres -d postgres -c "SELECT version();"
```

### Wrong Password
```sql
ALTER USER postgres WITH PASSWORD 'Navneit#21';
```

### Database Not Found
```bash
createdb -U postgres navashu_db
```

### Tables Not Created
```bash
psql -U postgres -d navashu_db -f src/main/resources/schema.sql
```

---

## 📚 Useful PostgreSQL Commands

```bash
# Connect to database
psql -U postgres -d navashu_db

# List all databases
\l

# List all tables
\dt

# Describe table
\d users

# Show table structure
\d+ users

# Run SQL file
\i 'path/to/schema.sql'

# Execute query
SELECT * FROM users;

# Count rows
SELECT COUNT(*) FROM users;

# Exit
\q
```

---

## ✅ Verification Checklist

- [ ] PostgreSQL installed and running
- [ ] Database `navashu_db` created
- [ ] User `postgres` created with password `Navneit#21`
- [ ] Schema created from `schema.sql`
- [ ] `application.properties` updated
- [ ] `pom.xml` has PostgreSQL dependency
- [ ] Application builds successfully
- [ ] Health endpoint returns UP status

---

**Status**: ✅ PostgreSQL Setup Complete!

Next: Run `mvn clean package -DskipTests` and `mvn spring-boot:run`

