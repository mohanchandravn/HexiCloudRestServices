package com.hexicloud.portaldb.bean;

import java.io.Serializable;

import java.util.List;

public class Services implements Serializable {
    @SuppressWarnings("compatibility:3091428427038459751")
    private static final long serialVersionUID = -7882021205316993879L;

    public Services() {
        super();
    }
    private List<Service> services = null;

    public void setServices(List<Service> services) {
        this.services = services;
    }

    public List<Service> getServices() {
        return services;
    }
}
