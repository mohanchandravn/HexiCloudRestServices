package com.hexicloud.portaldb.controller;

import com.hexicloud.portaldb.bean.guidedpath.GuidedPaths;
import com.hexicloud.portaldb.service.GuidedPathsService;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GuidedPathsController {
    private static final Logger logger = Logger.getLogger(GuidedPathsController.class);

    @Autowired
    private GuidedPathsService guidedPathsService;

    @RequestMapping(value = "/services/rest/getCoreGuidedPaths", method = RequestMethod.GET)
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<GuidedPaths> getCoreGuidedPaths(Authentication authentication) throws Exception {
        logger.info(" Begining of getCoreGuidedPaths() in controller");
        GuidedPaths coreGuidedPaths = guidedPathsService.getCoreGuidedPaths(authentication.getName());
        if (coreGuidedPaths.getGuidedPaths().isEmpty()) {

            logger.info("Core Guided paths not fount");
            return new ResponseEntity<GuidedPaths>(HttpStatus.NO_CONTENT);
        }
        logger.info(" End of getCoreGuidedPaths()  in controller");
        return new ResponseEntity<GuidedPaths>(coreGuidedPaths, HttpStatus.OK);
    }
}
