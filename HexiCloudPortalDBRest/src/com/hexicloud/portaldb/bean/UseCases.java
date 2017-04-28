package com.hexicloud.portaldb.bean;

import java.io.Serializable;

import java.util.List;

public class UseCases implements Serializable {
    @SuppressWarnings("compatibility:-6965500733543020899")
    private static final long serialVersionUID = -2389678982719730409L;

    public UseCases() {
        super();
    }

    private List<UseCaseDetail> useCases = null;

    public List<UseCaseDetail> getUseCases() {
        return useCases;
    }

    public void setUseCases(List<UseCaseDetail> useCases) {
        this.useCases = useCases;
    }
}
