# E-Insurance Management System – Backend

## 📌 Overview

The **E-Insurance Management System** is a Java-based backend application developed using **Spring Boot**. The application provides REST APIs to manage insurance-related operations including customers, employees, insurance agents, insurance plans, schemes, policies, payments, and commissions.

The project follows a **layered architecture** with separate layers for Controllers, Services, Repositories, DTOs, Mappers, Entities, Security, and Exception Handling.

---

## 🚀 Features

- JWT-based user authentication
- Role-based user management
- Admin management
- Employee management
- Insurance Agent management
- Customer management
- Insurance Plan management
- Scheme management
- Policy management
- Payment management
- Premium calculation
- Commission calculation
- Customer policy and payment management
- Payment receipt/invoice generation
- Request validation
- Global exception handling
- Pagination for Get All APIs
- PostgreSQL database integration
- BCrypt password encryption

---

## 🛠️ Technologies Used

- **Java 21**
- **Spring Boot 4.1.1**
- **Spring Web**
- **Spring Data JPA**
- **Spring Security**
- **JWT**
- **BCrypt**
- **PostgreSQL**
- **Maven**
- **Lombok**
- **Git & GitHub**
- **Postman**
- **IntelliJ IDEA**

---

## 🏗️ Architecture

The project follows a layered architecture:

```text
Controller
    ↓
Service
    ↓
Service Implementation
    ↓
Repository
    ↓
Database

DTOs and Mappers are used to separate API data from database entities:

Request DTO
    ↓
Mapper
    ↓
Entity
    ↓
Repository
    ↓
Database

Database
    ↓
Entity
    ↓
Mapper
    ↓
Response DTO
    ↓
Controller
📂 Project Structure
src/main/java/com/einsurance
│
├── EInsuranceApplication.java
│
├── config
│   └── SecurityConfig.java
│
├── controller
│   ├── AuthController.java
│   ├── AdminController.java
│   ├── EmployeeController.java
│   ├── InsuranceAgentController.java
│   ├── CustomerController.java
│   ├── InsurancePlanController.java
│   ├── SchemeController.java
│   ├── PolicyController.java
│   ├── PaymentController.java
│   └── CommissionController.java
│
├── dto
│   ├── auth
│   ├── admin
│   ├── employee
│   ├── agent
│   ├── customer
│   ├── plan
│   ├── scheme
│   ├── policy
│   ├── payment
│   └── commission
│
├── entity
│   ├── Admin.java
│   ├── Employee.java
│   ├── InsuranceAgent.java
│   ├── Customer.java
│   ├── InsurancePlan.java
│   ├── Scheme.java
│   ├── Policy.java
│   ├── Payment.java
│   ├── Commission.java
│   └── EmployeeScheme.java
│
├── enums
│   └── Role.java
│
├── exception
│   ├── ResourceNotFoundException.java
│   ├── DuplicateResourceException.java
│   ├── BadRequestException.java
│   ├── ErrorResponse.java
│   └── GlobalExceptionHandler.java
│
├── mapper
│   ├── AdminMapper.java
│   ├── EmployeeMapper.java
│   ├── InsuranceAgentMapper.java
│   ├── CustomerMapper.java
│   ├── InsurancePlanMapper.java
│   ├── SchemeMapper.java
│   ├── PolicyMapper.java
│   ├── PaymentMapper.java
│   └── CommissionMapper.java
│
├── repository
│   ├── AdminRepository.java
│   ├── EmployeeRepository.java
│   ├── InsuranceAgentRepository.java
│   ├── CustomerRepository.java
│   ├── InsurancePlanRepository.java
│   ├── SchemeRepository.java
│   ├── PolicyRepository.java
│   ├── PaymentRepository.java
│   ├── CommissionRepository.java
│   └── EmployeeSchemeRepository.java
│
├── security
│   ├── JwtService.java
│   ├── JwtAuthenticationFilter.java
│   └── CustomUserDetailsService.java
│
├── service
│   ├── AdminService.java
│   ├── AdminServiceImpl.java
│   ├── EmployeeService.java
│   ├── EmployeeServiceImpl.java
│   ├── InsuranceAgentService.java
│   ├── InsuranceAgentServiceImpl.java
│   ├── CustomerService.java
│   ├── CustomerServiceImpl.java
│   ├── InsurancePlanService.java
│   ├── InsurancePlanServiceImpl.java
│   ├── SchemeService.java
│   ├── SchemeServiceImpl.java
│   ├── PolicyService.java
│   ├── PolicyServiceImpl.java
│   ├── PaymentService.java
│   ├── PaymentServiceImpl.java
│   ├── CommissionService.java
│   └── CommissionServiceImpl.java
│
└── util
    └── (Reserved for common utility classes)
🗄️ Database

The application uses PostgreSQL as the relational database.

Database Name
einsurance_db
Main Entities
Admin
Employee
Insurance Agent
Customer
Insurance Plan
Scheme
Policy
Payment
Commission
Employee Scheme
Entity Relationships
Insurance Plan
      │
      └── Scheme
             │
             └── Policy
                    │
                    ├── Customer
                    │
                    └── Payment

Customer
   │
   └── Insurance Agent

Employee
   │
   └── Scheme
🔐 Authentication & Security

The application uses JWT-based authentication for user login and authentication.

The system supports the following roles:

ADMIN
EMPLOYEE
AGENT
CUSTOMER

Passwords are securely stored using BCrypt password hashing.

Authentication Flow
User Login
    ↓
Validate Credentials
    ↓
Generate JWT Token
    ↓
Client Receives Token
    ↓
Token Sent With API Request
    ↓
JWT Authentication Filter
    ↓
Authenticated Request
📋 Modules
Admin
Create Admin
Get Admin
Update Admin
Delete Admin
Manage users
Manage insurance-related data
Employee
Create Employee
Get Employee
Update Employee
Delete Employee
Manage assigned schemes
Insurance Agent
Manage Insurance Agents
Manage customer assignments
Manage policies sold by agents
Customer
Create Customer
View Customer details
View policies
View payments
Purchase multiple policies
Insurance Plan
Create Insurance Plan
View Insurance Plans
Update Insurance Plan
Delete Insurance Plan
Scheme
Create Scheme
View Schemes
Update Scheme
Delete Scheme
Associate Scheme with an Insurance Plan
Policy
Create Policy
View Policy
Update Policy
Manage policy details
Calculate premium
Payment
Record policy payments
View payment details
Calculate total customer payments
Generate payment receipt/invoice
Commission
Calculate commission
View commission details
Manage agent commissions
✅ Validation

The application uses Spring Boot validation for incoming API requests.

Validation includes:

Required fields
Email format validation
Field length validation
Numeric value validation
Date validation
Duplicate username/email validation
📄 Pagination

Pagination is implemented for Get All APIs to efficiently handle large datasets.

Example:

GET /api/customers?page=0&size=10
⚙️ Configuration

Database credentials are managed using environment variables.

Create a .env file in the project root:

DB_URL=jdbc:postgresql://localhost:5432/einsurance_db
DB_USERNAME=postgres
DB_PASSWORD=YOUR_POSTGRES_PASSWORD

Note: The .env file contains sensitive information and must not be committed to GitHub.

▶️ How to Run the Project
1. Clone the Repository
git clone <your-github-repository-url>
2. Open the Project

Open the project in IntelliJ IDEA.

3. Create PostgreSQL Database
CREATE DATABASE einsurance_db;
4. Configure Environment Variables

Create the .env file in the project root and add:

DB_URL=jdbc:postgresql://localhost:5432/einsurance_db
DB_USERNAME=postgres
DB_PASSWORD=YOUR_POSTGRES_PASSWORD
5. Build the Project
mvn clean install
6. Run the Application

Run:

EInsuranceApplication.java

The application will start on:

http://localhost:8080
🧪 API Testing

The REST APIs can be tested using Postman.

For authenticated APIs, send the JWT token using the Authorization header:

Authorization: Bearer <JWT_TOKEN>
🔒 Security Practices
JWT-based authentication
BCrypt password encryption
Environment variables for sensitive database configuration
.env excluded from Git
DTOs used instead of directly exposing entities
Request validation
Global exception handling
Role-based access control
📈 Development Approach

The project is developed module-by-module.

Each module follows the complete flow:

Entity
   ↓
Repository
   ↓
DTO
   ↓
Mapper
   ↓
Service
   ↓
ServiceImpl
   ↓
Controller
   ↓
API Testing
Development Sequence
Admin
  ↓
Employee
  ↓
Insurance Agent
  ↓
Customer
  ↓
Insurance Plan
  ↓
Scheme
  ↓
Policy
  ↓
Payment
  ↓
Commission
  ↓
Employee Scheme
🔮 Future Enhancements
Angular frontend application
Admin dashboard
Customer dashboard
Advanced search and filtering
Policy document generation
Email notifications
Detailed reports
Cloud deployment
👩‍💻 Author

Muskan Kapoor

Project: E-Insurance Management System

Backend: Java | Spring Boot | PostgreSQL

📜 License

This project is developed for educational and project implementation purposes.
