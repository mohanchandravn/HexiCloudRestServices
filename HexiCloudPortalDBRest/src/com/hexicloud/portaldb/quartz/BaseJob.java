package com.hexicloud.portaldb.quartz;


import com.hexicloud.portaldb.bean.JobConfiguration;
import com.hexicloud.portaldb.bean.JobHistory;
import com.hexicloud.portaldb.service.JobConfigurationService;

import java.sql.Timestamp;

import java.util.Date;

import org.apache.log4j.Logger;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import org.springframework.beans.factory.annotation.Autowired;

public class BaseJob implements Job {

    private static final Logger logger = Logger.getLogger(BaseJob.class.getName());
    protected Integer jobHistoryId = 0;

    @Autowired
    private JobConfigurationService jobConfigurationService;

    @Override
    public void execute(JobExecutionContext jec) throws JobExecutionException {
        logger.info("************" + jec.getJobDetail()
                                        .getKey()
                                        .getName() + "  Job Executed successfully****************");
        jobHistoryId = this.createJobHistory(jec.getJobDetail()
                                                .getKey()
                                                .getName());
    }

    /**
     * Methods creates job history record and returns Job History Id
     *
     * @param jobName
     * @return Job History Id
     */
    public Integer createJobHistory(String jobName) {
        logger.info("Entering the method createJobHistory");
        Integer jobId = null;
        Integer historyId = 0;
        try {
            //            InitialContext ic = new InitialContext();
            //            QuartzJobConfigurationFacadeREST configObj = (QuartzJobConfigurationFacadeREST) ic.lookup("java:comp/env/Scheduler/QuartzJobConfigurationFacadeREST");
            //            QuartzJobHistoryFacadeREST historyObj = (QuartzJobHistoryFacadeREST) ic.lookup("java:comp/env/Scheduler/QuartzJobHistoryFacadeREST");
            //            JobConfiguration entity = null;
            //            logger.info("rows size:::" + configObj.getjobConfigByJobname(jobName).size());
            JobConfiguration entity = jobConfigurationService.getJobConfiguration(jobName);

            if (entity != null) {
                jobId = entity.getJobId();
            }
            logger.info("job Id:::" + jobId);
            java.util.Date date = new java.util.Date();
            Date START_DATE = new Timestamp(date.getTime());
            String jobStatus = "P"; //SchedulerConstants.JOB_STATUS_IN_PROGRESS;
            JobHistory row = new JobHistory();
            row.setStartDate(START_DATE);
            row.setJobStatus(jobStatus);
            row.setJobId(jobId);
            logger.info("history obj:" + row);
            historyId = jobConfigurationService.createJobHistory(row);
            //            List<JobHistory> historybyjobid = historyObj.gethistorybyjobid(jobId.toString());
            //
            //            for (JobHistory historyRow : historybyjobid) {
            //                if (historyRow.getJobHistoryId() > historyId) {
            //                    historyId = historyRow.getJobHistoryId();
            //                }
            //            }
            logger.info("historyId Id =====>" + historyId + START_DATE + jobStatus + jobId);
        }
        //        catch (CommunicationException ex) {
        //            System.out.println(ex.getClass().getName());
        //            System.out.println(ex.getRootCause().getLocalizedMessage());
        //            System.out.println("\n*** A CommunicationException was raised.  This typically\n*** occurs when the target WebLogic server is not running.\n");
        //        }
        catch (Exception jobHistoryExp) {
            logger.error(jobHistoryExp.getMessage(), jobHistoryExp);
            throw new RuntimeException(jobHistoryExp);
        }
        logger.info("Exiting the method createJobHistory");
        return historyId;
    }

    /**
     * Method updates status, failed messasge, end date and last successfull
     * report run date
     *
     * @param historyId
     * @param failedMsg
     * @param status
     * @param reportSuccessFullRunDate
     */
    public void updateJobHistory(Integer historyId, String failedMsg, String status, Date reportSuccessFullRunDate) {
        logger.info("Entering the method updateJobHistory");
        try {
            logger.info("updateJobHistory() ====>historyId Id ==>" + historyId + " failedMsg :" + failedMsg +
                        " status:" + status);
            //            InitialContext ic = new InitialContext();
            //            QuartzJobHistoryFacadeREST historyObj =
            //                (QuartzJobHistoryFacadeREST) ic.lookup("java:comp/env/Scheduler/QuartzJobHistoryFacadeREST");
            JobHistory entity = new JobHistory();
            //            if (historyId != null) {
            //                entity = historyObj.find(historyId);
            //            }
            if (entity != null) {
                java.util.Date date = new java.util.Date();
                Date endDate = new Timestamp(date.getTime());
                entity.setJobStatus(status);
                entity.setJobFailedReason(failedMsg);
                entity.setEndDate(endDate);
                if (reportSuccessFullRunDate != null) {
                    entity.setSuccessfulRunReportDate(reportSuccessFullRunDate);
                }
                jobConfigurationService.updateJobHistory(entity);

            }
        } catch (Exception jobHistoryExp) {
            logger.error(jobHistoryExp.getMessage(), jobHistoryExp);
            throw new RuntimeException(jobHistoryExp);
        }
        logger.info("Exiting the method updateJobHistory");
    }
}
