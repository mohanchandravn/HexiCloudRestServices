package com.hexicloud.portaldb.serviceImpl;

import java.util.List;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexicloud.portaldb.bean.UserEmail;
import com.hexicloud.portaldb.dao.UserEmailsDAO;
import com.hexicloud.portaldb.service.EmailsService;

@Service("emailsService")
public class EmailsServiceImpl implements EmailsService {
    private static final Logger logger = Logger.getLogger(EmailsServiceImpl.class);

    @Autowired
    UserEmailsDAO userEmailsDAO;

    @Override
    public List<UserEmail> getUserEmails(String userId) {
        logger.info("*******  findDocsByStepId() of  service *****************");
        return userEmailsDAO.getUserEmails(userId);
    }

    @Override
    public UserEmail saveUserEmail(UserEmail userEmail) {
        logger.info("*******  saveUserEmail() of  service *****************");
        return userEmailsDAO.saveUserEmail(userEmail);

    }

}
