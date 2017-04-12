package com.hexicloud.portaldb.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;


public class AuthUserTokenState {
    private String access_token;
    private long expires_in;
    private String userId;
    private String email;
    private String userRole;
    private String firstName;
    private String lastName;
    @JsonIgnore
    private String curStepId;
    private String curStepCode;
    @JsonIgnore
    private String preStepId;
    private String preStepCode;
    private String phone;
    private boolean firstLogin;
    private boolean changePwd;
    private boolean onBoardingCompleted;

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setExpires_in(long expires_in) {
        this.expires_in = expires_in;
    }

    public long getExpires_in() {
        return expires_in;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setCurStepId(String curStepId) {
        this.curStepId = curStepId;
    }

    public String getCurStepId() {
        return curStepId;
    }

    public void setCurStepCode(String curStepCode) {
        this.curStepCode = curStepCode;
    }

    public String getCurStepCode() {
        return curStepCode;
    }

    public void setPreStepId(String preStepId) {
        this.preStepId = preStepId;
    }

    public String getPreStepId() {
        return preStepId;
    }

    public void setPreStepCode(String preStepCode) {
        this.preStepCode = preStepCode;
    }

    public String getPreStepCode() {
        return preStepCode;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setFirstLogin(boolean firstLogin) {
        this.firstLogin = firstLogin;
    }

    public boolean isFirstLogin() {
        return firstLogin;
    }

    public void setOnBoardingCompleted(boolean onBoardingCompleted) {
        this.onBoardingCompleted = onBoardingCompleted;
    }

    public boolean isOnBoardingCompleted() {
        return onBoardingCompleted;
    }

    public void setChangePwd(boolean changePwd) {
        this.changePwd = changePwd;
    }

    public boolean isChangePwd() {
        return changePwd;
    }
}
