package com.hexicloud.portaldb.bean;

import java.io.Serializable;

public class OtherUseCase implements Serializable {
    @SuppressWarnings("compatibility:-6380702821182939239")
    private static final long serialVersionUID = 1L;

    public OtherUseCase() {
        super();
    }

    private String userId;
    private String summary;
    private String services;
    private String benefits;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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


    public void setBenefits(String benefits) {
        this.benefits = benefits;
    }

    public String getBenefits() {
        return benefits;
    }
}
