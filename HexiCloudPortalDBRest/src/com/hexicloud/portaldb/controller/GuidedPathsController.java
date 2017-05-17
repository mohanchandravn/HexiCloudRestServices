package com.hexicloud.portaldb.controller;

import com.hexicloud.portaldb.bean.guidedpath.GuidedPathDetailResponse;
import com.hexicloud.portaldb.bean.guidedpath.GuidedPaths;
import com.hexicloud.portaldb.bean.guidedpath.UpdateLearningPathRequest;
import com.hexicloud.portaldb.service.GuidedPathsService;

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

            logger.info("Core Guided paths not found");
            return new ResponseEntity<GuidedPaths>(HttpStatus.NO_CONTENT);
        }
        logger.info(" End of getCoreGuidedPaths()  in controller");
        return new ResponseEntity<GuidedPaths>(coreGuidedPaths, HttpStatus.OK);
    }

    @RequestMapping(value = "/services/rest/getCompleGuidedPaths", method = RequestMethod.GET)
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<GuidedPaths> getCompleGuidedPaths(@RequestParam(value = "useCaseId", required = true)
                                                            Integer useCaseId,
                                                            Authentication authentication) throws Exception {
        logger.info(" Begining of getCompleGuidedPaths() in controller");
        GuidedPaths coreGuidedPaths =
            guidedPathsService.getComplementaryGuidedPaths(useCaseId, authentication.getName());
        if (coreGuidedPaths.getGuidedPaths().isEmpty()) {

            logger.info("getCompleGuidedPaths not found");
            return new ResponseEntity<GuidedPaths>(HttpStatus.NO_CONTENT);
        }
        logger.info(" End of getCompleGuidedPaths()  in controller");
        return new ResponseEntity<GuidedPaths>(coreGuidedPaths, HttpStatus.OK);
    }

    @RequestMapping(value = "/services/rest/getGuidedPathDetail", method = RequestMethod.GET)
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<GuidedPathDetailResponse> getGuidedPathDetail(@RequestParam(value = "pathId", required = true)
                                                                        Integer pathId,
                                                                        Authentication authentication) throws Exception {
        logger.info(" Begining of getGuidedPathDetail() in controller");
        GuidedPathDetailResponse response = guidedPathsService.getGuidedPathDetail(pathId, authentication.getName());
        if (response.getGuidedPathDetail() == null) {
            logger.info("GuidedPath Detail not found");
            return new ResponseEntity<GuidedPathDetailResponse>(HttpStatus.NO_CONTENT);
        }
        logger.info(" End of getGuidedPathDetail()  in controller");
        return new ResponseEntity<GuidedPathDetailResponse>(response, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/services/rest/updateLearningHistory", method = RequestMethod.POST)
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Void> updateLearningHistory(@RequestBody UpdateLearningPathRequest learningPathRequest, Authentication authentication) throws Exception {
        logger.info(" ******* Start of updateLearningHistory() in controller ***********");
        guidedPathsService.changeLearningHistory(learningPathRequest, authentication.getName());
        logger.info("******** End of updateLearningHistory() in controller ***********");
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
}
