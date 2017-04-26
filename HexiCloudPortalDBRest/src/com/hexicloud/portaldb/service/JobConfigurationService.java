package com.hexicloud.portaldb.service;

import com.hexicloud.portaldb.bean.JobConfiguration;
import com.hexicloud.portaldb.bean.JobHistory;

import java.util.List;

public interface JobConfigurationService {
    public List<JobConfiguration> getJobConfigurations(String jobName);
    
    public JobConfiguration getJobConfiguration(String jobName);
    
    public List<JobHistory> getJobHistoryForJob(Integer jobId);
    
    public Integer createJobHistory(JobHistory jobHistory);
    
    public void updateJobHistory(JobHistory jobHistory);
}
