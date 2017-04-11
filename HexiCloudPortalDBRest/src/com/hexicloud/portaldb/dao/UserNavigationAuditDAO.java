package com.hexicloud.portaldb.dao;

import com.hexicloud.portaldb.bean.UserNavAudit;

import java.util.List;

public interface UserNavigationAuditDAO {
    public List<UserNavAudit> getUserNavAudit(String whereClause);
}
