package com.hexicloud.portaldb.serviceImpl;

import com.hexicloud.portaldb.bean.Step;
import com.hexicloud.portaldb.bean.UserStep;
import com.hexicloud.portaldb.dao.StepsDAO;
import com.hexicloud.portaldb.dao.UserStepsDAO;
import com.hexicloud.portaldb.service.UserStepsService;

import java.util.List;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userStepsService")
public class UserStepsServiceImpl implements UserStepsService {

    private static final Logger logger = Logger.getLogger(UserStepsServiceImpl.class);

    @Autowired
    UserStepsDAO userStepsDao;

    @Autowired
    StepsDAO stepsDao;


    @Override
    public void createUserSteps(UserStep userStep) {
        logger.info("*******  createUserSteps() of  service *****************");
        userStepsDao.createUserSteps(userStep);

    }

    @Override
    public UserStep getUsersCurrentStep(String userId) {
        logger.info("*******  getUsersCurrentStep() of  service *****************");
        return userStepsDao.getUsersCurrentStep(userId);
    }

    @Override
    public List<Step> getApplicationSteps() {
        logger.info("*******  getApplicationSteps() of  service *****************");
        return stepsDao.getApplicationSteps();
    }

}
