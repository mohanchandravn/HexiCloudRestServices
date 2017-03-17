package com.hexicloud.portaldb.serviceImpl;

import com.hexicloud.portaldb.bean.AuthUser;
import com.hexicloud.portaldb.dao.UsersDAO;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthenticateHexiUserServiceImpl implements UserDetailsService {
    private static final Logger logger = Logger.getLogger(AuthenticateHexiUserServiceImpl.class);

    @Autowired
    UsersDAO usersDAO;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("Finding user for authentication with username "  + username);
        AuthUser authUser = usersDAO.getUserDetailsForAuthentication(username);
        if (authUser == null) {
            logger.error("Could not find user with user name "  + username);
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
        } else {
            usersDAO.updateLastLoggedIn(username);
            return authUser;
        }
    }
}
