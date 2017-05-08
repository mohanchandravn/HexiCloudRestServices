package com.hexicloud.portaldb.daoImpl;

import com.hexicloud.portaldb.dao.EmailUtilDAO;

import java.util.Map;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;


@Repository
public class EmailUtilDAOImpl implements EmailUtilDAO {
    private static final Logger logger = Logger.getLogger(EmailUtilDAOImpl.class);
    private JdbcTemplate jdbcTemplate;
    private DataSource dataSource;
    private SimpleJdbcCall sendEmailToCSCPrc;


    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        jdbcTemplate = new JdbcTemplate(this.dataSource);
        this.sendEmailToCSCPrc =
            new SimpleJdbcCall(dataSource).withCatalogName("PKG_EMAIL").withProcedureName("PRC_EMAIL_CSM_MAILING_LIST");

    }

    @Override
    public String sendEmailToCSC(String subject, String emailBody) {
        logger.info(" Begining of sendEmailToCSC() ");
        SqlParameterSource inParamsMap = new MapSqlParameterSource().addValue("IN_SUBJECT", subject)
                                                                    .addValue("IN_MESSAGE", emailBody)
                                                                    .addValue("OUT_EMAIL_SUCCESS", "N");
        Map<String, Object> out = sendEmailToCSCPrc.execute(inParamsMap);
        String emailSuccess = (String) out.get("OUT_EMAIL_SUCCESS");
        logger.info(" End of sendEmailToCSC() ");
        return emailSuccess;
    }
}
