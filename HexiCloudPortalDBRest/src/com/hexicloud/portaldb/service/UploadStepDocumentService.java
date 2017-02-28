package com.hexicloud.portaldb.service;


import com.hexi.dcs.client.pojo.AppLinkResponse;
import com.hexi.dcs.client.pojo.DocumentUploadReponse;
import com.hexi.dcs.client.pojo.PublicLinkResponse;
import com.hexicloud.portaldb.bean.StepDocument;
import com.hexicloud.portaldb.bean.UploadStepDocument;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Created by sgunjur on 2/23/2017.
 */

public interface UploadStepDocumentService {
     final static String TEMP_DIR = "/u01/app/oracle/middleware/HEXI/temp/";
     final static String FILE_KEY = "file";
     final static String JSON_INPUT_PARAMETERS = "jsonInputParameters";

    PublicLinkResponse createFilePublicLink(String fileId);
    void saveStepDocumentDetails(StepDocument stepDocument);
    String createOrRetrieveFolderId(UploadStepDocument uploadStepDocument);
    DocumentUploadReponse uploadDocument(MultipartFile multipartFile, String parentFolderId, String fileId) throws IOException;
    AppLinkResponse createFileAppLink(String fileId);
}
