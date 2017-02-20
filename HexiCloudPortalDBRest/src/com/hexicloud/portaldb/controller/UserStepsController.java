package com.hexicloud.portaldb.controller;

import com.hexicloud.portaldb.bean.Step;
import com.hexicloud.portaldb.bean.UserStep;
import com.hexicloud.portaldb.service.UserStepsService;

import java.util.List;

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
public class UserStepsController {
    private static final Logger logger = Logger.getLogger(UserStepsController.class);

    @Autowired
    private UserStepsService userStepsService;

    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Not found in the system")
    @ExceptionHandler(Exception.class)
    public void exceptionHandler() {

    }

    @RequestMapping(value = "/services/rest/createUserStep/", method = RequestMethod.POST)
    public ResponseEntity<Void> createUserStep(@RequestBody UserStep userStep) throws Exception {

        logger.info("******* Start of create() in controller ***********");

        userStepsService.createUserSteps(userStep);

        logger.info("******** End of findMyPoints() in controller ***********");
        return new ResponseEntity<Void>(HttpStatus.CREATED);

    }

    @RequestMapping(value = "/services/rest/findUsersCurrentStep/{userId}/", method = RequestMethod.GET)
    public ResponseEntity<UserStep> findStepDocsByStepId(@PathVariable("userId") String userId) throws Exception {
        logger.info("******* Start of findStepDocsByStepId() in controller ***********");

        UserStep currentStep = userStepsService.getUsersCurrentStep(userId);
        if (currentStep == null) {

            logger.info("Steps with user id " + userId + " not found");
            return new ResponseEntity<UserStep>(HttpStatus.NO_CONTENT);
        }

        logger.info("******** End of findStepDocsByStepId() in controller ***********");
        return new ResponseEntity<UserStep>(currentStep, HttpStatus.OK);

    }


    @RequestMapping(value = "/services/rest/getApplicationSteps", method = RequestMethod.GET)
    public ResponseEntity<List<Step>> getApplicationSteps() throws Exception {
        logger.info("******* Start of getApplicationSteps() in controller ***********");
        List<Step> stepsList = userStepsService.getApplicationSteps();
        if (stepsList.isEmpty()) {

            logger.info("No Steps found");
            return new ResponseEntity<List<Step>>(HttpStatus.NO_CONTENT);
        }

        logger.info("******** End of findStepDocsByStepId() in controller ***********");
        return new ResponseEntity<List<Step>>(stepsList, HttpStatus.OK);
    }

}
