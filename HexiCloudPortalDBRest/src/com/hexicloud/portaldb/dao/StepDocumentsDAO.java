package com.hexicloud.portaldb.dao;

import com.hexicloud.portaldb.bean.StepDocument;

import java.util.List;

public interface StepDocumentsDAO {
    public List<StepDocument> findDocsByStepId(int stepId);

    public void addStepDocument(StepDocument stepDocument);


}
