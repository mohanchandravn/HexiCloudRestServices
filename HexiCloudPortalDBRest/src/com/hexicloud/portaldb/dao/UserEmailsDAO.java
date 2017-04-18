package com.hexicloud.portaldb.dao;

import com.hexicloud.portaldb.bean.CallBack;
import com.hexicloud.portaldb.bean.RuleConfiguration;
import com.hexicloud.portaldb.bean.User;
import com.hexicloud.portaldb.bean.UserEmail;

import java.math.BigDecimal;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import java.sql.SQLException;

import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import javax.naming.NamingException;

public interface UserEmailsDAO {
    public List<UserEmail> getUserEmails(String userId, String isResolved, Number requestId, String searchCallBacks);

    public UserEmail saveUserEmail(UserEmail userEmail);
    
    public void updateResolution (UserEmail userEmail);

    public String sendEmail(String sendTo, User user) throws SQLException, NamingException, NoSuchAlgorithmException,
                                                             NoSuchPaddingException, InvalidKeyException,
                                                             IllegalBlockSizeException, BadPaddingException;

    public BigDecimal requestCallBack(CallBack callBack);
    
    public RuleConfiguration getEmailRule(String ruleKey);


}
