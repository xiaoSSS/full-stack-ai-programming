package com.xiaoss.starter.web.enums;

import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class EnumConversionWebMvcConfigurer implements WebMvcConfigurer {

    private final EnumArgumentConverterFactory enumArgumentConverterFactory;

    public EnumConversionWebMvcConfigurer(EnumArgumentConverterFactory enumArgumentConverterFactory) {
        this.enumArgumentConverterFactory = enumArgumentConverterFactory;
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverterFactory(enumArgumentConverterFactory);
    }
}
