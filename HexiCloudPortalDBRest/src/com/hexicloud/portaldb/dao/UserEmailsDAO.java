package com.hexicloud.portaldb.dao;

import java.util.List;

import com.hexicloud.portaldb.bean.UserEmail;

public interface UserEmailsDAO {
    public List<UserEmail> getUserEmails(String userId, String isResolved, Number requestId);

    public UserEmail saveUserEmail(UserEmail userEmail);
    
    public void updateResolution (UserEmail userEmail);

}
