package com.hexicloud.portaldb.authentication;

import com.hexicloud.portaldb.dao.UserNavigationAuditDAO;
import com.hexicloud.portaldb.util.token.TokenHelper;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

public class JwtLogoutHandler implements LogoutHandler {
    private static final Logger logger = Logger.getLogger(JwtLogoutHandler.class);
    @Value("${usercookie.name}")
    private String USER_COOKIE;

    @Value("${auth.header}")
    private String AUTH_HEADER;

    @Value("${auth.portalType}")
    private String PORTAL_TYPE;

    @Value("${auth.userType}")
    private String USER_TYPE;

    @Value("${auth.adminType}")
    private String ADMIN_TYPE;

    @Autowired
    TokenHelper tokenHelper;

    @Autowired
    UserNavigationAuditDAO userNavigationAuditDAO;


    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        logger.info("Handling logout code from portal");
        // Erase cookies
        //            Cookie authCookie = new Cookie( TOKEN_COOKIE, null ); // Not necessary, but saves bandwidth.
        //            authCookie.setPath( "/" );
        //            authCookie.setMaxAge( 0 ); // Don't set to -1 or it will become a session cookie!
        String authHeader = request.getHeader(AUTH_HEADER);
        String portalType = request.getHeader(PORTAL_TYPE);
        if (portalType.equalsIgnoreCase(USER_TYPE) && null != authHeader) {
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                authHeader = authHeader.substring(7);
            }
            String username = tokenHelper.getUsernameFromToken(authHeader);
            userNavigationAuditDAO.updateAuditOnly(username, "logout", "Logged out of portal");
        }

        Cookie userCookie = new Cookie(USER_COOKIE, null);
        userCookie.setPath("/");
        userCookie.setMaxAge(0);

        //            response.addCookie(authCookie);
        response.addCookie(userCookie);
    }


}
