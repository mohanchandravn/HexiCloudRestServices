package com.hexicloud.portaldb.dao;

import com.hexicloud.portaldb.bean.AuthUser;
import com.hexicloud.portaldb.bean.CustomerRegistry;
import com.hexicloud.portaldb.bean.User;

import java.util.List;

public interface UsersDAO {
    public User getUser(String userId);

    public void updateLastLoggedIn(String userId);

    public String createUser(User user) throws Exception;

    public void updatePassword(User user) throws Exception;

    public boolean checkExistingUser(String userId);
    
    public List<CustomerRegistry> getCustomerRegistryForLov();
    
    public AuthUser getUserDetailsForAuthentication(String userId);

    public List<User> searchUserDetails(String userId, String emailId, String customerId);

    public void updateUser(User user);
    
    public String sendWelcomeEmail(String userId, String password, String firstName, String userEmail);

}
