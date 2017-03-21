package com.hexicloud.portaldb.bean;

import java.io.Serializable;

import java.sql.Timestamp;

public class User implements Serializable {
    @SuppressWarnings("compatibility:-2482833352856503834")
    private static final long serialVersionUID = 1434610688784422089L;

    private String userId;
    private String password;
    private String email;
    private String userRole;
    private String firstName;
    private String lastName;
    private boolean active;
    private Timestamp pwdLastChanged;
    private Timestamp lastLoggedIn;
    private String registryId;
    public User() {

    }
    public User(String userId, String password, String email, String userRole, String firstName, String lastName,
                boolean active, Timestamp pwdLastChanged, Timestamp lastLoggedIn, String registryId) {
        this.userId = userId;
        this.password = password;
        this.email = email;
        this.userRole = userRole;
        this.firstName = firstName;
        this.lastName = lastName;
        this.active = active;
        this.pwdLastChanged = pwdLastChanged;
        this.lastLoggedIn = lastLoggedIn;
        this.registryId = registryId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
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

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isActive() {
        return active;
    }

    public void setPwdLastChanged(Timestamp pwdLastChanged) {
        this.pwdLastChanged = pwdLastChanged;
    }

    public Timestamp getPwdLastChanged() {
        return pwdLastChanged;
    }

    public void setLastLoggedIn(Timestamp lastLoggedIn) {
        this.lastLoggedIn = lastLoggedIn;
    }

    public Timestamp getLastLoggedIn() {
        return lastLoggedIn;
    }

    public void setRegistryId(String registryId) {
        this.registryId = registryId;
    }

    public String getRegistryId() {
        return registryId;
    }
}
