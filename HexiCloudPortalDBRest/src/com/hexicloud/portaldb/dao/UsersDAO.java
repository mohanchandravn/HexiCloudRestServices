package com.hexicloud.portaldb.dao;

import com.hexicloud.portaldb.bean.User;

public interface UsersDAO {
    public User getUser(String userId);

    public void updateLastLoggedIn(String userId);
}
