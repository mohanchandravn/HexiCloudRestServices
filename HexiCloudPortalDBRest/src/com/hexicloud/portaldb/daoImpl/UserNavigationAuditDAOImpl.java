package com.hexicloud.portaldb.daoImpl;

import com.hexicloud.portaldb.bean.UserNavAudit;
import com.hexicloud.portaldb.dao.UserNavigationAuditDAO;
import com.hexicloud.portaldb.factory.Steps;
import com.hexicloud.portaldb.util.SqlQueryConstantsUtil;

import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

@Repository
public class UserNavigationAuditDAOImpl implements UserNavigationAuditDAO {
    private static final Logger logger = Logger.getLogger(UserNavigationAuditDAOImpl.class);
    private JdbcTemplate jdbcTemplate;
    private DataSource dataSource;
    private Steps steps;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        jdbcTemplate = new JdbcTemplate(this.dataSource);
    }

    public void setSteps(Steps steps) {
        this.steps = steps;
    }

    @Override
    public List<UserNavAudit> getUserNavAudit(String whereClause) {
        logger.info(" Begining of getUserNavAudit() ");
        String searchQuery = SqlQueryConstantsUtil.SQL_GET_USER_AUDIT;
        if (!StringUtils.isEmpty(whereClause)) {
            searchQuery = searchQuery.concat(whereClause);
        }
        searchQuery.concat("ORDER BY CREATED_DATE");
        @SuppressWarnings({ "unchecked", "rawtypes" })
        List<UserNavAudit> auditList =
            (List<UserNavAudit>) jdbcTemplate.query(searchQuery, new BeanPropertyRowMapper(UserNavAudit.class));
        
        logger.info("getUserNavAudit size ===========> " + auditList != null ? auditList.size() : null);
        if (!StringUtils.isEmpty(auditList)) {
            for (UserNavAudit auditRecord : auditList) {
                auditRecord.setStepLabel(steps.getStepLabelWithStepId(auditRecord.getStepId()));
            }
        }
        logger.info(" End of getUserNavAudit() ");
        return auditList;
    }
}
