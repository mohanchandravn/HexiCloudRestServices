package com.hexicloud.portaldb.authentication;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component("customEntryPoint")
public class CustomEntryPoint implements AuthenticationEntryPoint {
    private static final Logger logger = Logger.getLogger(CustomEntryPoint.class);

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                         AuthenticationException authenticationException) throws IOException, ServletException {
        logger.info("Entering commence");
        httpServletResponse.sendError( HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized" );

    }
}
