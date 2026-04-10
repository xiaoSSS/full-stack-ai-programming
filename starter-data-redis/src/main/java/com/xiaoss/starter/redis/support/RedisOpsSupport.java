package com.xiaoss.starter.redis.support;

import com.xiaoss.starter.redis.properties.RedisStarterProperties;
import java.time.Duration;
import org.springframework.data.redis.core.RedisTemplate;

public class RedisOpsSupport {

    private final RedisTemplate<String, Object> redisTemplate;
    private final RedisStarterProperties properties;

    public RedisOpsSupport(RedisTemplate<String, Object> redisTemplate, RedisStarterProperties properties) {
        this.redisTemplate = redisTemplate;
        this.properties = properties;
    }

    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(wrapKey(key), value, properties.getDefaultTtl());
    }

    public void set(String key, Object value, Duration ttl) {
        redisTemplate.opsForValue().set(wrapKey(key), value, ttl);
    }

    public Object get(String key) {
        return redisTemplate.opsForValue().get(wrapKey(key));
    }

    public Boolean delete(String key) {
        return redisTemplate.delete(wrapKey(key));
    }

    private String wrapKey(String key) {
        return properties.getKeyPrefix() + key;
    }
}
