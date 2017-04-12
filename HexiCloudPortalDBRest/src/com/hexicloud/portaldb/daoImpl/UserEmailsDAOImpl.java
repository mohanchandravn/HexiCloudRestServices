package com.hexicloud.portaldb.daoImpl;

import com.hexicloud.portaldb.bean.CallBack;
import com.hexicloud.portaldb.bean.RuleConfiguration;
import com.hexicloud.portaldb.bean.User;
import com.hexicloud.portaldb.bean.UserEmail;
import com.hexicloud.portaldb.dao.UserEmailsDAO;
import com.hexicloud.portaldb.util.SqlQueryConstantsUtil;
import com.hexicloud.portaldb.util.encryption.EncryptionUtil;

import java.math.BigDecimal;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import java.sql.SQLException;

import java.util.List;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import javax.naming.NamingException;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;


@Repository
public class UserEmailsDAOImpl implements UserEmailsDAO {
    private static final Logger logger = Logger.getLogger(UserEmailsDAOImpl.class);
    private JdbcTemplate jdbcTemplate;
    private DataSource dataSource;
    private SimpleJdbcCall saveUserEmailPrc;
    private SimpleJdbcCall requestCallbackPrc;
    private static String FORGOT_PASSWORD_EMAIL_TEMPLATE_KEY = "FORGOT_PASSWORD_EMAIL_TEMPLATE";
    private static String FORGOT_PASSWORD_EMAIL_SUBJECT_KEY = "FORGOT_PASSWORD_EMAIL_SUBJECT";
//    private static String EMAIL_ACCOUND_ADMIN = "metcs-cloud.admin@oracleads.com";

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        jdbcTemplate = new JdbcTemplate(this.dataSource);
        this.saveUserEmailPrc =
            new SimpleJdbcCall(dataSource).withCatalogName("PKG_EMAIL").withProcedureName("PRC_SAVE_USER_EMAIL");
        this.requestCallbackPrc =
            new SimpleJdbcCall(dataSource).withCatalogName("PKG_EMAIL").withProcedureName("PRC_REQUEST_CALL_BACK");
    }

    @Override
    public List<UserEmail> getUserEmails(String userId, String isResolved, Number requestId, String searchCallBacks) {
        logger.info(" Begining of getUserEmails() ");
        StringBuilder whereClause = new StringBuilder();
        String query = SqlQueryConstantsUtil.SQL_FIND_USER_EMAILS;
        if (null != userId) {
            whereClause.append(" WHERE USER_ID = '" + userId + "'");
        }
        if (null != isResolved && isResolved != "A") {
            if (whereClause.length() > 0) {
                whereClause.append(" AND IS_RESOLVED = ");
                whereClause.append(isResolved.equalsIgnoreCase("Y") ? "1" : "0");

            } else {
                whereClause.append(" WHERE IS_RESOLVED = " + (isResolved.equalsIgnoreCase("Y") ? "1" : "0"));
            }
        }
        if (null != requestId) {
            if (whereClause.length() > 0) {
                whereClause.append(" AND SR_ID = " + requestId.toString());

            } else {
                whereClause.append(" WHERE SR_ID = " + requestId.toString());
            }
        }

        if (null != searchCallBacks && "Y".equalsIgnoreCase(searchCallBacks)) {
        if (whereClause.length() > 0) {
                whereClause.append(" AND CALL_BACK_REQUEST = 'Y'");

            } else {
                whereClause.append(" WHERE CALL_BACK_REQUEST = 'Y'");
            }
        }

        if (whereClause.length() > 0) {
            query = query.concat(whereClause.toString());
            query = query.concat(" ORDER BY SR_ID ASC");
        }
        @SuppressWarnings({ "unchecked", "rawtypes" })
        List<UserEmail> userEmailsList = jdbcTemplate.query(query, new BeanPropertyRowMapper(UserEmail.class));

        logger.info("getUserEmails size ===========> " + userEmailsList != null ? userEmailsList.size() : null);
        logger.info(" End of getUserEmails() ");
        return userEmailsList;
    }

    @Override
    public UserEmail saveUserEmail(UserEmail userEmail) {
        logger.info(" Begining of saveUserEmail() ");
        SqlParameterSource inParamsMap = new MapSqlParameterSource().addValue("IN_USER_ID", userEmail.getUserId())
                                                                    .addValue("IN_SUBJECT", userEmail.getSubject())
                                                                    .addValue("IN_MESSAGE", userEmail.getMessage())
                                                                    .addValue("IN_SENT_TO", userEmail.getSentTo())
                                                                    .addValue("IN_SENT_CC", userEmail.getSentCC())
                                                                    .addValue("IN_SENT_BCC", userEmail.getSentBCC());

        Map<String, Object> out = saveUserEmailPrc.execute(inParamsMap);
        userEmail.setSrId(((BigDecimal) out.get("OUT_SR_ID")).intValue());
        logger.info(" End of saveUserEmail() ");
        return userEmail;
    }

    @Override
    public void updateResolution(UserEmail userEmail) {
        logger.info(" Begining of updateResolution() ");

        jdbcTemplate.update(SqlQueryConstantsUtil.SQL_UPDATE_EMAIL_RESOLUTION,
                            new Object[] { userEmail.isIsResolved(), userEmail.getResolutionComments(),
                                           userEmail.getSrId() });
        logger.info(" End of updateResolution() ");

    }


    private RuleConfiguration getEmailRule(String ruleKey) {
        logger.info(" Begining of getEmailContent() ");
        @SuppressWarnings("unchecked")
        List<RuleConfiguration> rulesList =
            jdbcTemplate.query(SqlQueryConstantsUtil.SQL_RULE_CONFIGURATION, new Object[] { ruleKey },
                               new BeanPropertyRowMapper(RuleConfiguration.class));


        if (rulesList != null && !(rulesList.isEmpty())) {
            if (rulesList.size() == 1) {
                return rulesList.get(0);
}
        }
        logger.info(" End of getEmailContent() ");
        return null;
    }
    
    private RuleConfiguration getFromAddress(String ruleKey) {
        logger.info(" Begining of getFromAddress() ");
        @SuppressWarnings("unchecked")
        List<RuleConfiguration> rulesList =
            jdbcTemplate.query(SqlQueryConstantsUtil.SQL_RULE_CONFIGURATION, new Object[] { ruleKey },
                               new BeanPropertyRowMapper(RuleConfiguration.class));


        if (rulesList != null && !(rulesList.isEmpty())) {
            if (rulesList.size() == 1) {
                return rulesList.get(0);
    }
        }
        logger.info(" End of getFromAddress() ");
        return null;
    }


    @Override
    public String sendEmail(String sendTo, User user) throws SQLException, NamingException, NoSuchAlgorithmException,
                                                             NoSuchPaddingException, InvalidKeyException,
                                                             IllegalBlockSizeException, BadPaddingException {

        String result = null;
        if (user != null && this.dataSource != null) {
            SimpleJdbcCall sendEmailPrc = new SimpleJdbcCall(this.dataSource).withProcedureName("SEND_EMAIL");
            String decodedPassword = EncryptionUtil.decryptString(user.getPassword());
            String emailSubject = null;
            String emailContent = null;

            RuleConfiguration emailSubjectRule = this.getEmailRule(FORGOT_PASSWORD_EMAIL_SUBJECT_KEY);
            RuleConfiguration emailContentRule = this.getEmailRule(FORGOT_PASSWORD_EMAIL_TEMPLATE_KEY);
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
            SqlParameterSource inParamsMap = new MapSqlParameterSource().addValue("from_email_address", getFromAddress("PORTAL_NOTIFICATION_EMAIL_ID").getRuleValue())
                                                                        .addValue("to_email_address", sendTo)
                                                                        .addValue("email_subject", emailSubject)
                                                                        .addValue("email_body", emailContent)
                                                                        .addValue("cc_email_address", "");
            Map<String, Object> out = sendEmailPrc.execute(inParamsMap);
            if (out != null && !(out.isEmpty())) {
                result = (String) out.get("OUT_SR_ID");
            }


        }


        return result;
    }


    @Override
    public BigDecimal requestCallBack(CallBack callBack) {
        logger.info(" Begining of requestCallBack() ");
        SqlParameterSource inParamsMap = new MapSqlParameterSource().addValue("IN_USER_ID", callBack.getUserId())
                                                                    .addValue("IN_PHONE", callBack.getPhone())
                                                                    .addValue("IN_MESSAGE", callBack.getMessage());

        Map<String, Object> out = requestCallbackPrc.execute(inParamsMap);
        BigDecimal requestId = (BigDecimal) out.get("OUT_SR_ID");
        logger.info(" End of requestCallBack() ");
        return requestId;
    }
}
