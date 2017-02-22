package com.hexicloud.portaldb.bean;

import java.io.Serializable;

public class SubStep implements Serializable {
    @SuppressWarnings("compatibility:-1116894770923280069")
    private static final long serialVersionUID = -821454642324355810L;

    public SubStep() {
        super();
    }
    private int stepId;
    private String stepCode;
    private String subStepCode;
    private String subStepLabel;
    private String subStepDesc;

    public void setStepId(int stepId) {
        this.stepId = stepId;
    }

    public int getStepId() {
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

    public void setSubStepLabel(String subStepLabel) {
        this.subStepLabel = subStepLabel;
    }

    public String getSubStepLabel() {
        return subStepLabel;
    }

    public void setSubStepDesc(String subStepDesc) {
        this.subStepDesc = subStepDesc;
    }

    public String getSubStepDesc() {
        return subStepDesc;
    }
}
