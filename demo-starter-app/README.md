# demo-starter-app

Demo Spring Boot application that integrates xiaoss starters:

- starter-web
- starter-security-permission
- starter-data-redis
- starter-datasource-druid
- starter-mybatis
- starter-swagger

## Run

```bash
mvn -pl demo-starter-app -am spring-boot:run
```

## Demo API

1. Login and obtain token

```bash
curl -X POST http://localhost:8088/demo/login
```

2. Access secure endpoint

```bash
curl -H "Authorization: Bearer <access_token>" http://localhost:8088/demo/secure
```

3. Write/read cache

```bash
curl -X POST -H "Authorization: Bearer <access_token>" http://localhost:8088/demo/cache/foo
curl -H "Authorization: Bearer <access_token>" http://localhost:8088/demo/cache/foo
```

4. Swagger UI

- http://localhost:8088/swagger-ui/index.html
