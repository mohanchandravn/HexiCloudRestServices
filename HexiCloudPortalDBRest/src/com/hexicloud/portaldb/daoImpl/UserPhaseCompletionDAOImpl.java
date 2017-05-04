package com.hexicloud.portaldb.daoImpl;

import com.hexicloud.portaldb.bean.UserPhaseCompletion;
import com.hexicloud.portaldb.dao.UserPhaseCompletionDAO;
import com.hexicloud.portaldb.util.SqlQueryConstantsUtil;

import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserPhaseCompletionDAOImpl implements UserPhaseCompletionDAO {
    private static final Logger logger = Logger.getLogger(UserPhaseCompletionDAOImpl.class);
    private JdbcTemplate jdbcTemplate;
    private DataSource dataSource;

    @Value("${user.phase.completion.usecase.capture}")
    private String USER_PHASE_COMPLETION_USECASE_CAPTURE;
    
    @Value("${user.phase.completion.usecase.selection}")
    private String USER_PHASE_COMPLETION_USECASE_SELECTION;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        jdbcTemplate = new JdbcTemplate(this.dataSource);

    }

    @Override
    public void createPhaseCompletion(UserPhaseCompletion userPhaseCompletion) {
        logger.info(" Begining of createPhaseCompletion() ");
        jdbcTemplate.update(SqlQueryConstantsUtil.SQL_CREATE_PHASE_COMPLETION,
                            new Object[] { userPhaseCompletion.getUserId(), userPhaseCompletion.getPhase(), "N",
                                           null });
        logger.info(" End of createPhaseCompletion() ");
    }

    @Override
    public UserPhaseCompletion checkPhaseCompletion(String userId, String phase) {
        logger.info(" Begining of checkPhaseCompletion() ");

        @SuppressWarnings({ "unchecked", "rawtypes" })
        List<UserPhaseCompletion> userPhaseCompletionList =
            jdbcTemplate.query(SqlQueryConstantsUtil.SQL_CHECK_PHASE_COMPLETION, new Object[] { userId, phase },
                               new BeanPropertyRowMapper(UserPhaseCompletion.class));

        logger.info("checkPhaseCompletion size ===========> " + (!userPhaseCompletionList.isEmpty() ? userPhaseCompletionList.size() : null));
        logger.info(" End of checkPhaseCompletion() ");
        return userPhaseCompletionList.isEmpty() ? null : userPhaseCompletionList.get(0);
    }
}
