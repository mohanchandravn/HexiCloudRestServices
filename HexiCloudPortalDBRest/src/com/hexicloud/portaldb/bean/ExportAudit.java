package com.hexicloud.portaldb.bean;

import java.io.Serializable;

public class ExportAudit implements Serializable {
    @SuppressWarnings("compatibility:1401281432963145728")
    private static final long serialVersionUID = -6873984655344236384L;

    public ExportAudit() {
        super();
    }
    
    private String userId;
    private Integer stepId;
    private String stepLabel;
    private String action;
    private String createdDate;
    private String firstName;
    private String email;
    private String registryId;

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setStepId(Integer stepId) {
        this.stepId = stepId;
    }

    public Integer getStepId() {
        return stepId;
    }

    public void setStepLabel(String stepLabel) {
        this.stepLabel = stepLabel;
    }

    public String getStepLabel() {
        return stepLabel;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getAction() {
        return action;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setRegistryId(String registryId) {
        this.registryId = registryId;
    }

    public String getRegistryId() {
        return registryId;
    }
}
