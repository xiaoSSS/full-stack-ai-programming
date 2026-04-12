package com.xiaoss.starter.security.auth;

import java.util.Collections;
import java.util.Set;

public class TokenClaims {
    private final String userId;
    private final String username;
    private final Set<String> roles;
    private final Set<String> permissions;
    private final TokenType tokenType;

    public TokenClaims(String userId, String username, Set<String> roles, Set<String> permissions, TokenType tokenType) {
        this.userId = userId;
        this.username = username;
        this.roles = roles == null ? Collections.emptySet() : roles;
        this.permissions = permissions == null ? Collections.emptySet() : permissions;
        this.tokenType = tokenType;
    }

    public String getUserId() { return userId; }
    public String getUsername() { return username; }
    public Set<String> getRoles() { return roles; }
    public Set<String> getPermissions() { return permissions; }
    public TokenType getTokenType() { return tokenType; }
}
