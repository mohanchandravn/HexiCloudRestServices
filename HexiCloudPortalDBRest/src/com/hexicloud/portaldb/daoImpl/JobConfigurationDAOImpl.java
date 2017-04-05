package com.hexicloud.portaldb.daoImpl;

import com.hexicloud.portaldb.bean.JobConfiguration;
import com.hexicloud.portaldb.dao.JobConfigurationDAO;
import com.hexicloud.portaldb.util.SqlQueryConstantsUtil;

import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

@Repository
public class JobConfigurationDAOImpl implements JobConfigurationDAO {
    private static final Logger logger = Logger.getLogger(JobConfigurationDAOImpl.class);
    private JdbcTemplate jdbcTemplate;
    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        jdbcTemplate = new JdbcTemplate(this.dataSource);
    }

    @Override
    public List<JobConfiguration> getJobConfigurations(String jobName) {
        logger.info(" Begining of getJobConfigurations() ");
        StringBuilder query = new StringBuilder(SqlQueryConstantsUtil.SQL_GET_JOB_CONFIGURATION);
        if (!StringUtils.isEmpty(jobName)) {
            query.append(" WHERE JOB_NAME LIKE ('").append(jobName).append("%')");
        }
        @SuppressWarnings({ "unchecked", "rawtypes" })
        List<JobConfiguration> jobConfigurationList =
            (List<JobConfiguration>) jdbcTemplate.query(query.toString(),
                                                              new BeanPropertyRowMapper(JobConfiguration.class));
        logger.info("JobConfigurations size ===========> " + jobConfigurationList != null ?
                    jobConfigurationList.size() : null);
        logger.info(" End of getJobConfigurations() ");
        return jobConfigurationList;
    }



    @Override
    public void addJob(JobConfiguration jobConifg) {
        
    }

    @Override
    public JobConfiguration getJobConfiguration(String jobName) {
        logger.info(" Begining of getJobConfigurations() ");
        StringBuilder query = new StringBuilder(SqlQueryConstantsUtil.SQL_GET_JOB_CONFIGURATION);
        if (!StringUtils.isEmpty(jobName)) {
            query.append(" WHERE JOB_NAME = '").append(jobName).append("')");
        }
        @SuppressWarnings({ "unchecked", "rawtypes" })
        List<JobConfiguration> jobConfigurationList =
            (List<JobConfiguration>) jdbcTemplate.query(query.toString(),
                                                              new BeanPropertyRowMapper(JobConfiguration.class));
        logger.info("JobConfigurations size ===========> " + jobConfigurationList != null ?
                    jobConfigurationList.size() : null);
        logger.info(" End of getJobConfigurations() ");
        return jobConfigurationList.isEmpty() ? null : jobConfigurationList.get(0);
    }
}
