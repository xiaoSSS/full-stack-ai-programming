package com.xiaoss.starter.security.auth;

import com.xiaoss.starter.security.context.SecurityPrincipal;
import java.util.Set;

public interface PermissionProvider {
    Set<String> permissions(SecurityPrincipal principal);
}
