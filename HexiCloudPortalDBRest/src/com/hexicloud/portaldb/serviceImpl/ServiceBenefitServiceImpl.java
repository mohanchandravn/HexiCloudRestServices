package com.hexicloud.portaldb.serviceImpl;

import com.hexi.dcs.client.pojo.DownloadFileRequest;
import com.hexi.dcs.client.services.DCSFileService;

import com.hexicloud.portaldb.bean.Usecase;
import com.hexicloud.portaldb.dao.ServiceBenefitDAO;
import com.hexicloud.portaldb.service.ServiceBenefitService;

import java.util.List;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("serviceBenefitService")
public class ServiceBenefitServiceImpl implements ServiceBenefitService {
    public ServiceBenefitServiceImpl() {
        super();
    }
    private static final Logger logger = Logger.getLogger(ServiceBenefitServiceImpl.class);

    @Autowired
    ServiceBenefitDAO serviceBenefitDAO;
    
    @Override
    public String getServiceBenefits(String serviceName) {
        // TODO Implement this method
        DCSFileService fileService = new DCSFileService();
        DownloadFileRequest downloadFileRequest = new DownloadFileRequest();
        String fileId = serviceBenefitDAO.getServiceBenefitFileID(serviceName);
        String benefit = fileService.downloadFile( downloadFileRequest, fileId);
        return benefit;
        
    }

    @Override
    public List<Usecase> getUsecases() {
        List<Usecase> usecaseList = serviceBenefitDAO.getUsecases();
        return usecaseList;
    }

    @Override
    public String getUsecaseDetails(String usecaseCode) {
        DCSFileService fileService = new DCSFileService();
        DownloadFileRequest downloadFileRequest = new DownloadFileRequest();
        String fileId = serviceBenefitDAO.getUsecaseFileID(usecaseCode);
        String usecase = fileService.downloadFile( downloadFileRequest, fileId);
        return usecase;
    }
}
