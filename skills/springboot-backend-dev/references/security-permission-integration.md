# Security Permission Integration

## Goal

Use `starter-security-permission` as the unified authentication and authorization baseline for backend services.

## Dependency

Add `starter-security-permission` with `starter-web` and persistence starters.

## Core capabilities

- JWT access/refresh token support
- Request authentication filter
- Declarative authorization annotations
- RBAC provider extension points

## Recommended configuration

```yaml
permission:
  enabled: true
  ignore-paths:
    - /auth/login
    - /auth/refresh
    - /swagger-ui/**
    - /v3/api-docs/**
  auth:
    header: Authorization
    prefix: "Bearer "
    issuer: xiaoss
    secret: ${PERMISSION_JWT_SECRET}
    access-ttl: 2h
    refresh-ttl: 7d
```

## Extension points

- Implement `PermissionProvider` to load permissions by user from DB/cache.
- Implement `RoleProvider` to load roles by user.
- Replace default `TokenStore` with Redis-backed store when multi-instance deployment is required.
