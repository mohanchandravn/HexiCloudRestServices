package com.hexicloud.portaldb.bean.guidedpath;

import java.io.Serializable;

import java.util.List;

public class GuidedPaths implements Serializable {
    @SuppressWarnings("compatibility:-8288474663647245775")
    private static final long serialVersionUID = 6657311329436941030L;

    public GuidedPaths() {
        super();
    }
    private List<GuidedPath> guidedPaths = null;

    public List<GuidedPath> getGuidedPaths() {
        return guidedPaths;
    }

    public void setGuidedPaths(List<GuidedPath> guidedPaths) {
        this.guidedPaths = guidedPaths;
    }

}
