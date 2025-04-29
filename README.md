# Empik Complaints Management Service

# Description
Service for Complaints management.

# Table of Contents
- [Installation](#installation)
- [Launch Locally](#launch-locally)
- [Usage of Application](#usage-of-application)
- [Use Cases](#use-cases)
- [Architecture Details](#architecture-details)

# Installation
### Clone the repository
```bash
git clone https://github.com/szymonkajda/empik-recruitment.git
```

### Build project
```bash
mvn clean install
```

# Launch Locally
To launch application run following Java class:
[ComplaintsManagementServiceApplication.java](src/main/java/com/empik/complaintsmanagement/ComplaintsManagementServiceApplication.java)

Alternatively application can be launched from project root with command:
```bash
./mvnw spring-boot:run
```

# Usage of Application
### API
After Application is started, all http endpoints are published in http://localhost:8080/swagger-ui/index.html
### Database
Application uses non-ephemeral configuration of H2 database. Data is being persisted into file [database.mv.db](data/database.mv.db).
Database can be accessed via http://localhost:8080/h2-console
### Fetching Country Name by IP
The client IP is resolved in following order
- IP address taken from X-Forwarded-For header if exists
- IP address taken from WL-Proxy-Client-IP header if exists
- finally it's taken from remoteAddr of HttpServletRequest.

For resolving Country Name, API request to ipapi is sent with client IP. In case there Country Name is unknown, null value is resolved.
To test resolving of Country Name during Complaint creation, following curl command can be performed:
```bash
curl -X PUT "http://localhost:8080/api/v1/complaints" \
-H "X-Forwarded-For: 185.60.216.35" \
-H "Content-Type: application/json" \
-d '{
  "productId": 9007199254740993,
  "content": "Content1",
  "creationDate": "2025-04-28T13:44:12.857Z",
  "creationUser": "user"
}'
```

IP can be adjusted within the header section for X-Forwarded-For.

# Use Cases
All service use cases are located in [following package](src/main/java/com/empik/complaintsmanagement/application/port/in).

# Architecture Details
Following service is created following the principles of Hexagonal architecture with Domain-Driven Design approach. 
It ensures that project remains readable and maintainable, and that the Domain layer is clearly separated from technical 
frameworks, which can easily be replaced if needed.

For future development, adapters can be extracted into separate modules to reduce the size of pom.xml dependencies
and to create reusable modules that can be shared across other services.