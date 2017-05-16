package com.hexicloud.portaldb.bean.guidedpath;

import java.io.Serializable;

public class SectionDoc implements Serializable {
    @SuppressWarnings("compatibility:-6259651126280400017")
    private static final long serialVersionUID = 8201772427772696531L;

    public SectionDoc() {
        super();
    }

    private Integer sectionDocId;
    private String docName;
    private String docType;
    private String timeToComplete;
    private String publicLink;
    private String docDescription;
    private Integer docOrder;
    private CurrentDoc currentDoc;

    public Integer getSectionDocId() {
        return sectionDocId;
    }

    public void setSectionDocId(Integer sectionDocId) {
        this.sectionDocId = sectionDocId;
    }

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    public String getTimeToComplete() {
        return timeToComplete;
    }

    public void setTimeToComplete(String timeToComplete) {
        this.timeToComplete = timeToComplete;
    }

    public String getPublicLink() {
        return publicLink;
    }

    public void setPublicLink(String publicLink) {
        this.publicLink = publicLink;
    }

    public String getDocDescription() {
        return docDescription;
    }

    public void setDocDescription(String docDescription) {
        this.docDescription = docDescription;
    }

    public Integer getDocOrder() {
        return docOrder;
    }

    public void setDocOrder(Integer docOrder) {
        this.docOrder = docOrder;
    }

    public CurrentDoc getCurrentDoc() {
        return currentDoc;
    }

    public void setCurrentDoc(CurrentDoc currentDoc) {
        this.currentDoc = currentDoc;
    }
}
