package com.hexicloud.portaldb.resultextractor;

import com.hexicloud.portaldb.bean.Step;
import com.hexicloud.portaldb.bean.SubStep;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

public class StepSubStepResultExtractor implements ResultSetExtractor<List<Step>> {
    public StepSubStepResultExtractor() {
        super();
    }

    @Override
    public List<Step> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        Map<Integer, Step> stepsMap = new HashMap<Integer, Step>();
        while (resultSet.next()) {
            Integer id = Integer.valueOf(resultSet.getInt("STEP_ID"));
            Step step = stepsMap.get(id);
            if (step == null) {
                step = new Step();
                step.setStepId(id);
                step.setStepCode(resultSet.getString("STEP_CODE"));
                step.setStepLabel(resultSet.getString("STEP_LABEL"));
                step.setDescription(resultSet.getString("DESCRIPTION"));
                step.setRoleSelelction(resultSet.getBoolean("IS_ROLE_SELELCTION"));
                step.setDecisionMaking(resultSet.getBoolean("IS_DECISION_MAKING"));
                step.setNonRedirectStep(resultSet.getBoolean("NON_REDIRECT_STEP"));

                step.setStepLabel(resultSet.getString("STEP_LABEL"));
                step.setHasSubStep(resultSet.getString("HAS_SUB_STEP"));
                stepsMap.put(id, step);
            }
            if (step.getHasSubStep().equalsIgnoreCase("Y") && resultSet.getString("SUB_STEP_CODE") != null) {
                List subStepList = step.getSubSteps();
                if (subStepList == null) {
                    subStepList = new ArrayList<SubStep>();
                    step.setSubSteps(subStepList);
                }
                SubStep subStep = new SubStep();
                subStep.setStepId(step.getStepId());
                subStep.setStepCode(step.getStepCode());
                subStep.setSubStepCode(resultSet.getString("SUB_STEP_CODE"));
                subStep.setSubStepLabel(resultSet.getString("SUB_STEP_LABEL"));
                subStep.setSubStepDesc(resultSet.getString("SUB_STEP_DESC"));
                subStepList.add(subStep);
            }

 
        }
        return new ArrayList<Step>(stepsMap.values());
    }
}
