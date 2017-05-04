package com.hexicloud.portaldb.bean;

import java.io.Serializable;

public class UseCaseBenefit implements Serializable {
    @SuppressWarnings("compatibility:-7892452980405773852")
    private static final long serialVersionUID = -7473386719296391313L;

    public UseCaseBenefit() {
        super();
    }

    private Integer id;
    private String label;
    private Integer usecaseId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setUsecaseId(Integer usecaseId) {
        this.usecaseId = usecaseId;
    }

    public Integer getUsecaseId() {
        return usecaseId;
    }

}
