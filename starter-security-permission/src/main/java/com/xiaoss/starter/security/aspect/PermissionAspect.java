package com.xiaoss.starter.security.aspect;

import com.xiaoss.starter.security.annotation.RequireLogin;
import com.xiaoss.starter.security.annotation.RequirePermission;
import com.xiaoss.starter.security.annotation.RequireRole;
import com.xiaoss.starter.security.auth.PermissionProvider;
import com.xiaoss.starter.security.auth.RoleProvider;
import com.xiaoss.starter.security.context.SecurityPrincipal;
import com.xiaoss.starter.security.context.SecurityPrincipalContextHolder;
import com.xiaoss.starter.security.exception.ForbiddenException;
import com.xiaoss.starter.security.exception.UnauthorizedException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Set;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;

@Aspect
public class PermissionAspect {

    private final PermissionProvider permissionProvider;
    private final RoleProvider roleProvider;

    public PermissionAspect(PermissionProvider permissionProvider, RoleProvider roleProvider) {
        this.permissionProvider = permissionProvider;
        this.roleProvider = roleProvider;
    }

    @Before("@within(com.xiaoss.starter.security.annotation.RequireLogin) || @annotation(com.xiaoss.starter.security.annotation.RequireLogin)")
    public void checkLogin() {
        if (SecurityPrincipalContextHolder.get() == null) {
            throw new UnauthorizedException("Login required");
        }
    }

    @Before("@within(com.xiaoss.starter.security.annotation.RequirePermission) || @annotation(com.xiaoss.starter.security.annotation.RequirePermission)")
    public void checkPermission(JoinPoint joinPoint) {
        SecurityPrincipal principal = requirePrincipal();
        RequirePermission requirePermission = resolveAnnotation(joinPoint, RequirePermission.class);
        if (requirePermission == null) {
            return;
        }
        Set<String> permissions = permissionProvider.permissions(principal);
        boolean passed = requirePermission.requireAll()
                ? permissions.containsAll(Arrays.asList(requirePermission.value()))
                : Arrays.stream(requirePermission.value()).anyMatch(permissions::contains);
        if (!passed) {
            throw new ForbiddenException("Permission denied");
        }
    }

    @Before("@within(com.xiaoss.starter.security.annotation.RequireRole) || @annotation(com.xiaoss.starter.security.annotation.RequireRole)")
    public void checkRole(JoinPoint joinPoint) {
        SecurityPrincipal principal = requirePrincipal();
        RequireRole requireRole = resolveAnnotation(joinPoint, RequireRole.class);
        if (requireRole == null) {
            return;
        }
        Set<String> roles = roleProvider.roles(principal);
        boolean passed = requireRole.requireAll()
                ? roles.containsAll(Arrays.asList(requireRole.value()))
                : Arrays.stream(requireRole.value()).anyMatch(roles::contains);
        if (!passed) {
            throw new ForbiddenException("Role denied");
        }
    }

    private SecurityPrincipal requirePrincipal() {
        SecurityPrincipal principal = SecurityPrincipalContextHolder.get();
        if (principal == null) {
            throw new UnauthorizedException("Login required");
        }
        return principal;
    }

    private <T extends Annotation> T resolveAnnotation(JoinPoint joinPoint, Class<T> annotationType) {
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        T annotation = method.getAnnotation(annotationType);
        if (annotation != null) {
            return annotation;
        }
        return joinPoint.getTarget().getClass().getAnnotation(annotationType);
    }
}
