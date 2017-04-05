package com.hexicloud.portaldb.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;


public class ServiceDetail {
    private Integer quantity;
    private String uom;
    private String tier9ShortDesc;
    private String tier9LongDesc;
    @JsonIgnore
    private String actualTier9;

    public void setTier9ShortDesc(String tier9ShortDesc) {
        this.tier9ShortDesc = tier9ShortDesc;
    }

    public String getTier9ShortDesc() {
        return tier9ShortDesc;
    }

    public void setTier9LongDesc(String tier9LongDesc) {
        this.tier9LongDesc = tier9LongDesc;
    }

    public String getTier9LongDesc() {
        return tier9LongDesc;
    }

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

    public void setActualTier9(String actualTier9) {
        this.actualTier9 = actualTier9;
    }

    public String getActualTier9() {
        return actualTier9;
    }
}
