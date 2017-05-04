package com.hexicloud.portaldb.bean;

import java.io.Serializable;

import java.sql.Timestamp;

public class UserPhaseCompletion implements Serializable {
    @SuppressWarnings("compatibility:-6919468703136347380")
    private static final long serialVersionUID = 6877093920107556626L;

    public UserPhaseCompletion() {
        super();
    }
    
    private Number id;
    private String userId;
    private String phase;
    private Timestamp completedDate;
    private String completionEmailSent;
    private String sentEmailCount;
    
    public void setId(Number id) {
        this.id = id;
    }

    public Number getId() {
        return id;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setPhase(String phase) {
        this.phase = phase;
    }

    public String getPhase() {
        return phase;
    }

    public void setCompletedDate(Timestamp completedDate) {
        this.completedDate = completedDate;
    }

    public Timestamp getCompletedDate() {
        return completedDate;
    }

    public void setCompletionEmailSent(String completionEmailSent) {
        this.completionEmailSent = completionEmailSent;
    }

    public String getCompletionEmailSent() {
        return completionEmailSent;
    }

    public void setSentEmailCount(String sentEmailCount) {
        this.sentEmailCount = sentEmailCount;
    }

    public String getSentEmailCount() {
        return sentEmailCount;
    }

}
