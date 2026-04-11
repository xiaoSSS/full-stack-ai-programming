package com.xiaoss.starter.security.context;

public final class SecurityPrincipalContextHolder {
    private static final ThreadLocal<SecurityPrincipal> CONTEXT = new ThreadLocal<>();

    private SecurityPrincipalContextHolder() {
    }

    public static void set(SecurityPrincipal principal) {
        CONTEXT.set(principal);
    }

    public static SecurityPrincipal get() {
        return CONTEXT.get();
    }

    public static void clear() {
        CONTEXT.remove();
    }
}
