package com.hexicloud.portaldb.bean.guidedpath;

import java.io.Serializable;

import java.util.List;

public class Section implements Serializable {
    @SuppressWarnings("compatibility:4539670033691124448")
    private static final long serialVersionUID = -7450682606007736679L;

    public Section() {
        super();
    }

    private Integer sectionID;
    private String sectionTitle;
    private String description;
    private Integer sectionOrder;
    private List<SectionDoc> sectionDocs = null;

    public Integer getSectionID() {
        return sectionID;
    }

    public void setSectionID(Integer sectionID) {
        this.sectionID = sectionID;
    }

    public String getSectionTitle() {
        return sectionTitle;
    }

    public void setSectionTitle(String sectionTitle) {
        this.sectionTitle = sectionTitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getSectionOrder() {
        return sectionOrder;
    }

    public void setSectionOrder(Integer sectionOrder) {
        this.sectionOrder = sectionOrder;
    }

    public List<SectionDoc> getSectionDocs() {
        return sectionDocs;
    }

    public void setSectionDocs(List<SectionDoc> sectionDocs) {
        this.sectionDocs = sectionDocs;
    }
}
