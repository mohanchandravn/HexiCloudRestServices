package com.hexicloud.portaldb.serviceImpl;

import com.hexicloud.portaldb.bean.DecisionTree;
import com.hexicloud.portaldb.bean.Services;
import com.hexicloud.portaldb.bean.UseCases;
import com.hexicloud.portaldb.bean.UserUseCase;
import com.hexicloud.portaldb.bean.UserUseCases;
import com.hexicloud.portaldb.dao.ClmDataDAO;
import com.hexicloud.portaldb.dao.UseCasesDAO;
import com.hexicloud.portaldb.service.UseCaseService;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("useCaseService")
public class UseCaseServiceImpl implements UseCaseService {

    private static final Logger logger = Logger.getLogger(UseCaseServiceImpl.class);

    @Autowired
    UseCasesDAO useCasesDAO;

    @Autowired
    ClmDataDAO clmDataDAO;

    @Override
    public UseCases getAllUseCases() {
        logger.info("*******  getAllUseCases() of  service *****************");
        return useCasesDAO.getAllUseCases();
    }

    @Override
    public UseCases getUseCasesValidForUser(String userId) {
        logger.info("******* Starting of getAllUseCases() of  service *****************");

        List<String> trimmedServiceList = new ArrayList<String>();
        List<String> userServicesList = clmDataDAO.getServicesForUser(userId);
        String trimmedService = null;
        for (String service : userServicesList) {
            trimmedService = service.toUpperCase();
            trimmedService = trimmedService.replaceAll(" ", "");
            trimmedService = trimmedService.replace("-IAAS", "");
            trimmedServiceList.add(trimmedService);
        }
        if (!trimmedServiceList.isEmpty()) {
            return useCasesDAO.getUseCasesApplicableForServices(trimmedServiceList);
        }
        logger.info("******* Ending of getAllUseCases() of  service *****************");
        return null;
    }

    @Override
    public Services getAllServices() {
        logger.info("Service method for all services");

        return useCasesDAO.getAllServices();
    }

    @Override
    public DecisionTree getDecisionTree() {
        logger.info("Service method for all services");

        return useCasesDAO.getDecisionTree();
    }

    @Override
    public void createUserUseCases(UserUseCases userUseCases, String userId) {
        logger.info("Service method for all createUserUseCases, setting userId");
        for (UserUseCase useCase : userUseCases.getUserUseCases()) {
            useCase.setUserId(userId);
        }
        useCasesDAO.createUserUseCases(userUseCases);
        logger.info("Inserted user use cases : " + userUseCases.getUserUseCases().size());
    }

    @Override
    public UseCases getTailoredUseCases(String userId) {
        logger.info("*******  getTailoredUseCases() of  service *****************");
        return useCasesDAO.getTailoredUseCasesForUser(userId);
    }
}
