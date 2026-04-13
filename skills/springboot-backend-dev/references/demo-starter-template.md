# Demo Starter Template Baseline

## Goal

Use `demo-starter-app` as the canonical backend integration template for xiaoss starters.

## Baseline structure

- `DemoStarterApplication` as bootstrap class
- `controller` layer with starter capability examples
- `application.yml` with consolidated starter configs
- `README.md` with run and verification commands

## Required starter dependencies

1. `starter-web`
2. `starter-security-permission`
3. `starter-data-redis`
4. `starter-datasource-druid`
5. `starter-mybatis`
6. `starter-swagger`

## Endpoint template

- `POST /demo/login`: issue token via `TokenService`
- `GET /demo/secure`: verify annotation authorization
- `POST /demo/cache/{key}`: write redis value via `RedisOpsSupport`
- `GET /demo/cache/{key}`: read redis value via `RedisOpsSupport`

## Configuration template

- `permission.*`: auth header/prefix/issuer/secret/ttl and ignore paths
- `datasource.druid.*`: local datasource/routing defaults
- `redis.*`: key prefix/ttl/null marker settings
- `springdoc.*`: OpenAPI switches
- `management.endpoints.*`: health/info exposure

## Stable execution checklist

- Keep `/demo/login` in `permission.ignore-paths`.
- Keep secured endpoints covered with `@RequireLogin` and `@RequirePermission`.
- Keep configuration examples copyable and environment-variable ready.
- Keep README with exact curl commands for smoke tests.
