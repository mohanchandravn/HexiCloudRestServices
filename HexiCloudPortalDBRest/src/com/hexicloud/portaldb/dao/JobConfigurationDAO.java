package com.hexicloud.portaldb.dao;

import com.hexicloud.portaldb.bean.JobConfiguration;

import java.util.List;

public interface JobConfigurationDAO {

    public List<JobConfiguration> getJobConfigurations(String jobName);

    public JobConfiguration getJobConfiguration(String jobName);

    public JobConfiguration getJobConfigurationByJobId(Integer jobId);

    public void addJob(JobConfiguration jobConfig);

    public void updateJob(JobConfiguration jobConfig);

    public void deleteJob(Integer jobId);
}
