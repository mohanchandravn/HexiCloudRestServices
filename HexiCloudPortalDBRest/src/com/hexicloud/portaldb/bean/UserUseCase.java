package com.hexicloud.portaldb.bean;

import java.io.Serializable;

public class UserUseCase implements Serializable {
    @SuppressWarnings("compatibility:-927901199535286312")
    private static final long serialVersionUID = 1L;

    public UserUseCase() {
        super();
    }

    private String userId;
    private Integer useCaseId;
    private String code;
    private String summary;
    private String services;
    private String benefits;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getUseCaseId() {
        return useCaseId;
    }

    public void setUseCaseId(Integer useCaseId) {
        this.useCaseId = useCaseId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getServices() {
        return services;
    }

    public void setServices(String services) {
        this.services = services;
    }

    public String getBenefits() {
        return benefits;
    }

    public void setBenefits(String benefits) {
        this.benefits = benefits;
    }
}
