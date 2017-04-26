package com.hexicloud.portaldb.dao;

import com.hexicloud.portaldb.bean.JobConfiguration;

import java.util.List;

public interface JobConfigurationDAO {

    public List<JobConfiguration> getJobConfigurations(String jobName);
    
    public JobConfiguration getJobConfiguration(String jobName);
    

    
    public void addJob(JobConfiguration jobConifg);
}
