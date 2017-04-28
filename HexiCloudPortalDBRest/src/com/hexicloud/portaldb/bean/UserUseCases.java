package com.hexicloud.portaldb.bean;

import java.io.Serializable;

import java.util.List;

public class UserUseCases implements Serializable {
    @SuppressWarnings("compatibility:-8016938586627865632")
    private static final long serialVersionUID = -1091176656009245894L;

    public UserUseCases() {
        super();
    }

    private List<UserUseCase> userUseCases = null;

    public List<UserUseCase> getUserUseCases() {
        return userUseCases;
    }

    public void setUserUseCases(List<UserUseCase> userUseCases) {
        this.userUseCases = userUseCases;
    }
}
