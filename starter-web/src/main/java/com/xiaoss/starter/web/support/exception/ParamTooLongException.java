package com.xiaoss.starter.web.support.exception;

public class ParamTooLongException extends RuntimeException {

    public ParamTooLongException(String message) {
        super(message);
    }
}
