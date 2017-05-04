package com.hexicloud.portaldb.serviceImpl;

import com.hexicloud.portaldb.bean.DecisionTree;
import com.hexicloud.portaldb.bean.Services;
import com.hexicloud.portaldb.bean.UseCaseDetail;
import com.hexicloud.portaldb.bean.UseCases;
import com.hexicloud.portaldb.bean.UserPhaseCompletion;
import com.hexicloud.portaldb.bean.UserUseCase;
import com.hexicloud.portaldb.bean.UserUseCases;
import com.hexicloud.portaldb.dao.ClmDataDAO;
import com.hexicloud.portaldb.dao.UseCasesDAO;
import com.hexicloud.portaldb.dao.UserPhaseCompletionDAO;
import com.hexicloud.portaldb.service.UseCaseService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service("useCaseService")
public class UseCaseServiceImpl implements UseCaseService {

    private static final Logger logger = Logger.getLogger(UseCaseServiceImpl.class);

    @Autowired
    UseCasesDAO useCasesDAO;

    @Autowired
    ClmDataDAO clmDataDAO;

    @Autowired
    UserPhaseCompletionDAO userPhaseCompletionDAO;

    @Value("${user.phase.completion.usecase.capture}")
    private String USER_PHASE_COMPLETION_USECASE_CAPTURE;

    @Value("${user.phase.completion.usecase.selection}")
    private String USER_PHASE_COMPLETION_USECASE_SELECTION;

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
        if (null != userUseCases) {
            UserUseCases filterUseCases = new UserUseCases();
            List<UserUseCase> filterUseCaseDetailList = new ArrayList<UserUseCase>();
            Set<Integer> existingTailoredIds = new HashSet<Integer>();
            String code = userUseCases.getUserUseCases()
                                      .get(0)
                                      .getCode();
            UserPhaseCompletion upComp = new UserPhaseCompletion();
            upComp.setUserId(userId);

            if ("T".equalsIgnoreCase(code)) {
                UseCases tailoredUseCases = useCasesDAO.getTailoredUseCasesForUser(userId);
                logger.info("Recieved user use cases : " + userUseCases.getUserUseCases().size());
                for (UseCaseDetail useCaseDetail : tailoredUseCases.getUseCases()) {
                    existingTailoredIds.add(useCaseDetail.getId());
                }

                for (UserUseCase useCase : userUseCases.getUserUseCases()) {
                    if (!existingTailoredIds.contains(useCase.getUseCaseId())) {
                        useCase.setUserId(userId);
                        filterUseCaseDetailList.add(useCase);
                    }
                }
                filterUseCases.setUserUseCases(filterUseCaseDetailList);
                useCasesDAO.createUserUseCases(filterUseCases);
                upComp.setPhase(USER_PHASE_COMPLETION_USECASE_SELECTION);
                logger.info("Inserted user use cases after filtering from existing user use cases : " +
                            filterUseCases.getUserUseCases().size());

            } else {
                for (UserUseCase useCase : userUseCases.getUserUseCases()) {
                    useCase.setUserId(userId);
                }
                useCasesDAO.createUserUseCases(userUseCases);
                upComp.setPhase(USER_PHASE_COMPLETION_USECASE_CAPTURE);
            }
            userPhaseCompletionDAO.createPhaseCompletion(upComp);

        }
    }

    @Override
    public UseCases getTailoredUseCases(String userId) {
        logger.info("*******  getTailoredUseCases() of  service *****************");
        UseCases cases = useCasesDAO.getTailoredUseCasesForUser(userId);
        UserPhaseCompletion checkCaputeCompletion =
            userPhaseCompletionDAO.checkPhaseCompletion(userId, USER_PHASE_COMPLETION_USECASE_CAPTURE);
        UserPhaseCompletion checkSelectionCompletion =
            userPhaseCompletionDAO.checkPhaseCompletion(userId, USER_PHASE_COMPLETION_USECASE_SELECTION);
        if (checkCaputeCompletion != null) {
            cases.setCapturePhaseCompleted(true);
        }
        if (checkSelectionCompletion != null) {
            cases.setSelectionPhaseCompleted(true);
        }
        return cases;
    }

    @Override
    public void markUseCaseCaptureCompletion(String userId) {
        UserPhaseCompletion upComp = new UserPhaseCompletion();
        upComp.setUserId(userId);
        upComp.setPhase(USER_PHASE_COMPLETION_USECASE_CAPTURE);
        userPhaseCompletionDAO.createPhaseCompletion(upComp);
    }
}
