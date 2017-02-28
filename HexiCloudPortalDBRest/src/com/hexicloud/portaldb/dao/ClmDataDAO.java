package com.hexicloud.portaldb.dao;

import com.hexicloud.portaldb.bean.ClmData;

import java.math.BigDecimal;

import java.util.List;

public interface ClmDataDAO {
    public List<ClmData> getClmData(BigDecimal registryId);

}
