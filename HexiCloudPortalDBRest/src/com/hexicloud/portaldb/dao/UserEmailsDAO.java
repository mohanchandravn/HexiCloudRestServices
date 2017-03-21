package com.hexicloud.portaldb.dao;

import com.hexicloud.portaldb.bean.RuleConfiguration;
import com.hexicloud.portaldb.bean.UserEmail;

import java.util.List;

public interface UserEmailsDAO {
    public List<UserEmail> getUserEmails(String userId, String isResolved, Number requestId);

    public UserEmail saveUserEmail(UserEmail userEmail);
    
    public void updateResolution (UserEmail userEmail);
    
    public RuleConfiguration getEmailRule (String ruleKey);

}
