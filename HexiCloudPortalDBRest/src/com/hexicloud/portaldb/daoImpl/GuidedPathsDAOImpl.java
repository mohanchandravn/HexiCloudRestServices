package com.hexicloud.portaldb.daoImpl;

import com.hexicloud.portaldb.bean.guidedpath.GuidedPathDetailResponse;
import com.hexicloud.portaldb.bean.guidedpath.GuidedPaths;
import com.hexicloud.portaldb.bean.guidedpath.LearningUpdate;
import com.hexicloud.portaldb.bean.guidedpath.UpdateLearningPathRequest;
import com.hexicloud.portaldb.dao.GuidedPathsDAO;
import com.hexicloud.portaldb.resultextractor.GuidedPathDetailResultExtractor;
import com.hexicloud.portaldb.resultextractor.GuidedPathsResultExtractor;
import com.hexicloud.portaldb.util.SqlQueryConstantsUtil;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class GuidedPathsDAOImpl implements GuidedPathsDAO {

    private static final Logger logger = Logger.getLogger(GuidedPathsDAOImpl.class);
    private JdbcTemplate jdbcTemplate;
    private DataSource dataSource;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        jdbcTemplate = new JdbcTemplate(this.dataSource);
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource());
    }


    @Override
    public GuidedPaths getCoreGuidedPaths(String userId) {
        logger.info(" Begining of getCoreGuidedPaths() ");
        @SuppressWarnings({ "unchecked", "rawtypes" })
        GuidedPaths guidedPaths =
            (GuidedPaths) jdbcTemplate.query(SqlQueryConstantsUtil.SQL_GET_CORE_GUIDED_PATHS, new Object[] { userId },
                                             new GuidedPathsResultExtractor());
        logger.info(" End of getCoreGuidedPaths() ");
        return guidedPaths;
    }

    @Override
    public GuidedPaths getComplementaryGuidedPaths(Integer useCaseId, String userId) {
        logger.info(" Begining of getComplementaryGuidedPaths() ");
        @SuppressWarnings({ "unchecked", "rawtypes" })
        GuidedPaths guidedPaths =
            (GuidedPaths) jdbcTemplate.query(SqlQueryConstantsUtil.SQL_GET_COMPLE_GUIDED_PATHS,
                                             new Object[] { useCaseId, userId }, new GuidedPathsResultExtractor());
        logger.info(" End of getComplementaryGuidedPaths() ");
        return guidedPaths;
    }

    @Override
    public GuidedPathDetailResponse getGuidedPathDetail(Integer pathId, String userId) {
        logger.info(" Begining of getGuidedPathDetail() ");
        GuidedPathDetailResponse response =
            (GuidedPathDetailResponse) jdbcTemplate.query(SqlQueryConstantsUtil.SQL_GET_GUIDED_PATH_DETAIL,
                                                          new Object[] { pathId, userId },
                                                          new GuidedPathDetailResultExtractor());
        logger.info(" End of getGuidedPathDetail() ");
        return response;
    }

    @Override
    public void insertUpdateLearningHistory(UpdateLearningPathRequest learningPathRequest, String userId) {
        logger.info(" Begining of updateLearningHistory() ");
        LearningUpdate learningUpdate = learningPathRequest.getLearningUpdate();
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("userId", userId);
        parameters.addValue("pathId", learningUpdate.getPathId());
        parameters.addValue("sectionId", learningUpdate.getSectionID());
        parameters.addValue("sectionDocId", learningUpdate.getSectionDocId());
        parameters.addValue("status", learningUpdate.getStatus());
        parameters.addValue("pageNumber", learningUpdate.getPageNumber());
        int mergedRows =
            namedParameterJdbcTemplate.update(SqlQueryConstantsUtil.SQL_MERGE_LEARNING_HISTORY, parameters);
        logger.info(" End of updateLearningHistory(), no of rows merged : " + mergedRows);
    }
}
