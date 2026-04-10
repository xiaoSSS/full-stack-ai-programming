package com.xiaoss.starter.web.autoconfigure;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.xiaoss.starter.web.context.ContextArgumentResolver;
import com.xiaoss.starter.web.context.ContextWebMvcConfigurer;
import com.xiaoss.starter.web.context.IUserConfig;
import com.xiaoss.starter.web.enums.EnumArgumentConverterFactory;
import com.xiaoss.starter.web.enums.EnumConversionWebMvcConfigurer;
import com.xiaoss.starter.web.properties.WebStarterProperties;
import com.xiaoss.starter.web.support.GlobalExceptionHandler;
import java.util.TimeZone;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@AutoConfiguration
@ConditionalOnClass(Jackson2ObjectMapperBuilder.class)
@EnableConfigurationProperties(WebStarterProperties.class)
public class WebAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(name = "xiaossWebJacksonCustomizer")
    public Jackson2ObjectMapperBuilderCustomizer xiaossWebJacksonCustomizer(WebStarterProperties properties) {
        return builder -> {
            builder.simpleDateFormat(properties.getDateTimePattern());
            builder.timeZone(TimeZone.getTimeZone(properties.getZoneId()));
            if (properties.isWriteLongAsString()) {
                builder.modules(longToStringModule());
            }
        };
    }

    @Bean
    @ConditionalOnMissingBean
    public GlobalExceptionHandler globalExceptionHandler() {
        return new GlobalExceptionHandler();
    }

    @Bean
    @ConditionalOnClass(WebMvcConfigurer.class)
    @ConditionalOnProperty(prefix = "xiaoss.web", name = "context-enabled", havingValue = "true", matchIfMissing = true)
    @ConditionalOnMissingBean
    public ContextArgumentResolver contextArgumentResolver(ObjectProvider<IUserConfig> userConfig) {
        return new ContextArgumentResolver(userConfig.getIfAvailable());
    }

    @Bean
    @ConditionalOnClass(WebMvcConfigurer.class)
    @ConditionalOnProperty(prefix = "xiaoss.web", name = "context-enabled", havingValue = "true", matchIfMissing = true)
    @ConditionalOnMissingBean(name = "xiaossContextWebMvcConfigurer")
    public WebMvcConfigurer xiaossContextWebMvcConfigurer(ContextArgumentResolver contextArgumentResolver) {
        return new ContextWebMvcConfigurer(contextArgumentResolver);
    }

    @Bean
    @ConditionalOnClass(WebMvcConfigurer.class)
    @ConditionalOnProperty(prefix = "xiaoss.web", name = "enum-converter-enabled", havingValue = "true", matchIfMissing = true)
    @ConditionalOnMissingBean
    public EnumArgumentConverterFactory enumArgumentConverterFactory() {
        return new EnumArgumentConverterFactory();
    }

    @Bean
    @ConditionalOnClass(WebMvcConfigurer.class)
    @ConditionalOnProperty(prefix = "xiaoss.web", name = "enum-converter-enabled", havingValue = "true", matchIfMissing = true)
    @ConditionalOnMissingBean(name = "xiaossEnumConversionWebMvcConfigurer")
    public WebMvcConfigurer xiaossEnumConversionWebMvcConfigurer(
        EnumArgumentConverterFactory enumArgumentConverterFactory) {
        return new EnumConversionWebMvcConfigurer(enumArgumentConverterFactory);
    }

    private Module longToStringModule() {
        SimpleModule module = new SimpleModule();
        module.addSerializer(Long.class, ToStringSerializer.instance);
        module.addSerializer(Long.TYPE, ToStringSerializer.instance);
        return module;
    }
}
