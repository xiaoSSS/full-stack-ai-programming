package com.xiaoss.starter.security.exception;

public class TokenInvalidException extends UnauthorizedException {
    public TokenInvalidException(String message) { super(message); }
}
