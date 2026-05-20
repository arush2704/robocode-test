# 🔧 PostgreSQL Driver ClassNotFoundException - QUICK FIX

## ❌ Problem
```
java.lang.ClassNotFoundException: org.postgresql.Driver
```

## ✅ Solution

The PostgreSQL JDBC driver wasn't downloaded to your local Maven repository. Here's how to fix it:

---

## 🚀 QUICK FIX (Windows PowerShell)

### **Option 1: Complete Clean & Rebuild (Recommended)**

```powershell
# 1. Navigate to project
cd "C:\RDWS\Robo WS\Navashu"

# 2. Delete Maven cache (delete downloaded dependencies)
Remove-Item -Path "$env:USERPROFILE\.m2\repository\org\postgresql" -Recurse -Force

# 3. Clean Maven
mvn clean

# 4. Force update all dependencies
mvn clean compile

# 5. Wait for download to complete, then build
mvn clean package -DskipTests

# 6. If build succeeds, run
mvn spring-boot:run
```

### **Option 2: Quick Rebuild Only**

```powershell
cd "C:\RDWS\Robo WS\Navashu"
mvn clean package -DskipTests -U
mvn spring-boot:run
```

The `-U` flag forces Maven to update all dependencies from the repository.

### **Option 3: If Using IDE (IntelliJ IDEA)**

1. **File** → **Invalidate Caches** → **Invalidate and Restart**
2. Wait for IDE to restart
3. **Maven** → **Reload projects** (right panel)
4. Run → **Edit Configurations** → Select your run config → **Run**

### **Option 4: If Using Eclipse**

1. Right-click project → **Maven** → **Update Project**
2. Check **Force Update of Snapshots/Releases**
3. Click **OK**
4. Wait for download
5. Run As → Java Application

---

## 🔍 Verify the Fix

After running one of the options above, verify the driver was downloaded:

```powershell
# Check if PostgreSQL driver exists
Test-Path "$env:USERPROFILE\.m2\repository\org\postgresql\postgresql\42.7.1\postgresql-42.7.1.jar"

# Should return: True (if downloaded successfully)
```

---

## 📝 What Changed in pom.xml

Changed PostgreSQL dependency scope from `runtime` to `compile`:

```xml
<!-- BEFORE (runtime scope - not always available) -->
<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
    <version>42.7.1</version>
    <scope>runtime</scope>
</dependency>

<!-- AFTER (compile scope - always available) -->
<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
    <version>42.7.1</version>
</dependency>
```

---

## 🎯 Step-by-Step for Beginners

### **Step 1: Stop the application**
Press `Ctrl+C` in your terminal

### **Step 2: Open PowerShell**
- Press `Windows + R`
- Type `powershell`
- Press `Enter`

### **Step 3: Navigate to project**
```powershell
cd "C:\RDWS\Robo WS\Navashu"
```

### **Step 4: Clean everything**
```powershell
mvn clean
```

### **Step 5: Download dependencies**
```powershell
mvn dependency:resolve
```
Wait for this to complete (1-2 minutes)

### **Step 6: Build project**
```powershell
mvn clean package -DskipTests
```
Wait for success message

### **Step 7: Run application**
```powershell
mvn spring-boot:run
```

### **Step 8: Test**
Open another PowerShell and run:
```powershell
curl http://localhost:8080/api/auth/health
```

Expected response:
```json
{"status":"UP","message":"Application is running"}
```

---

## 🆘 If Still Not Working

### Try this advanced fix:

```powershell
# Navigate to project
cd "C:\RDWS\Robo WS\Navashu"

# Delete entire Maven repository (CAREFUL - this deletes ALL cached dependencies)
Remove-Item -Path "$env:USERPROFILE\.m2\repository" -Recurse -Force

# Now rebuild everything
mvn clean install -DskipTests

# Run
mvn spring-boot:run
```

---

## 📋 Checklist

After running the fix commands:

- [ ] Maven build completed successfully (BUILD SUCCESS)
- [ ] No download errors mentioned
- [ ] PostgreSQL driver JAR exists: `~/.m2/repository/org/postgresql/postgresql/42.7.1/postgresql-42.7.1.jar`
- [ ] Application started (Tomcat started on port 8080)
- [ ] Health endpoint returns UP status

---

## 🔗 Repository Location

Maven stores downloaded dependencies here:
```
C:\Users\[YourUsername]\.m2\repository\org\postgresql\postgresql\42.7.1\
```

The JAR file should be:
```
postgresql-42.7.1.jar
```

---

## 💡 Why This Happened

1. PostgreSQL dependency wasn't in your local Maven repository
2. Scope was set to `runtime` instead of `compile`
3. Maven hadn't downloaded it yet during first build

**Now Fixed:** Dependency is set to `compile` and will be included in all builds.

---

## ✅ You're All Set!

After completing the quick fix, your application should start without the ClassNotFoundException error.

**Next:** Run `mvn spring-boot:run` and test the health endpoint!

