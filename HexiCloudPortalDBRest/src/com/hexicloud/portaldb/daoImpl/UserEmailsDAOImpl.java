package com.hexicloud.portaldb.daoImpl;

import java.math.BigDecimal;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import com.hexicloud.portaldb.bean.UserEmail;
import com.hexicloud.portaldb.dao.UserEmailsDAO;
import com.hexicloud.portaldb.util.SqlQueryConstantsUtil;

@Repository
public class UserEmailsDAOImpl implements UserEmailsDAO {
    private static final Logger logger = Logger.getLogger(UserEmailsDAOImpl.class);
    private JdbcTemplate jdbcTemplate;
    private DataSource dataSource;
    private SimpleJdbcCall saveUserEmailPrc;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        jdbcTemplate = new JdbcTemplate(this.dataSource);
        this.saveUserEmailPrc =
            new SimpleJdbcCall(dataSource).withCatalogName("PKG_EMAIL").withProcedureName("PRC_SAVE_USER_EMAIL");
    }

    @Override
    public List<UserEmail> getUserEmails(String userId, String isResolved, Number requestId) {
        logger.info(" Begining of getUserEmails() ");
        StringBuilder whereClause = new StringBuilder();
        String query = SqlQueryConstantsUtil.SQL_FIND_USER_EMAILS;
        if (null != userId) {
            whereClause.append(" WHERE USER_ID = '" + userId + "'");
        }
        if (null != isResolved && isResolved != "A") {
            if (whereClause.length() > 0) {
                whereClause.append(" AND IS_RESOLVED = ");
                whereClause.append(isResolved == "Y" ? "1" : "0");

            } else {
                whereClause.append(" WHERE IS_RESOLVED = " + isResolved == "Y" ? "1" : "0");
            }
        }
        if (null != requestId) {
            if (whereClause.length() > 0) {
                whereClause.append(" AND SR_ID = " + requestId.toString());

            } else {
                whereClause.append(" WHERE SR_ID = " + requestId.toString());
            }
        }

        if (whereClause.length() > 0) {
            query = query.concat(whereClause.toString());
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

        // SQL_SAVE_USER_EMAIL = "INSERT INTO USER_EMAILS (USER_ID, SUBJECT,
        // MESSAGE, CREATED_DATE, SENT_TO,SENT_CC,SENT_BCC) VALUES
        // (?,?,?,SYSDATE,?,?,?)";

        // jdbcTemplate.update(SqlQueryConstantsUtil.SQL_SAVE_USER_EMAIL,
        // new Object[] { userEmail.getUserId(), userEmail.getSubject(),
        // userEmail.getMessage(),
        // userEmail.getSentTo(), userEmail.getSentCC(), userEmail.getSentBCC()
        // });

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
                            new Object[] { userEmail.isResolved(), userEmail.getResolutionComments(),userEmail.getSrId()});
        logger.info(" End of updateResolution() ");

    }
}
