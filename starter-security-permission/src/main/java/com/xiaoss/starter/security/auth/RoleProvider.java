package com.xiaoss.starter.security.auth;

import com.xiaoss.starter.security.context.SecurityPrincipal;
import java.util.Set;

public interface RoleProvider {
    Set<String> roles(SecurityPrincipal principal);
}
