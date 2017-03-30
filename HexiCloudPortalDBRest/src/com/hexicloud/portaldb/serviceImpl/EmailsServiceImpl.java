package com.hexicloud.portaldb.serviceImpl;

import com.hexicloud.portaldb.bean.CallBack;
import com.hexicloud.portaldb.bean.User;
import com.hexicloud.portaldb.bean.UserEmail;
import com.hexicloud.portaldb.dao.UserEmailsDAO;
import com.hexicloud.portaldb.service.EmailsService;

import java.math.BigDecimal;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import java.sql.SQLException;

import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import javax.naming.NamingException;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("emailsService")
public class EmailsServiceImpl implements EmailsService {
    private static final Logger logger = Logger.getLogger(EmailsServiceImpl.class);


    @Autowired
    UserEmailsDAO userEmailsDAO;

    @Override
    public List<UserEmail> getUserEmails(String userId, String isResolved, Number requestId, String searchCallBacks) {
        logger.info("*******  findDocsByStepId() of  service *****************");
        return userEmailsDAO.getUserEmails(userId, isResolved, requestId, searchCallBacks);
    }

    @Override
    public UserEmail saveUserEmail(UserEmail userEmail) {
        logger.info("*******  saveUserEmail() of  service *****************");
        return userEmailsDAO.saveUserEmail(userEmail);

    }

    @Override
    public void updateEmailResolution(UserEmail userEmail) {
        logger.info("*******  updateEmailResolution() of  service *****************");
        userEmailsDAO.updateResolution(userEmail);
    }

    @Override
    public String sendEmail(String sendTo, User user) throws SQLException, NamingException, NoSuchAlgorithmException,
                                                             NoSuchPaddingException, InvalidKeyException,
                                                             IllegalBlockSizeException, BadPaddingException {
        logger.info("Entering sendMail method");
        return userEmailsDAO.sendEmail(sendTo, user);

    }


    @Override
    public BigDecimal requestCallback(CallBack callBack) {
        logger.info("Entering requestCallback method");
        return userEmailsDAO.requestCallBack(callBack);

    }
}
