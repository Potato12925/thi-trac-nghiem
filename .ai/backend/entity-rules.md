# Entity Rules

- Create in src\main\java\com\entity
- Use @Entity
- Use @Table(name="")
- Use @Id
- Use manual business keys

Avoid:

- @GeneratedValue
- Business logic inside entity

Exception:

- Question ID may use auto increment
