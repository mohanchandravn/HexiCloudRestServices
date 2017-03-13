package com.hexicloud.portaldb.controller;

import com.hexicloud.portaldb.bean.UserEmail;
import com.hexicloud.portaldb.service.EmailsService;

import java.util.List;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {
    private static final Logger logger = Logger.getLogger(EmailController.class);

    @Autowired
    private EmailsService emailsService;

    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Not found in the system")
    @ExceptionHandler(Exception.class)
    public void exceptionHandler(Exception ex) {
        logger.error("Exception is :", ex);
    }

    @RequestMapping(value = "/services/rest/findUserEmails", method = RequestMethod.GET)
    public ResponseEntity<List<UserEmail>> findUserEmails(@RequestParam(value = "userId", required = false)
                                                          String userId,
                                                          @RequestParam(value = "isResolved", required = false)
                                                          String isResolved,
                                                          @RequestParam(value = "requestId", required = false)
                                                          Number requestId) throws Exception {
        logger.info("******* Start of findUserEmails() in controller ***********");
        List<UserEmail> emailsList = emailsService.getUserEmails(userId, isResolved, requestId);
        if (emailsList.isEmpty()) {
            logger.info("Emails with id " + userId + " not found");
            return new ResponseEntity<List<UserEmail>>(HttpStatus.NO_CONTENT);
        }
        logger.info("******** End of findUserEmails() in controller ***********");
        return new ResponseEntity<List<UserEmail>>(emailsList, HttpStatus.OK);
    }

    @RequestMapping(value = "/services/rest/saveAndSendEmail/", method = RequestMethod.POST)
    public ResponseEntity<UserEmail> saveAndSendEmail(@RequestBody UserEmail userEmail) throws Exception {
        logger.info("******* Start of create() in controller ***********");
        UserEmail resEmail = emailsService.saveUserEmail(userEmail);
        logger.info("******** End of findMyPoints() in controller ***********");
        if (resEmail == null) {
            logger.info("Could not save and send the email");
            return new ResponseEntity<UserEmail>(HttpStatus.NO_CONTENT);
        }
        logger.info("******** End of findUserEmails() in controller ***********");
        return new ResponseEntity<UserEmail>(resEmail, HttpStatus.CREATED);
    }
    
    @RequestMapping(value = "/services/rest/updateEmailResolution/", method = RequestMethod.POST)
    public ResponseEntity<Void> updateEmailResolution(@RequestBody UserEmail userEmail) throws Exception {
        logger.info("******* Start of updateEmailResolution() in controller ***********");

        emailsService.updateEmailResolution(userEmail);

        logger.info("******** End of updateEmailResolution() in controller ***********");
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }
}
