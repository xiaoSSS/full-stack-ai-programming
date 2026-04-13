package com.xiaoss.starter.security.auth;

import java.time.Duration;

public interface TokenStore {
    void revoke(String token, Duration ttl);
    boolean isRevoked(String token);
}
