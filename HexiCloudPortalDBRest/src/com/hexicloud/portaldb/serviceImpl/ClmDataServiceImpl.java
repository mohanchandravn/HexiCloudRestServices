package com.hexicloud.portaldb.serviceImpl;

import com.hexicloud.portaldb.bean.ProvisionedService;
import com.hexicloud.portaldb.dao.ClmDataDAO;
import com.hexicloud.portaldb.service.ClmDataService;

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
    public List<ProvisionedService> getClmData(String userId) {
        logger.info("*******  getApplicationSteps() of  service *****************");
        return clmDataDAO.getClmData(userId);
    }
}
