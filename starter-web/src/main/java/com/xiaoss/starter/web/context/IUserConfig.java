package com.xiaoss.starter.web.context;

import jakarta.servlet.http.HttpServletRequest;

public interface IUserConfig {

    IUser getUser(HttpServletRequest request);
}
