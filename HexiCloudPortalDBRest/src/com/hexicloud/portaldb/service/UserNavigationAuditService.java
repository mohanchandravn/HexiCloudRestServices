package com.hexicloud.portaldb.service;

import com.hexicloud.portaldb.bean.UserNavAudit;

import java.util.List;

public interface UserNavigationAuditService {
    public List<UserNavAudit> getUserNavAudit(String userId);


}
