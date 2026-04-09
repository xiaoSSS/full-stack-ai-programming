package com.xiaoss.starter.web.autoconfigure;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.xiaoss.starter.web.properties.WebStarterProperties;
import com.xiaoss.starter.web.support.GlobalExceptionHandler;
import java.util.TimeZone;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

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

    private Module longToStringModule() {
        SimpleModule module = new SimpleModule();
        module.addSerializer(Long.class, ToStringSerializer.instance);
        module.addSerializer(Long.TYPE, ToStringSerializer.instance);
        return module;
    }
}
