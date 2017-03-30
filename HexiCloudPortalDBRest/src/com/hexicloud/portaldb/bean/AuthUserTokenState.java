package com.hexicloud.portaldb.bean;


public class AuthUserTokenState {
    private String access_token;
    private long expires_in;
    private String userId;
    private String email;
    private String userRole;
    private String firstName;
    private String lastName;
    private String curStepId;
    private String curStepCode;
    private String preStepId;
    private String preStepCode;
    private String phone;

    public String getAccess_token() {
        return access_token;
    }


    public AuthUserTokenState(String access_token, long expires_in, String userId, String email, String userRole,
                              String firstName, String lastName, String curStepId, String curStepCode, String preStepId,
                              String preStepCode, String phone) {
        this.access_token = access_token;
        this.expires_in = expires_in;
        this.userId = userId;
        this.email = email;
        this.userRole = userRole;
        this.firstName = firstName;
        this.lastName = lastName;
        this.curStepId = curStepId;
        this.curStepCode = curStepCode;
        this.preStepId = preStepId;
        this.preStepCode = preStepCode;
        this.phone = phone;
    }


    public long getExpires_in() {
        return expires_in;
    }





    public String getUserId() {
        return userId;
    }



    public String getEmail() {
        return email;
    }



    public String getUserRole() {
        return userRole;
    }



    public String getFirstName() {
        return firstName;
    }



    public String getLastName() {
        return lastName;
    }



    public String getCurStepId() {
        return curStepId;
    }



    public String getCurStepCode() {
        return curStepCode;
    }



    public String getPreStepId() {
        return preStepId;
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
}
