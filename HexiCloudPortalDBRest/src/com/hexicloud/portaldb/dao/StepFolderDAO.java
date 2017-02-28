package com.hexicloud.portaldb.dao;

import com.hexicloud.portaldb.bean.StepFolder;

import java.util.List;

public interface StepFolderDAO {
    List<StepFolder> retrieveStepFolderDetails(String stepId);
    void addStepFolder(StepFolder stepFolder);
}
