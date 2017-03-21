package com.hexicloud.portaldb.bean;


public class ServiceDetail {
    private Integer quantity;
    private String uom;
    public ServiceDetail() {
        
    }
    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getUom() {
        return uom;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }
}
