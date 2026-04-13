package com.xiaoss.starter.mybatis.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "mybatis.starter")
public class MybatisStarterProperties {

    private boolean mapUnderscoreToCamelCase = true;
    private String defaultFetchSize = "100";
    private String defaultStatementTimeout = "30";

    public boolean isMapUnderscoreToCamelCase() {
        return mapUnderscoreToCamelCase;
    }

    public void setMapUnderscoreToCamelCase(boolean mapUnderscoreToCamelCase) {
        this.mapUnderscoreToCamelCase = mapUnderscoreToCamelCase;
    }

    public String getDefaultFetchSize() {
        return defaultFetchSize;
    }

    public void setDefaultFetchSize(String defaultFetchSize) {
        this.defaultFetchSize = defaultFetchSize;
    }

    public String getDefaultStatementTimeout() {
        return defaultStatementTimeout;
    }

    public void setDefaultStatementTimeout(String defaultStatementTimeout) {
        this.defaultStatementTimeout = defaultStatementTimeout;
    }
}
