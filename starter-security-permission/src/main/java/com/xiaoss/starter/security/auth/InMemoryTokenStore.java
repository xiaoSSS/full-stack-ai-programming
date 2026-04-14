package com.xiaoss.starter.security.auth;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryTokenStore implements TokenStore {
    private final Map<String, Instant> revoked = new ConcurrentHashMap<>();

    @Override
    public void revoke(String token, Duration ttl) {
        if (ttl == null || ttl.isZero() || ttl.isNegative()) {
            return;
        }
        revoked.put(token, Instant.now().plus(ttl));
    }

    @Override
    public boolean isRevoked(String token) {
        Instant expiredAt = revoked.get(token);
        if (expiredAt == null) {
            return false;
        }
        if (Instant.now().isAfter(expiredAt)) {
            revoked.remove(token);
            return false;
        }
        return true;
    }
}
