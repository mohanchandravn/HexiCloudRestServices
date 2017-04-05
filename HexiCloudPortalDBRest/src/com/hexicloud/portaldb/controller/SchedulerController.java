package com.hexicloud.portaldb.controller;

import com.hexicloud.portaldb.bean.JobConfiguration;
import com.hexicloud.portaldb.bean.JobHistory;
import com.hexicloud.portaldb.service.JobConfigurationService;

import java.util.List;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SchedulerController {
    private static final Logger logger = Logger.getLogger(SchedulerController.class);

    @Autowired
    private JobConfigurationService jobConfigurationService;

    @RequestMapping(value = "/services/rest/findJobConfigurations", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<JobConfiguration>> findJobConfigurations(@RequestParam(value = "jobName", required = false)
                                                                              String jobName) throws Exception {
        logger.info("******* Start of findJobConfigurations() in controller ***********");
        List<JobConfiguration> jobsList = jobConfigurationService.getJobConfigurations(jobName);
        if (jobsList.isEmpty()) {
            logger.info("jobsList not found");
            return new ResponseEntity<List<JobConfiguration>>(HttpStatus.NO_CONTENT);
        }
        logger.info("******** End of findJobConfigurations() in controller ***********");
        return new ResponseEntity<List<JobConfiguration>>(jobsList, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/services/rest/findJobHistoryForJob/{jobId}/", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<JobHistory>> findJobHistoryForJob(@PathVariable(value = "jobId") Integer jobId) throws Exception {
        logger.info("******* Start of findJobHistoryForJob() in controller ***********");
        List<JobHistory> jobHistoryList = jobConfigurationService.getJobHistoryForJob(jobId);
        if (jobHistoryList.isEmpty()) {
            logger.info("jobsHistoryList not found");
            return new ResponseEntity<List<JobHistory>>(HttpStatus.NO_CONTENT);
        }
        logger.info("******** End of findJobHistoryForJob() in controller ***********");
        return new ResponseEntity<List<JobHistory>>(jobHistoryList, HttpStatus.OK);
    }
}
