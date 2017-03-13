package com.hexicloud.portaldb.bean;

import java.io.Serializable;

public class UserEmail implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 8940802173458170997L;

    private Number srId;
    private String userId;
    private String subject;
    private String message;
    private String sentTo;
    private String sentCC;
    private String sentBCC;
    private boolean isResolved;
    private int csmEmailCount;
    private String resolutionComments;
    private String createdDate;

    public UserEmail() {
    }


    public UserEmail(Number srId, String userId, String subject, String message, String sentTo, String sentCC,
                     String sentBCC, boolean isResolved, int csmEmailCount, String resolutionComments,
                     String createdDate) {
        this.srId = srId;
        this.userId = userId;
        this.subject = subject;
        this.message = message;
        this.sentTo = sentTo;
        this.sentCC = sentCC;
        this.sentBCC = sentBCC;
        this.isResolved = isResolved;
        this.csmEmailCount = csmEmailCount;
        this.resolutionComments = resolutionComments;
        this.createdDate = createdDate;
    }

    /**
     * @return the srId
     */
    public Number getSrId() {
        return srId;
    }

    /**
     * @param srId
     *            the srId to set
     */
    public void setSrId(Number srId) {
        this.srId = srId;
    }

    /**
     * @return the userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @param userId
     *            the userId to set
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * @return the subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * @param subject
     *            the subject to set
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message
     *            the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return the sentTo
     */
    public String getSentTo() {
        return sentTo;
    }

    /**
     * @param sentTo
     *            the sentTo to set
     */
    public void setSentTo(String sentTo) {
        this.sentTo = sentTo;
    }

    /**
     * @return the sentCC
     */
    public String getSentCC() {
        return sentCC;
    }

    /**
     * @param sentCC
     *            the sentCC to set
     */
    public void setSentCC(String sentCC) {
        this.sentCC = sentCC;
    }

    /**
     * @return the sentBCC
     */
    public String getSentBCC() {
        return sentBCC;
    }

    /**
     * @param sentBCC
     *            the sentBCC to set
     */
    public void setSentBCC(String sentBCC) {
        this.sentBCC = sentBCC;
    }

    /**
     * @return the isResolved
     */
    public boolean isResolved() {
        return isResolved;
    }

    /**
     * @param isResolved
     *            the isResolved to set
     */
    public void setResolved(boolean isResolved) {
        this.isResolved = isResolved;
    }

    /**
     * @return the csmEmailCount
     */
    public int getCsmEmailCount() {
        return csmEmailCount;
    }

    /**
     * @param csmEmailCount
     *            the csmEmailCount to set
     */
    public void setCsmEmailCount(int csmEmailCount) {
        this.csmEmailCount = csmEmailCount;
    }

    public void setResolutionComments(String resolutionComments) {
        this.resolutionComments = resolutionComments;
    }

    public String getResolutionComments() {
        return resolutionComments;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedDate() {
        return createdDate;
    }
}
