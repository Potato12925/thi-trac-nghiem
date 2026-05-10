# AI Context Routing Guide

Purpose:

- Minimize token usage
- Load only required context
- Keep architecture consistent

---

# Core Context

Always read:

- .ai/core/stack.md
- .ai/core/architecture.md
- .ai/core/naming.md

---

# CRUD Module

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

- Required database table files only

Use for:

- CRUD generation
- DTO generation
- Controller generation
- Service generation
- Repository generation
- JSP generation

---

# Entity Generation

Read:

- .ai/core/architecture.md
- .ai/backend/entity-rules.md

- Required table files only

Use for:

- Hibernate entities
- Mapping annotations
- Relationships

Avoid:

- JSP rules
- Auth rules
- Exam rules

---

# Controller Tasks

Read:

- .ai/core/architecture.md
- .ai/backend/controller-rules.md
- .ai/backend/validation-rules.md

Use for:

- Request mapping
- Validation
- Form binding

---

# Service Tasks

Read:

- .ai/core/architecture.md
- .ai/backend/service-rules.md
- .ai/backend/transaction-rules.md

Use for:

- Business logic
- Transactions
- Service interfaces

---

# Repository Tasks

Read:

- .ai/core/architecture.md
- .ai/backend/repository-rules.md
- .ai/database/database-rules.md

- Required table files only

Use for:

- HQL queries
- Hibernate repositories
- Database access

---

# JSP / Frontend

Read:

- .ai/frontend/jsp-rules.md

Use for:

- JSP pages
- Bootstrap UI
- Form binding

---

# Authentication / Authorization

Read:

- .ai/business/auth-rules.md
- .ai/business/security-rules.md

- .ai/database/tables/taikhoan.md
- .ai/database/tables/giaovien.md

Use for:

- Login
- Session handling
- Role permissions
- Interceptors

---

# Exam Module

Read:

- .ai/business/exam-rules.md

- .ai/database/tables/bode.md
- .ai/database/tables/baithi.md
- .ai/database/tables/chitiet-baithi.md
- .ai/database/tables/giaovien-dangky.md
- .ai/database/tables/bangdiem.md

Use for:

- Random questions
- Scoring
- Exam timer
- Question distribution

---

# Database Loading Rules

Rules:

- Never load all database table files
- Load only related tables
- Load relationships.md only for joins or entity mappings
- Prefer isolated schema context

Examples:

Student CRUD:

- sinhvien.md
- lop.md

Exam Service:

- bode.md
- baithi.md
- chitiet-baithi.md

Authentication:

- taikhoan.md
- giaovien.md

---

# Bug Fixing

Read:

- Only files related to the bug

Always read:

- .ai/core/architecture.md

Avoid:

- Loading unrelated modules
- Re-reading all rules

---

# Performance Rules

- Never load all .md files unless required
- Prefer targeted context loading
- Read business rules only for business logic
- Read frontend rules only for UI tasks
- Read auth rules only for authentication tasks
- Avoid wildcard loading
- Prefer exact file references

---

# Architecture Rules

Always preserve:

Controller -> Service -> Repository -> Database

Forbidden:

- SQL in controller
- Business logic in JSP
- Repository access from controller

---

# Output Requirements

Generated code must be:

- Maven compatible
- Eclipse compatible
- Layered
- Maintainable
- Readable
- Modular
- Reusable
