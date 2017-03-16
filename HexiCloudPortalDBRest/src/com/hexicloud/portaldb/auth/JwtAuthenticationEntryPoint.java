package com.hexicloud.portaldb.auth;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import java.util.Collections;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import static javax.servlet.http.HttpServletResponse.SC_FORBIDDEN;

import org.apache.log4j.Logger;

import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private static final Logger logger = Logger.getLogger(JwtAuthenticationEntryPoint.class);
    @Override
       public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e)
               throws IOException, ServletException {
           logger.info("In commence method, redirecting with forbidden");
           httpServletResponse.setStatus(SC_FORBIDDEN);
           httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);

           String message;
           if(e.getCause() != null) {
               message = e.getCause().getMessage();
           } else {
               message = e.getMessage();
           }
           byte[] body = new ObjectMapper()
                   .writeValueAsBytes(Collections.singletonMap("error", message));
           httpServletResponse.getOutputStream().write(body);
       }
}
