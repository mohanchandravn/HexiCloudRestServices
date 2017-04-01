package com.hexicloud.portaldb.service;

import com.hexicloud.portaldb.bean.ProvisionedService;

import java.util.List;

public interface ClmDataService {
    public List<ProvisionedService> getClmData(String userId);


}
