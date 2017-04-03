package com.hexicloud.portaldb.exceptions;

import org.apache.log4j.Logger;

import org.springframework.security.core.AuthenticationException;

public class JwtAuthenticationException extends AuthenticationException {
    private static final Logger logger = Logger.getLogger(JwtAuthenticationException.class);
    public JwtAuthenticationException(String msg, Throwable t) {
        super(msg, t);
        logger.error(msg);
    }
}
