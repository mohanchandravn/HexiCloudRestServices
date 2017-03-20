package com.hexicloud.portaldb.bean;


public class AuthUserTokenState {
    private String access_token;
    private long expires_in;
    private String userId;

    public String getAccess_token() {
        return access_token;
    }

    public AuthUserTokenState(String access_token, long expires_in, String userId) {
        this.access_token = access_token;
        this.expires_in = expires_in;
        this.userId = userId;
    }


    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }


    public long getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(long expires_in) {
        this.expires_in = expires_in;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }
}
