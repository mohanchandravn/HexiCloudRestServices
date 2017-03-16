package com.hexicloud.portaldb.exceptions;

import static java.lang.String.format;

import org.apache.log4j.Logger;

public class UserNotFoundException extends RuntimeException {
    private static final Logger logger = Logger.getLogger(UserNotFoundException.class);
    public UserNotFoundException(String username) {
        super(format("User with username %s does not exist", username));
        logger.error("User with username %s does not exist" + username);
    }
   
}
