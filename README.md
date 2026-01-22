# HybridShift Tracker (Hybrid Work Tracking Portal)

## Introduction :
	The HybridShift Tracker is a backend application developed to monitor and manage employee work modes in a hybrid working environment. The system enables employees to log their daily work mode (Remote / Office / Hybrid) while allowing administrators and managers to manage employees, monitor attendance, and analyze work distribution. The project is implemented using Spring Boot, JWT-based authentication, and role-based authorization.

## Purpose of the System :
The purpose of the HybridShift Tracker is to track employee daily work modes 
–	Enforce secure access using authentication and roles 
–	Provide transparency for employees and administrators
–	Lay the foundation for attendance monitoring and managerial analytics

## Technology Stack
•	Backend Framework: Spring Boot
•	Language: Java
•	Security: Spring Security + JWT
•	Database: MySQL
•	ORM: Spring Data JPA / Hibernate
•	Testing Tool: Postman
•	Build Tool: Maven

## System Architecture
The application follows a layered architecture:
4.1 Architecture Layers
1.	Controller Layer
o	Exposes REST APIs
o	Handles HTTP requests from clients (Postman / Frontend)
2.	Service Layer
o	Contains business logic
o	Handles validations and processing rules
3.	Repository Layer
o	Communicates with the database
o	Uses JPA repositories
4.	Entity Layer
o	Defines database models (tables)
5.	Security Layer
o	JWT Authentication Filter
o	Role-based access control

## User Roles
The system supports two roles:
1. USER
•	Can log daily work mode
•	Can view own work logs
•	Cannot access admin APIs
2. ADMIN
•	Can manage employees
•	Can view all work logs
•	Can access reporting endpoints

## Security Implementation
1. Authentication
•	Implemented using JWT (JSON Web Token)
•	Users authenticate via /auth/login
•	On successful login, a JWT token is issued
2. Authorization
•	Role-based authorization using ROLE_USER and ROLE_ADMIN
•	Enforced via:
o	Spring Security configuration
o	Method-level security (@PreAuthorize)
3. JWT Token Structure
•	Subject (sub) → Username
•	Role (role) → USER / ADMIN
•	Expiration (exp) → Token validity

##  Database Design 
1. AppUser Entity
Stores user authentication and role information.
Fields: - id - username - password (BCrypt encrypted) - roles (Many-to-Many)
2. Role Entity
Defines system roles.
Fields: - id - name (ROLE_USER / ROLE_ADMIN)
3. Employee Entity
Stores employee profile data.
Fields: - id - name - email - workMode - workDate
4. WorkLog Entity
Stores daily work mode logs of employees.
Fields: - id - user (Many-to-One) - workDate - workMode
Constraint: - One work log per user per day

## Functional Modules Completed
1. Authentication Module
•	User login using username & password
•	JWT token generation
•	Token validation via filter
2. Employee Management Module
•	Admin can create employees
•	Admin can update and delete employee records
•	Users can view employee data (read-only)
3. Work Mode Logging Module
•	Users can log daily work mode
•	Duplicate entries for the same day are prevented
•	Users can view their own logs
•	Admin can view all user logs

## API Validation
The following APIs were successfully tested using Postman:
### USER APIs
•	POST /worklogs?workMode=REMOTE
•	GET /worklogs/me
### ADMIN APIs
•	GET /employees
•	POST /employees
•	GET /worklogs
Role-based access was verified by testing with both USER and ADMIN JWT tokens.

##  Conclusion
The HybridShift Tracker backend has been successfully implemented up to secure work mode logging. The system ensures strong authentication, proper role separation, and reliable data persistence. This foundation enables future enhancements such as shift scheduling, attendance monitoring, and manager dashboards.
The project meets the required objectives defined for hybrid work tracking and demonstrates practical usage of Spring Boot, JWT security, and RESTful API design.

## ER Diagram (Conceptual)
Entities & Relationships
•	AppUser (id, username, password)
o	One AppUser can have many Roles (Many-to-Many)
o	One AppUser can have many WorkLogs (One-to-Many)
•	Role (id, name)
o	Assigned to users via user_roles join table
•	Employee (id, name, email, workMode, workDate)
o	Managed by ADMIN
•	WorkLog (id, workDate, workMode)
o	Belongs to exactly one AppUser
o	Unique constraint on (user_id, work_date)

AppUser ───< WorkLog
   │
   └───< user_roles >─── Role

## Sequence Diagrams (Textual Representation)
1️. User Logs Daily Work Mode
User → AuthController : POST /auth/login
AuthController → JwtUtil : generateToken()
JwtUtil → User : JWT Token

User → WorkLogController : POST /worklogs?workMode=REMOTE
WorkLogController → JwtFilter : validate JWT
JwtFilter → SecurityContext : set Authentication
WorkLogController → WorkLogRepository : save WorkLog
WorkLogRepository → DB : INSERT

2️. Admin Views All Logs
Admin → AuthController : POST /auth/login
Admin → WorkLogController : GET /worklogs
SecurityConfig : checks ROLE_ADMIN
WorkLogController → WorkLogRepository : findAll()
WorkLogRepository → DB : SELECT

## Conclusion
The HybridShift Tracker backend has been successfully implemented up to Work Mode Logging with:
 - Secure JWT authentication
 - Role-based authorization 
 - Robust database design 
 - Fully tested REST APIs
The system is production-ready for further extension into Shift Scheduling and Analytics, aligning perfectly with Sasken’s problem statement.

