package com.hexicloud.portaldb.dao;

import com.hexicloud.portaldb.bean.JobHistory;

import java.util.List;


public interface JobHistoryDAO {
    public List<JobHistory> getJobHistoryOfJob(Integer jobId);
    
    public Integer createJobHistory(JobHistory jobHistory);
    
    public void updateJobHistory(JobHistory jobHistory);
}
