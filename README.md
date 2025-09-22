# EmployeeManagementSystem
# Employee Management System (Spring Boot + Maven)

A clean, minimal CRUD API for managing employees.

## Tech
- Spring Boot 3
- Java 17
- Spring Web, Spring Data JPA, Validation
- H2 in-memory database

## Run
```bash
mvn spring-boot:run
```
App starts on http://localhost:8080

## API
- `GET /api/employees` – list all
- `GET /api/employees/{id}` – get by id
- `POST /api/employees` – create
- `PUT /api/employees/{id}` – update
- `DELETE /api/employees/{id}` – remove

### Example requests
```bash
curl http://localhost:8080/api/employees

curl -X POST http://localhost:8080/api/employees \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Shreya Mittal",
    "email": "shreya@example.com",
    "designation": "SDE I",
    "department": "Engineering",
    "phone": "9876545555",
    "salary": 950000
  }'

curl -X PUT http://localhost:8080/api/employees/1 \
  -H "Content-Type: application/json" \
  -d '{
    "designation":"Senior Engineer",
    "salary": 1300000
  }'

curl -X DELETE http://localhost:8080/api/employees/1
```

### H2 Console
Visit http://localhost:8080/h2-console (JDBC URL: `jdbc:h2:mem:emsdb`, user `sa`, empty password).

## Notes
- Unique email constraint enforced.
- Basic validation on fields.
- Global exception handling for clean JSON errors.
- You can switch to MySQL/PostgreSQL by changing `application.properties`.
