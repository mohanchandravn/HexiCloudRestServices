package com.hexicloud.portaldb.serviceImpl;

import com.hexicloud.portaldb.bean.guidedpath.GuidedPath;
import com.hexicloud.portaldb.bean.guidedpath.GuidedPaths;
import com.hexicloud.portaldb.dao.ClmDataDAO;
import com.hexicloud.portaldb.dao.GuidedPathsDAO;
import com.hexicloud.portaldb.service.GuidedPathsService;

import java.util.List;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("guidedPathsService")
public class GuidedPathsServiceImpl implements GuidedPathsService {

    private static final Logger logger = Logger.getLogger(GuidedPathsServiceImpl.class);

    @Autowired
    GuidedPathsDAO guidedPathsDAO;

    @Autowired
    ClmDataDAO clmDataDAO;

    @Override
    public GuidedPaths getCoreGuidedPaths(String userId) {
        logger.info(" Begining of getCoreGuidedPaths() ");

        GuidedPaths guidedPaths = guidedPathsDAO.getCoreGuidedPaths(userId);
        List<String> servicesForUser = clmDataDAO.getServicesForUser(userId);
        if (!guidedPaths.getGuidedPaths().isEmpty() && !servicesForUser.isEmpty()) {
            for (GuidedPath guidedPath : guidedPaths.getGuidedPaths()) {
                if (servicesForUser.contains(guidedPath.getService().getServiceId())) {
                    guidedPath.setIsRecommmended(true);
                }
                if (null != guidedPath.getCompletedChapters() && null != guidedPath.getTotalChapters()) {
                    guidedPath.setProgress(guidedPath.getCompletedChapters().doubleValue()/guidedPath.getTotalChapters().doubleValue());
                } else {
                    guidedPath.setCompletedChapters(guidedPath.getCompletedChapters() !=null ? guidedPath.getCompletedChapters() : 0);
                    guidedPath.setProgress(0);
                }
            }
        }
        logger.info(" End of getCoreGuidedPaths() ");
        return guidedPaths;
    }

    @Override
    public GuidedPaths getComplementaryGuidedPaths(Integer usecaseId, String userId) {
        logger.info(" Begining of getComplementaryGuidedPaths() ");

        GuidedPaths guidedPaths = guidedPathsDAO.getComplementaryGuidedPaths(usecaseId, userId);
//        List<String> servicesForUser = clmDataDAO.getServicesForUser(userId);
        if (!guidedPaths.getGuidedPaths().isEmpty()) {
            for (GuidedPath guidedPath : guidedPaths.getGuidedPaths()) {
//                if (servicesForUser.contains(guidedPath.getService().getServiceId())) {
//                    guidedPath.setIsRecommmended(true);
//                }
                if (null != guidedPath.getCompletedChapters() && null != guidedPath.getTotalChapters()) {
                    guidedPath.setProgress(guidedPath.getCompletedChapters().doubleValue()/guidedPath.getTotalChapters().doubleValue());
                } else {
                    guidedPath.setCompletedChapters(guidedPath.getCompletedChapters() !=null ? guidedPath.getCompletedChapters() : 0);
                    guidedPath.setProgress(0);
                }
            }
        }
        logger.info(" End of getComplementaryGuidedPaths() ");
        return guidedPaths;
    }
}
