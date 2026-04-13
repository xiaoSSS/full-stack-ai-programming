package com.xiaoss.starter.utils.convert;

import com.fasterxml.jackson.databind.ObjectMapper;

public final class ConvertUtils {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private ConvertUtils() {
    }

    public static <T> T convert(Object source, Class<T> targetType) {
        return OBJECT_MAPPER.convertValue(source, targetType);
    }
}
