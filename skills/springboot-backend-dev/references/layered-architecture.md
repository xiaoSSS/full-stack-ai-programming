# Layered Architecture

## Controller

- Accept and validate request DTOs.
- Delegate business logic to services.
- Avoid repository access directly from controllers.

## Service

- Implement business rules and orchestration.
- Manage transaction boundaries.
- Emit domain-level errors with stable codes.

## Repository

- Handle persistence only.
- Keep query methods focused and testable.
