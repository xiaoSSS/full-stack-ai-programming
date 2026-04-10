package com.xiaoss.starter.web.enums;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

public class EnumArgumentConverterFactory implements ConverterFactory<String, BaseEnum<?>> {

    private final Map<Class<?>, Converter<String, ?>> converterMap = new ConcurrentHashMap<>();

    @Override
    @SuppressWarnings("unchecked")
    public <T extends BaseEnum<?>> Converter<String, T> getConverter(Class<T> targetType) {
        return (Converter<String, T>) converterMap.computeIfAbsent(targetType, key -> new StringToEnumConverter<>(targetType));
    }

    private static final class StringToEnumConverter<T extends BaseEnum<?>> implements Converter<String, T> {

        private final Class<T> targetType;

        private StringToEnumConverter(Class<T> targetType) {
            this.targetType = targetType;
        }

        @Override
        @SuppressWarnings("unchecked")
        public T convert(String source) {
            if (source == null || source.isBlank()) {
                return null;
            }

            Method fromValue = findFromValueMethod(targetType);
            if (fromValue != null) {
                try {
                    Object enumObj = fromValue.invoke(null, source);
                    if (enumObj != null) {
                        return (T) enumObj;
                    }
                } catch (ReflectiveOperationException ex) {
                    throw new IllegalArgumentException("Failed to convert enum via fromValue: " + targetType.getName(), ex);
                }
            }

            T[] constants = targetType.getEnumConstants();
            if (constants != null) {
                for (T constant : constants) {
                    Object value = constant.getValue();
                    if (value != null && source.equals(String.valueOf(value))) {
                        return constant;
                    }
                }
            }

            throw new IllegalArgumentException(
                "No enum constant matched value '" + source + "' for type " + targetType.getName());
        }

        private static Method findFromValueMethod(Class<?> targetType) {
            try {
                Method method = targetType.getMethod("fromValue", String.class);
                if (Modifier.isStatic(method.getModifiers())) {
                    method.setAccessible(true);
                    return method;
                }
                return null;
            } catch (NoSuchMethodException ex) {
                return null;
            }
        }
    }
}
