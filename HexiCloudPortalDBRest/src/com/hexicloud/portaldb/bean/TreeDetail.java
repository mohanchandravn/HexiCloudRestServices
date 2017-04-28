package com.hexicloud.portaldb.bean;

import java.io.Serializable;

public class TreeDetail implements Serializable {
    @SuppressWarnings("compatibility:-3182475450031862402")
    private static final long serialVersionUID = -6934135815069720516L;

    public TreeDetail() {
        super();
    }
    private Integer id;
    private String question;
    private Integer yesQId;
    private Integer noQId;
    private String yesSwitchOffCases;
    private String noSwitchOffCases;
    private String preQId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Integer getYesQId() {
        return yesQId;
    }

    public void setYesQId(Integer yesQId) {
        this.yesQId = yesQId;
    }

    public Integer getNoQId() {
        return noQId;
    }

    public void setNoQId(Integer noQId) {
        this.noQId = noQId;
    }

    public String getYesSwitchOffCases() {
        return yesSwitchOffCases;
    }

    public void setYesSwitchOffCases(String yesSwitchOffCases) {
        this.yesSwitchOffCases = yesSwitchOffCases;
    }

    public String getNoSwitchOffCases() {
        return noSwitchOffCases;
    }

    public void setNoSwitchOffCases(String noSwitchOffCases) {
        this.noSwitchOffCases = noSwitchOffCases;
    }

    public String getPreQId() {
        return preQId;
    }

    public void setPreQId(String preQId) {
        this.preQId = preQId;
    }
}
