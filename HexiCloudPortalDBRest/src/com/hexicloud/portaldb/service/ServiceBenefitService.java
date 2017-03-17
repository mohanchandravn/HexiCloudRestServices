package com.hexicloud.portaldb.service;

import com.hexicloud.portaldb.bean.Usecase;

import java.util.List;

public interface ServiceBenefitService {

    
    public String getServiceBenefits(String serviceName);
    public List<Usecase> getUsecases();
    public String getUsecaseDetails(String usecaseCode);
      
    
}
