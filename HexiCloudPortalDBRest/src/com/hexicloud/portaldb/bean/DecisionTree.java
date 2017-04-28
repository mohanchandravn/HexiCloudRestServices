package com.hexicloud.portaldb.bean;

import java.io.Serializable;

import java.util.List;

public class DecisionTree implements Serializable {
    @SuppressWarnings("compatibility:-3975874657574351862")
    private static final long serialVersionUID = 2658700464996030316L;

    public DecisionTree() {
        super();
    }


    private List<TreeDetail> decisionTree = null;

    public void setDecisionTree(List<TreeDetail> decisionTree) {
        this.decisionTree = decisionTree;
    }

    public List<TreeDetail> getDecisionTree() {
        return decisionTree;
    }
}
