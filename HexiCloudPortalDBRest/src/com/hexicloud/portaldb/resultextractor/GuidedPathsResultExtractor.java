package com.hexicloud.portaldb.resultextractor;

import com.hexicloud.portaldb.bean.Service;
import com.hexicloud.portaldb.bean.guidedpath.CurrentDoc;
import com.hexicloud.portaldb.bean.guidedpath.GuidedPath;
import com.hexicloud.portaldb.bean.guidedpath.GuidedPaths;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.util.StringUtils;

public class GuidedPathsResultExtractor implements ResultSetExtractor<GuidedPaths> {

    private static final Logger logger = Logger.getLogger(GuidedPathsResultExtractor.class);

    public GuidedPathsResultExtractor() {
        super();
    }

    @Override
    public GuidedPaths extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        GuidedPaths guidedPaths = new GuidedPaths();
        Map<Integer, GuidedPath> guidedPathMap = new HashMap<Integer, GuidedPath>();
        Integer pathId = null;
        Service service = null;
        String documentCompletion = null;
        CurrentDoc currentDoc = null;
        while (resultSet.next()) {
            pathId = resultSet.getInt("PATH_ID");
            GuidedPath guidedPath = guidedPathMap.get(pathId);
            if (guidedPath == null) {
                guidedPath = new GuidedPath();

                guidedPath.setPathId(pathId);
                guidedPath.setLabel(resultSet.getString("LABEL"));
                guidedPath.setShortDesc(resultSet.getString("SHORT_DESC"));
                guidedPath.setImage(resultSet.getString("IMAGE"));
                guidedPath.setImageLink(resultSet.getString("IMAGE_LINK"));
                try {
                    if (!StringUtils.isEmpty(resultSet.getString("SERVICE_ID"))) {
                        service = new Service();
                        service.setServiceId(resultSet.getString("SERVICE_ID"));
                        service.setLabel(resultSet.getString("S_LABEL"));
                    }
                } catch (SQLException sqle) {
                    logger.warn("Expected error as Complementary guided paths are not related to service");
                }

                if (!StringUtils.isEmpty(resultSet.getString("SECTION_DOC_ID"))) {
                    guidedPath.setTotalChapters(1);
                }
                documentCompletion = resultSet.getString("STATUS");
                if (!StringUtils.isEmpty(documentCompletion) && "C".equalsIgnoreCase(documentCompletion)) {
                    guidedPath.setCompletedChapters(1);
                } else if (!StringUtils.isEmpty(documentCompletion) && "I".equalsIgnoreCase(documentCompletion)) {
                    currentDoc = new CurrentDoc();
                    guidedPath.setCurrentDoc(mapCurrentDocument(currentDoc, resultSet));
                }
                guidedPath.setService(service);
                guidedPathMap.put(pathId, guidedPath);
            } else {
                if (!StringUtils.isEmpty(resultSet.getString("SECTION_DOC_ID"))) {
                    guidedPath.setTotalChapters(guidedPath.getTotalChapters() + 1);
                }
                documentCompletion = resultSet.getString("STATUS");
                if (!StringUtils.isEmpty(documentCompletion) && "C".equalsIgnoreCase(documentCompletion)) {
                    guidedPath.setCompletedChapters(guidedPath.getCompletedChapters() + 1);
                } else if (!StringUtils.isEmpty(documentCompletion) && "I".equalsIgnoreCase(documentCompletion)) {
                    currentDoc = guidedPath.getCurrentDoc();
                    if (null != currentDoc) {
                        Timestamp chkLastUpdatedTime = resultSet.getTimestamp("LAST_UPDATED_DATE");
                        if (chkLastUpdatedTime.after(currentDoc.getLastUpdatedDate())) {
                            mapCurrentDocument(currentDoc, resultSet);
                        }
                    } else {
                        currentDoc = new CurrentDoc();
                        guidedPath.setCurrentDoc(mapCurrentDocument(currentDoc, resultSet));
                    }
                }
            }
        }
        guidedPaths.setGuidedPaths(new ArrayList<GuidedPath>(guidedPathMap.values()));
        return guidedPaths;
    }
    
    private CurrentDoc mapCurrentDocument(CurrentDoc curDoc, ResultSet rs) throws SQLException {
        curDoc.setSectionId(rs.getInt("SECTION_ID"));
        curDoc.setSectionDocId(rs.getInt("SECTION_DOC_ID"));
        curDoc.setDocName(rs.getString("DOC_NAME"));
        curDoc.setDocDescription(rs.getString("DOC_DESCRIPTION"));
        curDoc.setDocType(rs.getString("DOC_TYPE"));
        curDoc.setPublicLink(rs.getString("PUBLIC_LINK"));
        curDoc.setPageNumber(rs.getInt("PAGE_NUMBER"));
        curDoc.setLastUpdatedDate(rs.getTimestamp("LAST_UPDATED_DATE"));
        return curDoc;
    }
}
