package com.hexicloud.portaldb.auth;

import com.hexicloud.portaldb.bean.User;
import com.hexicloud.portaldb.exceptions.JwtAuthenticationException;
import com.hexicloud.portaldb.service.JwtService;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {
    private static final Logger logger = Logger.getLogger(JwtAuthenticationProvider.class);
    private final JwtService jwtService;

    @SuppressWarnings("unused")
    public JwtAuthenticationProvider() {
        this(null);
    }

    @Autowired
    public JwtAuthenticationProvider(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        logger.info("Calling the jwtService.verify");
        try {
            User user = jwtService.verify((String) authentication.getCredentials());
            return new JwtAuthenticatedProfile(user);
        } catch (Exception e) {
            throw new JwtAuthenticationException("Failed to verify token", e);
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthToken.class.equals(authentication);
    }

}
