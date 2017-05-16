package com.hexicloud.portaldb.dao;

import com.hexicloud.portaldb.bean.guidedpath.GuidedPathDetailResponse;
import com.hexicloud.portaldb.bean.guidedpath.GuidedPaths;
import com.hexicloud.portaldb.bean.guidedpath.UpdateLearningPathRequest;


public interface GuidedPathsDAO {
    public GuidedPaths getCoreGuidedPaths(String userId);

    public GuidedPaths getComplementaryGuidedPaths(Integer useCaseId, String userId);

    public GuidedPathDetailResponse getGuidedPathDetail(Integer pathId, String userId);

    public void updateLearningHistory(UpdateLearningPathRequest learningPathRequest);
}
