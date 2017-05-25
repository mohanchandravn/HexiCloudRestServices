package com.hexicloud.portaldb.serviceImpl;

import com.hexicloud.portaldb.bean.DecisionTree;
import com.hexicloud.portaldb.bean.RuleConfiguration;
import com.hexicloud.portaldb.bean.Services;
import com.hexicloud.portaldb.bean.UseCase;
import com.hexicloud.portaldb.bean.UseCaseBenefits;
import com.hexicloud.portaldb.bean.UseCaseDetail;
import com.hexicloud.portaldb.bean.UseCases;
import com.hexicloud.portaldb.bean.UserPhaseCompletion;
import com.hexicloud.portaldb.bean.UserUseCase;
import com.hexicloud.portaldb.bean.UserUseCases;
import com.hexicloud.portaldb.bean.guidedpath.GuidedPath;
import com.hexicloud.portaldb.bean.guidedpath.GuidedPaths;
import com.hexicloud.portaldb.bean.guidedpath.PathProgressDetail;
import com.hexicloud.portaldb.dao.ClmDataDAO;
import com.hexicloud.portaldb.dao.EmailUtilDAO;
import com.hexicloud.portaldb.dao.GuidedPathsDAO;
import com.hexicloud.portaldb.dao.RuleConfigurationDAO;
import com.hexicloud.portaldb.dao.UseCasesDAO;
import com.hexicloud.portaldb.dao.UserPhaseCompletionDAO;
import com.hexicloud.portaldb.service.GuidedPathsService;
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

    @Autowired
    EmailUtilDAO emailUtilDAO;

    @Autowired
    RuleConfigurationDAO ruleConfigurationDAO;
    
    @Autowired
    GuidedPathsService guidedPathsService;

    @Value("${user.phase.completion.usecase.capture}")
    private String USER_PHASE_COMPLETION_USECASE_CAPTURE;

    @Value("${user.phase.completion.usecase.selection}")
    private String USER_PHASE_COMPLETION_USECASE_SELECTION;

    @Value("${usecase.selection.ignored.subject}")
    private String USE_CASE_SEL_IGNORED_SUB;

    @Value("${usecase.selection.ignored.body}")
    private String USE_CASE_SEL_IGNORED_BODY;

    @Value("${placeHolder.userId}")
    private String userIdPlaceHolder;

    @Value("${placeHolder.firstName}")
    private String firstNamePlaceHolder;

    @Override
    public UseCases getAllUseCases() {
        logger.info("*******  getAllUseCases() of  service *****************");
        return useCasesDAO.getAllUseCases();
    }

    @Override
    public UseCases getUseCasesValidForUser(String userId) {
        logger.info("******* Starting of getAllUseCases() of  service *****************");

//        List<String> trimmedServiceList = new ArrayList<String>();
        List<String> userServicesList = clmDataDAO.getServicesForUser(userId);
//        String trimmedService = null;
//        for (String service : userServicesList) {
//            trimmedService = service.toUpperCase();
//            trimmedService = trimmedService.replaceAll(" ", "");
//            trimmedService = trimmedService.replace("-IAAS", "");
//            trimmedServiceList.add(trimmedService);
//        }
        if (!userServicesList.isEmpty()) {
            return useCasesDAO.getUseCasesApplicableForServices(userServicesList);
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
                for (UseCase useCaseDetail : tailoredUseCases.getUseCases()) {
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
                //SENDING EMAIL OF THE TAILORED USE CASES
                useCasesDAO.sendTailoredUseCasesToUser(userId);

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
        logger.info("*******  markUseCaseCaptureCompletion() of  service *****************");
        UserPhaseCompletion upComp = new UserPhaseCompletion();
        upComp.setUserId(userId);
        upComp.setPhase(USER_PHASE_COMPLETION_USECASE_CAPTURE);
        userPhaseCompletionDAO.createPhaseCompletion(upComp);
    }

    @Override
    public UseCaseBenefits getOtherUseCaseBenefits() {
        logger.info("*******  getOtherUseCaseBenefits() of  service *****************");
        return useCasesDAO.getUseCaseBenefits(10);
    }

    @Override
    public String emailCSCUseCaseSelectionIgnored(String userId, String firstName) {
        String subject = null;
        String emailBody = null;
        List<String> ruleKeys = new ArrayList<String>();
        ruleKeys.add(USE_CASE_SEL_IGNORED_SUB);
        ruleKeys.add(USE_CASE_SEL_IGNORED_BODY);
        List<RuleConfiguration> ruleConfigs = ruleConfigurationDAO.getRuleConfigurationsByRuleKeys(ruleKeys);
        for (RuleConfiguration ruleConfig : ruleConfigs) {
            if (USE_CASE_SEL_IGNORED_SUB.equalsIgnoreCase(ruleConfig.getRuleKey())) {
                subject = ruleConfig.getRuleValue();
            } else if (USE_CASE_SEL_IGNORED_BODY.equalsIgnoreCase(ruleConfig.getRuleKey())) {
                emailBody = ruleConfig.getRuleValue();
            }
        }
        subject = subject.replaceAll(firstNamePlaceHolder, firstName);
        emailBody = emailBody.replaceAll(firstNamePlaceHolder, firstName);
        emailBody = emailBody.replaceAll(userIdPlaceHolder, userId);
        String emailSucess = emailUtilDAO.sendEmailToCSC(subject, emailBody);
        return emailSucess;
    }

    @Override
    public UseCaseDetail getUseCaseDetails(int useCaseId) {
        UseCaseDetail useCaseDetail = new UseCaseDetail();
        UseCaseBenefits benefits = useCasesDAO.getUseCaseBenefits(useCaseId);
        useCaseDetail.setBenefits(benefits.getBenefits());
        return useCaseDetail;
    }
    
    @Override
    public UseCases getGuidedPathsProgressForAllUseCases(String userId) {
        logger.info("*******  getGuidedPathsProgressForAllUseCases() of  service *****************");

        UseCases useCases = new UseCases();
        List<UseCase> useCaseList = new ArrayList<UseCase>();
        UseCase useCase = null;
        PathProgressDetail pathProgressDetail = null;
        List<PathProgressDetail> pathProgressDetails = null;
        
        UseCases tailoredUseCases = useCasesDAO.getTailoredUseCasesForUser(userId);
        for (UseCase eachUseCase : tailoredUseCases.getUseCases()) {
            useCase = new UseCase();
            useCase.setId(eachUseCase.getId());
            useCase.setTitle(eachUseCase.getTitle());
            
            pathProgressDetails = new ArrayList<PathProgressDetail>();
            
            // Core Technical Knowledge
            pathProgressDetail = new PathProgressDetail();
            pathProgressDetail.setCode("core");
            pathProgressDetail.setLabel("Core Technical Knowledge");
            double coreTechKnowledgeProgress = getCoreTechKnowledgeProgress(userId);
            pathProgressDetail.setProgress(coreTechKnowledgeProgress);
            pathProgressDetails.add(pathProgressDetail);
            
            // Complementary Knowledge
            pathProgressDetail = new PathProgressDetail();
            pathProgressDetail.setCode("complementary");
            pathProgressDetail.setLabel("Complementary Knowledge");  
            double complementaryKnowledgeProgress = getComplementaryKnowledgeProgress(eachUseCase.getId(), userId);
            pathProgressDetail.setProgress(complementaryKnowledgeProgress);
            pathProgressDetails.add(pathProgressDetail);
            
            // TCO Calculator
            pathProgressDetail = new PathProgressDetail();
            pathProgressDetail.setCode("tco");
            pathProgressDetail.setLabel("TCO Calculator");
            double tcoCalculatorProgress = 0;
            pathProgressDetail.setProgress(tcoCalculatorProgress);
            pathProgressDetails.add(pathProgressDetail);
            
            // Success Stories
            pathProgressDetail = new PathProgressDetail();
            pathProgressDetail.setCode("success");
            pathProgressDetail.setLabel("Success Stories");
            double successStoriesProgress = 0;
            pathProgressDetail.setProgress(successStoriesProgress);
            pathProgressDetails.add(pathProgressDetail);
            
            double overAllProgress = (coreTechKnowledgeProgress + complementaryKnowledgeProgress + tcoCalculatorProgress + successStoriesProgress) / 4;
            useCase.setProgress(overAllProgress);
            useCase.setPathProgressDetails(pathProgressDetails);
            useCaseList.add(useCase);
        }
        
        useCases.setUseCases(useCaseList);            
        return useCases;        
    }
    
    private double getCoreTechKnowledgeProgress(String userId) {
        double coreTechKnowledgeProgress = 0;
        GuidedPaths coreGuidedPaths = guidedPathsService.getCoreGuidedPaths(userId);
        for (GuidedPath eachGuidedPath : coreGuidedPaths.getGuidedPaths()) {
            coreTechKnowledgeProgress += eachGuidedPath.getProgress();
        }        
        return coreTechKnowledgeProgress / (double) coreGuidedPaths.getGuidedPaths().size();                                                         
    }
    
    private double getComplementaryKnowledgeProgress(int useCaseId, String userId) {
        double complementaryKnowledgeProgress = 0;
        int count = 0;
        GuidedPaths complementaryGuidedPaths = guidedPathsService.getComplementaryGuidedPaths(useCaseId, userId);
        for (GuidedPath eachGuidedPath : complementaryGuidedPaths.getGuidedPaths()) {
            if (eachGuidedPath.getUseCaseId() == useCaseId) {
                complementaryKnowledgeProgress += eachGuidedPath.getProgress();
                count++;
            }
        }
        return (count == 0) ? 0 : (complementaryKnowledgeProgress / (double) count);                                                         
    }
    
}
