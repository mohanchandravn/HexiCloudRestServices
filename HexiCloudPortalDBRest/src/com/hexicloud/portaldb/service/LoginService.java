package com.hexicloud.portaldb.service;

import com.hexicloud.portaldb.bean.User;

public interface LoginService {
    public User authenticate(User user);
}
