package com.hexicloud.portaldb.authentication;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

@Component("customAuthenticationProvider")
public class CustomAuthenticationProvider implements AuthenticationProvider {
    private static final Logger logger = Logger.getLogger(CustomAuthenticationProvider.class);

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String userName = authentication.getName();
        String password = authentication.getCredentials().toString();
        logger.info("Username " + userName);
        if (authorizedUser(userName, password)) {
            List<GrantedAuthority> grantedAuths = new ArrayList<>();
            grantedAuths.add(() -> { return "AUTH_USER"; });
            Authentication auth = new UsernamePasswordAuthenticationToken(userName, password, grantedAuths);
            logger.info(auth.getAuthorities());
            return auth;
        } else {
            throw new AuthenticationCredentialsNotFoundException("Invalid Credentials!");
        }
    }

    private boolean authorizedUser(String userName, String password) {
        logger.info("username is :" + userName + " and password is " + password);
        if ("Chandan".equals(userName) && "Chandan".equals(password))
            return true;
        return false;
    }


    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
