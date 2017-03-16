package com.hexicloud.portaldb.auth;

import com.hexicloud.portaldb.bean.User;

import java.util.Collection;
import java.util.Collections;

import org.apache.log4j.Logger;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class JwtAuthenticatedProfile implements Authentication {
    private static final Logger logger = Logger.getLogger(JwtAuthenticatedProfile.class);
    private final User user;

    public JwtAuthenticatedProfile(User user) {
        logger.info("Making the JwtAuthenticatedProfile");
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public Object getCredentials() {
        return null;
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
        return true;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

    }

    @Override
    public String getName() {
        return user.getUserId();
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
