package com.hexicloud.portaldb.bean;


public class CustomerRegistry {
    public CustomerRegistry() {
        super();
    }
    
    private String customerRegistry;
    private String registryId;
    private String customerNameTransl;

  
    public void setRegistryId(String registryId) {
        this.registryId = registryId;
    }

    public String getRegistryId() {
        return registryId;
    }

    public void setCustomerRegistry(String customerRegistry) {
        this.customerRegistry = customerRegistry;
    }

    public String getCustomerRegistry() {
        return customerRegistry;
    }

    public void setCustomerNameTransl(String customerNameTransl) {
        this.customerNameTransl = customerNameTransl;
    }

    public String getCustomerNameTransl() {
        return customerNameTransl;
    }
}
