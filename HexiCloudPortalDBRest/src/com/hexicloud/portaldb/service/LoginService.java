package com.hexicloud.portaldb.service;

import com.hexicloud.portaldb.bean.User;

public interface LoginService {
    public User authenticate(User user);

    public void createUser(User user) throws Exception;

    public void updatePassword(User user) throws Exception;

    public boolean checkExistingUser(String userId);
    
    public User queryUserInfoByUserId(String userId);
}
