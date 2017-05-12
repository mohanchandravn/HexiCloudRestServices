package com.hexicloud.portaldb.controller;


import com.hexicloud.portaldb.bean.UsecaseOld;
import com.hexicloud.portaldb.service.ServiceBenefitService;

import java.util.List;

import org.apache.log4j.Logger;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServiceBenefitsController {
    public ServiceBenefitsController() {
        super();
    }
    @Autowired
    private ServiceBenefitService serviceBenefitService;

    private static final Logger logger = Logger.getLogger(ServiceBenefitsController.class);


    @RequestMapping(value = "/services/rest/serviceBenefits/{serviceName}/", method = RequestMethod.GET)
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<JSONObject> getServiceBenefits(@PathVariable(value = "serviceName")
                                                         String serviceName) throws Exception {
        logger.info("******* Start of getServiceBenefits() in controller ***********");
        String benefit = null;
        benefit = serviceBenefitService.getServiceBenefits(serviceName);
        if (null == benefit || benefit.isEmpty()) {
            return new ResponseEntity<JSONObject>(HttpStatus.NO_CONTENT);
        }
        JSONParser parser = new JSONParser();
        //JSONObject json = (JSONObject) parser.parse("{\"phonetype\":\""+serviceName+"\",\"cat\":\"WP\"}");
        JSONObject json = (JSONObject) parser.parse(benefit);
        //	return json;
        return new ResponseEntity<JSONObject>(json, HttpStatus.OK);
    }

    @RequestMapping(value = "/services/rest/usecases/{usecaseCode}/", method = RequestMethod.GET)
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<JSONObject> getUsecaseDetails(@PathVariable(value = "usecaseCode")
                                                        String usecaseCode) throws Exception {
        logger.info("******* Start of getUsecaseDetails() in controller ***********");
        String usecaseDetails = null;
        usecaseDetails = serviceBenefitService.getUsecaseDetails(usecaseCode);
        if (null == usecaseDetails || usecaseDetails.isEmpty()) {
            return new ResponseEntity<JSONObject>(HttpStatus.NO_CONTENT);
        }
        JSONParser parser = new JSONParser();
        //JSONObject json = (JSONObject) parser.parse("{\"phonetype\":\""+serviceName+"\",\"cat\":\"WP\"}");
        JSONObject json = (JSONObject) parser.parse(usecaseDetails);
        //        return json;
        return new ResponseEntity<JSONObject>(json, HttpStatus.OK);
    }

    @RequestMapping(value = "/services/rest/usecases/", method = RequestMethod.GET)
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<UsecaseOld>> getUsecases() throws Exception {
        logger.info("******* Start of getUsecases() in controller ***********");

        List<UsecaseOld> usecaseList = serviceBenefitService.getUsecases();
        if (usecaseList.isEmpty()) {

            logger.info("NO Usecases found");
            return new ResponseEntity<List<UsecaseOld>>(HttpStatus.NO_CONTENT);
        }

        logger.info("******** End of getUsecases() in controller ***********");
        return new ResponseEntity<List<UsecaseOld>>(usecaseList, HttpStatus.OK);

    }


}
