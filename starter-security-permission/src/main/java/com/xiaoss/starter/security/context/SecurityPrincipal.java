package com.xiaoss.starter.security.context;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class SecurityPrincipal {
    private final String userId;
    private final String username;
    private final Set<String> roles;
    private final Set<String> permissions;

    public SecurityPrincipal(String userId, String username, Set<String> roles, Set<String> permissions) {
        this.userId = userId;
        this.username = username;
        this.roles = roles == null ? Collections.emptySet() : Collections.unmodifiableSet(new HashSet<>(roles));
        this.permissions = permissions == null ? Collections.emptySet() : Collections.unmodifiableSet(new HashSet<>(permissions));
    }

    public String getUserId() { return userId; }
    public String getUsername() { return username; }
    public Set<String> getRoles() { return roles; }
    public Set<String> getPermissions() { return permissions; }
}
