package com.xiaoss.starter.redis.autoconfigure;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xiaoss.starter.redis.properties.RedisStarterProperties;
import com.xiaoss.starter.redis.support.RedisOpsSupport;
import io.lettuce.core.api.StatefulConnection;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.redis.LettuceClientConfigurationBuilderCustomizer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.StringRedisTemplate;

@AutoConfiguration
@ConditionalOnClass(StringRedisTemplate.class)
@EnableConfigurationProperties(RedisStarterProperties.class)
public class RedisAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public RedisOpsSupport redisOpsSupport(StringRedisTemplate stringRedisTemplate,
                                           ObjectMapper objectMapper,
                                           RedisStarterProperties properties) {
        return new RedisOpsSupport(stringRedisTemplate, objectMapper, properties);
    }

    @Bean
    @ConditionalOnProperty(prefix = "redis.pool", name = "enabled", havingValue = "true", matchIfMissing = true)
    @ConditionalOnMissingBean
    public GenericObjectPoolConfig<StatefulConnection<?, ?>> redisPoolConfig(RedisStarterProperties properties) {
        RedisStarterProperties.Pool pool = properties.getPool();
        GenericObjectPoolConfig<StatefulConnection<?, ?>> config = new GenericObjectPoolConfig<>();
        config.setMaxTotal(pool.getMaxActive());
        config.setMaxIdle(pool.getMaxIdle());
        config.setMinIdle(pool.getMinIdle());
        config.setMaxWait(pool.getMaxWait());
        return config;
    }

    @Bean
    @ConditionalOnProperty(prefix = "redis.pool", name = "enabled", havingValue = "true", matchIfMissing = true)
    @ConditionalOnMissingBean(name = "xiaossRedisLettucePoolCustomizer")
    public LettuceClientConfigurationBuilderCustomizer xiaossRedisLettucePoolCustomizer(
        GenericObjectPoolConfig<StatefulConnection<?, ?>> redisPoolConfig) {
        return builder -> {
            if (builder instanceof LettucePoolingClientConfiguration.LettucePoolingClientConfigurationBuilder poolingBuilder) {
                poolingBuilder.poolConfig(redisPoolConfig);
            }
        };
    }
}
