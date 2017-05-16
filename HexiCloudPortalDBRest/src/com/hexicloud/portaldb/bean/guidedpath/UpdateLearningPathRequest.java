package com.hexicloud.portaldb.bean.guidedpath;

import java.io.Serializable;

public class UpdateLearningPathRequest implements Serializable {
    @SuppressWarnings("compatibility:5778870679076164017")
    private static final long serialVersionUID = 2974033027960451002L;

    public UpdateLearningPathRequest() {
        super();
    }
    private LearningUpdate learningUpdate;

    public LearningUpdate getLearningUpdate() {
        return learningUpdate;
    }

    public void setLearningUpdate(LearningUpdate learningUpdate) {
        this.learningUpdate = learningUpdate;
    }
}
