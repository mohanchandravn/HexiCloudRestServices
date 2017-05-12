package com.hexicloud.portaldb.bean;

import java.io.Serializable;

public class Benefit implements Serializable {
    @SuppressWarnings("compatibility:1350666050327929507")
    private static final long serialVersionUID = -3758182472709096056L;

    public Benefit() {
        super();
    }

    private Number benefitId;
    private String title;
    private String shortDesc;
    private String longDesc;
    private String image;
    private String imageLink;

    public void setBenefitId(Number benefitId) {
        this.benefitId = benefitId;
    }

    public Number getBenefitId() {
        return benefitId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public void setLongDesc(String longDesc) {
        this.longDesc = longDesc;
    }

    public String getLongDesc() {
        return longDesc;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public String getImageLink() {
        return imageLink;
    }

}
