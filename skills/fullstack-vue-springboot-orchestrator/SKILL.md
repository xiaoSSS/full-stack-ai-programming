---
name: fullstack-vue-springboot-orchestrator
description: Coordinate full-stack delivery with Vue frontend and Spring Boot backend. Use when requests span both frontend and backend, need task decomposition, API contract alignment, cross-layer troubleshooting, or routing work to specialized Vue/Spring skills.
---

# Full-stack Orchestrator (Vue + Spring Boot)

## Define objective and split scope

1. Clarify business goal, roles, and success criteria.
2. Split work into frontend, backend, integration, and acceptance tasks.
3. Assign each subtask to the matching specialized skill.

## Route work to specialized skills

- Route page/state/request tasks to `vue-frontend-dev`.
- Route API/domain/persistence tasks to `springboot-backend-dev`.
- Route reusable UI package work to `vue-component-packaging`.
- Route reusable backend starter/framework work to `springboot-framework-packaging`.

## Enforce contract-first integration

1. Define contract before implementation.
2. Keep request/response schemas stable and versioned.
3. Confirm pagination, error-code, authentication, and date/time format consistency.

Read details in:
- `references/routing-rules.md`
- `references/frontend-backend-contract.md`

## Integration checklist

- Verify API paths, methods, and payload schemas.
- Verify CORS/auth token behavior for browser requests.
- Verify error handling and user feedback mapping.
- Verify idempotency and retry behavior.
