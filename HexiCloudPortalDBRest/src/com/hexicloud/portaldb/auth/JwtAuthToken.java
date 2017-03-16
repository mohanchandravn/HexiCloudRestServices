package com.hexicloud.portaldb.auth;

import java.util.Collection;

import org.apache.log4j.Logger;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

public class JwtAuthToken implements Authentication {
    private static final Logger logger = Logger.getLogger(JwtAuthToken.class);
    private final String token;

    public JwtAuthToken(String token) {
        logger.info("Creating the JwtAuthToken");
        this.token = token;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public Object getCredentials() {
        return token;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }

    @Override
    public boolean isAuthenticated() {
        return false;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public int hashCode() {
        // TODO Implement this method
        return 0;
    }

    @Override
    public boolean equals(Object object) {
        // TODO Implement this method
        return false;
    }

    @Override
    public String toString() {
        // TODO Implement this method
        return null;
    }
}
