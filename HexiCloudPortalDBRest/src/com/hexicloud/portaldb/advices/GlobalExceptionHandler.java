package com.hexicloud.portaldb.advices;

import com.hexicloud.portaldb.exceptions.FailedToLoginException;
import com.hexicloud.portaldb.exceptions.UserNotFoundException;

import java.security.SignatureException;

import org.apache.log4j.Logger;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = Logger.getLogger(GlobalExceptionHandler.class);
    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(UserNotFoundException.class)
    public void profileNotFound(UserNotFoundException unfe ) {
        logger.error("Exception is :", unfe);
    }

    @ResponseStatus(UNAUTHORIZED)
    @ExceptionHandler(FailedToLoginException.class)
    public void failedToLogin(FailedToLoginException ftle) {
        logger.error("Exception is :", ftle);
    }

    @ResponseStatus(FORBIDDEN)
    @ExceptionHandler(SignatureException.class)
    public void failedToVerify(SignatureException se) {
        logger.error("Exception is :", se);
    }
}
