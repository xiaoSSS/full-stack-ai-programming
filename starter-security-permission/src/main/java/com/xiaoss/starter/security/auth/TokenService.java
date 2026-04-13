package com.xiaoss.starter.security.auth;

import com.xiaoss.starter.security.context.SecurityPrincipal;

public interface TokenService {
    TokenPair issueTokenPair(SecurityPrincipal principal);
    TokenClaims parse(String token);
    void revoke(String token);
    boolean isRevoked(String token);
}
