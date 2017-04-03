package com.hexicloud.portaldb.authentication;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    private static final Logger logger = Logger.getLogger(AuthenticationFailureHandler.class);
    @Override
       public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                           AuthenticationException exception) throws IOException, ServletException {
           logger.error("Aunthentication failed, reason : " + exception);
           super.onAuthenticationFailure(request, response, exception);
       }
}
