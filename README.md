# 📌 Job Portal REST API

A scalable backend REST API built using **Spring Boot** where companies can post jobs and candidates can apply for them.

---

## 🚀 Tech Stack

- Java 17+
- Spring Boot
- Spring Data JPA
- Hibernate
- PostgreSQL
- Lombok
- Jakarta Validation
- Maven

---

## 🏗️ Architecture

This project follows a clean layered architecture:

Controller → Service → Repository → Database

### Key Concepts Implemented:

- DTO Pattern (Avoid entity exposure)
- Pagination & Sorting (Spring Data JPA)
- Global Exception Handling (`@RestControllerAdvice`)
- Validation using Jakarta Annotations
- Proper JPA Relationships (`OneToMany`, `ManyToOne`)
- Business logic to prevent duplicate job applications

---

## 📂 Modules

### 🔹 Company Module
- Create Company
- Get All Companies (Pagination & Sorting)
- Get Company by ID
- Update Company
- Delete Company

### 🔹 Job Module
- Create Job
- Get All Jobs (Pagination & Sorting)
- Get Job by ID
- Search Jobs by Location
- Update Job
- Delete Job

### 🔹 Candidate Module
- Create Candidate
- Get All Candidates (Pagination & Sorting)
- Get Candidate by ID
- Update Candidate
- Delete Candidate

### 🔹 Job Application Module
- Apply for Job
- Get All Applications
- Get Applications by Job
- Get Applications by Candidate
- Prevent Duplicate Applications

---
## 🔗 Sample API Endpoints

POST /api/companies

GET /api/companies

POST /api/jobs

GET /api/jobs/search?location=Bangalore

POST /api/jobs/{jobId}/apply/{candidateId}

GET /api/applications


---

## 📊 Database Relationships

- Company → OneToMany → Job
- Job → ManyToOne → Company
- Job → OneToMany → JobApplication
- Candidate → OneToMany → JobApplication
- JobApplication → ManyToOne → Job & Candidate

---

## 👨‍💻 Author

**EngineerG** 
Developer


