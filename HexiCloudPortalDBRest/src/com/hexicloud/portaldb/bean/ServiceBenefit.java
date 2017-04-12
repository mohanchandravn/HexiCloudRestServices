package com.hexicloud.portaldb.bean;

import java.io.Serializable;

public class ServiceBenefit implements Serializable {
    @SuppressWarnings("compatibility:-8340279888218910692")
    private static final long serialVersionUID = 5679382399285428242L;

    public ServiceBenefit() {
        super();
    }
    private int id;
    private String serviceCode;
    private String serviceDesc;
    private String fileId;
    private String publicLinkId;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceDesc(String serviceDesc) {
        this.serviceDesc = serviceDesc;
    }

    public String getServiceDesc() {
        return serviceDesc;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getFileId() {
        return fileId;
    }

    public void setPublicLinkId(String publicLinkId) {
        this.publicLinkId = publicLinkId;
    }

    public String getPublicLinkId() {
        return publicLinkId;
    }
}
