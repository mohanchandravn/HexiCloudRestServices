package com.hexicloud.portaldb.dao;

import com.hexicloud.portaldb.bean.ProvisionedService;

import java.util.List;

public interface ClmDataDAO {
    public List<ProvisionedService> getClmData(String userId);
}
