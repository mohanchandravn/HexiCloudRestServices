package com.hexicloud.portaldb.bean;

import java.io.Serializable;

import java.util.List;

public class Step implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 7664948341933779997L;

    private int stepId;
    private String stepCode;
    private String description;
    private boolean isRoleSelelction;
    private boolean isDecisionMaking;
    private boolean nonRedirectStep;
    private String stepLabel;
    private String hasSubStep;
    private List<SubStep> subSteps;

    public int getStepId() {
        return stepId;
    }

    public void setStepId(int stepId) {
        this.stepId = stepId;
    }

    public String getStepCode() {
        return stepCode;
    }

    public void setStepCode(String stepCode) {
        this.stepCode = stepCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isRoleSelelction() {
        return isRoleSelelction;
    }

    public void setRoleSelelction(boolean isRoleSelelction) {
        this.isRoleSelelction = isRoleSelelction;
    }

    public boolean isDecisionMaking() {
        return isDecisionMaking;
    }

    public void setDecisionMaking(boolean isDecisionMaking) {
        this.isDecisionMaking = isDecisionMaking;
    }

    public boolean isNonRedirectStep() {
        return nonRedirectStep;
    }

    public void setNonRedirectStep(boolean nonRedirectStep) {
        this.nonRedirectStep = nonRedirectStep;
    }

    public void setStepLabel(String stepLabel) {
        this.stepLabel = stepLabel;
    }

    public String getStepLabel() {
        return stepLabel;
    }
    public void setHasSubStep(String hasSubStep) {
        this.hasSubStep = hasSubStep;
    }

    public String getHasSubStep() {
        return hasSubStep;
    }

    public void setSubSteps(List<SubStep> subSteps) {
        this.subSteps = subSteps;
    }

    public List<SubStep> getSubSteps() {
        return subSteps;
    }
}
