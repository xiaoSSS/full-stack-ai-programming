package com.xiaoss.starter.web.context;

public interface ISystem {

    String getMethod();

    String getRequestURI();

    String getServletPath();

    String getServerName();

    int getServerPort();

    String getRemoteAddr();

    String getRemoteHost();

    String getProtocol();
}
