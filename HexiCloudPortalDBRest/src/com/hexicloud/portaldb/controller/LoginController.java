package com.hexicloud.portaldb.controller;

import com.hexicloud.portaldb.bean.User;
import com.hexicloud.portaldb.service.EmailsService;
import com.hexicloud.portaldb.service.LoginService;
import com.hexicloud.portaldb.util.encryption.EncryptionUtil;

import java.sql.SQLException;

import javax.naming.NamingException;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    private static final Logger logger = Logger.getLogger(LoginController.class);

    @Autowired
    private LoginService loginService;
    @Autowired
    private EmailsService emailsService;

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

    @RequestMapping(value = "/services/rest/getUserDetails/{userId}", method = RequestMethod.POST)
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

    @RequestMapping(value = "/services/rest/createPortalUser/", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> createPortalUser(@RequestBody User user) throws Exception {
        logger.info("******* Start of createPortalUser() in controller ***********");
        loginService.createUser(user);

        logger.info("******** End of createPortalUser() in controller ***********");
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/services/rest/updateUserPassword/", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> updateUserPassword(@RequestBody User user) throws Exception {
        logger.info("******* Start of updateUserPassword() in controller ***********");
        loginService.updatePassword(user);

        logger.info("******** End of updateUserPassword() in controller ***********");
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/services/rest/checkUserIdAvailable/{userId}/", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Boolean> checkUserIdExist(@PathVariable("userId") String userId) throws Exception {

        logger.info("******* Start of checkUserIdExist() in controller ***********");
        boolean userIdExists = loginService.checkExistingUser(userId);
        logger.info("******* End of checkUserIdExist() in controller ***********");
        return new ResponseEntity<Boolean>(userIdExists, HttpStatus.OK);
    }

    @RequestMapping(value = "/services/rest/forgotPasswordService/{userId}/", method = RequestMethod.GET)
    @PreAuthorize("hasRole('USER') and #userId == authentication.name")
    public ResponseEntity<String> forgotPasswordService(@PathVariable("userId") String userId) {
        try {
            if (userId != null) {
                User user = loginService.queryUserInfoByUserId(userId);
                if (user == null)
                    return new ResponseEntity<String>("not a valid user id", HttpStatus.NO_CONTENT);
                else {
                    String decodedPassword = EncryptionUtil.decryptString(user.getPassword());
                    ;
                    String subject = "Password Details";
                    String emailContent = "your password is : " + decodedPassword;
                    //String emailId = "shivakumar.gunjur.manjukumar@oracle.com";//user.getEmail()
                    String result = emailsService.sendEmail(user.getEmail(), subject, emailContent);
                    if (result != null && result.equalsIgnoreCase("N")) {
                        return new ResponseEntity<String>(HttpStatus.EXPECTATION_FAILED);
                    }

                }
            }
        } catch (SQLException sqlExp) {
            sqlExp.printStackTrace();
            return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (NamingException nmExp) {
            nmExp.printStackTrace();
            return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception exp) {
            exp.printStackTrace();
            return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
        }


        return new ResponseEntity<String>(HttpStatus.OK);
    }

}
