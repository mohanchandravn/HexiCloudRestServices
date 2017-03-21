package com.hexicloud.portaldb.controller;

import com.hexicloud.portaldb.bean.ProvisionedService;
import com.hexicloud.portaldb.service.ClmDataService;

import java.util.List;

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
public class ClmDataController {
    private static final Logger logger = Logger.getLogger(ClmDataController.class);

    @Autowired
    private ClmDataService clmDataService;

    @RequestMapping(value = "/services/rest/getClmData/{userId}/", method = RequestMethod.GET)
    @PreAuthorize("hasRole('USER') and #userId == authentication.name")
    public ResponseEntity<List<ProvisionedService>> getClmDataByUserId(@PathVariable("userId")
                                                                String userId) throws Exception {
        logger.info("******* Start of getClmDataByUserId() in controller ***********");

        List<ProvisionedService> clmDataList = clmDataService.getClmData(userId);
        if (clmDataList.isEmpty()) {

            logger.info("CLM Data with id " + userId + " not found");
            return new ResponseEntity<List<ProvisionedService>>(HttpStatus.NO_CONTENT);
        }

        logger.info("******** End of getClmDataByUserId() in controller ***********");
        return new ResponseEntity<List<ProvisionedService>>(clmDataList, HttpStatus.OK);

    }


}
