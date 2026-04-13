package com.xiaoss.starter.security.auth;

import com.xiaoss.starter.security.context.SecurityPrincipal;
import java.util.Set;

public class DefaultRoleProvider implements RoleProvider {
    @Override
    public Set<String> roles(SecurityPrincipal principal) {
        return principal.getRoles();
    }
}
