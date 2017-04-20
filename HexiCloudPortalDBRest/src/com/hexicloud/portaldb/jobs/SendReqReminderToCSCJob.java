package com.hexicloud.portaldb.jobs;

import com.hexicloud.portaldb.quartz.BaseJob;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

public class SendReqReminderToCSCJob extends BaseJob implements Job {

    private static final Logger logger = Logger.getLogger(SendReqReminderToCSCJob.class);
    private JdbcTemplate jdbcTemplate;
    private DataSource dataSource;
    private SimpleJdbcCall sendRemainderJob;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        jdbcTemplate = new JdbcTemplate(this.dataSource);
        this.sendRemainderJob =
            new SimpleJdbcCall(dataSource).withCatalogName("PKG_RULES_ENGINE")
            .withProcedureName("PRC_SEND_REMINDER_TO_CSM");
    }

    @Override
    public void execute(JobExecutionContext jec) throws JobExecutionException {
        logger.info(" Start of execute in SendReqReminderToCSCJob ");
        SqlParameterSource inParamsMap = new MapSqlParameterSource().addValue("IN_JOB_NAME", jec.getJobDetail()
                                                                                                .getKey()
                                                                                                .getName());

        sendRemainderJob.execute(inParamsMap);
        logger.info(" End of execute in SendReqReminderToCSCJob");
    }
}
