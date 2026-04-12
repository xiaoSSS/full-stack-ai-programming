package com.xiaoss.starter.security.properties;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "permission")
public class PermissionStarterProperties {

    private boolean enabled = true;
    private List<String> ignorePaths = new ArrayList<>(List.of("/error", "/actuator/**", "/swagger-ui/**", "/v3/api-docs/**"));
    private AuthProperties auth = new AuthProperties();

    public boolean isEnabled() { return enabled; }
    public void setEnabled(boolean enabled) { this.enabled = enabled; }
    public List<String> getIgnorePaths() { return ignorePaths; }
    public void setIgnorePaths(List<String> ignorePaths) { this.ignorePaths = ignorePaths; }
    public AuthProperties getAuth() { return auth; }
    public void setAuth(AuthProperties auth) { this.auth = auth; }

    public static class AuthProperties {
        private String header = "Authorization";
        private String prefix = "Bearer ";
        private String issuer = "xiaoss";
        private String secret = "change-me-to-a-strong-secret-key";
        private Duration accessTtl = Duration.ofHours(2);
        private Duration refreshTtl = Duration.ofDays(7);
        private long clockSkewSeconds = 30;

        public String getHeader() { return header; }
        public void setHeader(String header) { this.header = header; }
        public String getPrefix() { return prefix; }
        public void setPrefix(String prefix) { this.prefix = prefix; }
        public String getIssuer() { return issuer; }
        public void setIssuer(String issuer) { this.issuer = issuer; }
        public String getSecret() { return secret; }
        public void setSecret(String secret) { this.secret = secret; }
        public Duration getAccessTtl() { return accessTtl; }
        public void setAccessTtl(Duration accessTtl) { this.accessTtl = accessTtl; }
        public Duration getRefreshTtl() { return refreshTtl; }
        public void setRefreshTtl(Duration refreshTtl) { this.refreshTtl = refreshTtl; }
        public long getClockSkewSeconds() { return clockSkewSeconds; }
        public void setClockSkewSeconds(long clockSkewSeconds) { this.clockSkewSeconds = clockSkewSeconds; }
    }
}
