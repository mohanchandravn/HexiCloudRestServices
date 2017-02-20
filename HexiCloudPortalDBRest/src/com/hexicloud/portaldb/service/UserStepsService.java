package com.hexicloud.portaldb.service;

import com.hexicloud.portaldb.bean.Step;
import com.hexicloud.portaldb.bean.UserStep;

import java.util.List;

public interface UserStepsService {


    public void createUserSteps(UserStep userStep);

    public UserStep getUsersCurrentStep(String userId);

    public List<Step> getApplicationSteps();
}
