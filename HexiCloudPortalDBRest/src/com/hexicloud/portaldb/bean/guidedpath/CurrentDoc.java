package com.hexicloud.portaldb.bean.guidedpath;

import java.io.Serializable;

public class CurrentDoc implements Serializable {
    @SuppressWarnings("compatibility:1320759071463334095")
    private static final long serialVersionUID = 8460379265540999483L;

    public CurrentDoc() {
        super();
    }

    private Integer sectionId;
    private String status;
    private String lastUpdatedDate;
    private Integer pageNumber;

    public Integer getSectionId() {
        return sectionId;
    }

    public void setSectionId(Integer sectionId) {
        this.sectionId = sectionId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(String lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }
}
