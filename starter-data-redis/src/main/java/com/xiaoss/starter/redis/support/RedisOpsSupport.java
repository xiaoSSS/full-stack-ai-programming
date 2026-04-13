package com.xiaoss.starter.redis.support;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xiaoss.starter.redis.properties.RedisStarterProperties;
import java.time.Duration;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;

public class RedisOpsSupport {

    private static final String UNLOCK_SCRIPT = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";

    private final StringRedisTemplate stringRedisTemplate;
    private final ObjectMapper objectMapper;
    private final RedisStarterProperties properties;

    public RedisOpsSupport(StringRedisTemplate stringRedisTemplate,
                           ObjectMapper objectMapper,
                           RedisStarterProperties properties) {
        this.stringRedisTemplate = stringRedisTemplate;
        this.objectMapper = objectMapper;
        this.properties = properties;
    }

    // -------- string --------

    public void setString(String key, String value, Duration ttl) {
        stringRedisTemplate.opsForValue().set(wrapKey(key), value, ttl == null ? properties.getDefaultTtl() : ttl);
    }

    public String getString(String key) {
        String value = stringRedisTemplate.opsForValue().get(wrapKey(key));
        if (properties.getNullValueMarker().equals(value)) {
            return null;
        }
        return value;
    }

    public <T> void set(String key, T value, Duration ttl) {
        setString(key, toJson(value), ttl);
    }

    public <T> T get(String key, Class<T> clazz) {
        String json = getString(key);
        if (json == null) {
            return null;
        }
        return fromJson(json, clazz);
    }

    public Long increment(String key, long delta) {
        return stringRedisTemplate.opsForValue().increment(wrapKey(key), delta);
    }

    // -------- hash --------

    public <T> void hSet(String key, String hashKey, T value) {
        stringRedisTemplate.opsForHash().put(wrapKey(key), hashKey, toJson(value));
    }

    public <T> T hGet(String key, String hashKey, Class<T> clazz) {
        Object raw = stringRedisTemplate.opsForHash().get(wrapKey(key), hashKey);
        if (raw == null) {
            return null;
        }
        return fromJson(String.valueOf(raw), clazz);
    }

    public Map<Object, Object> hGetAll(String key) {
        return stringRedisTemplate.opsForHash().entries(wrapKey(key));
    }

    // -------- list --------

    public <T> Long lPush(String key, T value) {
        return stringRedisTemplate.opsForList().leftPush(wrapKey(key), toJson(value));
    }

    public <T> Long rPush(String key, T value) {
        return stringRedisTemplate.opsForList().rightPush(wrapKey(key), toJson(value));
    }

    public <T> T lPop(String key, Class<T> clazz) {
        String raw = stringRedisTemplate.opsForList().leftPop(wrapKey(key));
        return raw == null ? null : fromJson(raw, clazz);
    }

    public List<String> lRange(String key, long start, long end) {
        List<String> values = stringRedisTemplate.opsForList().range(wrapKey(key), start, end);
        return values == null ? Collections.emptyList() : values;
    }

    // -------- set --------

    public <T> Long sAdd(String key, T... values) {
        String[] serialized = new String[values.length];
        for (int i = 0; i < values.length; i++) {
            serialized[i] = toJson(values[i]);
        }
        return stringRedisTemplate.opsForSet().add(wrapKey(key), serialized);
    }

    public Set<String> sMembers(String key) {
        Set<String> values = stringRedisTemplate.opsForSet().members(wrapKey(key));
        return values == null ? Collections.emptySet() : values;
    }

    // -------- zset --------

    public <T> Boolean zAdd(String key, T value, double score) {
        return stringRedisTemplate.opsForZSet().add(wrapKey(key), toJson(value), score);
    }

    public Set<String> zRange(String key, long start, long end) {
        Set<String> values = stringRedisTemplate.opsForZSet().range(wrapKey(key), start, end);
        return values == null ? Collections.emptySet() : values;
    }

    // -------- common --------

    public Boolean expire(String key, Duration ttl) {
        return stringRedisTemplate.expire(wrapKey(key), ttl);
    }

    public Boolean delete(String key) {
        return stringRedisTemplate.delete(wrapKey(key));
    }

    public Long delete(Collection<String> keys) {
        if (keys == null || keys.isEmpty()) {
            return 0L;
        }
        List<String> wrapped = keys.stream().map(this::wrapKey).toList();
        return stringRedisTemplate.delete(wrapped);
    }

    // -------- distributed lock --------

    public boolean tryLock(String key, String requestId, Duration ttl) {
        Boolean locked = stringRedisTemplate.opsForValue().setIfAbsent(wrapLockKey(key), requestId, ttl);
        return Boolean.TRUE.equals(locked);
    }

    public boolean unlock(String key, String requestId) {
        DefaultRedisScript<Long> script = new DefaultRedisScript<>();
        script.setScriptText(UNLOCK_SCRIPT);
        script.setResultType(Long.class);
        Long result = stringRedisTemplate.execute(script, List.of(wrapLockKey(key)), requestId);
        return result != null && result > 0;
    }

    // -------- cache penetration guard --------

    public <T> T getWithCacheAside(String key, Supplier<T> loader, Duration ttl, Class<T> clazz) {
        String fullKey = wrapKey(key);
        String raw = stringRedisTemplate.opsForValue().get(fullKey);

        if (raw != null) {
            if (properties.getNullValueMarker().equals(raw)) {
                return null;
            }
            return fromJson(raw, clazz);
        }

        T loaded = loader.get();
        if (loaded == null) {
            stringRedisTemplate.opsForValue().set(fullKey, properties.getNullValueMarker(), properties.getNullValueTtl());
            return null;
        }

        stringRedisTemplate.opsForValue().set(fullKey, toJson(loaded), ttl == null ? properties.getDefaultTtl() : ttl);
        return loaded;
    }

    private String wrapKey(String key) {
        return properties.getKeyPrefix() + key;
    }

    private String wrapLockKey(String key) {
        return wrapKey("lock:" + key);
    }

    private String toJson(Object value) {
        try {
            return objectMapper.writeValueAsString(value);
        } catch (JsonProcessingException ex) {
            throw new IllegalArgumentException("Serialize value to JSON failed", ex);
        }
    }

    private <T> T fromJson(String json, Class<T> clazz) {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (JsonProcessingException ex) {
            throw new IllegalArgumentException("Deserialize JSON failed", ex);
        }
    }
}
