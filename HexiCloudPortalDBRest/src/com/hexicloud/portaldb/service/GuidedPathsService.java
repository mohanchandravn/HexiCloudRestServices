package com.hexicloud.portaldb.service;

import com.hexicloud.portaldb.bean.guidedpath.GuidedPathDetailResponse;
import com.hexicloud.portaldb.bean.guidedpath.GuidedPaths;
import com.hexicloud.portaldb.bean.guidedpath.UpdateLearningPathRequest;


public interface GuidedPathsService {
    public GuidedPaths getCoreGuidedPaths(String userId);

    public GuidedPaths getComplementaryGuidedPaths(Integer usecaseId, String userId);
    
    public GuidedPathDetailResponse getGuidedPathDetail(Integer pathId, String userId);
    
    public void changeLearningHistory(UpdateLearningPathRequest learningPathRequest, String userId);

}
