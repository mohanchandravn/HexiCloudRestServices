package com.hexicloud.portaldb.daoImpl;

import com.hexicloud.portaldb.bean.JobHistory;
import com.hexicloud.portaldb.dao.JobHistoryDAO;
import com.hexicloud.portaldb.util.SqlQueryConstantsUtil;

import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JobHistoryDAOImpl implements JobHistoryDAO {
    private static final Logger logger = Logger.getLogger(JobHistoryDAOImpl.class);
    private JdbcTemplate jdbcTemplate;
    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        jdbcTemplate = new JdbcTemplate(this.dataSource);
    }

    @Override
    public List<JobHistory> getJobHistoryOfJob(Integer jobId) {
        logger.info(" Begining of getJobHistoryOfJob() ");
        @SuppressWarnings({ "unchecked", "rawtypes" })
        List<JobHistory> jobHistoryList =
            (List<JobHistory>) jdbcTemplate.query(SqlQueryConstantsUtil.SQL_GET_JOB_HISTORY_FOR_JOB,
                                                  new Object[] { jobId }, new BeanPropertyRowMapper(JobHistory.class));
        logger.info("jobHistoryList size ===========> " + jobHistoryList != null ? jobHistoryList.size() : null);
        logger.info(" End of getJobHistoryOfJob() ");
        return jobHistoryList;
    }

    @Override
    public Integer createJobHistory(JobHistory jobHistory) {
        Integer jobHistoryId = null;
        try {
            jobHistoryId =
                jdbcTemplate.queryForObject(SqlQueryConstantsUtil.SQL_GET_JOB_HISTORY_FOR_JOB, Integer.class);
            jdbcTemplate.update(SqlQueryConstantsUtil.SQL_ADD_JOB_HISTORY,
                                new Object[] { jobHistoryId, jobHistory.getJobId(), jobHistory.getStartDate(),
                                               jobHistory.getJobStatus() });
        } catch (EmptyResultDataAccessException erdae) {
            logger.error("Did not get any result while expecting a single row, returning null" + erdae);
            return jobHistoryId;
        }


        return jobHistoryId;

    }

    @Override
    public void updateJobHistory(JobHistory jobHistory) {
        jdbcTemplate.update(SqlQueryConstantsUtil.SQL_UPDATE_JOB_HISTORY,
                            new Object[] { jobHistory.getJobStatus(), jobHistory.getEndDate(),
                                           jobHistory.getJobFailedReason(), jobHistory.getSuccessfulRunReportDate(),
                                           jobHistory.getJobHistoryId() });
    }
}
