package com.hexicloud.portaldb.service;

import com.hexicloud.portaldb.bean.guidedpath.GuidedPaths;


public interface GuidedPathsService {
    public GuidedPaths getCoreGuidedPaths(String userId);

    public GuidedPaths getComplementaryGuidedPaths(Integer usecaseId, String userId);

}
