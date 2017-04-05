package com.hexicloud.portaldb.quartz;

import com.hexicloud.portaldb.bean.JobConfiguration;

import java.math.BigInteger;

import java.util.List;

import org.apache.log4j.Logger;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;

public class QuartzAPI {
    private static final Logger logger = Logger.getLogger(QuartzAPI.class.getName());

    /**
     * Method runs the job immediately onetime only.
     */
    public void runJobNow(String jobName, String className) {
        logger.info("Entering the method runJobNow");
        QuartzSchedulerSingleton quartzSchedulerSingleton = QuartzSchedulerSingleton.getInstance();
        try {
            logger.info("triggering the job now");
            boolean isExist = false;
            List<ScheduledJob> scheduledJobs = quartzSchedulerSingleton.getScheduledJobs();
            if (!scheduledJobs.isEmpty()) {
                for (ScheduledJob scheduledJob : scheduledJobs) {
                    if (scheduledJob != null) {
                        String scheduledJobName = scheduledJob.getJobName();
                        if (jobName.equals(scheduledJobName)) {
                            logger.info("Job found in the quartz");
                            isExist = true;
                            break;
                        }
                    }
                }
            }
            if (isExist) {
                logger.info("Triggering the job");
                JobKey jobKey = new JobKey(jobName, Scheduler.DEFAULT_GROUP);
                quartzSchedulerSingleton.getScheduler().triggerJob(jobKey);
            } else {
                logger.info("Triggering the job");
                Class jobClass = Class.forName(className);
                JobDetail job = JobBuilder.newJob(jobClass)
                                          .withIdentity(jobName)
                                          .build();
                Trigger trigger = TriggerBuilder.newTrigger()
                                                .withIdentity(jobName)
                                                .startNow()
                                                .forJob(job)
                                                .build();
                quartzSchedulerSingleton.getScheduler().scheduleJob(job, trigger);
            }
        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
        }

        logger.info("Exiting the method runJobNow");
    }

    /**
     * Method stops the job immediately.It removes the job from quartz scheduler.
     * It does not remove the job from the Job Configuration table.
     */
    public void stopJobNow(String jobName) {
        //jobName="TestJob";
        logger.info("Entering the method stopJobNow");
        QuartzSchedulerSingleton quartzSchedulerSingleton = QuartzSchedulerSingleton.getInstance();
        List<ScheduledJob> scheduledJobs = quartzSchedulerSingleton.getScheduledJobs();
        if (!scheduledJobs.isEmpty()) {
            for (ScheduledJob scheduledJob : scheduledJobs) {
                if (scheduledJob != null) {
                    String scheduledJobName = scheduledJob.getJobName();
                    if (jobName.equals(scheduledJobName)) {
                        logger.info("Deleting the job.");
                        quartzSchedulerSingleton.deleteScheduledJob(jobName, scheduledJob.getGroupName());
                        break;
                    }
                }
            }
        }

        logger.info("Entering the method stopJobNow");
    }

    /**
     * Method deletes the job and job history both from quartz and job configuration
     * table when the user confirms the delete.
     */
    public void deleteJob(String jobName) {
        logger.info("Entering the method deleteJob");
        QuartzSchedulerSingleton quartzSchedulerSingleton = QuartzSchedulerSingleton.getInstance();
        List<ScheduledJob> scheduledJobs = quartzSchedulerSingleton.getScheduledJobs();
        if (!scheduledJobs.isEmpty()) {
            for (ScheduledJob scheduledJob : scheduledJobs) {
                if (scheduledJob != null) {
                    String scheduledJobName = scheduledJob.getJobName();
                    if (jobName.equals(scheduledJobName)) {
                        logger.info("Deleting the job.");
                        quartzSchedulerSingleton.deleteScheduledJob(jobName, scheduledJob.getGroupName());
                        break;
                    }
                }
            }
        }
        logger.info("Entering the method deleteJob");
    }

    /**
     * Method saves the job and schedules the job with the quartz scheduler.
     */
    public void saveAndRunJob(JobConfiguration entity) {
        logger.info("Entering the method saveAndRunJob");
        try {
            if (entity != null) {
                String jobName = null;
                BigInteger jobFrequency = null;
                String jobFrequencyType = null;
                Integer jobFrequencyHour = null;
                Integer jobFrequencyMinute = null;
                String jobClass = null;
                if (entity.getJobName() != null) {
                    jobName = entity.getJobName();
                }
                if (entity.getJobFrequency() != null) //BigDecimal
                {
                    jobFrequency = entity.getJobFrequency();
                }
                if (entity.getJobFrequencyType() != null) {
                    jobFrequencyType = entity.getJobFrequencyType();
                }
                if (entity.getJobFrequencyHour() != null) {
                    jobFrequencyHour = entity.getJobFrequencyHour().intValue();
                }
                if (entity.getJobFrequencyMinute() != null) {
                    jobFrequencyMinute = entity.getJobFrequencyMinute().intValue();
                }
                if (entity.getClassName() != null) {
                    jobClass = entity.getClassName();
                }
                logger.info("jobName :" + jobName + " jobFrequency :" + jobFrequency + " jobFrequencyType:" +
                            jobFrequencyType + " jobFrequencyHour:" + jobFrequencyHour + " jobFrequencyMinute:" +
                            jobFrequencyMinute + " jobClass:" + jobClass);
                String _cronScheduler = null;
                if ("H".equals(jobFrequencyType)) {
                    int frequency = jobFrequency.intValue();
                    if (frequency != 0) {
                        _cronScheduler = "0 0 0/" + frequency + " * * ?";
                    } else {
                        _cronScheduler = null;
                    }
                } else if ("D".equals(jobFrequencyType)) {
                    int minute = jobFrequencyMinute.intValue();
                    int hour = jobFrequencyHour.intValue();
                    _cronScheduler = "0 " + minute + " " + hour + " * * ?";
                }
                //                else if("M".equals(jobFrequencyType)){
                //                     _cronScheduler = "0 0,5,10,15,20,25,30,35,40,45,50,55 * * * ?";
                //
                //                }
                // byte entityState = currentRow.getEntity(0).getEntityState();
                QuartzSchedulerSingleton quartzSchedulerSingleton = QuartzSchedulerSingleton.getInstance();
                // if (Entity.STATUS_NEW == entityState) {
                logger.info("Scheduling the new job.");
                if (_cronScheduler != null) {
                    _cronScheduler = "0/30 * * * * ?";
                    quartzSchedulerSingleton.scheduleJob(jobName, jobClass, _cronScheduler);
                }
                //                } else if (Entity.STATUS_MODIFIED == entityState) {
                //                    List<ScheduledJob> scheduledJobs = quartzSchedulerSingleton.getScheduledJobs();
                //                    if (!scheduledJobs.isEmpty()) {
                //                        for (ScheduledJob scheduledJob : scheduledJobs) {
                //                            if (scheduledJob != null) {
                //                                String scheduledJobName = scheduledJob.getJobName();
                //                                if (jobName.equals(scheduledJobName)) {
                //                                    logger.info("Deleting the job.");
                //                                    quartzSchedulerSingleton.deleteScheduledJob(jobName, scheduledJob.getGroupName());
                //                                    break;
                //                                }
                //                            }
                //                        }
                //                    }
                //                    if (_cronScheduler != null) {
                //                        logger.info("Scheduling the job.");
                //                        quartzSchedulerSingleton.scheduleJob(jobName, jobClass, _cronScheduler);
                //
                //                }
                //                JobKey jobKey = new JobKey(jobName, Scheduler.DEFAULT_GROUP);
                //                quartzSchedulerSingleton.getScheduler().triggerJob(jobKey);
                //            }
            }
        } catch (Exception ex) {
            logger.error(ex);
            ex.printStackTrace();
            throw new RuntimeException(ex);

        }

        logger.info("Exiting the method saveAndRunJob");
    }

}
