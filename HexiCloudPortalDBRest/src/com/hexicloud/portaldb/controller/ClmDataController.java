package com.hexicloud.portaldb.controller;

import com.hexicloud.portaldb.bean.ClmData;
import com.hexicloud.portaldb.service.ClmDataService;

import java.math.BigDecimal;

import java.util.List;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClmDataController {
    private static final Logger logger = Logger.getLogger(ClmDataController.class);

    @Autowired
    private ClmDataService clmDataService;

    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Not found in the system")
    @ExceptionHandler(Exception.class)
    public void exceptionHandler() {

    }

    @RequestMapping(value = "/services/rest/getClmData/{registryId}", method = RequestMethod.GET)
    public ResponseEntity<List<ClmData>> getClmDataByRegistryId(@PathVariable("registryId")
                                                                BigDecimal registryId) throws Exception {
        logger.info("******* Start of getClmDataByRegistryId() in controller ***********");

        List<ClmData> clmDataList = clmDataService.getClmData(registryId);
        if (clmDataList.isEmpty()) {

            logger.info("CLM Data with id " + registryId + " not found");
            return new ResponseEntity<List<ClmData>>(HttpStatus.NO_CONTENT);
        }

        logger.info("******** End of getClmDataByRegistryId() in controller ***********");
        return new ResponseEntity<List<ClmData>>(clmDataList, HttpStatus.OK);

    }


}
