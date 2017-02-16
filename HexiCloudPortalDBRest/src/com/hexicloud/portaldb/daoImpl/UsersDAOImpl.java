package com.hexicloud.portaldb.daoImpl;

import com.hexicloud.portaldb.bean.User;
import com.hexicloud.portaldb.dao.UsersDAO;
import com.hexicloud.portaldb.util.SqlQueryConstantsUtil;

import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UsersDAOImpl implements UsersDAO {
    private static final Logger logger = Logger.getLogger(UsersDAOImpl.class);
    private JdbcTemplate jdbcTemplate;
    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        jdbcTemplate = new JdbcTemplate(this.dataSource);
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
        logger.info(" Begining of updateLastLoggedIn() ");
    }
}
