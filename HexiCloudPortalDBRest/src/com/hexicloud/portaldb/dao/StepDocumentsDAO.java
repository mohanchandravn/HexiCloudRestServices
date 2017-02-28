package com.hexicloud.portaldb.dao;

import com.hexicloud.portaldb.bean.StepDocument;

import java.util.List;

public interface StepDocumentsDAO {
    public List<StepDocument> findDocsByStepId(int stepId);
    
    public List<StepDocument> findDocsByStepCode(String stepCode);
    
    public List<StepDocument> findDocsByStepCodeAndSubStep(String stepCode, String subStepCode);

    public void addStepDocument(StepDocument stepDocument);

//    public void saveOrUpdateStepDocument(UploadStepDocument uploadStepDocument);
}
