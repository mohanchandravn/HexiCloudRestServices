package com.hexicloud.portaldb.service;

import com.hexicloud.portaldb.bean.UsecaseOld;

import java.util.List;

public interface ServiceBenefitService {

    
    public String getServiceBenefits(String serviceName);
    public List<UsecaseOld> getUsecases();
    public String getUsecaseDetails(String usecaseCode);
      
    
}
