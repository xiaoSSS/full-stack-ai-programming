# Layered Architecture

## Controller

- Accept and validate request DTOs.
- Delegate business logic to services.
- Avoid repository access directly from controllers.
- Return unified response envelope from `starter-web`.

## Service

- Implement business rules and orchestration.
- Manage transaction boundaries.
- Emit domain-level errors with stable codes.
- Prefer `starter-utils` for generic helpers; keep business decisions in service.

## Repository

- Handle persistence only.
- Keep query methods focused and testable.
- Prefer `starter-mybatis` as default DAO stack; use explicit SQL for complex query paths.

## Data and cache

- Use `starter-datasource-druid` for datasource and pooling baseline.
- Use `starter-data-redis` for cache, lock, and anti-penetration flows.
- Keep cache key naming and TTL strategy documented by domain.
