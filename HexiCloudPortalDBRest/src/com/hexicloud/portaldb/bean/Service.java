package com.hexicloud.portaldb.bean;

import java.io.Serializable;

public class Service implements Serializable {
    @SuppressWarnings("compatibility:6885880837008395009")
    private static final long serialVersionUID = -9158781631301855167L;

    public Service() {
        super();
    }
    private String serviceId;
    private String label;

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
