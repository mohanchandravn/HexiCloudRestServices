package com.hexicloud.portaldb.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Created by sgunjur on 2/19/2017.
 */
public class UploadStepDocument {


    private String stepId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String stepCode;
    private String docType;
    private String docTypeExtn;
    private String docFieldId;
    private String docMetaData;
    private String fileName;
    private String publicLinkId;
    private String displayLabel;
    private String displayOrder;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String subStepCode;
    private String stepFolderName;
    private String stepFolderDesc;
    private String subStepFolderName;
    private String subStepFolderDesc;

    public String getDocMetaData() {
        return docMetaData;
    }

    public void setDocMetaData(String docMetaData) {
        this.docMetaData = docMetaData;
    }



    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    public String getDocTypeExtn() {
        return docTypeExtn;
    }

    public void setDocTypeExtn(String docTypeExtn) {
        this.docTypeExtn = docTypeExtn;
    }

    public String getDocFieldId() {
        return docFieldId;
    }

    public void setDocFieldId(String docFieldId) {
        this.docFieldId = docFieldId;
    }



    public String getParentID() {
        return parentID;
    }

    public void setParentID(String parentID) {
        this.parentID = parentID;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String parentID;

    public String getStepId() {
        return stepId;
    }

    public void setStepId(String stepId) {
        this.stepId = stepId;
    }

    public String getStepCode() {
        return stepCode;
    }

    public void setStepCode(String stepCode) {
        this.stepCode = stepCode;
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

    public void setDisplayLabel(String displayLabel) {
        this.displayLabel = displayLabel;
    }

    public String getDisplayLabel() {
        return displayLabel;
    }

    public void setDisplayOrder(String displayOrder) {
        this.displayOrder = displayOrder;
    }

    public String getDisplayOrder() {
        return displayOrder;
    }

    public void setSubStepCode(String subStepCode) {
        this.subStepCode = subStepCode;
    }

    public String getSubStepCode() {
        return subStepCode;
    }


    public void setStepFolderName(String stepFolderName) {
        this.stepFolderName = stepFolderName;
    }

    public String getStepFolderName() {
        return stepFolderName;
    }

    public void setStepFolderDesc(String stepFolderDesc) {
        this.stepFolderDesc = stepFolderDesc;
    }

    public String getStepFolderDesc() {
        return stepFolderDesc;
    }

    public void setSubStepFolderName(String subStepFolderName) {
        this.subStepFolderName = subStepFolderName;
    }

    public String getSubStepFolderName() {
        return subStepFolderName;
    }

    public void setSubStepFolderDesc(String subStepFolderDesc) {
        this.subStepFolderDesc = subStepFolderDesc;
    }

    public String getSubStepFolderDesc() {
        return subStepFolderDesc;
    }
}
