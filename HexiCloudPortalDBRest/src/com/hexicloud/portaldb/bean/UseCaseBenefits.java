package com.hexicloud.portaldb.bean;

import java.io.Serializable;

import java.util.List;

public class UseCaseBenefits implements Serializable {
    @SuppressWarnings("compatibility:39777334503603892")
    private static final long serialVersionUID = 7963660346990684831L;

    public UseCaseBenefits() {
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
