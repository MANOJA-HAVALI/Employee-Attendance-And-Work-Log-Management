# Employee Attendance And Work Log Management

A Spring Boot application to manage employees, attendance and work logs with role\-based access control (ADMIN, MANAGER, EMPLOYEE) and JWT authentication.

## Key features
\- User management (register, login)  
\- Role\-based authorization (ADMIN, MANAGER, EMPLOYEE)  
\- Manager \- employee relationship (employees assigned to a manager)  
\- Attendance records (check\-in, check\-out)  
\- Work log entries (create, update, list)  
\- JWT authentication and stateless sessions  
\- MySQL persistence and JPA repositories  
\- DTOs, mappers and validation for API payloads

## Architecture / components
\- Spring Boot (REST API)  
\- Spring Security with JWT  
\- Spring Data JPA (MySQL)  
\- MapStruct or custom mappers (DTO \<\-\> entity)  
\- Lombok for boilerplate reduction

## Entities (high level)
\- `User` — id, name, email, password, role, enabled, manager (self relation)  
\- `Attendance` — id, user, checkIn, checkOut, date  
\- `WorkLog` — id, user, title, description, timeSpent, date

## API (examples)
\- `POST /api/auth/register` \- register user (include `role` and `managerId` for EMPLOYEE)  
\- `POST /api/auth/login` \- returns JWT token  
\- `GET /api/users` \- list users (role restricted)  
\- `POST /api/attendance/checkin` \- record check\-in  
\- `POST /api/worklogs` \- create work log entry

Sample login cURL:
```bash
curl -X POST -H "Content-Type: application/json" \
-d '{"email":"user@example.com","password":"secret"}' \
http://localhost:9090/api/auth/login
