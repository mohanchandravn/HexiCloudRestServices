package com.hexicloud.portaldb.bean.guidedpath;

import com.hexicloud.portaldb.bean.Service;

import java.io.Serializable;

import java.util.List;

public class GuidedPathDetail implements Serializable {
    @SuppressWarnings("compatibility:6786073115728675861")
    private static final long serialVersionUID = -1489086636801076342L;

    public GuidedPathDetail() {
        super();
    }

    private Integer pathId;
    private String label;
    private String shortDesc;
    private String image;
    private String imageLink;
    private Boolean isRecommmended;
    private Service service;
    private Integer useCaseId;
    private Integer progress;
    private List<Section> sections = null;

    public Integer getPathId() {
        return pathId;
    }

    public void setPathId(Integer pathId) {
        this.pathId = pathId;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public Boolean getIsRecommmended() {
        return isRecommmended;
    }

    public void setIsRecommmended(Boolean isRecommmended) {
        this.isRecommmended = isRecommmended;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public Integer getUseCaseId() {
        return useCaseId;
    }

    public void setUseCaseId(Integer useCaseId) {
        this.useCaseId = useCaseId;
    }

    public Integer getProgress() {
        return progress;
    }

    public void setProgress(Integer progress) {
        this.progress = progress;
    }

    public List<Section> getSections() {
        return sections;
    }

    public void setSections(List<Section> sections) {
        this.sections = sections;
    }
}
