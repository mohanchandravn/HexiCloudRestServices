package com.hexicloud.portaldb.bean;


public class CallBack {
    public CallBack() {
        super();
    }
    private String userId;
    private String phone;
    private String message;

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
