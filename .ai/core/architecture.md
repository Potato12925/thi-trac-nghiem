# Architecture

Flow:

Controller -> Service -> Repository -> Database

Rules:

- Controller = request/response only
- Service = business logic
- Repository = database access
- JSP = UI rendering only

Forbidden:

- SQL in controller
- Business logic in JSP
- Repository access from controller
