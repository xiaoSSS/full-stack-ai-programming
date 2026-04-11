---
name: springboot-backend-dev
description: Build and maintain Spring Boot backend services including API design, domain logic, persistence, validation, exception handling, and transaction-safe workflows. Use when requests involve controller/service/repository implementation, data modeling, endpoint behavior, or backend integration contracts, especially when services should reuse xiaoss starters (web, redis, druid, mybatis, swagger, utils, security-permission).
---

# Spring Boot Backend Development

## Implement layered design

- Keep `controller`, `service`, and `repository` responsibilities clear.
- Keep DTO/VO/entity mappings explicit.
- Keep transactional boundaries in service layer.

## Prefer platform starters first

1. Use `starter-web` for response envelope, global exceptions, context injection, and enum conversion.
2. Use `starter-datasource-druid` + `starter-mybatis` as default data access stack.
3. Use `starter-data-redis` for cache, lock, and cache-aside patterns.
4. Use `starter-swagger` for API documentation baseline.
5. Use `starter-security-permission` for authentication, authorization, and token baseline.
6. Use `starter-utils` only as shared helper layer; avoid business logic in utils.

## Build robust APIs

1. Validate input with bean validation.
2. Use unified response and error format.
3. Define stable error code taxonomy.
4. Keep OpenAPI documentation aligned with implementation.

## Build persistence safely

- Use explicit pagination and sorting constraints.
- Guard writes with optimistic locking or business constraints when needed.
- Keep audit fields and soft-delete behavior consistent.

Read details in:
- `references/layered-architecture.md`
- `references/exception-and-response.md`
- `references/starter-integration.md`
- `references/security-permission-integration.md`

Use scaffolding resources:
- `assets/module-template/`
- `scripts/gen-module.sh`
