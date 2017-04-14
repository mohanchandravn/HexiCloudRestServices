package com.hexicloud.portaldb.serviceImpl;

import com.hexicloud.portaldb.bean.AuthUserTokenState;
import com.hexicloud.portaldb.bean.User;
import com.hexicloud.portaldb.bean.UserStep;
import com.hexicloud.portaldb.dao.UserNavigationAuditDAO;
import com.hexicloud.portaldb.dao.UserStepsDAO;
import com.hexicloud.portaldb.dao.UsersDAO;
import com.hexicloud.portaldb.service.LoginService;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service("loginService")
public class LoginServiceImpl implements LoginService {
    private static final Logger logger = Logger.getLogger(UserStepsServiceImpl.class);

    @Autowired
    UsersDAO usersDAO;
    @Autowired
    UserStepsDAO userStepsDao;
    @Autowired
    UserNavigationAuditDAO userNavigationAuditDAO;

    //    @Override
    //    public User authenticate(User user) {
    //        logger.info("******* starting authenticate() of  service *****************");
    //        User dbUser = null;
    //        if (user != null && user.getUserId() != null) {
    //
    //            dbUser = usersDAO.getUser(user.getUserId());
    //            if (dbUser != null) {
    //                byte[] decodedPasswordBytes = Base64.decodeBase64(user.getPassword().getBytes());
    //                String decodedPassword = new String(decodedPasswordBytes);
    //                if (decodedPassword.equalsIgnoreCase(dbUser.getPassword())) {
    //                    usersDAO.updateLastLoggedIn(user.getUserId());
    //                    dbUser.setPassword(null);
    //                    return dbUser;
    //                }
    //            }
    //        }
    //        logger.info("******* starting authenticate() of  service *****************");
    //        return null;
    //    }

    @Override
    public User getUserDetails(String userName) {
        logger.info("*******  getUserDetails() of  service *****************");
        User dbUser = usersDAO.getUser(userName);
        dbUser.setPassword(null);
        logger.info("*******  getUserDetails() of  service *****************");
        return dbUser;
    }


    @Override
    public AuthUserTokenState getPortalUserDetails(String userName, String accessToken, long expiresIn) {
        AuthUserTokenState userTokenState = new AuthUserTokenState();
        User userDetails = usersDAO.getUser(userName);
        UserStep userStep = userStepsDao.getUsersCurrentStep(userName);

        if (userDetails != null) {
            userTokenState.setAccess_token(accessToken);
            userTokenState.setExpires_in(expiresIn);
            userTokenState.setUserId(userName);
            userTokenState.setEmail(userDetails.getEmail());
            userTokenState.setUserRole(userDetails.getUserRole());
            userTokenState.setFirstName(userDetails.getFirstName());
            userTokenState.setLastName(userDetails.getLastName());
            userTokenState.setPhone(userDetails.getPhone());
            if (StringUtils.isEmpty(userDetails.getLastLoggedIn())) {
                userTokenState.setFirstLogin(false);
            }
            if (StringUtils.isEmpty(userDetails.getPwdLastChanged())) {
                userTokenState.setChangePwd(true);
            }
        }
        if (userStep != null) {
            userTokenState.setPreStepCode(userStep.getPreStepCode());
            userTokenState.setPreStepId(String.valueOf(userStep.getPreStepId()));
            userTokenState.setCurStepId(String.valueOf(userStep.getCurStepId()));
            userTokenState.setCurStepCode(userStep.getCurStepCode());
            userTokenState.setOnBoardingCompleted(userStepsDao.onBoardingComplete(userName));
        }
        usersDAO.updateLastLoggedIn(userName);
        userNavigationAuditDAO.updateAuditOnly(userName, "login", "Login Successfull");
        return userTokenState;
    }

    @Override
    public AuthUserTokenState getAdminUserDetails(String userName, String accessToken, long expiresIn, String portalRole) {
        AuthUserTokenState userTokenState = new AuthUserTokenState();
        User userDetails = usersDAO.getUser(userName);
        if (userDetails != null) {
            userTokenState.setAccess_token(accessToken);
            userTokenState.setExpires_in(expiresIn);
            userTokenState.setUserId(userName);
            userTokenState.setEmail(userDetails.getEmail());
            userTokenState.setUserRole(userDetails.getUserRole());
            userTokenState.setFirstName(userDetails.getFirstName());
            userTokenState.setLastName(userDetails.getLastName());
            userTokenState.setPhone(userDetails.getPhone());
            userTokenState.setPortalRole(portalRole);
        }
        usersDAO.updateLastLoggedIn(userName);
        return userTokenState;
    }
}
