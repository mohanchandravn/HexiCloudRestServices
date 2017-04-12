package com.hexicloud.portaldb.serviceImpl;

import com.hexicloud.portaldb.bean.UserNavAudit;
import com.hexicloud.portaldb.dao.UserNavigationAuditDAO;
import com.hexicloud.portaldb.service.UserNavigationAuditService;

import java.util.List;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service("userNavigationAuditService")
public class UserNavigationAuditServiceImpl implements UserNavigationAuditService {
    private static final Logger logger = Logger.getLogger(UserNavigationAuditServiceImpl.class);

    @Autowired
    UserNavigationAuditDAO userNavigationAuditDAO;

    @Override
    public List<UserNavAudit> getUserNavAudit(String userId) {
        StringBuilder whereClause = new StringBuilder();
        if (!StringUtils.isEmpty(userId)) {
            whereClause.append(" WHERE USER_ID = '")
                       .append(userId)
                       .append("'");
        }
        logger.info("the where clause is :" + whereClause.toString());
        return userNavigationAuditDAO.getUserNavAudit(whereClause.toString());
    }

    @Override
    public void updateAuditOnly(String userId, String stepCode, String action) {
        logger.info("updateAuditOnly in the service");
        userNavigationAuditDAO.updateAuditOnly(userId, stepCode, action);
    }
}
