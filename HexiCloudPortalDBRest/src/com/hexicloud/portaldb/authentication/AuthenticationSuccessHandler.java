package com.hexicloud.portaldb.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.hexicloud.portaldb.bean.AuthUser;
import com.hexicloud.portaldb.bean.AuthUserTokenState;
import com.hexicloud.portaldb.bean.User;
import com.hexicloud.portaldb.bean.UserStep;
import com.hexicloud.portaldb.dao.UserStepsDAO;
import com.hexicloud.portaldb.dao.UsersDAO;
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
    UsersDAO usersDAO;
    @Autowired
    UserStepsDAO userStepsDao;

    @Value("${usercookie.expiresIn}")
    private int EXPIRES_IN;


    //    private String TOKEN_COOKIE = "AUTH-TOKEN";

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
            AuthUserTokenState userTokenState = null; //
            User userDetails = usersDAO.getUser(user.getUsername());
            UserStep userStep = userStepsDao.getUsersCurrentStep(user.getUsername());
            if (userDetails != null && userStep != null) {
                userTokenState =
                    new AuthUserTokenState(jws, EXPIRES_IN, user.getUserId(), userDetails.getEmail(),
                                           userDetails.getUserRole(), userDetails.getFirstName(),
                                           userDetails.getLastName(), String.valueOf(userStep.getCurStepId()),
                                           userStep.getCurStepCode(), String.valueOf(userStep.getPreStepId()),
                                           userStep.getPreStepCode(), userDetails.getPhone());
            }
            else if(userDetails != null && userStep == null) {
                userTokenState =
                    new AuthUserTokenState(jws, EXPIRES_IN, user.getUserId(), userDetails.getEmail(),
                                           userDetails.getUserRole(), userDetails.getFirstName(),
                                           userDetails.getLastName(), "","", "", "",  userDetails.getPhone());
            }
            else if(userDetails == null && userStep == null) {
                userTokenState =
                    new AuthUserTokenState(jws, EXPIRES_IN, user.getUserId(), "","", "","", "","", "", "", "");
            }
            usersDAO.updateLastLoggedIn(user.getUsername());
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

