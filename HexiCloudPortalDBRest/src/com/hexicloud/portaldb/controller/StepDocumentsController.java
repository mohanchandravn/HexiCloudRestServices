package com.hexicloud.portaldb.controller;

import com.hexicloud.portaldb.bean.StepDocument;
import com.hexicloud.portaldb.service.StepDocumentsService;

import java.util.List;

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
public class StepDocumentsController {
    private static final Logger logger = Logger.getLogger(StepDocumentsController.class);

    @Autowired
    private StepDocumentsService stepDocumentsService;

    @RequestMapping(value = "/services/rest/findStepDocsByStepId/{stepId}", method = RequestMethod.GET)
    public ResponseEntity<List<StepDocument>> findStepDocsByStepId(@PathVariable("stepId")
                                                                   int stepId) throws Exception {
        logger.info("******* Start of findStepDocsByStepId() in controller ***********");

        List<StepDocument> stepDocsList = stepDocumentsService.findDocsByStepId(stepId);
        if (stepDocsList.isEmpty()) {

            logger.info("Step documents with id " + stepId + " not found");
            return new ResponseEntity<List<StepDocument>>(HttpStatus.NO_CONTENT);
        }

        logger.info("******** End of findStepDocsByStepId() in controller ***********");
        return new ResponseEntity<List<StepDocument>>(stepDocsList, HttpStatus.OK);

    }

    @RequestMapping(value = "/services/rest/findStepDocsByCode/{stepCode}/{subStepCode}", method = RequestMethod.GET)
    public ResponseEntity<List<StepDocument>> findStepDocsByStepCodeAndSubStep(@PathVariable("stepCode")
                                                                               String stepCode,
                                                                               @PathVariable("subStepCode")
                                                                               String subStepCode) throws Exception {
        logger.info("******* Start of findStepDocsByStepCode() in controller ***********");

        List<StepDocument> stepDocsList = stepDocumentsService.findDocsByStepCode(stepCode, subStepCode);
        if (stepDocsList.isEmpty()) {

            logger.info("Step documents with code " + stepCode + " not found");
            return new ResponseEntity<List<StepDocument>>(HttpStatus.NO_CONTENT);
        }

        logger.info("******** End of findStepDocsByStepCode() in controller ***********");
        return new ResponseEntity<List<StepDocument>>(stepDocsList, HttpStatus.OK);

    }

    @RequestMapping(value = "/services/rest/findStepDocsByCode/{stepCode}", method = RequestMethod.GET)
    public ResponseEntity<List<StepDocument>> findStepDocsByStepCode(@PathVariable("stepCode")
                                                                     String stepCode) throws Exception {
        logger.info("******* Start of findStepDocsByStepCode() in controller ***********");

        List<StepDocument> stepDocsList = stepDocumentsService.findDocsByStepCode(stepCode, null);
        if (stepDocsList.isEmpty()) {

            logger.info("Step documents with code " + stepCode + " not found");
            return new ResponseEntity<List<StepDocument>>(HttpStatus.NO_CONTENT);
        }

        logger.info("******** End of findStepDocsByStepCode() in controller ***********");
        return new ResponseEntity<List<StepDocument>>(stepDocsList, HttpStatus.OK);

    }


    @RequestMapping(value = "/services/rest/addStepDocument/", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> addStepDocument(@RequestBody StepDocument stepDocument) throws Exception {

        logger.info("******* Start of addStepDocument() in controller ***********");

        stepDocumentsService.addStepDocument(stepDocument);

        logger.info("******** End of addStepDocument() in controller ***********");
        return new ResponseEntity<Void>(HttpStatus.CREATED);

    }


}
