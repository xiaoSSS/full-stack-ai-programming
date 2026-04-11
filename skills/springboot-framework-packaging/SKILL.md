---
name: springboot-framework-packaging
description: Package reusable Spring Boot framework capabilities into starters or shared modules, including auto-configuration, common middleware, observability, security hooks, and compatibility controls. Use when requests involve extracting platform-level backend capabilities for multi-service reuse.
---

# Spring Boot Framework Packaging

## Package reusable backend capabilities

1. Identify cross-service concerns suitable for shared modules.
2. Isolate feature flags and defaults via configuration properties.
3. Provide auto-configuration with clear conditional loading.

## Provide safe extension points

- Expose interfaces or strategy hooks for service-specific customization.
- Keep default implementations production-safe.
- Avoid forcing unnecessary transitive dependencies.

## Ensure compatibility

- Document supported Spring Boot versions.
- Test auto-configuration activation and back-off behavior.
- Version modules with clear migration notes for breaking changes.

Read details in:
- `references/starter-design.md`
- `references/auto-configuration-patterns.md`

Use scaffolding resources:
- `assets/starter-template/`


## Review and harden abstractions

- Prefer structured properties over flat keys, while keeping compatibility aliases.
- Add strict-mode validation for infra features (datasource routing, messaging, security).
- Ensure extension points support nested invocations and ordering (AOP/context holders).
- Document production defaults and migration guidance in references.
