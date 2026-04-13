# Frontend/Backend Contract

## Standard response envelope (example)

- success: boolean
- code: string
- message: string
- data: object | array | null
- traceId: string

## Pagination contract (example)

- page: number (1-based)
- size: number
- total: number
- items: array

## Common constraints

- Use ISO-8601 with timezone for date/time fields.
- Return stable error codes; avoid message-only error handling.
- Keep idempotency key support for write endpoints that can be retried.
- Version breaking API changes explicitly.
