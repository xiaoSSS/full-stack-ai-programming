# Project Structure

## Suggested layout

- `src/pages/` for route-level pages
- `src/components/` for shared UI
- `src/composables/` for reusable logic
- `src/stores/` for Pinia stores
- `src/services/` for HTTP/API modules
- `src/types/` for shared TypeScript types

## Rules

- Keep domain-specific code together by feature when project grows.
- Keep cross-cutting utilities in separate `utils` modules.
- Avoid direct API calls inside deeply nested UI components.
