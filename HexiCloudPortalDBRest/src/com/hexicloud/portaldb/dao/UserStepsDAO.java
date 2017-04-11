package com.hexicloud.portaldb.dao;

import com.hexicloud.portaldb.bean.UserStep;

public interface UserStepsDAO {


    public void createUserSteps(UserStep userStep);

    public UserStep getUsersCurrentStep(String userId);
    
    public boolean onBoardingComplete(String userId);
    
    public void updateAuditOnly(String userId, String stepCode, String action);
}
