package com.hexicloud.portaldb.controller;

import com.hexicloud.portaldb.bean.User;
import com.hexicloud.portaldb.service.LoginService;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    private static final Logger logger = Logger.getLogger(LoginController.class);

    @Autowired
    private LoginService loginService;

    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Not found authenticate in the system")
    @ExceptionHandler(Exception.class)
    public void exceptionHandler(Exception ex) {
        logger.error("Exception is :", ex);
    }


    @RequestMapping(value = "/services/rest/authenticate/", method = RequestMethod.POST)
    public ResponseEntity<User> authenticateUser(@RequestBody User user) throws Exception {

        logger.info("******* Start of authenticate() in controller ***********");
        User loggedInUser = null;
        loggedInUser = loginService.authenticate(user);
        if (loggedInUser == null) {

            logger.info("Users with user id " + loggedInUser + " not found");
            return new ResponseEntity<User>(HttpStatus.UNAUTHORIZED);
        }
        logger.info("******** End of authenticate() in controller ***********");
        return new ResponseEntity<User>(loggedInUser, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/services/rest/createPortalUser/", method = RequestMethod.POST)
    public ResponseEntity<Void> createPortalUser(@RequestBody User user) throws Exception {
        logger.info("******* Start of createPortalUser() in controller ***********");
        loginService.createUser(user);
   
        logger.info("******** End of createPortalUser() in controller ***********");
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }
    
    @RequestMapping(value = "/services/rest/updateUserPassword/", method = RequestMethod.POST)
    public ResponseEntity<Void> updateUserPassword(@RequestBody User user) throws Exception {
        logger.info("******* Start of updateUserPassword() in controller ***********");
        loginService.updatePassword(user);
    
        logger.info("******** End of updateUserPassword() in controller ***********");
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }
    
    @RequestMapping(value = "/services/rest/checkUserIdAvailable/{userId}/", method = RequestMethod.GET)
    public ResponseEntity<Boolean> checkUserIdExist(@PathVariable("userId") String userId) throws Exception {

        logger.info("******* Start of checkUserIdExist() in controller ***********");
        boolean userIdExists = loginService.checkExistingUser(userId);
        logger.info("******* End of checkUserIdExist() in controller ***********");
        return new ResponseEntity<Boolean>(userIdExists, HttpStatus.OK);
    }
}
