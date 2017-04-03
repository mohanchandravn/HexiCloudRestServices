package com.hexicloud.portaldb.authentication;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

public class JwtLogoutHandler implements LogoutHandler {
    private static final Logger logger = Logger.getLogger(JwtLogoutHandler.class);
    @Value("${usercookie.name}")
    private String USER_COOKIE;

    //        @Value("${jwt.cookie}")
    //        private String TOKEN_COOKIE;


    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        logger.info("Handling logout code from portal");
        // Erase cookies
        //            Cookie authCookie = new Cookie( TOKEN_COOKIE, null ); // Not necessary, but saves bandwidth.
        //            authCookie.setPath( "/" );
        //            authCookie.setMaxAge( 0 ); // Don't set to -1 or it will become a session cookie!

        Cookie userCookie = new Cookie(USER_COOKIE, null);
        userCookie.setPath("/");
        userCookie.setMaxAge(0);

        //            response.addCookie(authCookie);
        response.addCookie(userCookie);
    }


}
