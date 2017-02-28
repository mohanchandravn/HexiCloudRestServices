package com.hexicloud.portaldb.controller;


import com.fasterxml.jackson.databind.ObjectMapper;


import com.hexi.dcs.client.pojo.AppLinkResponse;
import com.hexi.dcs.client.pojo.DeleteFileRequest;
import com.hexi.dcs.client.pojo.DeleteFileResponse;
import com.hexi.dcs.client.pojo.DocumentUploadReponse;
import com.hexi.dcs.client.pojo.PublicLinkResponse;

import com.hexi.dcs.client.services.DCSFileService;
import com.hexi.dcs.client.util.DCSRestEndpointFactory;
import com.hexicloud.portaldb.bean.StepDocument;
import com.hexicloud.portaldb.bean.UploadStepDocument;


import java.io.IOException;

import java.util.Iterator;

import java.util.Map;


import com.hexicloud.portaldb.service.UploadStepDocumentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import org.springframework.web.multipart.commons.CommonsMultipartFile;


@RestController
public class UploadStepDocumentController {

    //private final static String TEMP_DIR = "java.io.tmpdir";/u01/app/oracle/middleware/HEXI/temp
    private final static String PRIMARY_FILE_KEY = "primaryFile";
    private final static int HTTP_STATUS_CREATED = 201;


    @Autowired
    private UploadStepDocumentService uploadStepDocumentService;


    @RequestMapping(value = "services/rest/uploadStepDocument", method = RequestMethod.POST)
    public ResponseEntity processUploadStepDocument(MultipartHttpServletRequest multipartHttpServletRequest) {

            try {
                UploadStepDocument stepDocumentInput = null;
                DocumentUploadReponse uploadResponse = null;
                CommonsMultipartFile multipartFile =
                        (CommonsMultipartFile) multipartHttpServletRequest.getFile(PRIMARY_FILE_KEY); //retveieve file
                Map<String, String[]> paramMap = multipartHttpServletRequest.getParameterMap(); //get all parameters
                if (multipartFile != null) {
                    if (paramMap != null && !(paramMap.isEmpty())) {
                        ObjectMapper mapper = new ObjectMapper();
                        Iterator parameterEntries = paramMap.entrySet().iterator();
                        while (parameterEntries.hasNext()) {
                            Map.Entry entryObj = (Map.Entry) parameterEntries.next();
                            String[] paramValue = (String[]) entryObj.getValue();
                            stepDocumentInput =
                                    mapper.readValue(paramValue[0].getBytes(), UploadStepDocument.class);

                        }
                    }

                    stepDocumentInput.setStepFolderName((stepDocumentInput.getStepCode() != null &&
                            stepDocumentInput.getStepId() != null) ?
                            ((stepDocumentInput.getStepCode() + " - " +
                                    stepDocumentInput.getStepId())).toUpperCase() : null);
                    stepDocumentInput.setStepFolderDesc((stepDocumentInput.getStepCode() != null &&
                            stepDocumentInput.getStepId() != null) ?
                            (stepDocumentInput.getStepCode() + " - " +
                                    stepDocumentInput.getStepId()) : null);

                    stepDocumentInput.setSubStepFolderName(stepDocumentInput.getSubStepCode() != null ? stepDocumentInput.getSubStepCode().toUpperCase(): null);
                    stepDocumentInput.setStepFolderDesc(stepDocumentInput.getSubStepCode());
                    stepDocumentInput.setFileName(multipartFile.getOriginalFilename());


                    //create folder Id or retrieve folder Id
                    String folderId = uploadStepDocumentService.createOrRetrieveFolderId(stepDocumentInput);

                    if (folderId != null) {
                        uploadResponse = uploadStepDocumentService.uploadDocument(multipartFile, folderId, stepDocumentInput.getDocFieldId()); //upload the document

                        if (uploadResponse !=
                                null) {
                            //check if new document is uploaded for the selected step id, then insert into STEP_DOCUMENT table to link step id and document id
                            if (uploadResponse.getHttpStatus() ==
                                    UploadStepDocumentController.HTTP_STATUS_CREATED) //|| uploadResponse.getHttpStatus() == UploadDocumentController.HTTP_STATUS_UPDATED
                            {
                                //the logic to insert the STEP_DOCUMENT db table
                                PublicLinkResponse publicLinkResponse =
                                        uploadStepDocumentService.createFilePublicLink(uploadResponse.getId());

                                AppLinkResponse appLinkResponse = uploadStepDocumentService.createFileAppLink(uploadResponse.getId());

                                if (publicLinkResponse != null && publicLinkResponse.getLinkID() != null) {
                                    stepDocumentInput.setPublicLinkId(publicLinkResponse.getLinkID());

                                }
                                StepDocument stepDocument = this.createStepDocumentRequest(stepDocumentInput,uploadResponse,publicLinkResponse,appLinkResponse);

                                uploadStepDocumentService.saveStepDocumentDetails(stepDocument); //save to step_documents table

                            }

                        }

                    }

                }

                if (uploadResponse == null) {
                    return new ResponseEntity(HttpStatus.BAD_REQUEST);
                } else {
                    return new ResponseEntity(uploadResponse, HttpStatus.valueOf(uploadResponse.getHttpStatus()));
                }

            } catch (IOException ioEx) {
                ioEx.printStackTrace();

            } catch (Exception ex) {
                ex.printStackTrace();

            }


        return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);

    }

   @RequestMapping(value = "services/rest/deleteStepDocument")
    public ResponseEntity handleDeleteDocument(@RequestBody String fileId, String fileVersion)
    {
        DeleteFileRequest deleteFileRequest = new DeleteFileRequest();
        DeleteFileResponse deleteFileResponse = null;
        deleteFileRequest.setVersion(fileVersion);
        DCSFileService dcsFileService = DCSRestEndpointFactory.getDCSFileService();
        if(dcsFileService != null)
        {
            deleteFileResponse = dcsFileService.deleteFile(deleteFileRequest, fileId);
            if(deleteFileResponse != null) {
                return new ResponseEntity(deleteFileResponse, HttpStatus.OK);
            }
        }

       
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }





    private StepDocument createStepDocumentRequest(UploadStepDocument stepDocumentInput, DocumentUploadReponse uploadResponse, PublicLinkResponse publicLinkResponse, AppLinkResponse appLinkResponse)
    {
        StepDocument stepDocument = null;
        if(stepDocumentInput != null && uploadResponse != null) {
            stepDocument = new StepDocument();
            stepDocument.setStepId(stepDocumentInput.getStepId() != null ? Integer.valueOf(stepDocumentInput.getStepId()) : -1);
            stepDocument.setStepCode(stepDocumentInput.getStepCode());
            stepDocument.setSubStepCode(stepDocumentInput.getSubStepCode());
            if(publicLinkResponse != null) {
                stepDocument.setPublicLinkId(stepDocumentInput.getPublicLinkId());
            }
            stepDocument.setDisplayLabel(stepDocumentInput.getDisplayLabel());
            stepDocument.setDisplayOrder(stepDocumentInput.getDisplayOrder() != null ? Integer.valueOf(stepDocumentInput.getDisplayOrder()): 0);
            stepDocument.setDocFileId(uploadResponse.getId());
            stepDocument.setDocType("FILE"); //ned to check this value
            stepDocument.setFileName(uploadResponse.getName());
            String fileExtn =
                    uploadResponse.getName() != null ?
                            uploadResponse.getName()
                                    .substring(uploadResponse.getName().lastIndexOf(".") + 1, uploadResponse.getName().length()) : "NA";
            stepDocument.setDocTypeExtn(fileExtn);
            if(appLinkResponse != null)
            {
                stepDocument.setAccessToken(appLinkResponse.getAccessToken());
                stepDocument.setAppLinkUrl(appLinkResponse.getAppLinkUrl());
                stepDocument.setAppLinkId(appLinkResponse.getAppLinkID());
                stepDocument.setRefreshToken(appLinkResponse.getRefreshToken());
                stepDocument.setDocCsRole(appLinkResponse.getRole());
            }
        }

        return stepDocument;
    }




}
