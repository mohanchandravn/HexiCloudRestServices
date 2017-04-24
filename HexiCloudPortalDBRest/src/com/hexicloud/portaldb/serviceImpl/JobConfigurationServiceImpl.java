package com.hexicloud.portaldb.serviceImpl;

import com.hexicloud.portaldb.bean.JobConfiguration;
import com.hexicloud.portaldb.bean.JobHistory;
import com.hexicloud.portaldb.dao.JobConfigurationDAO;
import com.hexicloud.portaldb.dao.JobHistoryDAO;
import com.hexicloud.portaldb.quartz.QuartzAPI;
import com.hexicloud.portaldb.service.JobConfigurationService;

import java.util.List;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("jobConfigurationService")
public class JobConfigurationServiceImpl implements JobConfigurationService {
    private static final Logger logger = Logger.getLogger(JobConfigurationServiceImpl.class);


    @Autowired
    JobConfigurationDAO jobConfigurationDAO;

    @Autowired
    JobHistoryDAO jobHistoryDAO;
    @Autowired
    QuartzAPI quartzAPI;

    @Override
    public List<JobConfiguration> getJobConfigurations(String jobName) {
        logger.info("*******  getJobConfigurations() of  service *****************");
        return jobConfigurationDAO.getJobConfigurations(jobName);
    }

    @Override
    public List<JobHistory> getJobHistoryForJob(Integer jobId) {
        logger.info("*******  getJobHistoryForJob() of  service *****************");
        return jobHistoryDAO.getJobHistoryOfJob(jobId);
    }

    @Override
    public JobConfiguration getJobConfiguration(String jobName) {
        logger.info("*******  getJobConfiguration() of  service *****************");
        return jobConfigurationDAO.getJobConfiguration(jobName);
    }

    @Override
    public Integer createJobHistory(JobHistory jobHistory) {
        logger.info("*******  getJobConfiguration() of  service *****************");
        return jobHistoryDAO.createJobHistory(jobHistory);
    }

    @Override
    public void updateJobHistory(JobHistory jobHistory) {
        logger.info("*******  getJobConfiguration() of  service *****************");
        jobHistoryDAO.updateJobHistory(jobHistory);
    }

    @Override
    public void addJobConfiguration(JobConfiguration jobConfig) {
        logger.info("*******  addJobConfiguration() of  service *****************");
        jobConfigurationDAO.addJob(jobConfig);
    }

    @Override
    public void updateJobFrequency(JobConfiguration jobConfig) {
        logger.info("*******  addJobConfiguration() of  service *****************");
        jobConfigurationDAO.updateJob(jobConfig);
    }

    @Override
    public void deleteJob(Integer jobId) {
        logger.info("*******  deleteJob() of  service *****************");
        jobConfigurationDAO.deleteJob(jobId);
    }

    @Override
    public void runJobNow(Integer jobId) {
        logger.info("******* start of run job now of  service *****************");
        JobConfiguration jobConfig = jobConfigurationDAO.getJobConfigurationByJobId(jobId);
        if (jobConfig != null) {
            logger.info("Job running immediately :" + jobConfig.getJobName());
            quartzAPI.runJobNow(jobConfig.getJobName(), jobConfig.getClassName());
        }
        logger.info("******* end of run job now of  service *****************");
    }

    @Override
    public void startStopJob(Integer jobId, String status) {
        logger.info("******* start of run job now of  service *****************");
        JobConfiguration jobConfig = jobConfigurationDAO.getJobConfigurationByJobId(jobId);
        if (jobConfig.getJobStatus() != null && "Stopped".equalsIgnoreCase(status)) {
            if (jobConfig.getJobName() != null) {
                jobConfigurationDAO.updateJobStatus(jobId, status);
                quartzAPI.stopJobNow(jobConfig.getJobName()); //stop
            }
        } else if (jobConfig.getJobStatus() != null &&
                   "running".equalsIgnoreCase(status)) {
            jobConfigurationDAO.updateJobStatus(jobId, status);
            quartzAPI.saveAndRunJob(jobConfig);
        }
        logger.info("******* end of run job now of  service *****************");
    }
}
