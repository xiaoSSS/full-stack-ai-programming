# Starter Integration Guide (xiaoss)

## Default backend stack

- API and exception baseline: `starter-web`
- Database and transaction baseline: `starter-datasource-druid`
- DAO baseline: `starter-mybatis`
- Cache/lock baseline: `starter-data-redis`
- API docs baseline: `starter-swagger`
- Shared helpers baseline: `starter-utils`
- Authentication and authorization baseline: `starter-security-permission`

## Suggested dependency order

1. `starter-web`
2. `starter-datasource-druid`
3. `starter-mybatis`
4. `starter-data-redis`
5. `starter-swagger`
6. `starter-utils`
7. `starter-security-permission`

## Service implementation checklist

- Confirm exception and response conventions are inherited from `starter-web`.
- Confirm datasource pool and transaction manager come from `starter-datasource-druid`.
- Confirm mapper configuration comes from `starter-mybatis`.
- Confirm cache key naming and TTL are explicit when using `starter-data-redis`.
- Confirm OpenAPI metadata and grouping are configured with `starter-swagger`.

## Transaction and read/write split conventions

- Use `@Transactional` on service methods; write methods should keep default `readOnly = false`.
- Enable read/write split with either key:
  - `datasource.druid.routing.enabled=true` (preferred)
  - `datasource.druid.read-write-separation-enabled=true` (compatibility)
- Configure nodes:
  - `datasource.druid.primary.*` for master
  - `datasource.druid.replicas[n].*` for read replicas
- Explicit route annotations:
  - `@ReadOnlyRoute` for forced read routing
  - `@WriteRoute` for forced write routing
- Read-only transactions (`@Transactional(readOnly = true)`) are automatically routed to replicas when available.
- Keep `datasource.druid.routing.strict=true` in production to fail fast on invalid routing configuration.


## Permission starter conventions

- Use `starter-security-permission` for login state validation, role/permission checks, and token parsing.
- Keep login endpoints in `permission.ignore-paths` to allow anonymous access.
- Prefer annotations on service methods: `@RequireLogin`, `@RequirePermission`, `@RequireRole`.
- For custom RBAC source, provide `PermissionProvider` / `RoleProvider` beans in business services.


## Demo template workflow

1. Copy structure from `demo-starter-app` (`Application`, `controller`, `application.yml`, `README`).
2. Keep starter dependency order consistent with this guide.
3. Keep `/demo/login`-equivalent endpoint in ignore paths for token bootstrap.
4. Keep at least one secured endpoint and one cache endpoint as smoke examples.
5. Verify with curl commands before handing off to business implementation.
