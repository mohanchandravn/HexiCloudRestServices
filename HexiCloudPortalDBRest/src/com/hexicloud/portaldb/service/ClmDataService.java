package com.hexicloud.portaldb.service;

import com.hexicloud.portaldb.bean.ClmData;

import java.math.BigDecimal;

import java.util.List;

public interface ClmDataService {
    public List<ClmData> getClmData(BigDecimal registryId);


}
