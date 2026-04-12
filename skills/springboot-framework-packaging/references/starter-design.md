# Starter Design

## Module boundaries

- Keep each starter focused on one concern.
- Publish clear dependency and capability matrix.
- Keep configuration key namespace consistent.

## Operational defaults

- Use safe defaults for timeout, retry, and logging verbosity.
- Allow full override through properties.
- Provide strict-mode startup validation for high-risk infra features.

## Abstraction checklist

- Is core capability behind interface/annotation extension points?
- Can default beans back off cleanly when business projects provide custom beans?
- Are old config keys still supported when introducing new structured properties?
- Do docs include migration notes and production-safe examples?


## Security starter checklist

- Define explicit auth/authz boundaries (authentication filter, authorization evaluator, exception mapping).
- Ensure secure defaults (stateless session, explicit ignore-paths, strong token secret requirements).
- Provide extension points (`PermissionProvider`, `RoleProvider`, `TokenStore`) with clear back-off behavior.
- Keep compatibility and migration notes for token schema/config changes.
