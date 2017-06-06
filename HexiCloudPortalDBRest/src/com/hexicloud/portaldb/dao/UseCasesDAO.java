package com.hexicloud.portaldb.dao;

import com.hexicloud.portaldb.bean.DecisionTree;
import com.hexicloud.portaldb.bean.Services;
import com.hexicloud.portaldb.bean.UseCaseBenefits;
import com.hexicloud.portaldb.bean.UseCases;
import com.hexicloud.portaldb.bean.UserUseCases;

import java.util.List;

public interface UseCasesDAO {
    public UseCases getAllUseCases();
    
    public UseCases getAllOtherUseCases();

    public UseCases getUseCasesApplicableForServices(List<String> services);

    public Services getAllServices();

    public UseCases getTailoredUseCasesForUser(String userId);

    public DecisionTree getDecisionTree();
    
    public void createUserUseCases(UserUseCases userUseCases);
    
    public UseCaseBenefits getUseCaseBenefits(int useCaseId);
    
    public String sendTailoredUseCasesToUser(String userId);
}
