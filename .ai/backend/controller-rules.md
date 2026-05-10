# Controller Rules

- Use @Controller
- Use @ModelAttribute for form binding
- Use @Valid for validation
- Return JSP view names only
- Keep controller methods small

Avoid:

- SQL
- Business logic
- Manual parameter parsing
- Large methods

URL style:

GET /subjects
GET /subjects/{id}
POST /subjects
PUT /subjects/{id}
DELETE /subjects/{id}
