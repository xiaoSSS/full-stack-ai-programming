package com.xiaoss.starter.druid.aop;

import com.xiaoss.starter.druid.routing.DataSourceContextHolder;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

@Aspect
@Order(Ordered.HIGHEST_PRECEDENCE)
public class WriteRouteAspect {

    @Around("@within(com.xiaoss.starter.druid.annotation.WriteRoute) || @annotation(com.xiaoss.starter.druid.annotation.WriteRoute)")
    public Object routeWrite(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            DataSourceContextHolder.useWrite();
            return joinPoint.proceed();
        } finally {
            DataSourceContextHolder.clear();
        }
    }
}
