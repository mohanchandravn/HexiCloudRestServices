package com.hexicloud.portaldb.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

public class UserNavAudit implements Serializable {
    public UserNavAudit() {
        super();
    }

    private Integer navId;
    private String userId;
    @JsonIgnore
    private String userNavTrail;
    private String createdDate;
    @JsonIgnore
    private String updatedDate;
    @JsonIgnore
    private Integer helpEmailSentCount;
    private Integer stepId;
    private String action;
    private String stepCode;
    private String stepLabel;

    public void setNavId(Integer navId) {
        this.navId = navId;
    }

    public Integer getNavId() {
        return navId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setStepLabel(String stepLabel) {
        this.stepLabel = stepLabel;
    }

    public String getStepLabel() {
        return stepLabel;
    }

    public void setUserNavTrail(String userNavTrail) {
        this.userNavTrail = userNavTrail;
    }

    public String getUserNavTrail() {
        return userNavTrail;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setHelpEmailSentCount(Integer helpEmailSentCount) {
        this.helpEmailSentCount = helpEmailSentCount;
    }

    public Integer getHelpEmailSentCount() {
        return helpEmailSentCount;
    }

    public void setStepId(Integer stepId) {
        this.stepId = stepId;
    }

    public Integer getStepId() {
        return stepId;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getAction() {
        return action;
    }

    public void setStepCode(String stepCode) {
        this.stepCode = stepCode;
    }

    public String getStepCode() {
        return stepCode;
    }
}
