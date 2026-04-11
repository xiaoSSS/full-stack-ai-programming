---
name: vue-frontend-dev
description: Build and maintain Vue 3 frontend applications with TypeScript, routing, state management, API integration, and page-level delivery. Use when requests involve Vue pages, components, composables, UI behavior, form/table flows, frontend architecture, or backend API integration from the client side.
---

# Vue Frontend Development

## Implement with standard stack

- Use Vue 3 + TypeScript + Vite.
- Use Vue Router for navigation.
- Use Pinia for state management.
- Keep API calls in dedicated service modules.

## Build page features

1. Define page-level state and loading/error states.
2. Implement form validation and submit/reset flows.
3. Implement table/list pagination and filtering.
4. Provide user feedback for success/failure.

## Keep architecture clean

- Put reusable logic in composables.
- Keep components presentational where possible.
- Isolate side effects from view rendering logic.

Read details in:
- `references/project-structure.md`
- `references/api-layer-pattern.md`

Use scaffolding resources:
- `assets/page-template/`
- `scripts/gen-page.sh`
