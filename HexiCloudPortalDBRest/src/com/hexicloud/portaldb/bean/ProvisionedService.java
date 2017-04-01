package com.hexicloud.portaldb.bean;

import java.util.List;

public class ProvisionedService {
    private int displayOrder;
    private String platform;
    private String service;
    private String serviceType;
    private List<ServiceDetail> details = null;

    public ProvisionedService() {

    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public List<ServiceDetail> getDetails() {
        return details;
    }

    public void setDetails(List<ServiceDetail> details) {
        this.details = details;
    }

    public void setDisplayOrder(int displayOrder) {
        this.displayOrder = displayOrder;
    }

    public int getDisplayOrder() {
        return displayOrder;
    }

  
}
