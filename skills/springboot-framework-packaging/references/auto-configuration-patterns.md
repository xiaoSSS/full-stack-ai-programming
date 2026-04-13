# Auto-Configuration Patterns

## Activation

- Use conditional annotations for classpath, bean, and property presence.
- Back off when user-defined beans exist.
- Keep optional features behind feature flags (e.g. `*.enabled`).

## Validation

- Verify configuration binding at startup.
- Fail fast on invalid critical configuration (e.g. routing enabled but replica URL missing).
- Keep backward compatibility aliases when renaming keys.

## Routing and transaction starter patterns

- Prefer `AbstractRoutingDataSource` for read/write split.
- Support both declarative transaction routing (`@Transactional(readOnly=true)`) and explicit route annotations.
- Make route context nesting-safe to avoid wrong datasource in nested service calls.
- Allow annotation routing to be disabled by config in case of conflict with host applications.


## Permission starter hardening

- Register `SecurityFilterChain` with `@ConditionalOnMissingBean` to avoid overriding app-level security by force.
- Keep auth filter idempotent and request-scoped context cleanup in `finally`.
- Validate critical auth config at startup (e.g. non-default secret in production profile).
- Document unified 401/403 error contracts for frontend/backend consistency.
