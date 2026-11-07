# Student Management System (Spring Boot REST API)

Stack: Java 17, Spring Boot 3.3, Spring Data JPA, H2 (embedded), Bean Validation.

## Run
No external database is required — H2 is embedded and auto-configured.

1. `mvn spring-boot:run`  (API at http://localhost:8080)
   - H2 console (optional): http://localhost:8080/h2-console (JDBC URL: `jdbc:h2:mem:studentdb`)

## Endpoints
| Method | Endpoint | Description | Success |
|---|---|---|---|
| POST | /api/students | Add a new student | 201 |
| GET | /api/students | Retrieve all students | 200 |
| GET | /api/students/{id} | Retrieve student by ID | 200 |
| PUT | /api/students/{id} | Update student details | 200 |
| DELETE | /api/students/{id} | Delete a student | 204 |
| GET | /api/students/course/{course} | Retrieve students by course | 200 |

Errors: 400 (validation / bad input), 404 (student not found), 409 (duplicate email), 500.

## Postman
Import these files into Postman:

- `postman/Student-Management.postman_collection.json` — all requests + assertions
- `postman/Student-Management.postman_environment.json` — sets `baseUrl = http://localhost:8080`

Run the requests in order. The "Add Student" test saves the created `id` as a collection variable so the GET/PUT/DELETE-by-ID requests chain automatically. Error-case requests verify 404 / 400 / 409 responses.

Test body (POST / PUT):
```json
{
  "name": "Satyam",
  "email": "satyam@example.com",
  "course": "Computer Science",
  "age": 20
}
```
Error cases to try: GET `/api/students/999` -> 404; POST with `"email": "bad"` -> 400; POST the same email twice -> 409.
# Student-Management-System-Rest-API-Java-SpringBoot
