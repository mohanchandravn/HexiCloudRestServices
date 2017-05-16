package com.hexicloud.portaldb.resultextractor;

import com.hexicloud.portaldb.bean.guidedpath.GuidedPathDetail;
import com.hexicloud.portaldb.bean.guidedpath.GuidedPathDetailResponse;
import com.hexicloud.portaldb.bean.guidedpath.Section;
import com.hexicloud.portaldb.bean.guidedpath.SectionDoc;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.util.StringUtils;

public class GuidedPathDetailResultExtractor implements ResultSetExtractor<GuidedPathDetailResponse> {

    private static final Logger logger = Logger.getLogger(GuidedPathDetailResultExtractor.class);

    public GuidedPathDetailResultExtractor() {
        super();
    }

    @Override
    public GuidedPathDetailResponse extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        GuidedPathDetailResponse response = new GuidedPathDetailResponse();
        GuidedPathDetail gpDetail = new GuidedPathDetail();
        Map<Integer, Section> sectionMap = new HashMap<Integer, Section>();
        Map<Integer, SectionDoc> sectionDocMap = new HashMap<Integer, SectionDoc>();
        List<SectionDoc> sectionDocs = null;
        String documentCompletion = null;
        Integer sectionId = null;
        Integer sectionDocId = null;
        Section section = null;
        while (resultSet.next()) {
            if (null == gpDetail.getPathId()) {
                gpDetail.setPathId(resultSet.getInt("PATH_ID"));
                gpDetail.setLabel(resultSet.getString("LABEL"));
                gpDetail.setShortDesc(resultSet.getString("SHORT_DESC"));
                gpDetail.setImage(resultSet.getString("IMAGE"));
                gpDetail.setImageLink(resultSet.getString("IMAGE_LINK"));
            }
            sectionId = resultSet.getInt("SECTION_ID");
            section = sectionMap.get(sectionId);
            if (section == null) {
                section = new Section();
                section.setSectionID(sectionId);
                section.setSectionTitle(resultSet.getString("SECTION_TITLE"));
                section.setDescription(resultSet.getString("DESCRIPTION"));
                section.setSectionOrder(resultSet.getInt("SECTION_ORDER"));
                sectionDocs = new ArrayList<SectionDoc>();
                sectionDocs.add(makeSectionDoc(resultSet));
                section.setSectionDocs(sectionDocs);
                sectionMap.put(sectionId, section);
            } else {
                section.getSectionDocs().add(makeSectionDoc(resultSet));
            }
            if (!StringUtils.isEmpty(resultSet.getString("SECTION_DOC_ID"))) {
                gpDetail.setTotalChapters(gpDetail.getTotalChapters() == null ? 1 : gpDetail.getTotalChapters() + 1);
            }
            documentCompletion = resultSet.getString("STATUS");
            if (!StringUtils.isEmpty(documentCompletion) && "C".equalsIgnoreCase(documentCompletion)) {
                gpDetail.setCompletedChapters(gpDetail.getCompletedChapters() == null ? 1 :
                                              gpDetail.getCompletedChapters() + 1);
            }
        }
        gpDetail.setSections(new ArrayList<Section>(sectionMap.values()));
        response.setGuidedPathDetail(gpDetail);
        return response;
    }

    private SectionDoc makeSectionDoc(ResultSet rSet) throws SQLException {
        SectionDoc doc = new SectionDoc();
        doc.setSectionDocId(rSet.getInt("SECTION_DOC_ID"));
        doc.setDocName(rSet.getString("DOC_NAME"));
        doc.setPublicLink(rSet.getString("PUBLIC_LINK"));
        doc.setDocType(rSet.getString("DOC_TYPE"));
        doc.setDocDescription(rSet.getString("DOC_DESCRIPTION"));
        doc.setDocOrder(rSet.getInt("DOC_ORDER"));
        doc.setTimeToComplete(rSet.getString("TIME_TO_COMPLETE"));
        doc.setStatus(rSet.getString("STATUS"));
        doc.setLastUpdatedDate(rSet.getTimestamp("LAST_UPDATED_DATE"));
        doc.setPageNumber(rSet.getInt("PAGE_NUMBER"));
        return doc;
    }

}
