package com.xiaoss.starter.security.auth;

import java.time.Duration;
import org.springframework.data.redis.core.StringRedisTemplate;

public class RedisTokenStore implements TokenStore {

    private static final String REVOKED_TOKEN_KEY_PREFIX = "permission:auth:revoked-token:";

    private final StringRedisTemplate stringRedisTemplate;

    public RedisTokenStore(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public void revoke(String token, Duration ttl) {
        if (ttl == null || ttl.isZero() || ttl.isNegative()) {
            return;
        }
        stringRedisTemplate.opsForValue().set(key(token), "", ttl);
    }

    @Override
    public boolean isRevoked(String token) {
        Boolean exists = stringRedisTemplate.hasKey(key(token));
        return Boolean.TRUE.equals(exists);
    }

    private String key(String token) {
        return REVOKED_TOKEN_KEY_PREFIX + token;
    }
}
