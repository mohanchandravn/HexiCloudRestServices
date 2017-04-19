package com.hexicloud.portaldb.service;

import com.hexicloud.portaldb.bean.AuthUserTokenState;
import com.hexicloud.portaldb.bean.User;

public interface LoginService {
    //    public User authenticate(User user);
    public User getUserDetails(String userName);

    public AuthUserTokenState getPortalUserDetails(String userName, String accessToken, long expiresIn);

    public AuthUserTokenState getAdminUserDetails(String userName, String accessToken, long expiresIn, String portalRole);
}
