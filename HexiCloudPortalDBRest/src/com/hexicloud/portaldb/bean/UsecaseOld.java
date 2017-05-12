package com.hexicloud.portaldb.bean;

import java.io.Serializable;

public class UsecaseOld  implements Serializable {
    @SuppressWarnings("compatibility:-1466594326124746834")
    private static final long serialVersionUID = 1L;

    public UsecaseOld() {
        super();
    }
    
    private int id;
    private String usecaseCode;
    private String usecaseName;
    private String usecaseDesc;
    private String fileId;
    private String publicLinkId;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setUsecaseCode(String usecaseCode) {
        this.usecaseCode = usecaseCode;
    }

    public String getUsecaseCode() {
        return usecaseCode;
    }

    public void setUsecaseName(String usecaseName) {
        this.usecaseName = usecaseName;
    }

    public String getUsecaseName() {
        return usecaseName;
    }

    public void setUsecaseDesc(String usecaseDesc) {
        this.usecaseDesc = usecaseDesc;
    }

    public String getUsecaseDesc() {
        return usecaseDesc;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getFileId() {
        return fileId;
    }

    public void setPublicLinkId(String publicLinkId) {
        this.publicLinkId = publicLinkId;
    }

    public String getPublicLinkId() {
        return publicLinkId;
    }
}
