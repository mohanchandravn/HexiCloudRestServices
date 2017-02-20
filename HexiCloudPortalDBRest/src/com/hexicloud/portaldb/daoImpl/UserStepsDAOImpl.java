package com.hexicloud.portaldb.daoImpl;

import com.hexicloud.portaldb.bean.Step;
import com.hexicloud.portaldb.bean.UserStep;
import com.hexicloud.portaldb.dao.UserStepsDAO;
import com.hexicloud.portaldb.factory.Steps;
import com.hexicloud.portaldb.util.SqlQueryConstantsUtil;

import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserStepsDAOImpl implements UserStepsDAO {

    private static final Logger logger = Logger.getLogger(UserStepsDAOImpl.class);
    private JdbcTemplate jdbcTemplate;
    private DataSource dataSource;
    private Steps steps;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        jdbcTemplate = new JdbcTemplate(this.dataSource);
    }

    public void setSteps(Steps steps) {
        this.steps = steps;
    }


    @Override
    public void createUserSteps(UserStep userStep) {
        logger.info(" Begining of createUserSteps() ");

        logger.info("Retrieved id's, current Step : " + steps.getStepId(userStep.getCurStepCode()) + ", Previous : " +
                    steps.getStepId(userStep.getPreStepCode()));
        @SuppressWarnings({ "unchecked", "rawtypes" })
        List<UserStep> existingUserSteps =
            jdbcTemplate.query(SqlQueryConstantsUtil.SQL_FIND_USER_CURRENT_STEP, new Object[] { userStep.getUserId() },
                               new BeanPropertyRowMapper(UserStep.class));
        // SQL_CREATE_USER_STEP = "INSERT INTO USER_STEPS (USER_ID, USER_ROLE,
        // CUR_STEP_ID, CUR_STEP_CODE, PRE_STEP_ID, PRE_STEP_CODE, CREATED_DATE,
        // UPDATED_DATE) VALUES (?, ?, ?, ?, ?, ?, SYSDATE, SYSDATE)";
        // SQL_UPDATE_USER_STEP = "UPDATE USER_STEPS SET USER_ROLE = ?,
        // CUR_STEP_ID = ?, CUR_STEP_CODE = ?, PRE_STEP_ID = ?, PRE_STEP_CODE =
        // ?, UPDATED_DATE = SYSDATE WHERE USER_ID = ?";

        if (null != existingUserSteps && existingUserSteps.size() > 0) {
            jdbcTemplate.update(SqlQueryConstantsUtil.SQL_UPDATE_USER_STEP,
                                new Object[] { userStep.getUserRole(), steps.getStepId(userStep.getCurStepCode()),
                                               userStep.getCurStepCode(), steps.getStepId(userStep.getPreStepCode()),
                                               userStep.getPreStepCode(), userStep.getUserId() });
        } else {
            jdbcTemplate.update(SqlQueryConstantsUtil.SQL_CREATE_USER_STEP,
                                new Object[] { userStep.getUserId(), userStep.getUserRole(),
                                               steps.getStepId(userStep.getCurStepCode()), userStep.getCurStepCode(),
                                               steps.getStepId(userStep.getPreStepCode()), userStep.getPreStepCode() });
        }
        logger.info(" End of createUserSteps() ");

    }

    @Override
    public UserStep getUsersCurrentStep(String userId) {
        logger.info(" Begining of getUsersCurrentStep() ");
        UserStep userStep;
        @SuppressWarnings({ "unchecked", "rawtypes" })
        List<UserStep> existingUserSteps =
            jdbcTemplate.query(SqlQueryConstantsUtil.SQL_FIND_USER_CURRENT_STEP, new Object[] { userId },
                               new BeanPropertyRowMapper(UserStep.class));
        if (null != existingUserSteps && existingUserSteps.size() > 0) {
            userStep = existingUserSteps.get(0);
            Step dbStep = steps.getSteps().get(userStep.getCurStepCode());
            userStep.setDecisionMakingStep(dbStep.isDecisionMaking());
            userStep.setRoleSelectionStep(dbStep.isRoleSelelction());
            userStep.setNonRedirectStep(dbStep.isNonRedirectStep());
        }
        logger.info(" Ending of getUsersCurrentStep() ");
        return !existingUserSteps.isEmpty() ? (UserStep) existingUserSteps.get(0) : null;
    }

}
