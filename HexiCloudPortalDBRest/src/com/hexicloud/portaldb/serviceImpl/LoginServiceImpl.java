package com.hexicloud.portaldb.serviceImpl;

import com.hexicloud.portaldb.bean.User;
import com.hexicloud.portaldb.dao.UsersDAO;
import com.hexicloud.portaldb.service.LoginService;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("loginService")
public class LoginServiceImpl implements LoginService {
    private static final Logger logger = Logger.getLogger(UserStepsServiceImpl.class);

    @Autowired
    UsersDAO usersDAO;

    @Override
    public User authenticate(User user) {
        logger.info("******* starting authenticate() of  service *****************");
        User dbUser = null;
        if (user != null && user.getUserId() != null) {

            dbUser = usersDAO.getUser(user.getUserId());
            if (dbUser != null) {
                byte[] decodedPasswordBytes = Base64.decodeBase64(user.getPassword().getBytes());
                String decodedPassword = new String(decodedPasswordBytes);
                if (decodedPassword.equalsIgnoreCase(dbUser.getPassword())) {
                    usersDAO.updateLastLoggedIn(user.getUserId());
                    dbUser.setPassword(null);
                    return dbUser;
                }
            }
        }
        logger.info("******* starting authenticate() of  service *****************");
        return null;
    }

    @Override
    public void createUser(User user) throws Exception {
        logger.info("*******  createUser() of  service *****************");
        usersDAO.createUser(user);
    }

    @Override
    public void updatePassword(User user) throws Exception {
        logger.info("*******  updatePassword() of  service *****************");
        usersDAO.updatePassword(user);
    }

    @Override
    public boolean checkExistingUser(String userId) {
        logger.info("*******  checkExistingUser() of  service *****************");
        return usersDAO.checkExistingUser(userId);
    }
    
    @Override
        public User queryUserInfoByUserId(String userId) {
            logger.info("*******  queryUserInfoByUserId() of  service *****************");
            User dbUser = null;
            if(userId != null)
            {
                dbUser = usersDAO.getUser(userId);
                return dbUser;
            }
            return null;
        }
    
    @Override
    public User getMimimalUser(String userId) {
        logger.info("******* starting authenticate() of  service *****************");
        User dbUser = null;
        dbUser = usersDAO.getUser(userId);
        if (dbUser != null) {
            dbUser.setPassword(null);
            return dbUser;
        }
        logger.info("******* starting authenticate() of  service *****************");
        return null;
    }

}
