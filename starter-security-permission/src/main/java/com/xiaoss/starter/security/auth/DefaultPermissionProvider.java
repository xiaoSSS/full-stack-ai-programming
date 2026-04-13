package com.xiaoss.starter.security.auth;

import com.xiaoss.starter.security.context.SecurityPrincipal;
import java.util.Set;

public class DefaultPermissionProvider implements PermissionProvider {
    @Override
    public Set<String> permissions(SecurityPrincipal principal) {
        return principal.getPermissions();
    }
}
