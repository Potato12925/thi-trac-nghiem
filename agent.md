# AGENT_GUIDE.md

## Project Context

This project is a Java web application built with:

- Java
- Spring MVC
- Maven
- JSP + JSTL
- SQL Server
- Hibernate ORM
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
│   └── impl/
├── entity/
├── dto/
├── config/
├── utils/
├── validator/
├── exception/
├── interceptor/
└── constants/

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
- Never access Repository directly from Controller

---

# Coding Style

## Java

- Use clean code principles
- Use dependency injection with `@Autowired`
- Use annotations:
  - `@Controller`
  - `@Service`
  - `@Repository`
  - `@Transactional`
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
StudentRepositoryImpl
```

## Entities

```text
Student
Teacher
Subject
Question
Score
```

## JSP Naming

Use camelCase naming.

Examples:

```text
subjectList.jsp
subjectForm.jsp
studentDetail.jsp
examResult.jsp
teacherManagement.jsp
```

---

# CRUD Method Convention

## Service Layer Naming

All service methods must use explicit entity naming.

Example:

```java
public interface SubjectService {

    void createSubject(Subject subject);

    void updateSubject(Subject subject);

    void deleteSubject(String subjectId);

    Subject getSubjectById(String subjectId);

    List<Subject> getAllSubjects();

    List<Subject> searchSubjects(String keyword);
}
```

## Repository Layer Naming

```java
public interface SubjectRepository {

    void save(Subject subject);

    void update(Subject subject);

    void delete(String subjectId);

    Subject findById(String subjectId);

    List<Subject> findAll();

    List<Subject> search(String keyword);
}
```

---

# Return Type Convention

Rules:

- Service layer returns Entity directly
- Repository layer returns Entity directly
- Controller uses DTO for request validation only
- Do not use Response DTO unless explicitly required

Example:

```java
Subject getSubjectById(String subjectId);
```

---

# Controller Parameter Convention

Rules:

- Use JavaBean DTO objects for form binding
- Use `@ModelAttribute` for form data binding
- Use `@Valid` for validation
- Avoid using multiple `@RequestParam` for large forms
- Avoid using `HttpServletRequest` to manually get parameters
- DTO objects must follow JavaBean standard
- DTO must contain:
  - private fields
  - getter/setter
  - no business logic

Example Controller:

```java
@PostMapping("/subjects")
public String createSubject(
        @Valid @ModelAttribute SubjectDto subjectDto,
        BindingResult bindingResult) {

    if (bindingResult.hasErrors()) {
        return "subjectForm";
    }

    Subject subject = new Subject();

    subject.setSubjectId(subjectDto.getSubjectId());
    subject.setSubjectName(subjectDto.getSubjectName());

    subjectService.createSubject(subject);

    return "redirect:/subjects";
}
```

Example DTO:

```java
public class SubjectDto {

    @NotBlank
    private String subjectId;

    @NotBlank
    private String subjectName;

    public SubjectDto() {
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }
}
```

---

# Validation Convention

Validation flow must follow:

```text
Controller -> @Valid DTO
Service -> business validation
Repository -> database validation
```

Rules:

- Use Jakarta Validation annotations in DTO
- Business rules must be validated in Service layer
- Database constraints handled in Repository layer

Example:

```java
@NotBlank
@Size(max = 40)
private String subjectName;
```

---

# Transaction Rule

Rules:

- Use `@Transactional` in Service layer only
- Never use `@Transactional` in Controller
- Never use `@Transactional` in Repository

Example:

```java
@Service
@Transactional
public class SubjectServiceImpl implements SubjectService {
}
```

---

# Repository Convention

Rules:

- Use Hibernate ORM
- Use Hibernate SessionFactory
- Do not use raw JDBC
- Do not use Spring Data JPA auto repositories
- Repository layer handles HQL/Criteria queries only
- Use HQL instead of native SQL whenever possible
- Use LAZY loading by default

Example:

```java
@Repository
public class SubjectRepositoryImpl implements SubjectRepository {

    @Autowired
    private SessionFactory sessionFactory;

}
```

---

# Hibernate Entity Convention

Rules:

- All entities must use `@Entity`
- Use `@Table(name = "...")`
- Use manual primary keys for business entities
- Do not use `@GeneratedValue` except for auto-generated question ID

Example:

```java
@Entity
@Table(name = "MONHOC")
public class Subject {

    @Id
    @Column(name = "MAMH")
    private String subjectId;
}
```

---

# Controller URL Convention

Use REST-like URL design.

Examples:

```text
GET     /subjects
GET     /subjects/{id}
POST    /subjects
PUT     /subjects/{id}
DELETE  /subjects/{id}
```

Rules:

- Use plural resource naming
- Use path variable for ID
- Avoid `/save`, `/edit`, `/delete`

---

# JSP Rules

- Use JSP + JSTL only
- Avoid Java scriptlets `<% %>`
- Use Expression Language `${}`
- Use Bootstrap for UI
- Keep reusable layouts/components separated
- Use Spring Form Tag Library for form binding

Example:

```jsp
<form:form modelAttribute="subjectDto" method="post">

    <form:input path="subjectId"/>

    <form:input path="subjectName"/>

</form:form>
```

All JSP files must be placed inside:

```text
/WEB-INF/views/
```

Reusable layouts:

```text
/WEB-INF/views/layout/
```

---

# Database Rules

- Use SQL Server
- Use Hibernate ORM
- Never use `SELECT *`
- Keep database logic inside Repository layer only
- Use HQL whenever possible
- Use uppercase SQL keywords

Example:

```sql
SELECT s.subjectId, s.subjectName
FROM Subject s
WHERE s.subjectId = :subjectId
```

---

# Authentication Convention

## Session Convention

Example:

```java
session.setAttribute("LOGIN_USER", user);
session.setAttribute("ROLE", role);
```

## Role Convention

```java
public enum Role {
    PGV,
    GIANGVIEN,
    SINHVIEN
}
```

---

# Authorization Convention

Use Interceptor for authorization.

Example structure:

```text
interceptor/
├── AuthInterceptor
├── RoleInterceptor
```

---

# Exam Business Rules

## Question Level Rules

LEVEL A:

- Minimum 70% A questions
- Maximum 30% B questions

LEVEL B:

- Minimum 70% B questions
- Maximum 30% C questions

LEVEL C:

- 100% C questions

Rules:

- Questions must not duplicate
- Questions must be randomized
- Score maximum is 10
- All questions have equal score
- Auto submit when exam time expires

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
- Follow all conventions defined in this document

---

# Final Notes

Always assume:

- Eclipse IDE
- Maven project
- Spring MVC architecture
- Hibernate ORM
- JSP frontend
- SQL Server database
- Layered architecture
- Clean code principles
