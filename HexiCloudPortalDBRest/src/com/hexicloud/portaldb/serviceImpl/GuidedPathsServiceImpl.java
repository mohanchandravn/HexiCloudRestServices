package com.hexicloud.portaldb.serviceImpl;

import com.hexicloud.portaldb.bean.guidedpath.GuidedPath;
import com.hexicloud.portaldb.bean.guidedpath.GuidedPathDetail;
import com.hexicloud.portaldb.bean.guidedpath.GuidedPathDetailResponse;
import com.hexicloud.portaldb.bean.guidedpath.GuidedPaths;
import com.hexicloud.portaldb.bean.guidedpath.Section;
import com.hexicloud.portaldb.bean.guidedpath.SectionDoc;
import com.hexicloud.portaldb.bean.guidedpath.UpdateLearningPathRequest;
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
                    guidedPath.setProgress((guidedPath.getCompletedChapters().doubleValue() /
                                            guidedPath.getTotalChapters().doubleValue()) * 100);
                } else {
                    guidedPath.setCompletedChapters(guidedPath.getCompletedChapters() != null ?
                                                    guidedPath.getCompletedChapters() : 0);
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
        if (!guidedPaths.getGuidedPaths().isEmpty()) {
            for (GuidedPath guidedPath : guidedPaths.getGuidedPaths()) {
                if (null != guidedPath.getCompletedChapters() && null != guidedPath.getTotalChapters()) {
                    guidedPath.setProgress((guidedPath.getCompletedChapters().doubleValue() /
                                            guidedPath.getTotalChapters().doubleValue()) * 100);
                } else {
                    guidedPath.setCompletedChapters(guidedPath.getCompletedChapters() != null ?
                                                    guidedPath.getCompletedChapters() : 0);
                    guidedPath.setProgress(0);
                }
            }
        }
        logger.info(" End of getComplementaryGuidedPaths() ");
        return guidedPaths;
    }

    @Override
    public GuidedPathDetailResponse getGuidedPathDetail(Integer pathId, String userId) {
        logger.info(" Begining of getGuidedPathDetail() ");
        GuidedPathDetailResponse response = guidedPathsDAO.getGuidedPathDetail(pathId, userId);
        if (null != response.getGuidedPathDetail()) {
            GuidedPathDetail detail = response.getGuidedPathDetail();
            if (null != detail.getCompletedChapters() && null != detail.getTotalChapters()) {
                detail.setProgress((detail.getCompletedChapters().doubleValue() /
                                    detail.getTotalChapters().doubleValue()) * 100);
            } else {
                detail.setCompletedChapters(detail.getCompletedChapters() != null ? detail.getCompletedChapters() : 0);
                detail.setProgress(0);
            }

            for (Section section : detail.getSections()) {
                section.setTotalSubSections(section.getSectionDocs().size());
                section.setCompletedSubSections(0);
                for (SectionDoc doc : section.getSectionDocs()) {
                    if ("C".equalsIgnoreCase(doc.getStatus())) {
                        section.setCompletedSubSections(section.getCompletedSubSections() + 1);
                    }
                }
                if (section.getTotalSubSections().compareTo(section.getCompletedSubSections()) == 0) {
                    section.setIsCompleted(true);
                }
            }
        }
        logger.info(" End of getGuidedPathDetail() ");
        return response;
    }

    @Override
    public void changeLearningHistory(UpdateLearningPathRequest learningPathRequest, String userId) {
        logger.info(" Begining of changeLearningHistory() ");
        guidedPathsDAO.insertUpdateLearningHistory(learningPathRequest, userId);
        logger.info(" End of changeLearningHistory() ");

    }
}
