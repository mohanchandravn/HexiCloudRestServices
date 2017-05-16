package com.hexicloud.portaldb.bean.guidedpath;

import java.io.Serializable;

public class LearningUpdate implements Serializable {
    @SuppressWarnings("compatibility:4196320312093701404")
    private static final long serialVersionUID = -4903419545042803339L;

    public LearningUpdate() {
        super();
    }
    private Integer pathId;
    private Integer sectionID;
    private Integer sectionDocId;
    private Integer pageNumber;
    private String status;

    public Integer getPathId() {
        return pathId;
    }

    public void setPathId(Integer pathId) {
        this.pathId = pathId;
    }

    public Integer getSectionID() {
        return sectionID;
    }

    public void setSectionID(Integer sectionID) {
        this.sectionID = sectionID;
    }

    public Integer getSectionDocId() {
        return sectionDocId;
    }

    public void setSectionDocId(Integer sectionDocId) {
        this.sectionDocId = sectionDocId;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
