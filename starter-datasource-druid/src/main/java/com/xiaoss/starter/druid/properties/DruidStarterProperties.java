package com.xiaoss.starter.druid.properties;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "datasource.druid")
public class DruidStarterProperties {

    /**
     * Backward-compatible single datasource URL. Used as primary datasource when primary.url is empty.
     */
    private String url;
    private String username;
    private String password;
    private String driverClassName = "com.mysql.cj.jdbc.Driver";

    private int initialSize = 5;
    private int minIdle = 5;
    private int maxActive = 20;
    private Duration maxWait = Duration.ofSeconds(30);

    private DatasourceNodeProperties primary = new DatasourceNodeProperties();
    private List<DatasourceNodeProperties> replicas = new ArrayList<>();

    /**
     * Backward-compatible flag. Prefer datasource.druid.routing.enabled.
     */
    private boolean readWriteSeparationEnabled = false;

    private RoutingProperties routing = new RoutingProperties();

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
    public DatasourceNodeProperties getPrimary() { return primary; }
    public void setPrimary(DatasourceNodeProperties primary) { this.primary = primary; }
    public List<DatasourceNodeProperties> getReplicas() { return replicas; }
    public void setReplicas(List<DatasourceNodeProperties> replicas) { this.replicas = replicas; }
    public boolean isReadWriteSeparationEnabled() { return readWriteSeparationEnabled; }
    public void setReadWriteSeparationEnabled(boolean readWriteSeparationEnabled) { this.readWriteSeparationEnabled = readWriteSeparationEnabled; }
    public RoutingProperties getRouting() { return routing; }
    public void setRouting(RoutingProperties routing) { this.routing = routing; }

    public boolean isRoutingEnabled() {
        return readWriteSeparationEnabled || routing.enabled;
    }

    public static class DatasourceNodeProperties {
        private String url;
        private String username;
        private String password;
        private String driverClassName;

        public String getUrl() { return url; }
        public void setUrl(String url) { this.url = url; }
        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
        public String getDriverClassName() { return driverClassName; }
        public void setDriverClassName(String driverClassName) { this.driverClassName = driverClassName; }
    }

    public static class RoutingProperties {
        /**
         * Enable read/write routing datasource.
         */
        private boolean enabled = false;
        /**
         * Enable @ReadOnlyRoute/@WriteRoute annotation based routing.
         */
        private boolean annotationEnabled = true;
        /**
         * Fail fast when routing is enabled but primary/replica config is invalid.
         */
        private boolean strict = true;

        public boolean isEnabled() { return enabled; }
        public void setEnabled(boolean enabled) { this.enabled = enabled; }
        public boolean isAnnotationEnabled() { return annotationEnabled; }
        public void setAnnotationEnabled(boolean annotationEnabled) { this.annotationEnabled = annotationEnabled; }
        public boolean isStrict() { return strict; }
        public void setStrict(boolean strict) { this.strict = strict; }
    }
}
