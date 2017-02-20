package com.hexicloud.portaldb.service;

import com.hexicloud.portaldb.bean.StepDocument;

import java.util.List;

public interface StepDocumentsService {
    public List<StepDocument> findDocsByStepId(int stepId);

    public void addStepDocument(StepDocument stepDocument);
}