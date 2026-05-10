# AI Context Routing + Project Architecture Guide

## Purpose

This guide combines:

- Context routing optimization
- Spring MVC architecture rules
- Hibernate ORM conventions
- Business rules for Thi Trac Nghiem system
- Code generation standards

Goals:

- Minimize token usage
- Load only required context
- Keep layered architecture consistent
- Generate maintainable Maven projects
- Preserve Eclipse compatibility

---

# Core Context

Always load:

- .ai/core/stack.md
- .ai/core/architecture.md
- .ai/core/naming.md

Global architecture:

Controller -> Service -> Repository -> Database

Rules:

- Controller handles request/response only
- Service contains business logic
- Repository handles Hibernate access
- JSP handles UI rendering only

Forbidden:

- SQL inside Controller
- Business logic inside JSP
- Repository access from Controller

---

# Project Stack

Project uses:

- Java
- Spring MVC
- Maven
- Hibernate ORM
- SQL Server
- JSP + JSTL
- Apache Tomcat
- Eclipse IDE

---

# Maven Project Structure

Always generate:

src/main/
├───java
│   ├───com
│   │   └───tracnghiem
│   │       ├───config
│   │       ├───controller
│   │       ├───dao
│   │       ├───entity
│   │       │   └───id
│   │       ├───repository
│   │       ├───service
│   │       └───utils
│   ├───config
│   └───utils
├───resources
└──webapp
    ├───META-INF
    └───WEB-INF
        ├───configs
        └───views
---

# Context Routing Rules

Load only required rules/modules.

Never load all .md files unless absolutely necessary.

---

# CRUD Module Context

Read:

- .ai/core/stack.md
- .ai/core/architecture.md
- .ai/core/naming.md

- .ai/backend/controller-rules.md
- .ai/backend/service-rules.md
- .ai/backend/repository-rules.md
- .ai/backend/entity-rules.md
- .ai/backend/validation-rules.md

- .ai/frontend/jsp-rules.md

- Required table schema only

Use for:

- CRUD generation
- DTO generation
- Controller generation
- Service generation
- Repository generation
- JSP generation

---

# Entity Generation Context

Read:

- .ai/core/architecture.md
- .ai/backend/entity-rules.md

- Required table files only

Use for:

- Hibernate entities
- Entity relationships
- Mapping annotations

Rules:

- Use @Entity
- Use @Table(name="...")
- Use manual business keys
- Use LAZY fetch by default

Avoid:

- JSP rules
- Authentication rules
- Exam rules

---

# Controller Context

Read:

- .ai/core/architecture.md
- .ai/backend/controller-rules.md
- .ai/backend/validation-rules.md

Rules:

- Use @Controller
- Use @ModelAttribute
- Use @Valid
- Use DTO for form binding

Avoid:

- HttpServletRequest parameter extraction
- Business logic
- Repository access

---

# Service Context

Read:

- .ai/core/architecture.md
- .ai/backend/service-rules.md
- .ai/backend/transaction-rules.md

Rules:

- Use @Service
- Use @Transactional only here
- Handle business validation here

Use for:

- Business logic
- Exam logic
- Random question generation
- Score calculation

---

# Repository Context

Read:

- .ai/core/architecture.md
- .ai/backend/repository-rules.md
- .ai/database/database-rules.md

Rules:

- Use Hibernate SessionFactory
- Use HQL
- No raw JDBC
- No Spring Data JPA

Use for:

- HQL queries
- Data access

---

# JSP / Frontend Context

Read:

- .ai/frontend/jsp-rules.md

Rules:

- JSP + JSTL only
- Bootstrap UI
- Spring Form Tag Library
- No Java scriptlets

Views location:

/WEB-INF/views/

---

# Authentication Context

Read:

- .ai/business/auth-rules.md
- .ai/business/security-rules.md

- .ai/database/tables/taikhoan.md
- .ai/database/tables/giaovien.md

Use for:

- Login
- Session
- Authorization
- Interceptors

Session convention:

session.setAttribute("LOGIN_USER", user);
session.setAttribute("ROLE", role);

Roles:

- PGV
- GIANGVIEN
- SINHVIEN

---

# Authorization Rules

Use interceptor-based authorization.

Structure:

interceptor/
├── AuthInterceptor
├── RoleInterceptor

Permissions:

PGV:

- Full access
- Create accounts
- Manage teachers
- Manage subjects
- No exam participation

GIANGVIEN:

- Manage own questions
- Register exams
- View scores
- Trial exams without saving scores

SINHVIEN:

- Take exams
- View exam history

---

# Exam Module Context

Read:

- .ai/business/exam-rules.md

- bode.md
- baithi.md
- chitiet-baithi.md
- bangdiem.md
- giaovien-dangky.md

Use for:

- Random question generation
- Exam timer
- Auto scoring
- Question distribution

---

# Exam Business Rules

Question distribution:

LEVEL A:

- Minimum 70% A
- Maximum 30% B

LEVEL B:

- Minimum 70% B
- Maximum 30% C

LEVEL C:

- 100% C

Rules:

- Questions must not duplicate
- Questions randomized
- Equal score per question
- Max score = 10
- Auto submit when time expires
- Students can review previous attempts

---

# Database Loading Rules

Never load all tables.

Only load related tables.

Examples:

Student CRUD:

- sinhvien.md
- lop.md

Authentication:

- taikhoan.md
- giaovien.md

Exam:

- bode.md
- bangdiem.md
- giaovien-dangky.md

Use relationships.md only for joins.

---

# Validation Rules

Validation flow:

Controller -> DTO Validation
Service -> Business Validation
Repository -> Database Validation

Use Jakarta Validation:

@NotBlank
@Size
@Pattern

---

# Repository Query Rules

Use HQL whenever possible.

Avoid:

- SELECT \*
- Native SQL
- Business logic inside query

Example:

FROM Subject s
WHERE s.subjectId = :subjectId

---

# Naming Convention

Controller:

- SubjectController
- StudentController

Service:

- SubjectService
- SubjectServiceImpl

Repository:

- SubjectRepository
- SubjectRepositoryImpl

DTO:

- SubjectDto

Entity:

- Subject

JSP:

- subjectList.jsp
- subjectForm.jsp

---

# CRUD Method Convention

Service:

createSubject()
updateSubject()
deleteSubject()
getSubjectById()

Repository:

save()
update()
delete()
findById()
findAll()

---

# Performance Rules

- Never wildcard-load contexts
- Load business rules only when needed
- Load frontend rules only for JSP tasks
- Load auth rules only for login/security
- Load exam rules only for exam logic

---

# Bug Fixing Rules

Read only:

- Related module
- Related entity
- Related business rule

Always read:

- architecture.md

Avoid:

- Reloading entire project rules

---

# Security Rules

Always:

- Validate all inputs
- Prevent SQL injection
- Use session authentication
- Encrypt passwords

Never:

- Store plain passwords
- Trust frontend validation

---

# Output Requirements

Generated code must be:

- Maven compatible
- Eclipse compatible
- Layered
- Modular
- Reusable
- Readable
- Maintainable

Always follow:

Spring MVC + Hibernate + JSP architecture.
