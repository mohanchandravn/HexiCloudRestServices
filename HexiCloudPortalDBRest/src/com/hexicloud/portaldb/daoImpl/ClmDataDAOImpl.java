/**
 * 
 */
package com.hexicloud.portaldb.daoImpl;

import com.hexicloud.portaldb.bean.ClmData;
import com.hexicloud.portaldb.dao.ClmDataDAO;
import com.hexicloud.portaldb.util.SqlQueryConstantsUtil;

import java.math.BigDecimal;

import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * @author mohchand
 *
 */
@Repository
public class ClmDataDAOImpl implements ClmDataDAO {


    private static final Logger logger = Logger.getLogger(ClmDataDAOImpl.class);
    private JdbcTemplate jdbcTemplate;
    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        jdbcTemplate = new JdbcTemplate(this.dataSource);
    }

    @Override
    public List<ClmData> getClmData(BigDecimal registryId) {
        logger.info(" Begining of getClmData() ");

        @SuppressWarnings({ "unchecked", "rawtypes" })
        List<ClmData> clmDataList =
            jdbcTemplate.query(SqlQueryConstantsUtil.SQL_GET_CLM_DATA, new Object[] { registryId },
                               new BeanPropertyRowMapper(ClmData.class));

        logger.info("clmDataList size ===========> " + clmDataList != null ? clmDataList.size() : null);
        logger.info(" End of getClmData() ");
        return clmDataList;
    }
}
