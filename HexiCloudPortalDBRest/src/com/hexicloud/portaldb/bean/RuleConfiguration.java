package com.hexicloud.portaldb.bean;


public class RuleConfiguration {
    private String ruleKey;
    private String ruleType;
    private String ruleValue;

 
    public void setRuleKey(String ruleKey) {
        this.ruleKey = ruleKey;
    }

 
    public String getRuleKey() {
        return ruleKey;
    }

 
    public void setRuleType(String ruleType) {
        this.ruleType = ruleType;
    }

 
    public String getRuleType() {
        return ruleType;
    }

 
    public void setRuleValue(String ruleValue) {
        this.ruleValue = ruleValue;
    }

 
    public String getRuleValue() {
        return ruleValue;
    }
}
