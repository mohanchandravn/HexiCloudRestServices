package com.hexicloud.portaldb.bean;

import java.io.Serializable;

public class UpdatePassword implements Serializable {
    @SuppressWarnings("compatibility:-4091006454953959176")
    private static final long serialVersionUID = 1367090306415294713L;

    public UpdatePassword() {
        super();
    }
    
    private String userName;
    private String oldPassword;
    private String newPassword;

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }
}
