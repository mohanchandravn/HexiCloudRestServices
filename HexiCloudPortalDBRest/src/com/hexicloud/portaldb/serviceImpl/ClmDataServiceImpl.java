package com.hexicloud.portaldb.serviceImpl;

import com.hexicloud.portaldb.bean.ClmData;
import com.hexicloud.portaldb.dao.ClmDataDAO;
import com.hexicloud.portaldb.service.ClmDataService;

import java.math.BigDecimal;

import java.util.List;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("clmDataService")
public class ClmDataServiceImpl implements ClmDataService {

    private static final Logger logger = Logger.getLogger(ClmDataServiceImpl.class);

    @Autowired
    ClmDataDAO clmDataDAO;

    @Override
    public List<ClmData> getClmData(BigDecimal registryId) {
        logger.info("*******  getApplicationSteps() of  service *****************");
        return clmDataDAO.getClmData(registryId);
    }
}
