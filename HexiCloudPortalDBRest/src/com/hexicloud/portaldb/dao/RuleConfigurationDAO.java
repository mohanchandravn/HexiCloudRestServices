package com.hexicloud.portaldb.dao;

import com.hexicloud.portaldb.bean.RuleConfiguration;

import java.util.List;

public interface RuleConfigurationDAO {
    public List<RuleConfiguration> getRuleConfByJobId(Integer jobId);

    public void updateRuleConfigByRuleKey(RuleConfiguration ruleConfig);
    
    public List<RuleConfiguration> getRuleConfigurationsByRuleKeys(List<String> ruleKeys);
}
