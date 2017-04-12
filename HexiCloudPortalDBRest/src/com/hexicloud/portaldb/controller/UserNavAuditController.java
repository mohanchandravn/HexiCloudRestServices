package com.hexicloud.portaldb.controller;

import com.hexicloud.portaldb.bean.UserNavAudit;
import com.hexicloud.portaldb.service.UserNavigationAuditService;

import java.util.List;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserNavAuditController {
    private static final Logger logger = Logger.getLogger(UserNavAuditController.class);

    @Autowired
    private UserNavigationAuditService userNavigationAuditService;


    @RequestMapping(value = "/services/rest/getUserNavAudit", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserNavAudit>> getUserNavAudit(@RequestParam(value = "userId", required = false)
                                                              String userId) throws Exception {
        logger.info("******* Start of getUserNavAudit() in controller ***********");
        List<UserNavAudit> stepsList = userNavigationAuditService.getUserNavAudit(userId);
        if (stepsList.isEmpty()) {

            logger.info("No audit found");
            return new ResponseEntity<List<UserNavAudit>>(HttpStatus.NO_CONTENT);
        }

        logger.info("******** End of getUserNavAudit() in controller ***********");
        return new ResponseEntity<List<UserNavAudit>>(stepsList, HttpStatus.OK);
    }
    
    
    @RequestMapping(value = "/services/rest/updateAudit/", method = RequestMethod.POST)
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Void> updateAudit(@RequestBody UserNavAudit userNavAudit, Authentication authentication) throws Exception {
        logger.info("******* Start of updateAudit() in controller ***********");
        userNavigationAuditService.updateAuditOnly(authentication.getName(), userNavAudit.getStepCode(), userNavAudit.getAction());
        logger.info("******** End of updateAudit() in controller ***********");
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }


}
