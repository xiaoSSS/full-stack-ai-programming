package com.xiaoss.starter.swagger.autoconfigure;

import com.xiaoss.starter.swagger.properties.SwaggerStarterProperties;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
@ConditionalOnClass(OpenAPI.class)
@ConditionalOnProperty(prefix = "xiaoss.swagger", name = "enabled", havingValue = "true", matchIfMissing = true)
@EnableConfigurationProperties(SwaggerStarterProperties.class)
public class SwaggerAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public OpenAPI openAPI(SwaggerStarterProperties properties) {
        return new OpenAPI()
            .info(new Info()
                .title(properties.getTitle())
                .description(properties.getDescription())
                .version(properties.getVersion()));
    }
}
