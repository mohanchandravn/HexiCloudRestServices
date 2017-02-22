package com.hexicloud.portaldb.bean;

import java.io.Serializable;

public class StepDocument implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -6606025768623129422L;

    private int stepId;
    private String stepCode;
    private String docType;
    private String docTypeExtn;
    private String docFileId;
    private String docMetaData;
    private String fileName;
    private String publicLinkId;
    private String subStepCode;

    public StepDocument() {

    }


    /**
     * @return the stepId
     */
    public int getStepId() {
        return stepId;
    }

    /**
     * @param stepId
     *            the stepId to set
     */
    public void setStepId(int stepId) {
        this.stepId = stepId;
    }

    /**
     * @return the docType
     */
    public String getDocType() {
        return docType;
    }

    /**
     * @param docType
     *            the docType to set
     */
    public void setDocType(String docType) {
        this.docType = docType;
    }

    /**
     * @return the docTypeExtn
     */
    public String getDocTypeExtn() {
        return docTypeExtn;
    }

    /**
     * @param docTypeExtn
     *            the docTypeExtn to set
     */
    public void setDocTypeExtn(String docTypeExtn) {
        this.docTypeExtn = docTypeExtn;
    }


    public void setStepCode(String stepCode) {
        this.stepCode = stepCode;
    }

    public String getStepCode() {
        return stepCode;
    }

    public void setDocMetaData(String docMetaData) {
        this.docMetaData = docMetaData;
    }

    public String getDocMetaData() {
        return docMetaData;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setPublicLinkId(String publicLinkId) {
        this.publicLinkId = publicLinkId;
    }

    public String getPublicLinkId() {
        return publicLinkId;
    }

    public void setDocFileId(String docFileId) {
        this.docFileId = docFileId;
    }

    public String getDocFileId() {
        return docFileId;
    }

    public void setSubStepCode(String subStepCode) {
        this.subStepCode = subStepCode;
    }

    public String getSubStepCode() {
        return subStepCode;
    }
}
