package com.xiaoss.starter.redis.properties;

import java.time.Duration;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "redis")
public class RedisStarterProperties {

    private String keyPrefix = "";
    private Duration defaultTtl = Duration.ofMinutes(30);
    private Duration nullValueTtl = Duration.ofMinutes(2);
    private String nullValueMarker = "__NULL__";
    private final Pool pool = new Pool();

    public String getKeyPrefix() {
        return keyPrefix;
    }

    public void setKeyPrefix(String keyPrefix) {
        this.keyPrefix = keyPrefix;
    }

    public Duration getDefaultTtl() {
        return defaultTtl;
    }

    public void setDefaultTtl(Duration defaultTtl) {
        this.defaultTtl = defaultTtl;
    }

    public Duration getNullValueTtl() {
        return nullValueTtl;
    }

    public void setNullValueTtl(Duration nullValueTtl) {
        this.nullValueTtl = nullValueTtl;
    }

    public String getNullValueMarker() {
        return nullValueMarker;
    }

    public void setNullValueMarker(String nullValueMarker) {
        this.nullValueMarker = nullValueMarker;
    }

    public Pool getPool() {
        return pool;
    }

    public static class Pool {

        private boolean enabled = true;
        private int maxActive = 16;
        private int maxIdle = 16;
        private int minIdle = 0;
        private Duration maxWait = Duration.ofSeconds(5);

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        public int getMaxActive() {
            return maxActive;
        }

        public void setMaxActive(int maxActive) {
            this.maxActive = maxActive;
        }

        public int getMaxIdle() {
            return maxIdle;
        }

        public void setMaxIdle(int maxIdle) {
            this.maxIdle = maxIdle;
        }

        public int getMinIdle() {
            return minIdle;
        }

        public void setMinIdle(int minIdle) {
            this.minIdle = minIdle;
        }

        public Duration getMaxWait() {
            return maxWait;
        }

        public void setMaxWait(Duration maxWait) {
            this.maxWait = maxWait;
        }
    }
}
