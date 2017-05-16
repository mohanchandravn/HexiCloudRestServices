package com.hexicloud.portaldb.resultextractor;

import com.hexicloud.portaldb.bean.Service;
import com.hexicloud.portaldb.bean.guidedpath.GuidedPath;
import com.hexicloud.portaldb.bean.guidedpath.GuidedPaths;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.util.StringUtils;

public class GuidedPathsResultExtractor implements ResultSetExtractor<GuidedPaths> {
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
        while (resultSet.next()) {
            pathId = resultSet.getInt("PATH_ID");
            GuidedPath guidedPath = guidedPathMap.get(pathId);
            if (guidedPath == null) {
                guidedPath = new GuidedPath();
                service = new Service();
                guidedPath.setPathId(pathId);
                guidedPath.setLabel(resultSet.getString("LABEL"));
                guidedPath.setShortDesc(resultSet.getString("SHORT_DESC"));
                guidedPath.setImage(resultSet.getString("IMAGE"));
                guidedPath.setImageLink(resultSet.getString("IMAGE_LINK"));
                service.setServiceId(resultSet.getString("SERVICE_ID"));
                service.setLabel(resultSet.getString("S_LABEL"));
                if (!StringUtils.isEmpty(resultSet.getString("SECTION_DOC_ID"))) {
                    guidedPath.setTotalChapters(1);
                }
                documentCompletion = resultSet.getString("STATUS");
                if (!StringUtils.isEmpty(documentCompletion) && "C".equalsIgnoreCase(documentCompletion)) {
                    guidedPath.setCompletedChapters(1);
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
                }
            }
            
        }
        guidedPaths.setGuidedPaths(new ArrayList<GuidedPath>(guidedPathMap.values()));
        return guidedPaths;
    }
}
