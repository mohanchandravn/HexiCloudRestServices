package com.hexicloud.portaldb.dao;

import com.hexicloud.portaldb.bean.ExportAudit;
import com.hexicloud.portaldb.bean.UserNavAudit;

import java.util.List;

public interface UserNavigationAuditDAO {
    public List<UserNavAudit> getUserNavAudit(String whereClause);

    public void updateAuditOnly(String userId, String stepCode, String action);

    public List<ExportAudit> exportAudit(String whereClause);

    public void deleteUserNavEmails(String userId);
}
