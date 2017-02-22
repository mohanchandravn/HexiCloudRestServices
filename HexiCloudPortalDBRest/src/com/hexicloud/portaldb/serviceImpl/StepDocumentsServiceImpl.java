package com.hexicloud.portaldb.serviceImpl;

import com.hexicloud.portaldb.bean.StepDocument;
import com.hexicloud.portaldb.dao.StepDocumentsDAO;
import com.hexicloud.portaldb.service.StepDocumentsService;

import java.util.List;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("stepDocumentsService")
public class StepDocumentsServiceImpl implements StepDocumentsService {

    private static final Logger logger = Logger.getLogger(StepDocumentsServiceImpl.class);

    @Autowired
    StepDocumentsDAO stepDocumentsDAO;

    @Override
    public List<StepDocument> findDocsByStepId(int stepId) {
        logger.info("*******  findDocsByStepId() of  service *****************");
        return stepDocumentsDAO.findDocsByStepId(stepId);
    }
    
    @Override
    public List<StepDocument> findDocsByStepCode(String stepCode) {
        logger.info("*******  findDocsByStepCode() of  service *****************");
        return stepDocumentsDAO.findDocsByStepCode(stepCode);
    }

    @Override
    public void addStepDocument(StepDocument stepDocument) {
        logger.info("*******  addStepDocument() of  service *****************");
        stepDocumentsDAO.addStepDocument(stepDocument);
    }

//    @Override
//    public void saveOrUpdateStepDocument(UploadStepDocument uploadStepDocument)
//    {
//        stepDocumentsDAO.saveOrUpdateStepDocument(uploadStepDocument);
//    }
}
