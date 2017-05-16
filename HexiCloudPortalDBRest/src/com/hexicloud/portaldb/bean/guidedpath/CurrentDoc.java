package com.hexicloud.portaldb.bean.guidedpath;

import java.io.Serializable;

import java.sql.Timestamp;

public class CurrentDoc implements Serializable {
    @SuppressWarnings("compatibility:1320759071463334095")
    private static final long serialVersionUID = 8460379265540999483L;

    public CurrentDoc() {
        super();
    }

    private Integer sectionId;
    private Integer sectionDocId;
    private String docName;
    private String publicLink;
    private String docType;
    private String docDescription;
    private String status;
    private Timestamp lastUpdatedDate;
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

    public Timestamp getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(Timestamp lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public String getDocName() {
        return docName;
    }

    public void setPublicLink(String publicLink) {
        this.publicLink = publicLink;
    }

    public String getPublicLink() {
        return publicLink;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    public String getDocType() {
        return docType;
    }

    public void setDocDescription(String docDescription) {
        this.docDescription = docDescription;
    }

    public String getDocDescription() {
        return docDescription;
    }

    public void setSectionDocId(Integer sectionDocId) {
        this.sectionDocId = sectionDocId;
    }

    public Integer getSectionDocId() {
        return sectionDocId;
    }
}
