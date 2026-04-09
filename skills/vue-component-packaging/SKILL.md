---
name: vue-component-packaging
description: Design and package reusable Vue components and UI primitives with clear props/events/slots contracts, theming, documentation, and versioning. Use when requests require extracting reusable UI elements, building component libraries, or standardizing frontend component APIs.
---

# Vue Component Packaging

## Design reusable contracts

1. Define component responsibilities and boundaries.
2. Keep props typed and minimal.
3. Emit stable events with clear payload types.
4. Expose slots intentionally for extension points.

## Ensure reusability

- Separate style tokens from business logic.
- Support accessibility and keyboard navigation.
- Avoid direct coupling to app-specific stores/services.

## Document and version

- Provide usage examples for common scenarios.
- Record breaking changes and migration notes.
- Follow semantic versioning for package releases.

Read details in:
- `references/component-design-rules.md`

Use scaffolding resources:
- `assets/component-template/`
