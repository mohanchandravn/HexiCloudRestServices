package com.hexicloud.portaldb.bean.guidedpath;

import java.io.Serializable;

public class PathProgressDetail implements Serializable {
    
    @SuppressWarnings("compatibility:-3470681934069633461")
    private static final long serialVersionUID = 7297274478413953880L;

    public PathProgressDetail() {
        super();
    }
    
    private String code;
    private String label;
    private Double progress;

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public void setProgress(Double progress) {
        this.progress = progress;
    }

    public Double getProgress() {
        return progress;
    }

}
