package com.hexicloud.portaldb.service;

import com.hexicloud.portaldb.bean.StepFolder;

import java.util.List;

public interface StepFolderService {
    
    List<StepFolder> retrieveStepFolderDetails(String stepId);
    void addStepFolder(StepFolder stepFolder);
}
