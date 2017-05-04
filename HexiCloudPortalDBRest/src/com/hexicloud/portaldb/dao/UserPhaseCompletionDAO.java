package com.hexicloud.portaldb.dao;

import com.hexicloud.portaldb.bean.UserPhaseCompletion;

public interface UserPhaseCompletionDAO {
    public void createPhaseCompletion(UserPhaseCompletion userPhaseCompletion);
    
    public UserPhaseCompletion checkPhaseCompletion(String userId, String phase);
}
