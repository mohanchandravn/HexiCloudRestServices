/**
 * 
 */
package com.hexicloud.portaldb.daoImpl;

import com.hexicloud.portaldb.bean.Step;
import com.hexicloud.portaldb.dao.StepsDAO;
import com.hexicloud.portaldb.resultextractor.StepSubStepResultExtractor;
import com.hexicloud.portaldb.util.SqlQueryConstantsUtil;

import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * @author mohchand
 *
 */
@Repository
public class StepsDAOImpl implements StepsDAO {


    private static final Logger logger = Logger.getLogger(StepsDAOImpl.class);
    private JdbcTemplate jdbcTemplate;
    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        jdbcTemplate = new JdbcTemplate(this.dataSource);
    }

    @Override
    public List<Step> getApplicationSteps() {
        logger.info(" Begining of getApplicationSteps() ");

        @SuppressWarnings({ "unchecked", "rawtypes" })
        List<Step> stepsList =
            ( List<Step>)jdbcTemplate.query(SqlQueryConstantsUtil.SQL_FIND_APPLICATION_STEPS, new StepSubStepResultExtractor());

        logger.info("stepsList size ===========> " + stepsList != null ? stepsList.size() : null);
        logger.info(" End of getApplicationSteps() ");
        return stepsList;
    }

}
