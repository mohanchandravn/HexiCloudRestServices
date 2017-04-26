package com.hexicloud.portaldb.serviceImpl;

import com.hexicloud.portaldb.bean.JobConfiguration;
import com.hexicloud.portaldb.bean.JobHistory;
import com.hexicloud.portaldb.dao.JobConfigurationDAO;
import com.hexicloud.portaldb.dao.JobHistoryDAO;
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
}
