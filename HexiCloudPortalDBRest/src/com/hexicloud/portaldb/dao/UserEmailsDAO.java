package com.hexicloud.portaldb.dao;

import java.util.List;

import com.hexicloud.portaldb.bean.UserEmail;

public interface UserEmailsDAO {
    public List<UserEmail> getUserEmails(String userId);

    public UserEmail saveUserEmail(UserEmail userEmail);

}
