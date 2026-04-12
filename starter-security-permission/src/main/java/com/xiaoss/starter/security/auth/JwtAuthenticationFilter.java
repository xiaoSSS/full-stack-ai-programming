package com.xiaoss.starter.security.auth;

import com.xiaoss.starter.security.context.SecurityPrincipal;
import com.xiaoss.starter.security.context.SecurityPrincipalContextHolder;
import com.xiaoss.starter.security.exception.TokenExpiredException;
import com.xiaoss.starter.security.exception.TokenInvalidException;
import com.xiaoss.starter.security.properties.PermissionStarterProperties;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.http.MediaType;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final PermissionStarterProperties properties;
    private final AntPathMatcher antPathMatcher = new AntPathMatcher();

    public JwtAuthenticationFilter(TokenService tokenService, PermissionStarterProperties properties) {
        this.tokenService = tokenService;
        this.properties = properties;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        if (shouldIgnore(request.getRequestURI())) {
            filterChain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader(properties.getAuth().getHeader());
        if (authHeader == null || !authHeader.startsWith(properties.getAuth().getPrefix())) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(properties.getAuth().getPrefix().length());
        try {
            TokenClaims claims = tokenService.parse(token);
            if (claims.getTokenType() != TokenType.ACCESS) {
                throw new TokenInvalidException("Only access token can be used for request authorization");
            }
            SecurityPrincipalContextHolder.set(new SecurityPrincipal(
                    claims.getUserId(),
                    claims.getUsername(),
                    claims.getRoles(),
                    claims.getPermissions()
            ));
            filterChain.doFilter(request, response);
        } catch (TokenExpiredException | TokenInvalidException ex) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.getWriter().write("{\"code\":\"UNAUTHORIZED\",\"message\":\"" + ex.getMessage() + "\"}");
        } finally {
            SecurityPrincipalContextHolder.clear();
        }
    }

    private boolean shouldIgnore(String path) {
        return properties.getIgnorePaths().stream().anyMatch(pattern -> antPathMatcher.match(pattern, path));
    }
}
