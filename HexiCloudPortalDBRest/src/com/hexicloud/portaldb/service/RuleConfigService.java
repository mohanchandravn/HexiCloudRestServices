package com.hexicloud.portaldb.service;

import com.hexicloud.portaldb.bean.RuleConfiguration;

import java.util.List;

public interface RuleConfigService {
    public List<RuleConfiguration> getRuleConfigsByJob(Integer jobId);

    public void updateBulkRuleConfigs(List<RuleConfiguration> ruleConfigs);


}
