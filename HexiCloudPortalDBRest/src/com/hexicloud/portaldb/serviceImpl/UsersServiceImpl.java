package com.hexicloud.portaldb.serviceImpl;

import com.hexicloud.portaldb.bean.CustomerRegistry;
import com.hexicloud.portaldb.bean.User;
import com.hexicloud.portaldb.dao.UsersDAO;
import com.hexicloud.portaldb.service.UsersService;

import java.util.List;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("usersService")
public class UsersServiceImpl implements UsersService {
    private static final Logger logger = Logger.getLogger(UserStepsServiceImpl.class);

    @Autowired
    UsersDAO usersDAO;



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
    public List<CustomerRegistry> getCustRegistries() {
        logger.info("*******  getCustRegistries() of  service *****************");
        return usersDAO.getCustomerRegistryForLov();
    }

    @Override
    public List<User> searchUserDetails(String userId, String emailId, String customerId) {
        logger.info("entering method searchUserDetails");
        return usersDAO.searchUserDetails(userId,emailId,customerId);
    }

    @Override
    public void updateUser(User user) {
        logger.info("Entering method updateUser()");
        usersDAO.updateUser(user);
        logger.info("Exiting method updateUser()");
    }
}
