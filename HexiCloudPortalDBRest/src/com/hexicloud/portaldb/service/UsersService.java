package com.hexicloud.portaldb.service;

import com.hexicloud.portaldb.bean.CustomerRegistry;
import com.hexicloud.portaldb.bean.UpdatePassword;
import com.hexicloud.portaldb.bean.User;

import java.util.List;

import org.springframework.http.HttpStatus;

public interface UsersService {

    public void createUser(User user) throws Exception;
    
    public List<CustomerRegistry> getCustRegistries();

    public void updatePassword(User user) throws Exception;

    public boolean checkExistingUser(String userId);
    
    public User queryUserInfoByUserId(String userId);

    public List<User> searchUserDetails(String userId, String emailId, String customerId);

    public void updateUser(User user);
    
    public HttpStatus checkAndUpdatePassword(UpdatePassword updatePassword) throws Exception;
}
