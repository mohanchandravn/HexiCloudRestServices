package com.hexicloud.portaldb.serviceImpl;

import com.hexicloud.portaldb.bean.RuleConfiguration;
import com.hexicloud.portaldb.dao.RuleConfigurationDAO;
import com.hexicloud.portaldb.service.RuleConfigService;

import java.util.List;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("ruleConfigService")
public class RuleConfigServiceImpl implements RuleConfigService {
    private static final Logger logger = Logger.getLogger(RuleConfigServiceImpl.class);

    @Autowired
    RuleConfigurationDAO ruleConfigurationDAO;

    @Override
    public List<RuleConfiguration> getRuleConfigsByJob(Integer jobId) {
        logger.info(" Begining of getRuleConfigsByJob() ");
        return ruleConfigurationDAO.getRuleConfByJobId(jobId);
    }

    @Override
    public void updateBulkRuleConfigs(List<RuleConfiguration> ruleConfigs) {
        logger.info(" Begining of updateBulkRuleConfigs() ");
        if (!ruleConfigs.isEmpty()) {
            for (RuleConfiguration ruleConfig : ruleConfigs) {
                ruleConfigurationDAO.updateRuleConfigByRuleKey(ruleConfig);
            }
        }
        logger.info(" Ending of updateBulkRuleConfigs() ");
    }
}
