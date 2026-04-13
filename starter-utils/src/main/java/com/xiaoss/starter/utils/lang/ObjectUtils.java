package com.xiaoss.starter.utils.lang;

public final class ObjectUtils {

    private ObjectUtils() {
    }

    public static boolean isNull(Object value) {
        return value == null;
    }

    public static <T> T requireNonNull(T value, String message) {
        if (value == null) {
            throw new IllegalArgumentException(message);
        }
        return value;
    }
}
