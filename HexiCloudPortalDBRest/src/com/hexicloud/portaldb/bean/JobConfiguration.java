package com.hexicloud.portaldb.bean;

import java.io.Serializable;

import java.math.BigInteger;

import java.util.Collection;

public class JobConfiguration implements Serializable {
    @SuppressWarnings("compatibility:7755508500563637418")
    private static final long serialVersionUID = 7948483984784755509L;

    public JobConfiguration() {
        super();
    }

    private Collection<Placeholders> placeholdersCollection;

    private Collection<RuleConfiguration> ruleConfigurationCollection;

    private Integer jobId;
    private String jobName;
    private String jobDescription;
    private BigInteger jobFrequency;
    private String jobFrequencyType;
    private String className;
    private Short jobFrequencyHour;
    private Short jobFrequencyMinute;
    private String jobStatus;

    public void setPlaceholdersCollection(Collection<Placeholders> placeholdersCollection) {
        this.placeholdersCollection = placeholdersCollection;
    }

    public Collection<Placeholders> getPlaceholdersCollection() {
        return placeholdersCollection;
    }

    public void setRuleConfigurationCollection(Collection<RuleConfiguration> ruleConfigurationCollection) {
        this.ruleConfigurationCollection = ruleConfigurationCollection;
    }

    public Collection<RuleConfiguration> getRuleConfigurationCollection() {
        return ruleConfigurationCollection;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    public Integer getJobId() {
        return jobId;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobFrequency(BigInteger jobFrequency) {
        this.jobFrequency = jobFrequency;
    }

    public BigInteger getJobFrequency() {
        return jobFrequency;
    }

    public void setJobFrequencyType(String jobFrequencyType) {
        this.jobFrequencyType = jobFrequencyType;
    }

    public String getJobFrequencyType() {
        return jobFrequencyType;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassName() {
        return className;
    }

    public void setJobFrequencyHour(Short jobFrequencyHour) {
        this.jobFrequencyHour = jobFrequencyHour;
    }

    public Short getJobFrequencyHour() {
        return jobFrequencyHour;
    }

    public void setJobFrequencyMinute(Short jobFrequencyMinute) {
        this.jobFrequencyMinute = jobFrequencyMinute;
    }

    public Short getJobFrequencyMinute() {
        return jobFrequencyMinute;
    }

    public void setJobStatus(String jobStatus) {
        this.jobStatus = jobStatus;
    }

    public String getJobStatus() {
        return jobStatus;
    }


}
