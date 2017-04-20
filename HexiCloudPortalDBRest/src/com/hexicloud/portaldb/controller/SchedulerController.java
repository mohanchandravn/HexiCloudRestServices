package com.hexicloud.portaldb.controller;

import com.hexicloud.portaldb.bean.JobConfiguration;
import com.hexicloud.portaldb.bean.JobHistory;
import com.hexicloud.portaldb.bean.RuleConfiguration;
import com.hexicloud.portaldb.service.JobConfigurationService;
import com.hexicloud.portaldb.service.RuleConfigService;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SchedulerController {
    private static final Logger logger = Logger.getLogger(SchedulerController.class);

    @Autowired
    private JobConfigurationService jobConfigurationService;

    @Autowired
    private RuleConfigService ruleConfigService;

    @RequestMapping(value = "/services/rest/findJobConfigurations", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<JobConfiguration>> findJobConfigurations(@RequestParam(value = "jobName",
                                                                                      required = false)
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
    public ResponseEntity<List<JobHistory>> findJobHistoryForJob(@PathVariable(value = "jobId")
                                                                 Integer jobId) throws Exception {
        logger.info("******* Start of findJobHistoryForJob() in controller ***********");
        List<JobHistory> jobHistoryList = jobConfigurationService.getJobHistoryForJob(jobId);
        if (jobHistoryList.isEmpty()) {
            logger.info("jobsHistoryList not found");
            return new ResponseEntity<List<JobHistory>>(HttpStatus.NO_CONTENT);
        }
        logger.info("******** End of findJobHistoryForJob() in controller ***********");
        return new ResponseEntity<List<JobHistory>>(jobHistoryList, HttpStatus.OK);
    }

    @RequestMapping(value = "/services/rest/findRulesByJob/{jobId}/", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<RuleConfiguration>> findRulesByJob(@PathVariable(value = "jobId")
                                                                  Integer jobId) throws Exception {
        logger.info("******* Start of findRulesByJob() in controller ***********");
        List<RuleConfiguration> ruleConfigs = ruleConfigService.getRuleConfigsByJob(jobId);
        if (ruleConfigs.isEmpty()) {
            logger.info("findRulesByJob not found");
            return new ResponseEntity<List<RuleConfiguration>>(HttpStatus.NO_CONTENT);
        }
        logger.info("RuleConfiguration******** End of findRulesByJob() in controller ***********");
        return new ResponseEntity<List<RuleConfiguration>>(ruleConfigs, HttpStatus.OK);
    }

    @RequestMapping(value = "/services/rest/updateRuleConfig/", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> updateRuleConfig(@RequestBody List<RuleConfiguration> ruleConfigs) throws Exception {
        logger.info("******* Start of updateRuleConfig() in controller ***********");
        ruleConfigService.updateBulkRuleConfigs(ruleConfigs);
        logger.info("******** End of updateRuleConfig() in controller ***********");
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @RequestMapping(value = "/services/rest/addJob/", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> addJob(@RequestBody JobConfiguration jobConfig) throws Exception {
        logger.info("******* Start of addJob() in controller ***********");
        jobConfigurationService.addJobConfiguration(jobConfig);
        logger.info("******** End of addJob() in controller ***********");
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @RequestMapping(value = "/services/rest/updateJobFrequency/", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> updateJobFrequency(@RequestBody JobConfiguration jobConfig) throws Exception {
        logger.info("******* Start of updateJobFrequency() in controller ***********");
        jobConfigurationService.updateJobFrequency(jobConfig);
        logger.info("******** End of updateJobFrequency() in controller ***********");
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @RequestMapping(value = "/services/rest/deletJob/{jobId}", method = RequestMethod.DELETE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteJob(@PathVariable(value = "jobId") Integer jobId) throws Exception {
        logger.info("******* Start of deleteJob() in controller ***********");
        jobConfigurationService.deleteJob(jobId);
        logger.info("******** End of deleteJob() in controller ***********");
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @RequestMapping(value = "/services/rest/runJobNow/{jobId}", method = RequestMethod.PATCH)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> runJobNow(@PathVariable(value = "jobId") Integer jobId) throws Exception {
        logger.info("******* Start of runJobNow() in controller ***********");
        jobConfigurationService.runJobNow(jobId);
        logger.info("******** End of runJobNow() in controller ***********");
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @RequestMapping(value = "/services/rest/startStopJob", method = RequestMethod.PATCH)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> startStopJob(@RequestParam(value = "jobId", required = true) Integer jobId,
                                             @RequestParam(value = "status", required = true) String status) throws Exception {
        logger.info("******* Start of startStopJob() in controller ***********");
        jobConfigurationService.runJobNow(jobId, status);
        logger.info("******** End of startStopJob() in controller ***********");
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

}
