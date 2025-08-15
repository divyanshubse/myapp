# Coaching ERP - Microservices (Spring Boot)

## Build

- Prerequisites: Java 17, Maven 3.9+
- Build all modules:

```
mvn -f pom.xml clean package -DskipTests
```

## Run with Docker Compose

```
docker compose up --build
```

Services:
- Gateway: http://localhost:8080
- Auth: http://localhost:8081
- Student: http://localhost:8082
- MySQL: localhost:3306 (root/root)

## Quick test

- Sign up:
```
curl -X POST "http://localhost:8081/auth/signup?username=alice&password=pass123"
```
- Login:
```
curl -X POST "http://localhost:8081/auth/login?username=alice&password=pass123"
```
- Create Student:
```
curl -X POST http://localhost:8082/students -H 'Content-Type: application/json' -d '{"fullName":"Alice","email":"alice@example.com","phone":"9999"}'
```
