package com.hexicloud.portaldb.controller;

import com.hexicloud.portaldb.bean.CallBack;
import com.hexicloud.portaldb.bean.UserEmail;
import com.hexicloud.portaldb.service.EmailsService;

import java.math.BigDecimal;

import java.util.List;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {
    private static final Logger logger = Logger.getLogger(EmailController.class);

    @Autowired
    private EmailsService emailsService;

    @RequestMapping(value = "/services/rest/findUserEmails", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserEmail>> findUserEmails(@RequestParam(value = "userId", required = false)
                                                          String userId,
                                                          @RequestParam(value = "isResolved", required = false)
                                                          String isResolved,
                                                          @RequestParam(value = "requestId", required = false)
                                                          Number requestId,
                                                          @RequestParam(value = "searchCallBacks", required = false)
                                                          String searchCallBacks) throws Exception {
        logger.info("******* Start of findUserEmails() in controller ***********");
        List<UserEmail> emailsList = emailsService.getUserEmails(userId, isResolved, requestId, searchCallBacks);
        if (emailsList.isEmpty()) {
            logger.info("Emails with id " + userId + " not found");
            return new ResponseEntity<List<UserEmail>>(HttpStatus.NO_CONTENT);
        }
        logger.info("******** End of findUserEmails() in controller ***********");
        return new ResponseEntity<List<UserEmail>>(emailsList, HttpStatus.OK);
    }

    @RequestMapping(value = "/services/rest/saveAndSendEmail/", method = RequestMethod.POST)
    @PreAuthorize("hasRole('USER') and #userEmail.userId == authentication.name")
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
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> updateEmailResolution(@RequestBody UserEmail userEmail) throws Exception {
        logger.info("******* Start of updateEmailResolution() in controller ***********");

        emailsService.updateEmailResolution(userEmail);

        logger.info("******** End of updateEmailResolution() in controller ***********");
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }


    @RequestMapping(value = "/services/rest/requestCallback/", method = RequestMethod.POST)
    @PreAuthorize("hasRole('USER') and #callBack.userId == authentication.name")
    public ResponseEntity<BigDecimal> requestCallBack(@RequestBody CallBack callBack) throws Exception {
        logger.info("******* Start of requestCallBack() in controller ***********");
        BigDecimal requestId = emailsService.requestCallback(callBack);
        logger.info("******** End of requestCallBack() in controller ***********");
        if (requestId == null) {
            logger.info("Could not request call back now");
            return new ResponseEntity<BigDecimal>(HttpStatus.NO_CONTENT);
        }
        logger.info("******** End of requestCallBack() in controller ***********");
        return new ResponseEntity<BigDecimal>(requestId, HttpStatus.CREATED);
    }
}
