package com.hexicloud.portaldb.controller;

import com.hexicloud.portaldb.bean.DecisionTree;
import com.hexicloud.portaldb.bean.Services;
import com.hexicloud.portaldb.bean.UseCases;
import com.hexicloud.portaldb.bean.UserUseCases;
import com.hexicloud.portaldb.service.UseCaseService;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UseCasesController {
    private static final Logger logger = Logger.getLogger(UseCasesController.class);

    @Autowired
    private UseCaseService useCaseService;

    @RequestMapping(value = "/services/rest/getAllUseCases", method = RequestMethod.GET)
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<UseCases> getAllUseCases() throws Exception {
        logger.info("******* Start of getAllUseCases() in controller ***********");
        UseCases useCases = useCaseService.getAllUseCases();
        if (useCases.getUseCases().isEmpty()) {
            logger.info("No Use Cases fount");
            return new ResponseEntity<UseCases>(HttpStatus.NO_CONTENT);
        }
        logger.info("******** End of getAllUseCases() in controller ***********");
        return new ResponseEntity<UseCases>(useCases, HttpStatus.OK);
    }

    @RequestMapping(value = "/services/rest/getUseCasesForUser", method = RequestMethod.GET)
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<UseCases> getUseCasesForUser(Authentication authentication) throws Exception {
        logger.info("******* Start of getUseCasesForUser() in controller ***********");
        UseCases useCases = useCaseService.getUseCasesValidForUser(authentication.getName());
        if (useCases.getUseCases().isEmpty()) {
            logger.info("No Use Cases found for user :" + authentication.getName());
            return new ResponseEntity<UseCases>(HttpStatus.NO_CONTENT);
        }
        logger.info("******** End of getUseCasesForUser() in controller ***********");
        return new ResponseEntity<UseCases>(useCases, HttpStatus.OK);
    }

    @RequestMapping(value = "/services/rest/getAllServices", method = RequestMethod.GET)
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Services> getAllServices() throws Exception {
        logger.info("******* Start of getAllServices() in controller ***********");
        Services services = useCaseService.getAllServices();
        if (services.getServices().isEmpty()) {
            logger.info("No services found");
            return new ResponseEntity<Services>(HttpStatus.NO_CONTENT);
        }
        logger.info("******** End of getAllServices() in controller ***********");
        return new ResponseEntity<Services>(services, HttpStatus.OK);
    }

    @RequestMapping(value = "/services/rest/getDecisionTree", method = RequestMethod.GET)
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<DecisionTree> getDecisionTree() throws Exception {
        logger.info("******* Start of getDecisionTree() in controller ***********");
        DecisionTree decisionTree = useCaseService.getDecisionTree();
        if (decisionTree.getDecisionTree().isEmpty()) {
            logger.info("No services found");
            return new ResponseEntity<DecisionTree>(HttpStatus.NO_CONTENT);
        }
        logger.info("******** End of getDecisionTree() in controller ***********");
        return new ResponseEntity<DecisionTree>(decisionTree, HttpStatus.OK);
    }

    @RequestMapping(value = "/services/rest/saveUserUseCases/", method = RequestMethod.POST)
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Void> saveUserUseCases(@RequestBody UserUseCases userUseCases,
                                                 Authentication authentication) throws Exception {
        logger.info("******* Start of saveUserUseCases() in controller ***********");
        useCaseService.createUserUseCases(userUseCases, authentication.getName());
        logger.info("******** End of saveUserUseCases() in controller ***********");
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/services/rest/getTailoredUseCases", method = RequestMethod.GET)
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<UseCases> getTailoredUseCases(Authentication authentication) throws Exception {
        logger.info("******* Start of getTailoredUseCases() in controller ***********");
        UseCases useCases = useCaseService.getTailoredUseCases(authentication.getName());
//        if (useCases.getUseCases().isEmpty()) {
//            logger.info("No Tailored Use Cases found");
//            return new ResponseEntity<UseCases>(HttpStatus.NO_CONTENT);
//        }
        logger.info("******** End of getTailoredUseCases() in controller ***********");
        return new ResponseEntity<UseCases>(useCases, HttpStatus.OK);
    }

    @RequestMapping(value = "/services/rest/markUCCaptureCompletion/", method = RequestMethod.POST)
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Void> markUCCaptureCompletion(Authentication authentication) throws Exception {
        logger.info("******* Start of markUCCaptureCompletion() in controller ***********");
        useCaseService.markUseCaseCaptureCompletion(authentication.getName());
        logger.info("******** End of markUCCaptureCompletion() in controller ***********");
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }
}