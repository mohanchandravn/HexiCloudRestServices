package com.hexicloud.portaldb.bean;

import java.io.Serializable;

public class ClmData implements Serializable {
    @SuppressWarnings("compatibility:3775056980475736013")
    private static final long serialVersionUID = 928820173217527175L;

    private String custRegistryId;
    private String fphDescriptionTier4;
    private String fphDescriptionTier5;
    private String fphDescriptionTier6;
    private Integer quantity;
    private String quantityUnitOfMeasure;
    public ClmData() {

    }
    public ClmData(String custRegistryId, String fphDescriptionTier4, String fphDescriptionTier5,
                   String fphDescriptionTier6, Integer quantity, String quantityUnitOfMeasure) {
        this.custRegistryId = custRegistryId;
        this.fphDescriptionTier4 = fphDescriptionTier4;
        this.fphDescriptionTier5 = fphDescriptionTier5;
        this.fphDescriptionTier6 = fphDescriptionTier6;
        this.quantity = quantity;
        this.quantityUnitOfMeasure = quantityUnitOfMeasure;
    }


    public void setCustRegistryId(String custRegistryId) {
        this.custRegistryId = custRegistryId;
    }

    public String getCustRegistryId() {
        return custRegistryId;
    }

    public void setFphDescriptionTier4(String fphDescriptionTier4) {
        this.fphDescriptionTier4 = fphDescriptionTier4;
    }

    public String getFphDescriptionTier4() {
        return fphDescriptionTier4;
    }

    public void setFphDescriptionTier5(String fphDescriptionTier5) {
        this.fphDescriptionTier5 = fphDescriptionTier5;
    }

    public String getFphDescriptionTier5() {
        return fphDescriptionTier5;
    }

    public void setFphDescriptionTier6(String fphDescriptionTier6) {
        this.fphDescriptionTier6 = fphDescriptionTier6;
    }

    public String getFphDescriptionTier6() {
        return fphDescriptionTier6;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantityUnitOfMeasure(String quantityUnitOfMeasure) {
        this.quantityUnitOfMeasure = quantityUnitOfMeasure;
    }

    public String getQuantityUnitOfMeasure() {
        return quantityUnitOfMeasure;
    }
   

}
