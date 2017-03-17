package com.hexicloud.portaldb.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.hexicloud.portaldb.bean.AuthUser;
import com.hexicloud.portaldb.bean.AuthUserTokenState;
import com.hexicloud.portaldb.util.token.TokenHelper;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private static final Logger logger = Logger.getLogger(AuthenticationSuccessHandler.class);
    private int EXPIRES_IN = 600;


    //    private String TOKEN_COOKIE = "AUTH-TOKEN";


    private String USER_COOKIE = "c_user";

    @Autowired
    TokenHelper tokenHelper;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        logger.info("Authentication success, generating the token");
        clearAuthenticationAttributes(request);
        AuthUser user = (AuthUser) authentication.getPrincipal();

        String jws = tokenHelper.generateToken(user.getUsername());

        // Create token auth Cookie
        //        Cookie authCookie = new Cookie( TOKEN_COOKIE, ( jws ) );
        //              authCookie.setPath( "/" );
        //              authCookie.setHttpOnly( true );
        //              authCookie.setMaxAge( EXPIRES_IN );
        // Create flag Cookie
        Cookie userCookie = new Cookie(USER_COOKIE, (user.getFirstName()));
        userCookie.setPath("/");
        userCookie.setMaxAge(EXPIRES_IN);
        // Add cookie to response
        //              response.addCookie( authCookie );
        response.addCookie(userCookie);
        // JWT is also in the response
        AuthUserTokenState userTokenState = new AuthUserTokenState(jws, EXPIRES_IN);
        ObjectMapper mapper = new ObjectMapper();
        String jwtResponse = mapper.writeValueAsString(userTokenState);
        response.setContentType("application/json");
        response.getWriter().write(jwtResponse);
    }
}

