package com.hexicloud.portaldb.service;

import com.hexicloud.portaldb.bean.StepDocument;

import java.util.List;

public interface StepDocumentsService {
    public List<StepDocument> findDocsByStepId(int stepId);

    public List<StepDocument> findDocsByStepCode(String stepCode, String subStepCode);

    public void addStepDocument(StepDocument stepDocument);

    //    public void saveOrUpdateStepDocument(UploadStepDocument uploadStepDocument);
}
