package com.xiaoss.starter.web.context;

import java.util.List;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class ContextWebMvcConfigurer implements WebMvcConfigurer {

    private final ContextArgumentResolver contextArgumentResolver;

    public ContextWebMvcConfigurer(ContextArgumentResolver contextArgumentResolver) {
        this.contextArgumentResolver = contextArgumentResolver;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(contextArgumentResolver);
    }
}
