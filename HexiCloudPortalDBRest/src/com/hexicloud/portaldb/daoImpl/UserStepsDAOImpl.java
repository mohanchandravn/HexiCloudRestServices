package com.hexicloud.portaldb.daoImpl;

import com.hexicloud.portaldb.bean.Step;
import com.hexicloud.portaldb.bean.UserStep;
import com.hexicloud.portaldb.dao.UserStepsDAO;
import com.hexicloud.portaldb.factory.Steps;
import com.hexicloud.portaldb.util.SqlQueryConstantsUtil;

import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

@Repository
public class UserStepsDAOImpl implements UserStepsDAO {

    private static final Logger logger = Logger.getLogger(UserStepsDAOImpl.class);
    private JdbcTemplate jdbcTemplate;
    private DataSource dataSource;
    private Steps steps;
    private SimpleJdbcCall saveUserNavAudit;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        jdbcTemplate = new JdbcTemplate(this.dataSource);
        this.saveUserNavAudit =
            new SimpleJdbcCall(dataSource).withCatalogName("PKG_USER_NAV_AUDIT")
            .withProcedureName("PRC_SAVE_USER_NAV_AUDIT");
    }

    public void setSteps(Steps steps) {
        this.steps = steps;
    }


    @Override
    public void createUserSteps(UserStep userStep) {
        logger.info(" Begining of createUserSteps() ");

        logger.info("Retrieved id's, current Step : " + steps.getStepIdWithStepCode(userStep.getCurStepCode()) + ", Previous : " +
                    steps.getStepIdWithStepCode(userStep.getPreStepCode()));
        if (!StringUtils.isEmpty(userStep.getCurStepCode())) {
            @SuppressWarnings({ "unchecked", "rawtypes" })
            List<UserStep> existingUserSteps =
                jdbcTemplate.query(SqlQueryConstantsUtil.SQL_FIND_USER_CURRENT_STEP, new Object[] { userStep.getUserId() },
                                   new BeanPropertyRowMapper(UserStep.class));
            

            if (null != existingUserSteps && existingUserSteps.size() > 0) {
                jdbcTemplate.update(SqlQueryConstantsUtil.SQL_UPDATE_USER_STEP,
                                    new Object[] { userStep.getUserRole(), steps.getStepIdWithStepCode(userStep.getCurStepCode()),
                                                   userStep.getCurStepCode(), steps.getStepIdWithStepCode(userStep.getPreStepCode()),
                                                   userStep.getPreStepCode(), userStep.getUserId() });
            } else {
                jdbcTemplate.update(SqlQueryConstantsUtil.SQL_CREATE_USER_STEP,
                                    new Object[] { userStep.getUserId(), userStep.getUserRole(),
                                                   steps.getStepIdWithStepCode(userStep.getCurStepCode()), userStep.getCurStepCode(),
                                                   steps.getStepIdWithStepCode(userStep.getPreStepCode()), userStep.getPreStepCode() });
            }
        }
        if (userStep.isUpdateRole()) {
            jdbcTemplate.update(SqlQueryConstantsUtil.SQL_UPDATE_USER_ROLE,
                                new Object[] { userStep.getUserRole(), userStep.getUserId() });
        }
        
        SqlParameterSource inParamsMap = new MapSqlParameterSource().addValue("IN_USER_ID", userStep.getUserId())
                                                                    .addValue("IN_STEP_ID", steps.getStepIdWithStepCode(userStep.getPreStepCode()))
                                                                    .addValue("IN_CUR_STEP_LABEL", userStep.getCurStepCode())
                                                                    .addValue("IN_ACTION", userStep.getUserAction());
        saveUserNavAudit.execute(inParamsMap);
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
            Step dbStep = steps.getStepsMapWithStepCodeKey().get(userStep.getCurStepCode());
            userStep.setDecisionMakingStep(dbStep.isDecisionMaking());
            userStep.setRoleSelectionStep(dbStep.isRoleSelelction());
            userStep.setNonRedirectStep(dbStep.isNonRedirectStep());
        }
        logger.info(" Ending of getUsersCurrentStep() ");
        return !existingUserSteps.isEmpty() ? (UserStep) existingUserSteps.get(0) : null;
    }

    @Override
    public boolean onBoardingComplete(String userId) {
       boolean onBoardingComplete = false;
       String completionDate = null;
        try {
            completionDate =
                jdbcTemplate.queryForObject(SqlQueryConstantsUtil.SQL_CHECK_ON_BOARDING_COMPLETION,
                                            new Object[] { userId }, String.class);
            if (null != completionDate) {
                onBoardingComplete = true;
            }
        } catch (EmptyResultDataAccessException erdae) {
            logger.error("Did not get the onboarding completion, expected error so not logging trace");
            return onBoardingComplete;
        }
        return onBoardingComplete;
    }

  
}
