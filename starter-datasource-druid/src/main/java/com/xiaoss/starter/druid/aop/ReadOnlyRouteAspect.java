package com.xiaoss.starter.druid.aop;

import com.xiaoss.starter.druid.routing.DataSourceContextHolder;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

@Aspect
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ReadOnlyRouteAspect {

    @Around("@within(com.xiaoss.starter.druid.annotation.ReadOnlyRoute) || @annotation(com.xiaoss.starter.druid.annotation.ReadOnlyRoute)")
    public Object routeReadOnly(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            DataSourceContextHolder.useRead();
            return joinPoint.proceed();
        } finally {
            DataSourceContextHolder.clear();
        }
    }
}
