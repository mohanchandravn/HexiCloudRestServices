package com.hexicloud.portaldb.service;

import java.util.List;

import com.hexicloud.portaldb.bean.Step;
import com.hexicloud.portaldb.bean.StepDocument;
import com.hexicloud.portaldb.bean.UserStep;

public interface UserStepsService {
    public List<StepDocument> findDocsByStepId(int stepId);

    public void createUserSteps(UserStep userStep);

    public UserStep getUsersCurrentStep(String userId);

    public List<Step> getApplicationSteps();
}
