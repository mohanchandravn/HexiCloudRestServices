package com.hexicloud.portaldb.service;

import java.util.List;

import com.hexicloud.portaldb.bean.UserEmail;

import java.sql.SQLException;

import javax.naming.NamingException;

public interface EmailsService {

    public List<UserEmail> getUserEmails(String userId, String isResolved, Number requestId);

    public UserEmail saveUserEmail(UserEmail userEmail);

    public void updateEmailResolution(UserEmail userEmail);
    
    public String sendEmail(String sendTo, String subject, String emailContent) throws SQLException, NamingException;
}
