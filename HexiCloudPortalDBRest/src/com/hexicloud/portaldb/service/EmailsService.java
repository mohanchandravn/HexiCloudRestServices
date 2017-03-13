package com.hexicloud.portaldb.service;

import java.util.List;

import com.hexicloud.portaldb.bean.UserEmail;

public interface EmailsService {

    public List<UserEmail> getUserEmails(String userId, String isResolved, Number requestId);

    public UserEmail saveUserEmail(UserEmail userEmail);

    public void updateEmailResolution(UserEmail userEmail);
}
