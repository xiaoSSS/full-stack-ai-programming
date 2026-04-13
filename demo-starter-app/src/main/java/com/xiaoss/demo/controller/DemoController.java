package com.xiaoss.demo.controller;

import com.xiaoss.starter.redis.support.RedisOpsSupport;
import com.xiaoss.starter.security.annotation.RequireLogin;
import com.xiaoss.starter.security.annotation.RequirePermission;
import com.xiaoss.starter.security.auth.TokenPair;
import com.xiaoss.starter.security.auth.TokenService;
import com.xiaoss.starter.security.context.SecurityPrincipal;
import com.xiaoss.starter.web.support.ApiResponse;
import java.time.Duration;
import java.util.Set;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demo")
public class DemoController {

    private final TokenService tokenService;
    private final RedisOpsSupport redisOpsSupport;

    public DemoController(TokenService tokenService, RedisOpsSupport redisOpsSupport) {
        this.tokenService = tokenService;
        this.redisOpsSupport = redisOpsSupport;
    }

    @PostMapping("/login")
    public ApiResponse<TokenPair> login() {
        SecurityPrincipal principal = new SecurityPrincipal(
                "10001",
                "demo-user",
                Set.of("admin"),
                Set.of("demo:read", "demo:write")
        );
        return ApiResponse.success(tokenService.issueTokenPair(principal));
    }

    @RequireLogin
    @RequirePermission("demo:read")
    @GetMapping("/secure")
    public ApiResponse<String> secureEndpoint() {
        return ApiResponse.success("secure endpoint accessed");
    }

    @RequireLogin
    @RequirePermission("demo:write")
    @PostMapping("/cache/{key}")
    public ApiResponse<String> writeCache(@PathVariable String key) {
        redisOpsSupport.setString("demo:" + key, "value-" + key, Duration.ofMinutes(5));
        return ApiResponse.success("ok");
    }

    @RequireLogin
    @RequirePermission("demo:read")
    @GetMapping("/cache/{key}")
    public ApiResponse<String> readCache(@PathVariable String key) {
        return ApiResponse.success(redisOpsSupport.getString("demo:" + key));
    }
}
