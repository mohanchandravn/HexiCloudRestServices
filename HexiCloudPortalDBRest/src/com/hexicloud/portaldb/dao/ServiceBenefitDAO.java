package com.hexicloud.portaldb.dao;

import com.hexicloud.portaldb.bean.Usecase;

import java.util.List;

public interface ServiceBenefitDAO {
    public String getServiceBenefitFileID(String serviceName);
    public List<Usecase> getUsecases();
    public String getUsecaseFileID(String usecaseCode);
}
