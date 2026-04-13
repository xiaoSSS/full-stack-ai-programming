package com.xiaoss.starter.web.context;

public class Context {

    private IUser user;

    private ISystem system;

    public IUser getUser() {
        return user;
    }

    public void setUser(IUser user) {
        this.user = user;
    }

    public ISystem getSystem() {
        return system;
    }

    public void setSystem(ISystem system) {
        this.system = system;
    }
}
