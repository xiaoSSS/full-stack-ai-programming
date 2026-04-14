package com.xiaoss.starter.security.auth;

import com.xiaoss.starter.security.context.SecurityPrincipal;
import com.xiaoss.starter.security.exception.TokenExpiredException;
import com.xiaoss.starter.security.exception.TokenInvalidException;
import com.xiaoss.starter.security.properties.PermissionStarterProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class JwtTokenService implements TokenService {

    private static final String CLAIM_ROLES = "roles";
    private static final String CLAIM_PERMISSIONS = "permissions";
    private static final String CLAIM_TOKEN_TYPE = "tokenType";

    private final PermissionStarterProperties.AuthProperties properties;
    private final TokenStore tokenStore;

    public JwtTokenService(PermissionStarterProperties.AuthProperties properties, TokenStore tokenStore) {
        this.properties = properties;
        this.tokenStore = tokenStore;
    }

    @Override
    public TokenPair issueTokenPair(SecurityPrincipal principal) {
        String accessToken = buildToken(principal, TokenType.ACCESS, properties.getAccessTtl());
        String refreshToken = buildToken(principal, TokenType.REFRESH, properties.getRefreshTtl());
        return new TokenPair(accessToken, refreshToken);
    }

    @Override
    public TokenClaims parse(String token) {
        if (isRevoked(token)) {
            throw new TokenInvalidException("Token has been revoked");
        }
        try {
            Claims claims = Jwts.parser().verifyWith(signingKey())
                    .clockSkewSeconds(properties.getClockSkewSeconds())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            TokenType type = TokenType.valueOf((String) claims.get(CLAIM_TOKEN_TYPE));
            return new TokenClaims(
                    claims.getSubject(),
                    claims.get("username", String.class),
                    toStringSet(claims.get(CLAIM_ROLES)),
                    toStringSet(claims.get(CLAIM_PERMISSIONS)),
                    type
            );
        } catch (ExpiredJwtException e) {
            throw new TokenExpiredException("Token expired");
        } catch (JwtException | IllegalArgumentException e) {
            throw new TokenInvalidException("Token invalid");
        }
    }

    @Override
    public void revoke(String token) {
        tokenStore.revoke(token, resolveRemainingTtl(token));
    }

    @Override
    public boolean isRevoked(String token) {
        return tokenStore.isRevoked(token);
    }

    private String buildToken(SecurityPrincipal principal, TokenType tokenType, Duration ttl) {
        Instant now = Instant.now();
        return Jwts.builder()
                .subject(principal.getUserId())
                .issuer(properties.getIssuer())
                .claim("username", principal.getUsername())
                .claim(CLAIM_ROLES, principal.getRoles())
                .claim(CLAIM_PERMISSIONS, principal.getPermissions())
                .claim(CLAIM_TOKEN_TYPE, tokenType.name())
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plus(ttl)))
                .signWith(signingKey())
                .compact();
    }

    private Set<String> toStringSet(Object value) {
        if (!(value instanceof Iterable<?> iterable)) {
            return Set.of();
        }
        Set<String> result = new HashSet<>();
        for (Object item : iterable) {
            if (item != null) {
                result.add(String.valueOf(item));
            }
        }
        return result;
    }

    private javax.crypto.SecretKey signingKey() {
        byte[] keyBytes = properties.getSecret().getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes.length >= 32 ? keyBytes : padKey(keyBytes));
    }

    private Duration resolveRemainingTtl(String token) {
        try {
            Claims claims = Jwts.parser().verifyWith(signingKey())
                    .clockSkewSeconds(properties.getClockSkewSeconds())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            Date expiration = claims.getExpiration();
            if (expiration == null) {
                return properties.getRefreshTtl();
            }
            Duration remaining = Duration.between(Instant.now(), expiration.toInstant());
            if (remaining.isNegative()) {
                return Duration.ZERO;
            }
            return remaining;
        } catch (JwtException | IllegalArgumentException e) {
            return properties.getRefreshTtl();
        }
    }

    private byte[] padKey(byte[] raw) {
        byte[] padded = new byte[32];
        System.arraycopy(raw, 0, padded, 0, Math.min(raw.length, padded.length));
        return padded;
    }
}
