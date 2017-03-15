package com.hexicloud.portaldb.serviceImpl;


import com.hexi.dcs.client.constants.Role;
import com.hexi.dcs.client.pojo.AppLinkResponse;
import com.hexi.dcs.client.pojo.ApplinkRequest;
import com.hexi.dcs.client.pojo.CreateFolderRequest;
import com.hexi.dcs.client.pojo.CreateFolderResponse;
import com.hexi.dcs.client.pojo.DocumentUploadReponse;
import com.hexi.dcs.client.pojo.PublicLinkRequest;
import com.hexi.dcs.client.pojo.PublicLinkResponse;
import com.hexi.dcs.client.services.DCSAppLinkService;
import com.hexi.dcs.client.services.DCSFileService;
import com.hexi.dcs.client.services.DCSFolderService;
import com.hexi.dcs.client.services.DCSPublicLinkService;
import com.hexi.dcs.client.util.DCSRestEndpointFactory;

import com.hexicloud.portaldb.bean.StepDocument;
import com.hexicloud.portaldb.bean.StepFolder;
import com.hexicloud.portaldb.bean.UploadStepDocument;
import com.hexicloud.portaldb.service.StepDocumentsService;
import com.hexicloud.portaldb.service.StepFolderService;
import com.hexicloud.portaldb.service.UploadStepDocumentService;

import java.io.File;
import java.io.IOException;

import java.util.List;

import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.file.FileDataBodyPart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


/**
 * Created by shiva on 2/23/2017.
 */
@Service("uploadStepDocumentService")
public class UploadStepDocumentServiceImpl implements UploadStepDocumentService {


    @Autowired
    private StepDocumentsService stepDocumentsService;
    @Autowired
    private StepFolderService stepFolderService;


    @Override
    public DocumentUploadReponse uploadDocument(MultipartFile multipartFile, String parentFolderId,
                                                String fileId) throws IOException {
        File uploadFile = null;
        DocumentUploadReponse uploadResponse = null;
        if (multipartFile != null) {

            try {

                if (multipartFile != null) {
                    FormDataMultiPart multipart = new FormDataMultiPart();
                    String tempDir = UploadStepDocumentService.TEMP_DIR; //System.getProperty("java.io.tmpdir");
                    uploadFile = new File(tempDir + multipartFile.getOriginalFilename());
                    multipartFile.transferTo(uploadFile);
                    String jsonPayload = "{\"parentID\":\"" + parentFolderId + "\"}";
                    FormDataBodyPart fdpjson =
                        new FormDataBodyPart(UploadStepDocumentService.JSON_INPUT_PARAMETERS, jsonPayload);
                    multipart.bodyPart(fdpjson); //parentID

                    FileDataBodyPart fdp =
                        new FileDataBodyPart(UploadStepDocumentService.FILE_KEY, uploadFile, MediaType.TEXT_PLAIN_TYPE);

                    fdp.setName(multipartFile.getName());
                    multipart.bodyPart(fdp); //file
                    DCSFileService dcsFileService = DCSRestEndpointFactory.getDCSFileService();
                    if (dcsFileService != null) {
                        if (fileId == null) {
                            uploadResponse = dcsFileService.uploadDocumentToDCS(multipart);
                            //uploadResponse = DocumentCloudCProxy.uploadDocumentToDCS(multipart);
                        } else {
                            uploadResponse = dcsFileService.uploadDocumentToDCS(multipart, fileId);
                            //uploadResponse = DocumentCloudCProxy.uploadDocumentToDCS(multipart,fileId);
                        }
                    }


                }

            } finally {
                if (uploadFile != null)
                    uploadFile.delete();
            }


        }
        return uploadResponse;
    }

    @Override
    public PublicLinkResponse createFilePublicLink(String fileId) {
        PublicLinkResponse publicLinkResponse = null;
        PublicLinkRequest publicLinkRequest = new PublicLinkRequest();
        publicLinkRequest.setAssignedUsers("@everybody");
        publicLinkRequest.setLinkName("hexiCloudPublicLinkTest");
        publicLinkRequest.setRole(Role.CONTRIBUTOR.toString());
        DCSPublicLinkService dcsPublicLinkService = DCSRestEndpointFactory.getPublicLinkService();
        if (dcsPublicLinkService != null) {
            publicLinkResponse = dcsPublicLinkService.createPublicLinkforaFile(fileId, publicLinkRequest);
        }
        return publicLinkResponse;
    }

    @Override
    public void saveStepDocumentDetails(StepDocument stepDocument) {
        if (stepDocument != null) {
            stepDocumentsService.addStepDocument(stepDocument);
        }


    }

    @Override
    public String createOrRetrieveFolderId(UploadStepDocument uploadStepDocument) {
        String folderId = null;
        String stepLevelFolderId = null;
        String subStepLevelFolderId = null;
        if (stepFolderService != null && uploadStepDocument != null && uploadStepDocument.getStepId() != null) {
            List<StepFolder> listOfStepFolders =
                stepFolderService.retrieveStepFolderDetails(uploadStepDocument.getStepId());
            if (listOfStepFolders != null && !(listOfStepFolders.isEmpty())) {
                for (StepFolder stepFolder : listOfStepFolders) {
                    if (uploadStepDocument.getStepId() != null &&
                        (uploadStepDocument.getSubStepCode() == null ||
                         uploadStepDocument.getSubStepCode().equals(""))) {
                        if (stepFolder.getStepId() != null && stepFolder.getSubStepCode() == null) {
                            stepLevelFolderId = stepFolder.getFolderId();
                            break;
                        }
                    } else if (uploadStepDocument.getStepId() != null &&
                               (uploadStepDocument.getSubStepCode() != null &&
                                !(uploadStepDocument.getSubStepCode().equals("")))) {
                        if (stepLevelFolderId == null && stepFolder.getStepId() != null &&
                            stepFolder.getSubStepCode() == null) {
                            stepLevelFolderId = stepFolder.getFolderId();
                        }
                        if (stepFolder.getStepId() != null && stepFolder.getSubStepCode() != null) {

                            if (stepFolder.getSubStepCode().equalsIgnoreCase(uploadStepDocument.getSubStepCode())) {
                                subStepLevelFolderId = stepFolder.getFolderId();
                                break;
                            }
                        }
                    }
                }


            }
            if (uploadStepDocument.getStepId() != null) {

                DCSFolderService dcsFolderService = DCSRestEndpointFactory.getDCSFolderService();
                if ((uploadStepDocument.getSubStepCode() == null || uploadStepDocument.getSubStepCode().equals(""))) {
                    if (stepLevelFolderId == null) {

                        if (dcsFolderService != null) {
                            CreateFolderResponse createFolderResponse =
                                dcsFolderService.createFolder("self",
                                                              this.prepareCreateStepFolderRequest(uploadStepDocument));
                            if (createFolderResponse != null) {
                                StepFolder stepFolder = this.prepareStepFolderRequest(uploadStepDocument);
                                folderId = createFolderResponse.getId();
                                stepFolder.setFolderId(folderId);
                                stepFolderService.addStepFolder(stepFolder);
                            }
                        }
                        //                        CreateFolderResponse createFolderResponse =
                        //                                DocumentCloudCProxy.createFolder("self", stepFolderRequest);
                        //                        if (createFolderResponse != null) {
                        //                            folderId = createFolderResponse.getId();
                        //                            stepFolder.setFolderId(folderId);
                        //                            stepFolderService.addStepFolder(stepFolder);
                        //                        }
                    } else {
                        folderId = stepLevelFolderId;
                    }


                } else if ((uploadStepDocument.getSubStepCode() != null &&
                            !(uploadStepDocument.getSubStepCode().equals("")))) {
                    if (stepLevelFolderId == null) {
                        if (dcsFolderService != null) {
                            CreateFolderResponse createFolderResponse =
                                dcsFolderService.createFolder("self",
                                                              this.prepareCreateStepFolderRequest(uploadStepDocument));
                            if (createFolderResponse != null) {
                                StepFolder stepFolder = this.prepareStepFolderRequest(uploadStepDocument);
                                stepFolder.setFolderId(createFolderResponse.getId());
                                stepLevelFolderId = createFolderResponse.getId();
                                stepFolderService.addStepFolder(stepFolder);
                            }
                        }
                        //                        CreateFolderResponse createFolderResponse =
                        //                                DocumentCloudCProxy.createFolder("self", stepFolderRequest);
                        //                        if (createFolderResponse != null) {
                        //                            stepFolder.setFolderId(createFolderResponse.getId());
                        //                            stepLevelFolderId = createFolderResponse.getId();
                        //                            stepFolderService.addStepFolder(stepFolder);
                        //                        }
                    }
                    if (subStepLevelFolderId == null) {
                        CreateFolderResponse createFolderResponse =
                            dcsFolderService.createFolder(stepLevelFolderId,
                                                          this.prepareCreateSubStepFolderRequest(uploadStepDocument));
                        if (createFolderResponse != null) {
                            folderId = createFolderResponse.getId();
                            StepFolder subStepFolder = new StepFolder();
                            subStepFolder.setFolderId(folderId);
                            subStepFolder.setStepId(uploadStepDocument.getStepId());
                            subStepFolder.setStepCode(uploadStepDocument.getStepCode());
                            subStepFolder.setSubStepCode(uploadStepDocument.getSubStepCode());
                            stepFolderService.addStepFolder(subStepFolder);
                        }

                        //                        CreateFolderResponse createFolderResponse =
                        //                                DocumentCloudCProxy.createFolder(stepLevelFolderId, this.prepareCreateSubStepFolderRequest());
                        //                        if (createFolderResponse != null) {
                        //                            folderId = createFolderResponse.getId();
                        ////                            SubStep subStep = new SubStep();
                        ////                            subStep.setStepId(uploadStepDocument.getStepId());
                        ////                            subStep.setStepCode(uploadStepDocument.getStepCode());
                        ////                            subStep.setSubStepCode(uploadStepDocument.getSubStepCode());
                        ////                            subStep.setSubStepLabel(uploadStepDocument.getSubStepCode());
                        ////                            subStep.setSubStepDesc(uploadStepDocument.getSubStepCode());
                        ////                            subStepService.addSubStep(subStep);
                        //                            StepFolder subStepFolder = new StepFolder();
                        //                            subStepFolder.setFolderId(folderId);
                        //                            subStepFolder.setStepId(uploadStepDocument.getStepId());
                        //                            subStepFolder.setStepCode(uploadStepDocument.getStepCode());
                        //                            subStepFolder.setSubStepCode(uploadStepDocument.getSubStepCode());
                        //                            stepFolderService.addStepFolder(subStepFolder);
                        //                        }
                    } else {
                        folderId = subStepLevelFolderId;
                    }

                }
            }


        }

        return folderId;
    }

    @Override
    public AppLinkResponse createFileAppLink(String fileId) {
        AppLinkResponse appLinkResponse = null;
        ApplinkRequest applinkRequest = new ApplinkRequest();
        applinkRequest.setAssignedUser("cloud.admin"); // need to change this;
        applinkRequest.setRole(Role.DOWNLOADER.toString());
        DCSAppLinkService dcsAppLinkService = DCSRestEndpointFactory.getAppLinkService();
        if (dcsAppLinkService != null) {
            appLinkResponse = dcsAppLinkService.createFileAppLink(applinkRequest, fileId);
        }

        //AppLinkResponse appLinkResponse =  DocumentCloudCProxy.createFileAppLink(applinkRequest, fileId);
        return appLinkResponse;
    }

    private CreateFolderRequest prepareCreateStepFolderRequest(UploadStepDocument uploadStepDocument) {
        CreateFolderRequest createFolderRequestRequest = new CreateFolderRequest();
        createFolderRequestRequest.setName(uploadStepDocument.getStepFolderName());
        createFolderRequestRequest.setDescription(uploadStepDocument.getStepFolderDesc());

        return createFolderRequestRequest;
    }

    private CreateFolderRequest prepareCreateSubStepFolderRequest(UploadStepDocument uploadStepDocument) {
        CreateFolderRequest createFolderRequestRequest = new CreateFolderRequest();
        createFolderRequestRequest.setName(uploadStepDocument.getSubStepFolderName());
        createFolderRequestRequest.setDescription(uploadStepDocument.getSubStepFolderDesc());

        return createFolderRequestRequest;
    }

    private StepFolder prepareStepFolderRequest(UploadStepDocument uploadStepDocument) {
        StepFolder stepFolder = new StepFolder();
        stepFolder.setStepId(uploadStepDocument.getStepId());
        stepFolder.setStepCode(uploadStepDocument.getStepCode());
        return stepFolder;
    }
}
