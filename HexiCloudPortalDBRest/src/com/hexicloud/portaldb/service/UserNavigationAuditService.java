package com.hexicloud.portaldb.service;

import com.hexicloud.portaldb.bean.ExportAudit;
import com.hexicloud.portaldb.bean.UserNavAudit;

import java.util.List;

public interface UserNavigationAuditService {
    public List<UserNavAudit> getUserNavAudit(String userId);

    public void updateAuditOnly(String userId, String stepCode, String action);
    
    public List<ExportAudit> exportAudit(String userId);
}
