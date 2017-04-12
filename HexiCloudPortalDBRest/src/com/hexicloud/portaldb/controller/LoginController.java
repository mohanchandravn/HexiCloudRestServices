package com.hexicloud.portaldb.controller;

import com.hexicloud.portaldb.bean.User;
import com.hexicloud.portaldb.service.LoginService;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    private static final Logger logger = Logger.getLogger(LoginController.class);

    @Autowired
    private LoginService loginService;


    //    @RequestMapping(value = "/services/rest/authenticate/", method = RequestMethod.POST)
    //    public ResponseEntity<User> authenticateUser(@RequestBody User user) throws Exception {
    //
    //        logger.info("******* Start of authenticate() in controller ***********");
    //        User loggedInUser = null;
    //        loggedInUser = loginService.authenticate(user);
    //        if (loggedInUser == null) {
    //
    //            logger.info("Users with user id " + loggedInUser + " not found");
    //            return new ResponseEntity<User>(HttpStatus.UNAUTHORIZED);
    //        }
    //        logger.info("******** End of authenticate() in controller ***********");
    //        return new ResponseEntity<User>(loggedInUser, HttpStatus.OK);
    //    }

    @RequestMapping(value = "/services/rest/getUserDetails/{userId}/", method = RequestMethod.GET)
    @PreAuthorize("hasRole('USER') and #userId == authentication.name")
    public ResponseEntity<User> getUserDetails(@PathVariable("userId") String userId) throws Exception {
        logger.info("******* Start of findStepDocsByStepId() in controller ***********");
        User user = loginService.getUserDetails(userId);
        if (user == null) {
            logger.info("User with user id " + userId + " not found");
            return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
        }
        logger.info("******** End of findStepDocsByStepId() in controller ***********");
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }
}
