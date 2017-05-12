package com.hexicloud.portaldb.advices;

import com.hexicloud.portaldb.exceptions.FailedToLoginException;
import com.hexicloud.portaldb.exceptions.UserNotFoundException;

import java.security.SignatureException;

import org.apache.log4j.Logger;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = Logger.getLogger(GlobalExceptionHandler.class);

    @ResponseStatus(value = NOT_FOUND, reason = "We cannot find you in our records")
    @ExceptionHandler(UserNotFoundException.class)
    public void profileNotFound(UserNotFoundException unfe) {
        logger.error("Exception is :", unfe);
    }

    @ResponseStatus(value = UNAUTHORIZED, reason="Looks like you have provide wrong credentials")
    @ExceptionHandler(FailedToLoginException.class)
    public void failedToLogin(FailedToLoginException ftle) {
        logger.error("Exception is :", ftle);
    }

    @ResponseStatus(value=FORBIDDEN, reason="You do not have the Access")
    @ExceptionHandler(SignatureException.class)
    public void failedToVerify(SignatureException se) {
        logger.error("Exception is :", se);
    }
    
    @ResponseStatus(value=UNAUTHORIZED, reason="You are not authorized to access this resource")
    @ExceptionHandler(AccessDeniedException.class)
    public void failedToAccess(AccessDeniedException ae) {
        logger.error("Exception is :", ae);
    }
    
    @ResponseStatus(value=INTERNAL_SERVER_ERROR, reason="Had issue processing your request")
    @ExceptionHandler(NullPointerException.class)
    public void nullException(NullPointerException npe) {
        logger.error("Exception is :", npe);
    }
    
    @ResponseStatus(value=BAD_REQUEST, reason="Request parameters are either missing or mismatching")
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public void badRequestException(MissingServletRequestParameterException msrpe) {
        logger.error("Exception is :", msrpe);
    }
    
    @ResponseStatus(value=INTERNAL_SERVER_ERROR, reason="Had issue processing your request")
    @ExceptionHandler(Exception.class)
    public void failedToProcess(Exception ex) {
        logger.error("Exception is :", ex);
    }
}
