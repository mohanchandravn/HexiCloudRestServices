package com.hexicloud.portaldb.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.hexicloud.portaldb.bean.AuthUser;
import com.hexicloud.portaldb.bean.AuthUserTokenState;
import com.hexicloud.portaldb.service.LoginService;
import com.hexicloud.portaldb.util.token.TokenHelper;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private static final Logger logger = Logger.getLogger(AuthenticationSuccessHandler.class);

    @Autowired
    private LoginService loginService;

    @Value("${usercookie.expiresIn}")
    private int EXPIRES_IN;

    @Value("${usercookie.name}")
    private String USER_COOKIE;

    @Value("${forbidden.errorMessage}")
    private String FORBIDDEN_MESSAGE;

    @Autowired
    TokenHelper tokenHelper;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        logger.info("Authentication success, generating the token");
        clearAuthenticationAttributes(request);
        AuthUser user = (AuthUser) authentication.getPrincipal();
        String portalType = request.getHeader("Portal-Type");
        if (portalType.equalsIgnoreCase("admin") && user.getAuthority()
                                                        .replace("ROLE_", "")
                                                        .equalsIgnoreCase("admin") ||
            portalType.equalsIgnoreCase("user") && user.getAuthority()
                                                       .replace("ROLE_", "")
                                                       .equalsIgnoreCase("user")) {
            String jws = tokenHelper.generateToken(user.getUsername());
            Cookie userCookie = new Cookie(USER_COOKIE, (user.getFirstName()));
            userCookie.setPath("/");
            userCookie.setMaxAge(EXPIRES_IN);
            response.addCookie(userCookie);
            AuthUserTokenState userTokenState = null;
            if (portalType.equalsIgnoreCase("user")) {
                userTokenState = loginService.getPortalUserDetails(user.getUserId(), jws, EXPIRES_IN);
            } else {
                userTokenState = loginService.getAdminUserDetails(user.getUserId(), jws, EXPIRES_IN);
            }
            ObjectMapper mapper = new ObjectMapper();
            String jwtResponse = mapper.writeValueAsString(userTokenState);
            response.setContentType("application/json");
            response.getWriter().write(jwtResponse);
        } else {
            response.setStatus(401);
            response.getWriter().write(FORBIDDEN_MESSAGE);
        }
    }
}

