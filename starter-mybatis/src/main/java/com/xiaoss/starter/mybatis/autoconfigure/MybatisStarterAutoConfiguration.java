package com.xiaoss.starter.mybatis.autoconfigure;

import com.xiaoss.starter.mybatis.properties.MybatisStarterProperties;
import org.apache.ibatis.session.Configuration;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
@ConditionalOnClass(Configuration.class)
@EnableConfigurationProperties(MybatisStarterProperties.class)
public class MybatisStarterAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(name = "xiaossMybatisConfigurationCustomizer")
    public ConfigurationCustomizer xiaossMybatisConfigurationCustomizer(MybatisStarterProperties properties) {
        return configuration -> {
            configuration.setMapUnderscoreToCamelCase(properties.isMapUnderscoreToCamelCase());
            configuration.setDefaultFetchSize(Integer.parseInt(properties.getDefaultFetchSize()));
            configuration.setDefaultStatementTimeout(Integer.parseInt(properties.getDefaultStatementTimeout()));
        };
    }
}
