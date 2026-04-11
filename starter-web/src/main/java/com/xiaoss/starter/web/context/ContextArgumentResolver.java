package com.xiaoss.starter.web.context;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class ContextArgumentResolver implements HandlerMethodArgumentResolver {

    private static final Logger logger = LoggerFactory.getLogger(ContextArgumentResolver.class);

    private final IUserConfig userConfig;

    public ContextArgumentResolver(IUserConfig userConfig) {
        this.userConfig = userConfig;
    }

    @Override
    public boolean supportsParameter(@NonNull MethodParameter parameter) {
        return Context.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(@NonNull MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  @NonNull NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) {
        Context context = new Context();
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        if (request == null) {
            return context;
        }

        setSystemInfo(context, request);

        if (userConfig != null) {
            if (logger.isDebugEnabled()) {
                logger.debug("Set user information into Context");
            }
            context.setUser(userConfig.getUser(request));
        }

        return context;
    }

    private void setSystemInfo(Context context, HttpServletRequest request) {
        if (logger.isDebugEnabled()) {
            logger.debug("Set system information into Context");
        }

        SystemImpl system = new SystemImpl();
        system.setMethod(request.getMethod());
        system.setRequestURI(request.getRequestURI());
        system.setServletPath(request.getServletPath());
        system.setServerName(request.getServerName());
        system.setServerPort(request.getServerPort());
        system.setRemoteAddr(IpUtil.getIpAddr(request));
        system.setRemoteHost(request.getRemoteHost());
        system.setProtocol(request.getProtocol());
        context.setSystem(system);
    }
}
