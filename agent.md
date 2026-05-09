# AGENT_GUIDE.md

## Project Context

This project is a Java web application built with:

- Java
- Spring MVC
- Maven
- JSP + JSTL
- SQL Server
- Apache Tomcat
- Eclipse IDE

The project follows a standard MVC architecture and must always be generated as a Maven project compatible with Eclipse.

---

# Project Structure

Always use this structure:

```text
src/main/java/
├── controller/
├── service/
│   └── impl/
├── repository/
├── entity/
├── dto/
├── config/
├── utils/
└── validator/

src/main/resources/
├── application.properties

src/main/webapp/
├── WEB-INF/
│   ├── views/
│   └── web.xml
│
├── assets/
│   ├── css/
│   ├── js/
│   └── images/
```

---

# Architecture Rules

Always follow:

```text
Controller -> Service -> Repository -> Database
```

Rules:

- Controller handles request/response only
- Service contains business logic
- Repository handles database access
- JSP is for UI rendering only
- Never place SQL inside Controller
- Never place business logic inside JSP

---

# Coding Style

## Java

- Use clean code principles
- Use dependency injection with `@Autowired`
- Use annotations:
  - `@Controller`
  - `@Service`
  - `@Repository`
- Keep methods small and readable
- Use layered architecture

---

# Naming Convention

## Controllers

```text
AuthController
StudentController
SubjectController
ExamController
```

## Services

```text
StudentService
StudentServiceImpl
```

## Repositories

```text
StudentRepository
```

## Entities

```text
Student
Teacher
Subject
Question
Score
```

---

# JSP Rules

- Use JSP + JSTL only
- Avoid Java scriptlets `<% %>`
- Use Expression Language `${}`
- Use Bootstrap for UI
- Keep reusable layouts/components separated

Example:

```jsp
<c:forEach items="${list}" var="item">
    <tr>
        <td>${item.id}</td>
        <td>${item.name}</td>
    </tr>
</c:forEach>
```

---

# Database Rules

- Use SQL Server
- Use PreparedStatement
- Never use `SELECT *`
- Keep SQL inside Repository layer only
- Separate database logic properly

Example:

```sql
SELECT ID, NAME
FROM STUDENT
WHERE ID = ?
```

---

# Maven Rules

Always manage dependencies inside `pom.xml`.

Common dependencies:

- spring-webmvc
- jstl
- mssql-jdbc
- lombok

---

# Security Rules

- Validate all user inputs
- Prevent SQL Injection
- Prevent unauthorized access
- Never store plain text passwords
- Use session-based authentication

---

# Clean Code Requirements

Generated code must be:

- Readable
- Maintainable
- Modular
- Reusable
- Easy to extend

Avoid:

- Duplicate logic
- Large controller methods
- Mixed responsibilities
- Hardcoded values

---

# AI Code Generation Rules

When generating code:

- Always follow Maven structure
- Keep layers separated
- Generate Eclipse-compatible code
- Use Spring MVC conventions
- Put JSP files inside `/WEB-INF/views`
- Use consistent naming conventions
- Prefer clean architecture

---

# Final Notes

Always assume:

- Eclipse IDE
- Maven project
- Spring MVC architecture
- JSP frontend
- SQL Server database
- Layered architecture
- Clean code principles
