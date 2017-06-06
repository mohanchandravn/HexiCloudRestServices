package com.hexicloud.portaldb.bean;

import java.io.Serializable;

import java.util.List;

public class OtherUseCases implements Serializable {
    @SuppressWarnings("compatibility:-1414111056555912711")
    private static final long serialVersionUID = 1L;

    public OtherUseCases() {
        super();
    }
    
    private List<OtherUseCase> otherUseCases = null;

    public List<OtherUseCase> getOtherUseCases() {
    return otherUseCases;
    }

    public void setOtherUseCases(List<OtherUseCase> otherUseCases) {
    this.otherUseCases = otherUseCases;
    }
}
