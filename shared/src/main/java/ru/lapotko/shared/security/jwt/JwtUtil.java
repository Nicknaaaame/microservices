package ru.lapotko.shared.security.jwt;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.jwt.Jwt;

public final class JwtUtil {
    private JwtUtil() {
    }

    /**
     * Returns current jwt only
     * */
    public static String getJwt() {
        Jwt currentJwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        return currentJwt.getTokenValue();
    }

    /**
     * Returns jwt with Bearer prefix
     * */
    public static String getAuthorizationHeader() {
        return "%s %s".formatted(OAuth2AccessToken.TokenType.BEARER.getValue(), getJwt());
    }
}
