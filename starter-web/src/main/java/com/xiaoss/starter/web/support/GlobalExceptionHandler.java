package com.xiaoss.starter.web.support;

import com.xiaoss.starter.web.support.exception.NullException;
import com.xiaoss.starter.web.support.exception.ParamIsNullException;
import com.xiaoss.starter.web.support.exception.ParamTooLongException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponse<Void> handleValidationException(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult().getFieldErrors().stream()
            .findFirst()
            .map(error -> error.getField() + ": " + error.getDefaultMessage())
            .orElse("Validation failed");
        return ApiResponse.error("VALIDATION_ERROR", message);
    }

    @ExceptionHandler({ParamIsNullException.class, NullException.class})
    public ApiResponse<Void> handleParamNull(RuntimeException ex) {
        return ApiResponse.error("PARAM_IS_NULL", ex.getMessage());
    }

    @ExceptionHandler(ParamTooLongException.class)
    public ApiResponse<Void> handleParamTooLong(ParamTooLongException ex) {
        return ApiResponse.error("PARAM_TOO_LONG", ex.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ApiResponse<Void> handleIllegalArgument(IllegalArgumentException ex) {
        return ApiResponse.error("ILLEGAL_ARGUMENT", ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ApiResponse<Void> handleException(Exception ex) {
        return ApiResponse.error("INTERNAL_ERROR", ex.getMessage());
    }
}
