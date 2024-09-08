package com.assignment.task_manager.utility;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

public abstract class TokenDetails {
    /**
     * Get the email from the JWT token.
     * @return the email extracted from the JWT token, or null if not present.
     */
    public static String getEmail() {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (jwt != null) {
            return jwt.getClaim("email");
        }
        return null;
    }
}
