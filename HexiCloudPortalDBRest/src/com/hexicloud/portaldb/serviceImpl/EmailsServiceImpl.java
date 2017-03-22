package com.hexicloud.portaldb.serviceImpl;

import com.hexicloud.portaldb.bean.RuleConfiguration;
import com.hexicloud.portaldb.bean.User;
import com.hexicloud.portaldb.bean.UserEmail;
import com.hexicloud.portaldb.dao.UserEmailsDAO;
import com.hexicloud.portaldb.service.EmailsService;
import com.hexicloud.portaldb.util.encryption.EncryptionUtil;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
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
    private static String CONNECTION_URL =
        "jdbc:oracle:thin:@129.152.129.164:1521/PDB1.usoracleam82569.oraclecloud.internal";
    private static String DB_USER = "hexicloud";
    private static String DB_USER_PWD = "hexi123";
    private static String FORGOT_PASSWORD_EMAIL_TEMPLATE_KEY = "FORGOT_PASSWORD_EMAIL_TEMPLATE";
     private static String FORGOT_PASSWORD_EMAIL_SUBJECT_KEY = "FORGOT_PASSWORD_EMAIL_SUBJECT";


    @Autowired
    UserEmailsDAO userEmailsDAO;

    @Override
    public List<UserEmail> getUserEmails(String userId, String isResolved, Number requestId) {
        logger.info("*******  findDocsByStepId() of  service *****************");
        return userEmailsDAO.getUserEmails(userId, isResolved, requestId);
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
        CallableStatement callableStatement = null;
        String result = null;
        Connection conn = null;
    
        String sendEmailProc = "{call send_email(?,?,?,?,?)}";
    
        try {
            if (user != null) {
                String decodedPassword = EncryptionUtil.decryptString(user.getPassword());
                String emailSubject = null;
                String emailContent = null;
    
                RuleConfiguration emailSubjectRule = userEmailsDAO.getEmailRule(FORGOT_PASSWORD_EMAIL_SUBJECT_KEY);
                RuleConfiguration emailContentRule = userEmailsDAO.getEmailRule(FORGOT_PASSWORD_EMAIL_TEMPLATE_KEY);
                if (emailSubjectRule == null) {
                    emailSubject = "Password Details";
                    logger.error("no rule is not configured for forgot password email subject and hence subject is described by application");
                } else {
                    emailSubject = emailSubjectRule.getRuleValue();
                }
                if (emailContentRule == null) {
                    logger.error("rule is not configured for forgot password email content hence content is described by application");
                    emailContent = "The requested password for user id " + user.getUserId() + " is " + decodedPassword;
                } else {
                    emailContent = emailContentRule.getRuleValue();
                    emailContent = emailContent.replaceAll("<<USER_ID>>", user.getUserId());
                    emailContent = emailContent.replaceAll("<<PASSWORD>>", decodedPassword);
                }
    
                conn = this.getDBConnection();
                callableStatement = conn.prepareCall(sendEmailProc);
    
                callableStatement.setString(1, "metcs-cloud.admin@oracleads.com");
                callableStatement.setString(2, sendTo);
                callableStatement.setString(3, emailSubject);
                callableStatement.setString(4, emailContent);
    
                callableStatement.registerOutParameter(5, java.sql
                                                              .Types
                                                              .VARCHAR);
    
                // execute procedure
                callableStatement.executeUpdate();
    
                result = callableStatement.getString(5);
                logger.info("Mail sent successfully " + result);
    
    
            }
    
        } finally {
    
            if (callableStatement != null) {
                callableStatement.close();
            }
            if (conn != null) {
                conn.close();
            }
    
    
        }
        return result;
    
    }
    
    /* need to change the below code to data source to get DB connection instance*/
    private Connection getDBConnection() throws SQLException, NamingException {
    
        Connection dbConnection = null;
    
        DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
        dbConnection = DriverManager.getConnection(CONNECTION_URL, DB_USER, DB_USER_PWD);
        logger.info("dbConnection created successfully");
        return dbConnection;
    
    
    }
}
