package com.hexicloud.portaldb.dao;

import java.util.List;

import com.hexicloud.portaldb.bean.StepDocument;
import com.hexicloud.portaldb.bean.UserStep;

public interface UserStepsDAO {
	public List<StepDocument> findDocsByStepId(int stepId); 
	public void createUserSteps(UserStep userStep);
	public UserStep getUsersCurrentStep(String userId);
}
