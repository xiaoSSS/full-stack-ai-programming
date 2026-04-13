package com.xiaoss.starter.security.autoconfigure;

import com.xiaoss.starter.security.aspect.PermissionAspect;
import com.xiaoss.starter.security.auth.DefaultPermissionProvider;
import com.xiaoss.starter.security.auth.DefaultRoleProvider;
import com.xiaoss.starter.security.auth.InMemoryTokenStore;
import com.xiaoss.starter.security.auth.JwtAuthenticationFilter;
import com.xiaoss.starter.security.auth.JwtTokenService;
import com.xiaoss.starter.security.auth.PermissionProvider;
import com.xiaoss.starter.security.auth.RoleProvider;
import com.xiaoss.starter.security.auth.TokenService;
import com.xiaoss.starter.security.auth.TokenStore;
import com.xiaoss.starter.security.properties.PermissionStarterProperties;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@AutoConfiguration
@ConditionalOnClass(SecurityFilterChain.class)
@EnableConfigurationProperties(PermissionStarterProperties.class)
@ConditionalOnProperty(prefix = "permission", name = "enabled", havingValue = "true", matchIfMissing = true)
public class PermissionAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public TokenStore tokenStore() {
        return new InMemoryTokenStore();
    }

    @Bean
    @ConditionalOnMissingBean
    public TokenService tokenService(PermissionStarterProperties properties, TokenStore tokenStore) {
        return new JwtTokenService(properties.getAuth(), tokenStore);
    }

    @Bean
    @ConditionalOnMissingBean
    public PermissionProvider permissionProvider() {
        return new DefaultPermissionProvider();
    }

    @Bean
    @ConditionalOnMissingBean
    public RoleProvider roleProvider() {
        return new DefaultRoleProvider();
    }

    @Bean
    @ConditionalOnMissingBean
    public PermissionAspect permissionAspect(PermissionProvider permissionProvider, RoleProvider roleProvider) {
        return new PermissionAspect(permissionProvider, roleProvider);
    }

    @Bean
    @ConditionalOnMissingBean
    public JwtAuthenticationFilter jwtAuthenticationFilter(TokenService tokenService, PermissionStarterProperties properties) {
        return new JwtAuthenticationFilter(tokenService, properties);
    }

    @Bean
    @ConditionalOnMissingBean(name = "xiaossPermissionSecurityFilterChain")
    public SecurityFilterChain xiaossPermissionSecurityFilterChain(HttpSecurity http,
                                                                   PermissionStarterProperties properties,
                                                                   JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> {
                    properties.getIgnorePaths().forEach(pattern -> authorize.requestMatchers(pattern).permitAll());
                    authorize.anyRequest().authenticated();
                })
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }
}
