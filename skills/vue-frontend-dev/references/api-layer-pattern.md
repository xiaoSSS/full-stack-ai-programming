# API Layer Pattern

## Organize request modules

1. Create one service file per domain.
2. Define typed request/response interfaces.
3. Normalize backend envelope and expose domain-friendly return shape.

## Handle errors consistently

- Map backend error codes to user-friendly messages.
- Expose retry hooks for transient failures.
- Surface validation errors next to form fields when possible.
