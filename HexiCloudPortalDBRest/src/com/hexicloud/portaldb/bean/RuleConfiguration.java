package com.hexicloud.portaldb.bean;


public class RuleConfiguration {
    private String ruleKey;
    private String ruleType;
    private String ruleValue;
    private Integer jobId;
    private String uiLabel;
    private String inputFieldType;
    private String isUpdatable;


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

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    public Integer getJobId() {
        return jobId;
    }

    public void setUiLabel(String uiLabel) {
        this.uiLabel = uiLabel;
    }

    public String getUiLabel() {
        return uiLabel;
    }

    public void setInputFieldType(String inputFieldType) {
        this.inputFieldType = inputFieldType;
    }

    public String getInputFieldType() {
        return inputFieldType;
    }

    public void setIsUpdatable(String isUpdatable) {
        this.isUpdatable = isUpdatable;
    }

    public String getIsUpdatable() {
        return isUpdatable;
    }
}
