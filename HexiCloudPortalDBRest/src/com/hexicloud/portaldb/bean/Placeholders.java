package com.hexicloud.portaldb.bean;

import java.io.Serializable;

public class Placeholders implements Serializable {
    @SuppressWarnings("compatibility:1738351476175306578")
    private static final long serialVersionUID = 1320425314144811957L;

    public Placeholders() {
        super();
    }
    
    private String placeholderCode;
    private String placeholderDescription;
    private Integer jobId;

    public void setPlaceholderCode(String placeholderCode) {
        this.placeholderCode = placeholderCode;
    }

    public String getPlaceholderCode() {
        return placeholderCode;
    }

    public void setPlaceholderDescription(String placeholderDescription) {
        this.placeholderDescription = placeholderDescription;
    }

    public String getPlaceholderDescription() {
        return placeholderDescription;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    public Integer getJobId() {
        return jobId;
    }
}
