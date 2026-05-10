# Validation Rules

Flow:

Controller -> DTO validation
Service -> business validation
Repository -> DB validation

DTO rules:

- private fields
- getter/setter
- no business logic

Use:

- @NotBlank
- @Size
- javax Validation
