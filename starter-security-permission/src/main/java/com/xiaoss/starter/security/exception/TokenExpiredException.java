package com.xiaoss.starter.security.exception;

public class TokenExpiredException extends UnauthorizedException {
    public TokenExpiredException(String message) { super(message); }
}
