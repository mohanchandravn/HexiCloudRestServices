package com.hexicloud.portaldb.daoImpl;

import com.hexicloud.portaldb.bean.RuleConfiguration;
import com.hexicloud.portaldb.dao.RuleConfigurationDAO;
import com.hexicloud.portaldb.util.SqlQueryConstantsUtil;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class RuleConfigurationDAOImpl implements RuleConfigurationDAO {
    private static final Logger logger = Logger.getLogger(ClmDataDAOImpl.class);
    private JdbcTemplate jdbcTemplate;
    private DataSource dataSource;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        jdbcTemplate = new JdbcTemplate(this.dataSource);
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource());
    }

    @Override
    public List<RuleConfiguration> getRuleConfByJobId(Integer jobId) {
        logger.info(" Begining of getRuleConfByJobId() ");
        @SuppressWarnings({ "unchecked", "rawtypes" })
        List<RuleConfiguration> ruleConfigs =
            jdbcTemplate.query(SqlQueryConstantsUtil.SQL_RULE_CONFIGURATION_BY_JOB, new Object[] { jobId },
                               new BeanPropertyRowMapper(RuleConfiguration.class));
        logger.info("getRuleConfByJobId size ===========> " + ruleConfigs != null ? ruleConfigs.size() : null);
        logger.info(" End of getRuleConfByJobId() ");
        return ruleConfigs;
    }

    @Override
    public void updateRuleConfigByRuleKey(RuleConfiguration ruleConfig) {
        logger.info(" Begining of updateRuleConfigByRuleKey() ");
        jdbcTemplate.update(SqlQueryConstantsUtil.SQL_UPDATE_RULE_CONFIG_BY_RULE_KEY,
                            new Object[] { ruleConfig.getRuleValue(), ruleConfig.getRuleKey() });
        logger.info(" Ending of updateRuleConfigByRuleKey() ");
    }

    @Override
    public List<RuleConfiguration> getRuleConfigurationsByRuleKeys(List<String> ruleKeys) {
        Map<String, List> paramMap = Collections.singletonMap("ruleKeys", ruleKeys);
        @SuppressWarnings({ "unchecked", "rawtypes" })
        List<RuleConfiguration> ruleConfigs =
            namedParameterJdbcTemplate.query(SqlQueryConstantsUtil.SQL_GET_RULE_CONFIGURATIONS_WITH_RULE_KEYS, paramMap,
                                             new BeanPropertyRowMapper(RuleConfiguration.class));
        return ruleConfigs;
    }
}
