package com.hexicloud.portaldb.daoImpl;

import com.hexicloud.portaldb.bean.StepDocument;
import com.hexicloud.portaldb.dao.StepDocumentsDAO;
import com.hexicloud.portaldb.util.SqlQueryConstantsUtil;

import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class StepDocumentsDAOImpl implements StepDocumentsDAO {

    private static final Logger logger = Logger.getLogger(StepDocumentsDAOImpl.class);
    private JdbcTemplate jdbcTemplate;
    private DataSource dataSource;


    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        jdbcTemplate = new JdbcTemplate(this.dataSource);
    }


    @Override
    public List<StepDocument> findDocsByStepId(int stepId) {
        logger.info(" Begining of findDocsByStepId() ");

        @SuppressWarnings({ "unchecked", "rawtypes" })
        List<StepDocument> stepDocumentsList =
            jdbcTemplate.query(SqlQueryConstantsUtil.SQL_FIND_STEP_DOCS_BY_STEP_ID, new Object[] { stepId },
                               new BeanPropertyRowMapper(StepDocument.class));

        logger.info("stepDocumentsList size ===========> " + stepDocumentsList != null ? stepDocumentsList.size() :
                    null);
        logger.info(" End of findDocsByStepId() ");
        return stepDocumentsList;
    }

    @Override
    public List<StepDocument> findDocsByStepCode(String stepCode) {
        logger.info(" Begining of findDocsByStepCode() ");

        @SuppressWarnings({ "unchecked", "rawtypes" })
        List<StepDocument> stepDocumentsList =
            jdbcTemplate.query(SqlQueryConstantsUtil.SQL_FIND_STEP_DOCS_BY_STEP_CODE, new Object[] { stepCode },
                               new BeanPropertyRowMapper(StepDocument.class));

        logger.info("stepDocumentsList size ===========> " + stepDocumentsList != null ? stepDocumentsList.size() :
                    null);
        logger.info(" End of findDocsByStepCode() ");
        return stepDocumentsList;
    }
    
    @Override
    public List<StepDocument> findDocsByStepCodeAndSubStep(String stepCode, String subStepCode) {
        logger.info(" Begining of findDocsByStepCode() ");

        @SuppressWarnings({ "unchecked", "rawtypes" })
        List<StepDocument> stepDocumentsList =
            jdbcTemplate.query(SqlQueryConstantsUtil.SQL_FIND_STEP_DOCS_BY_STEP_CODE_AND_SUB_STEP, new Object[] { stepCode, subStepCode },
                               new BeanPropertyRowMapper(StepDocument.class));

        logger.info("stepDocumentsList size ===========> " + stepDocumentsList != null ? stepDocumentsList.size() :
                    null);
        logger.info(" End of findDocsByStepCode() ");
        return stepDocumentsList;
    }

    @Override
    public void addStepDocument(StepDocument stepDocument) {
        logger.info(" Begining of addStepDocument() ");

        jdbcTemplate.update(SqlQueryConstantsUtil.SQL_ADD_DOC_BY_STEP_ID,
                            new Object[] { stepDocument.getStepId(), stepDocument.getStepCode(),
                                           stepDocument.getDocType(), stepDocument.getDocTypeExtn(),
                                           stepDocument.getDocFileId(), stepDocument.getDocMetaData(),
                                           stepDocument.getFileName(), stepDocument.getPublicLinkId(),
                                           stepDocument.getSubStepCode(), stepDocument.getAppLinkUrl(),
                                           stepDocument.getAccessToken(), stepDocument.getRefreshToken(),
                                           stepDocument.getAppLinkId(), stepDocument.getDisplayOrder(),
                                           stepDocument.getDisplayLabel()});
        logger.info(" End of addStepDocument() ");
    }




}
