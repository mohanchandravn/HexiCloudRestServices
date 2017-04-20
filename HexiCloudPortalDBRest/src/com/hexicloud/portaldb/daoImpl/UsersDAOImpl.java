package com.hexicloud.portaldb.daoImpl;

import com.hexicloud.portaldb.bean.AuthUser;
import com.hexicloud.portaldb.bean.CustomerRegistry;
import com.hexicloud.portaldb.bean.User;
import com.hexicloud.portaldb.dao.UsersDAO;
import com.hexicloud.portaldb.util.SqlQueryConstantsUtil;
import com.hexicloud.portaldb.util.encryption.EncryptionUtil;

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
import org.springframework.util.StringUtils;

@Repository
public class UsersDAOImpl implements UsersDAO {
    private static final Logger logger = Logger.getLogger(UsersDAOImpl.class);
    private JdbcTemplate jdbcTemplate;
    private DataSource dataSource;
    private SimpleJdbcCall sendWelcomeEmail;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        jdbcTemplate = new JdbcTemplate(this.dataSource);
        this.sendWelcomeEmail =
            new SimpleJdbcCall(dataSource).withCatalogName("PKG_EMAIL").withProcedureName("PRC_SEND_WELCOME_EMAIL");
    }


    @Override
    public User getUser(String userId) {
        logger.info(" Begining of getUser() ");

        @SuppressWarnings({ "unchecked", "rawtypes" })
        List<User> users =
            jdbcTemplate.query(SqlQueryConstantsUtil.SQL_GET_USER, new Object[] { userId },
                               new BeanPropertyRowMapper(User.class));

        logger.info("users size ===========> " + users != null ? users.size() : null);
        logger.info(" End of getUser() ");
        return !users.isEmpty() ? users.get(0) : null;
    }

    @Override
    public void updateLastLoggedIn(String userId) {
        logger.info(" Begining of updateLastLoggedIn() ");
        jdbcTemplate.update(SqlQueryConstantsUtil.SQL_UPDATE_USER_LAST_LOGGED_IN, new Object[] { userId });
        logger.info(" Ending of updateLastLoggedIn() ");
    }

    @Override
    public String createUser(User user) throws Exception {
        logger.info(" Begining of createUser() ");
        try {
            jdbcTemplate.update(SqlQueryConstantsUtil.SQL_CREATE_USER,
                                new Object[] { user.getUserId().trim(),
                                               EncryptionUtil.encryptString(user.getPassword()), user.getEmail().trim(),
                                               user.getUserRole(), user.getFirstName(), user.getLastName(),
                                               user.getRegistryId(), user.getPhone()
                                                                         .trim()
                                                                         .equals("-") ? "" : user.getPhone().trim() });
        } catch (Exception ex) {
            logger.error("Encryption failed or user creation failed" + ex);
            throw ex;
        }
        logger.info(" End of createUser() ");
        return user.getPassword();
    }

    public String sendWelcomeEmail(String userId, String password, String firstName, String userEmail) {
        logger.info(" Start of sendWelcomeEmail() ");
        SqlParameterSource inParamsMap = new MapSqlParameterSource().addValue("IN_USER_ID", userId)
                                                                    .addValue("IN_PASSWORD", password)
                                                                    .addValue("IN_FIRST_NAME", firstName)
                                                                    .addValue("IN_USER_EMAIL", userEmail);
        Map<String, Object> out = sendWelcomeEmail.execute(inParamsMap);

        logger.info(" End of sendWelcomeEmail() ");
        return (String) out.get("OUT_SR_ID");
    }

    @Override
    public void updatePassword(User user) throws Exception {
        logger.info(" Begining of updatePassword() ");
        try {
            jdbcTemplate.update(SqlQueryConstantsUtil.SQL_UPDATE_USER_PASSWORD,
                                new Object[] { EncryptionUtil.encryptString(user.getPassword()), user.getUserId() });
        } catch (Exception ex) {
            logger.error("Encryption failed or password updated failed : " + ex);
            throw ex;
        }
        logger.info(" Ending of updatePassword() ");
    }

    @Override
    public boolean checkExistingUser(String userId) {
        logger.info(" Begining of checkExistingUser() ");

        @SuppressWarnings({ "unchecked", "rawtypes" })
        List<User> users =
            jdbcTemplate.query(SqlQueryConstantsUtil.SQL_CHECK_USER_ID_EXISTS, new Object[] { userId.trim() },
                               new BeanPropertyRowMapper(User.class));

        logger.info("users size ===========> " + users != null ? users.size() : null);
        logger.info(" End of checkExistingUser() ");
        return users.isEmpty();
    }

    @Override
    public AuthUser getUserDetailsForAuthentication(String userId) {
        logger.info(" Begining of getUser() ");

        @SuppressWarnings({ "unchecked", "rawtypes" })
        List<AuthUser> users =
            jdbcTemplate.query(SqlQueryConstantsUtil.SQL_GET_USER_FOR_AUTHENTICATION, new Object[] { userId },
                               new BeanPropertyRowMapper(AuthUser.class));

        logger.info("users size ===========> " + users != null ? users.size() : null);
        logger.info(" End of getUser() ");
        return !users.isEmpty() ? users.get(0) : null;
    }

    @Override
    public List<CustomerRegistry> getCustomerRegistryForLov() {
        logger.info(" Begining of getCustomerRegistryForLov() ");

        @SuppressWarnings({ "unchecked", "rawtypes" })
        List<CustomerRegistry> customerRegistries =
            jdbcTemplate.query(SqlQueryConstantsUtil.SQL_GET_CUSTOMER_REGISTRY_DATA,
                               new BeanPropertyRowMapper(CustomerRegistry.class));
        //        String customerRegistry = null;
        //        for (CustomerRegistry registry : customerRegistries) {
        //            if(registry.getCustomerNameTransl().length() > 50) {
        ////                customerRegistry = registry.getCustomerRegistry().substring(0, registry.getCustomerRegistry().lastIndexOf(" - "));
        //                customerRegistry = registry.getCustomerNameTransl().substring(0, 50).concat("** - ").concat(registry.getRegistryId());
        //                registry.setCustomerRegistry(customerRegistry);
        //            }
        //        }

        logger.info("customerRegistries size ===========> " + customerRegistries != null ? customerRegistries.size() :
                    null);
        logger.info(" End of getCustomerRegistryForLov() ");
        return customerRegistries;
    }

    @Override
    public List<User> searchUserDetails(String userId, String emailId, String customerId) {
        logger.info("Entering method searchUserDetails");
        String query = SqlQueryConstantsUtil.SQL_USER_QUERY;
        StringBuilder whereClause = new StringBuilder();
        whereClause.append(" WHERE AUTHORITY = 'ROLE_USER'");
        if (!(StringUtils.isEmpty(userId))) {
            //            whereClause.append(" WHERE USER_ID = '" + userId + "'");
            whereClause.append(" AND USER_ID = '" + userId + "'");
        }
        if (!(StringUtils.isEmpty(emailId))) {
            //            if (whereClause.length() > 0) {
            whereClause.append(" AND EMAIL = '" + emailId + "'");
            //
            //            } else {
            //                whereClause.append(" WHERE EMAIL = '"+ emailId + "'");
            //            }
        }
        if (!(StringUtils.isEmpty(customerId))) {
            //            if (whereClause.length() > 0) {
            whereClause.append(" AND REGISTRY_ID = '" + customerId + "'");
            //
            //            } else {
            //                whereClause.append(" WHERE REGISTRY_ID = '" +customerId + "'" );
            //            }
        }

        if (whereClause.length() > 0) {
            query = query.concat(whereClause.toString());
        }
        List<User> usersList = jdbcTemplate.query(query, new BeanPropertyRowMapper(User.class));

        logger.info("searchUserDetails ->users size ===========> " + usersList != null ? usersList.size() : null);
        logger.info("Exiting method searchUserDetails");
        return usersList;
    }

    @Override
    public void updateUser(User user) {
        logger.info("Entering method updateUser()");
        if (user != null) {
            jdbcTemplate.update(SqlQueryConstantsUtil.SQL_UPDATE_USER_QUERY,
                                new Object[] { user.getEmail().trim(), user.getUserRole(), user.getFirstName(),
                                               user.getLastName(), user.getRegistryId(), user.getPhone().trim().equals("-") ? "" : user.getPhone().trim(),
                                               user.getUserId() });
        }
        logger.info("Exiting method updateUser()");
    }
}
