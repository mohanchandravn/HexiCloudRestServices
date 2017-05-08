package com.hexicloud.portaldb.service;

import com.hexicloud.portaldb.bean.DecisionTree;
import com.hexicloud.portaldb.bean.Services;
import com.hexicloud.portaldb.bean.UseCaseBenefits;
import com.hexicloud.portaldb.bean.UseCases;
import com.hexicloud.portaldb.bean.UserUseCases;

public interface UseCaseService {
    public UseCases getAllUseCases();

    public UseCases getUseCasesValidForUser(String userId);
    
    public Services getAllServices();
    
    public DecisionTree getDecisionTree();
    
    public void createUserUseCases(UserUseCases userUseCases, String userId);
    
    public UseCases getTailoredUseCases(String userId);
    
    public void markUseCaseCaptureCompletion(String userId);
    
    public UseCaseBenefits getOtherUseCaseBenefits();
    
    public String emailCSCUseCaseSelectionIgnored(String userId, String firstName);

}
