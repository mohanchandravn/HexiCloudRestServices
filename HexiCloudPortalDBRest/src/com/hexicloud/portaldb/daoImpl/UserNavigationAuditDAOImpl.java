package com.hexicloud.portaldb.daoImpl;

import com.hexicloud.portaldb.bean.ExportAudit;
import com.hexicloud.portaldb.bean.UserNavAudit;
import com.hexicloud.portaldb.dao.UserNavigationAuditDAO;
import com.hexicloud.portaldb.factory.Steps;
import com.hexicloud.portaldb.util.SqlQueryConstantsUtil;

import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

@Repository
public class UserNavigationAuditDAOImpl implements UserNavigationAuditDAO {
    private static final Logger logger = Logger.getLogger(UserNavigationAuditDAOImpl.class);
    private JdbcTemplate jdbcTemplate;
    private DataSource dataSource;
    private Steps steps;
    private SimpleJdbcCall saveUserNavAudit;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        jdbcTemplate = new JdbcTemplate(this.dataSource);
        this.saveUserNavAudit =
            new SimpleJdbcCall(dataSource).withCatalogName("PKG_USER_NAV_AUDIT")
            .withProcedureName("PRC_SAVE_USER_NAV_AUDIT");
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

    @Override
    public void updateAuditOnly(String userId, String stepCode, String action) {
        logger.info(" Start of updateAuditOnly() ");
        SqlParameterSource inParamsMap = new MapSqlParameterSource().addValue("IN_USER_ID", userId)
                                                                    .addValue("IN_STEP_ID", steps.getStepIdWithStepCode(stepCode))
                                                                    .addValue("IN_CUR_STEP_LABEL", null)
                                                                    .addValue("IN_ACTION", action);
        saveUserNavAudit.execute(inParamsMap);
        logger.info(" End of updateAuditOnly() ");
    }

    @Override
    public List<ExportAudit> exportAudit(String whereClause) {
        logger.info(" Begining of exportAudit() ");
        String searchQuery = SqlQueryConstantsUtil.SQL_GET_AUDIT_FOR_EXPORT;
        if (!StringUtils.isEmpty(whereClause)) {
            searchQuery = searchQuery.concat(whereClause);
        }
        searchQuery.concat("ORDER BY UNA.USER_ID, UNA.CREATED_DATE DESC");
        @SuppressWarnings({ "unchecked", "rawtypes" })
        List<ExportAudit> auditList =
            (List<ExportAudit>) jdbcTemplate.query(searchQuery, new BeanPropertyRowMapper(ExportAudit.class));

        logger.info("exportAudit size ===========> " + auditList != null ? auditList.size() : null);
        if (!StringUtils.isEmpty(auditList)) {
            for (ExportAudit auditRecord : auditList) {
                auditRecord.setStepLabel(steps.getStepLabelWithStepId(auditRecord.getStepId()));
            }
        }
        logger.info(" End of exportAudit() ");
        return auditList;
    }
}
