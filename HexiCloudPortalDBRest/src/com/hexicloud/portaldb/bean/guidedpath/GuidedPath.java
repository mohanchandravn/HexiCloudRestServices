package com.hexicloud.portaldb.bean.guidedpath;

import com.hexicloud.portaldb.bean.Service;

import java.io.Serializable;

public class GuidedPath implements Serializable {
    @SuppressWarnings("compatibility:4757133362625324677")
    private static final long serialVersionUID = 7100379152083111214L;

    public GuidedPath() {
        super();
    }

    private Integer pathId;
    private String label;
    private String shortDesc;
    private String image;
    private String imageLink;
    private boolean isRecommmended;
    private Service service;
    private Integer useCaseId;
    private double progress;
    private Integer totalChapters;
    private Integer completedChapters;
    private CurrentDoc currentDoc;

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

    public boolean getIsRecommmended() {
        return isRecommmended;
    }

    public void setIsRecommmended(boolean isRecommmended) {
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

    public double getProgress() {
        return progress;
    }

    public void setProgress(double progress) {
        this.progress = progress;
    }

    public CurrentDoc getCurrentDoc() {
        return currentDoc;
    }

    public void setCurrentDoc(CurrentDoc currentDoc) {
        this.currentDoc = currentDoc;
    }


    public void setTotalChapters(Integer totalChapters) {
        this.totalChapters = totalChapters;
    }

    public Integer getTotalChapters() {
        return totalChapters;
    }

    public void setCompletedChapters(Integer completedChapters) {
        this.completedChapters = completedChapters;
    }

    public Integer getCompletedChapters() {
        return completedChapters;
    }

}
