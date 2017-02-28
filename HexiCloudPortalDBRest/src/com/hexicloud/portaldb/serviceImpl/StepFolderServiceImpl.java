package com.hexicloud.portaldb.serviceImpl;

import com.hexicloud.portaldb.bean.StepFolder;
import com.hexicloud.portaldb.dao.StepFolderDAO;
import com.hexicloud.portaldb.service.StepFolderService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("stepFolderService")
public class StepFolderServiceImpl implements StepFolderService {
    
    @Autowired
    private StepFolderDAO stepFolderDAO;
    
    
    @Override
    public List<StepFolder> retrieveStepFolderDetails(String stepId) {
        if(stepId != null) {
            return stepFolderDAO.retrieveStepFolderDetails(stepId);
        }
        
        return null;
    }
    
    @Override
    public void addStepFolder(StepFolder stepFolder) {
       if(stepFolder != null) {
           stepFolderDAO.addStepFolder(stepFolder);
       }
    }
}
