package com.hexicloud.portaldb.bean.guidedpath;

import java.io.Serializable;

public class GuidedPathDetailResponse implements Serializable {
    @SuppressWarnings("compatibility:3421875894206759892")
    private static final long serialVersionUID = 1L;

    public GuidedPathDetailResponse() {
        super();
    }
    private GuidedPathDetail guidedPathDetail;

    public GuidedPathDetail getGuidedPathDetail() {
        return guidedPathDetail;
    }

    public void setGuidedPathDetail(GuidedPathDetail guidedPathDetail) {
        this.guidedPathDetail = guidedPathDetail;
    }

}
