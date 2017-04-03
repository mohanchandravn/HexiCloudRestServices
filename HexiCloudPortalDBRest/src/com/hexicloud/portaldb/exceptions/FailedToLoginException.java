package com.hexicloud.portaldb.exceptions;

import static java.lang.String.format;

import org.apache.log4j.Logger;

public class FailedToLoginException extends RuntimeException {
    private static final Logger logger = Logger.getLogger(FailedToLoginException.class);
    public FailedToLoginException(String username) {
           super(format("Failed to login with username %s", username));
           logger.error("Failed to login with username %s" + username);
       }
}
