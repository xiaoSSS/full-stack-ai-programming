package com.xiaoss.starter.druid.properties;

import java.time.Duration;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "datasource.druid")
public class DruidStarterProperties {

    private String url;
    private String username;
    private String password;
    private String driverClassName = "com.mysql.cj.jdbc.Driver";

    private int initialSize = 5;
    private int minIdle = 5;
    private int maxActive = 20;
    private Duration maxWait = Duration.ofSeconds(30);

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getDriverClassName() { return driverClassName; }
    public void setDriverClassName(String driverClassName) { this.driverClassName = driverClassName; }
    public int getInitialSize() { return initialSize; }
    public void setInitialSize(int initialSize) { this.initialSize = initialSize; }
    public int getMinIdle() { return minIdle; }
    public void setMinIdle(int minIdle) { this.minIdle = minIdle; }
    public int getMaxActive() { return maxActive; }
    public void setMaxActive(int maxActive) { this.maxActive = maxActive; }
    public Duration getMaxWait() { return maxWait; }
    public void setMaxWait(Duration maxWait) { this.maxWait = maxWait; }
}
