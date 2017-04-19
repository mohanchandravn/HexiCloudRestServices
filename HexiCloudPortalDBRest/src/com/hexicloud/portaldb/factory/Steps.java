package com.hexicloud.portaldb.factory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;

import com.hexicloud.portaldb.bean.Step;
import com.hexicloud.portaldb.service.UserStepsService;

public class Steps {
    private static final Logger logger = Logger.getLogger(Steps.class);
    public Map<String, Step> stepsMapWithStepCodeKey = new HashMap<String, Step>();
    public Map<Integer, Step> stepsMapWithStepIdKey = new HashMap<Integer, Step>();
    @Autowired
    private UserStepsService userStepsService;

    public void setStepsMapWithStepCodeKey(Map<String, Step> stepsMapWithStepCodeKey) {
        this.stepsMapWithStepCodeKey = stepsMapWithStepCodeKey;
    }

    public Map<String, Step> getStepsMapWithStepCodeKey() {
        if (stepsMapWithStepCodeKey.isEmpty()) {
            logger.info("Steps not loaded yet, loading the steps from DB");
            List<Step> dbSteps = userStepsService.getApplicationSteps();
            for (Step dbStep : dbSteps) {
                stepsMapWithStepCodeKey.put(dbStep.getStepCode(), dbStep);
            }
            logger.info("No of steps loaded from DB : " + dbSteps.size());
        }
        return stepsMapWithStepCodeKey;
    }

    public void setStepsMapWithStepIdKey(Map<Integer, Step> stepsMapWithStepIdKey) {
        this.stepsMapWithStepIdKey = stepsMapWithStepIdKey;
    }

    public Map<Integer, Step> getStepsMapWithStepIdKey() {
        if (stepsMapWithStepIdKey.isEmpty()) {
            logger.info("Steps not loaded yet, loading the steps from DB");
            List<Step> dbSteps = userStepsService.getApplicationSteps();
            for (Step dbStep : dbSteps) {
                stepsMapWithStepIdKey.put(dbStep.getStepId(), dbStep);
            }
            logger.info("No of steps loaded from DB : " + dbSteps.size());
        }
        return stepsMapWithStepIdKey;
    }


    public int getStepIdWithStepCode(String stepCode) {
        return getStepsMapWithStepCodeKey() != null ? getStepsMapWithStepCodeKey().get(stepCode).getStepId() : 0;
    }

    public String getStepLabelWithStepCode(String stepCode) {
        return getStepsMapWithStepCodeKey() != null ? getStepsMapWithStepCodeKey().get(stepCode).getStepLabel() : null;
    }

    public String getStepLabelWithStepId(Integer stepId) {
        return getStepsMapWithStepIdKey() != null ? getStepsMapWithStepIdKey().get(stepId).getStepLabel() : null;
    }


}
