package com.hexicloud.portaldb.bean;

import java.io.Serializable;

import java.util.Date;

public class JobHistory implements Serializable {
    @SuppressWarnings("compatibility:-8404475947683614024")
    private static final long serialVersionUID = 445016301975651797L;

    public JobHistory() {
        super();
    }

    private Integer jobHistoryId;
    private Integer jobId;
    private Date startDate;
    private Date endDate;
    private String jobStatus;
    private String jobFailedReason;
    private Date successfulRunReportDate;


    public void setJobHistoryId(Integer jobHistoryId) {
        this.jobHistoryId = jobHistoryId;
    }

    public Integer getJobHistoryId() {
        return jobHistoryId;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    public Integer getJobId() {
        return jobId;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setJobStatus(String jobStatus) {
        this.jobStatus = jobStatus;
    }

    public String getJobStatus() {
        return jobStatus;
    }

    public void setJobFailedReason(String jobFailedReason) {
        this.jobFailedReason = jobFailedReason;
    }

    public String getJobFailedReason() {
        return jobFailedReason;
    }

    public void setSuccessfulRunReportDate(Date successfulRunReportDate) {
        this.successfulRunReportDate = successfulRunReportDate;
    }

    public Date getSuccessfulRunReportDate() {
        return successfulRunReportDate;
    }
}
