# Exception and Response

## Response conventions

- Return consistent envelope fields across endpoints.
- Keep `traceId` in both logs and responses for diagnostics.

## Exception handling

- Map validation, business, and system exceptions separately.
- Never leak internal stack detail to clients.
- Keep error code + message mapping documented and stable.
