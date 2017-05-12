package com.hexicloud.portaldb.bean;

import java.io.Serializable;

import java.util.List;

public class UseCase implements Serializable {
    @SuppressWarnings("compatibility:-5292630709372001411")
    private static final long serialVersionUID = 408258975992037223L;

    public UseCase() {
        super();
    }

    private Integer id;
    private String title;
    private String shortDesc;
    private String longDesc;
    private String image;
    private List<Service> services = null;
    private String benefits;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    public String getLongDesc() {
        return longDesc;
    }

    public void setLongDesc(String longDesc) {
        this.longDesc = longDesc;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }

    public String getBenefits() {
        return benefits;
    }

    public void setBenefits(String benefits) {
        this.benefits = benefits;
    }

}
