package com.hexicloud.portaldb.dao;

import com.hexicloud.portaldb.bean.UsecaseOld;

import java.util.List;

public interface ServiceBenefitDAO {
    public String getServiceBenefitFileID(String serviceName);
    public List<UsecaseOld> getUsecases();
    public String getUsecaseFileID(String usecaseCode);
}
