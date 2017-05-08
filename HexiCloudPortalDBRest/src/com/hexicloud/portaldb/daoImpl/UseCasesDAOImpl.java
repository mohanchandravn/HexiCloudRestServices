package com.hexicloud.portaldb.daoImpl;

import com.hexicloud.portaldb.bean.DecisionTree;
import com.hexicloud.portaldb.bean.Service;
import com.hexicloud.portaldb.bean.Services;
import com.hexicloud.portaldb.bean.TreeDetail;
import com.hexicloud.portaldb.bean.UseCaseBenefit;
import com.hexicloud.portaldb.bean.UseCaseBenefits;
import com.hexicloud.portaldb.bean.UseCases;
import com.hexicloud.portaldb.bean.UserUseCase;
import com.hexicloud.portaldb.bean.UserUseCases;
import com.hexicloud.portaldb.dao.UseCasesDAO;
import com.hexicloud.portaldb.resultextractor.UseCaseServicesExtractor;
import com.hexicloud.portaldb.util.SqlQueryConstantsUtil;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

@Repository
public class UseCasesDAOImpl implements UseCasesDAO {
    private static final Logger logger = Logger.getLogger(UseCasesDAOImpl.class);
    private JdbcTemplate jdbcTemplate;
    private DataSource dataSource;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private SimpleJdbcCall emailTailoredUCToUserPRC;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        jdbcTemplate = new JdbcTemplate(this.dataSource);
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource());
        this.emailTailoredUCToUserPRC =
            new SimpleJdbcCall(dataSource).withCatalogName("PKG_EMAIL").withProcedureName("PRC_SEND_TAILORED_USE_CASES");

    }

    @Override
    public UseCases getAllUseCases() {
        logger.info(" Begining of getAllUseCases() ");
        @SuppressWarnings({ "unchecked", "rawtypes" })
        UseCases useCases =
            (UseCases) jdbcTemplate.query(SqlQueryConstantsUtil.SQL_GET_ALL_USE_CASES_WITH_JOINS,
                                          new UseCaseServicesExtractor());
        logger.info("useCases size ===========> " + useCases != null ? useCases.getUseCases().size() : null);
        logger.info(" End of getAllUseCases() ");
        return useCases;
    }

    @Override
    public UseCases getUseCasesApplicableForServices(List<String> services) {
        logger.info(" Begin of getUseCasesApplicableForServices() ");
        Map<String, List> paramMap = Collections.singletonMap("servs", services);
        @SuppressWarnings({ "unchecked", "rawtypes" })
        List<Integer> useCaseIds =
            namedParameterJdbcTemplate.queryForList(SqlQueryConstantsUtil.SQL_GET_USE_CASE_IDS_FOR_SERVICES, paramMap,
                                                    Integer.class);
        logger.info("Number of ids retrieved are : " + useCaseIds != null ? useCaseIds.size() : 0);
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("ids", useCaseIds);
        UseCases usecases =
            namedParameterJdbcTemplate.query(SqlQueryConstantsUtil.SQL_GET_USE_CASES_FOR_IDS, parameters,
                                             new UseCaseServicesExtractor());

        logger.info("useCases size ===========> " + usecases != null ? usecases.getUseCases().size() : null);
        logger.info(" End of getUseCasesApplicableForServices() ");
        return usecases;
    }

    @Override
    public Services getAllServices() {
        logger.info("Starting of the getAllServices");
        Services services = new Services();
        @SuppressWarnings("unchecked")
        List<Service> servicesList =
            jdbcTemplate.query(SqlQueryConstantsUtil.SQL_GET_ALL_SERVICES, new BeanPropertyRowMapper(Service.class));
        services.setServices(servicesList);
        logger.info("Ending of the getAllServices");
        return services;
    }

    @Override
    public UseCases getTailoredUseCasesForUser(String userId) {
        logger.info(" Begining of getTailoredUseCasesForUser() ");
        @SuppressWarnings({ "unchecked", "rawtypes" })
        UseCases useCases =
            (UseCases) jdbcTemplate.query(SqlQueryConstantsUtil.SQL_GET_TAILORED_USE_CASES, new Object[] { userId },
                                          new UseCaseServicesExtractor());
        logger.info("tailored useCases size ===========> " + useCases != null ? useCases.getUseCases().size() : null);
        logger.info(" End of getTailoredUseCasesForUser() ");
        return useCases;
    }

    @Override
    public DecisionTree getDecisionTree() {
        logger.info("Starting of the getAllServices");
        DecisionTree decisionTree = new DecisionTree();
        @SuppressWarnings("unchecked")
        List<TreeDetail> treeDetails =
            jdbcTemplate.query(SqlQueryConstantsUtil.SQL_GET_DECSION_TREE, new BeanPropertyRowMapper(TreeDetail.class));
        decisionTree.setDecisionTree(treeDetails);
        logger.info("Ending of the getAllServices");
        return decisionTree;
    }

    @Override
    public void createUserUseCases(UserUseCases userUseCases) {
        logger.info("Starting of the createUserUseCases method");
        List<UserUseCase> cases = userUseCases.getUserUseCases();
        jdbcTemplate.batchUpdate(SqlQueryConstantsUtil.SQL_CREATE_USER_USE_CASE, new BatchPreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                UserUseCase useCase = cases.get(i);
                ps.setString(1, useCase.getUserId());
                ps.setInt(2, useCase.getUseCaseId());
                ps.setString(3, useCase.getCode());
                ps.setString(4, useCase.getSummary());
                ps.setString(5, useCase.getServices());
                ps.setString(6, useCase.getBenefits());
            }

            @Override
            public int getBatchSize() {
                return cases.size();
            }
        });
        logger.info("Ending of the createUserUseCases method");
    }

    @Override
    public UseCaseBenefits getUseCaseBenefits(int useCaseId) {
        logger.info("Starting of the getUseCaseBenefits");
        UseCaseBenefits useCaseBenefits = new UseCaseBenefits();
        @SuppressWarnings("unchecked")
        List<UseCaseBenefit> benefits =
            jdbcTemplate.query(SqlQueryConstantsUtil.SQL_GET_USE_CASE_BENEFITS, new Object [] {useCaseId}, new BeanPropertyRowMapper(UseCaseBenefit.class));
        useCaseBenefits.setBenefits(benefits);
        logger.info("Ending of the getUseCaseBenefits");
        return useCaseBenefits;
    }

    @Override
    public String sendTailoredUseCasesToUser(String userId) {
        logger.info(" Begining of sendTailoredUseCasesToUser() ");
        SqlParameterSource inParamsMap = new MapSqlParameterSource().addValue("IN_USER_ID", userId)
                                                                    
                                                                    .addValue("OUT_EMAIL_SENT", "N");
        Map<String, Object> out = emailTailoredUCToUserPRC.execute(inParamsMap);
        String emailSuccess = (String) out.get("OUT_EMAIL_SUCCESS");
        logger.info(" End of sendTailoredUseCasesToUser() ");
        return emailSuccess;
    }
}
