# Exception and Response

## Response conventions

- Return consistent envelope fields across endpoints.
- Keep `traceId` in both logs and responses for diagnostics.
- Use `ApiResponse.success(...)` and `ApiResponse.error(...)` conventions from `starter-web`.
- Attach pageable metadata for paged list endpoints when applicable.

## Exception handling

- Map validation, business, and system exceptions separately.
- Never leak internal stack detail to clients.
- Keep error code + message mapping documented and stable.
- Reuse base exceptions from `starter-web` where they fit (`ParamIsNullException`, `ParamTooLongException`, etc.).
