package com.hexicloud.portaldb.bean;

import java.io.Serializable;

import java.util.List;

public class UseCaseDetail implements Serializable {
    public UseCaseDetail() {
        super();
    }
    
    private List<Benefit> benefits;

    public void setBenefits(List<Benefit> benefits) {
        this.benefits = benefits;
    }

    public List<Benefit> getBenefits() {
        return benefits;
    }
}
