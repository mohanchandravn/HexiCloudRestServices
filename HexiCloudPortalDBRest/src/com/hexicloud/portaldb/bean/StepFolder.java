package com.hexicloud.portaldb.bean;


public class StepFolder {
    private String stepId;
    private String stepCode;
    private String subStepCode;
    private String folderId;

    public void setStepId(String stepId) {
        this.stepId = stepId;
    }

    public String getStepId() {
        return stepId;
    }

    public void setStepCode(String stepCode) {
        this.stepCode = stepCode;
    }

    public String getStepCode() {
        return stepCode;
    }

    public void setSubStepCode(String subStepCode) {
        this.subStepCode = subStepCode;
    }

    public String getSubStepCode() {
        return subStepCode;
    }

    public void setFolderId(String folderId) {
        this.folderId = folderId;
    }

    public String getFolderId() {
        return folderId;
    }
}
