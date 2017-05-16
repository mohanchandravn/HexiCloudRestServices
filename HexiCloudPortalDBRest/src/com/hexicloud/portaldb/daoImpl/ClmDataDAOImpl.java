/**
 * 
 */
package com.hexicloud.portaldb.daoImpl;

import com.hexicloud.portaldb.bean.ProvisionedService;
import com.hexicloud.portaldb.dao.ClmDataDAO;
import com.hexicloud.portaldb.resultextractor.ClmDataResultExtractor;
import com.hexicloud.portaldb.util.SqlQueryConstantsUtil;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

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
    public List<ProvisionedService> getClmData(String userId) {
        logger.info(" Begining of getClmData() ");

        @SuppressWarnings({ "unchecked", "rawtypes" })
        List<ProvisionedService> servicesList =
            (List<ProvisionedService>) jdbcTemplate.query(SqlQueryConstantsUtil.SQL_GET_CLM_DATA,
                                                          new Object[] { userId }, new ClmDataResultExtractor());
        logger.info("clmDataList size ===========> " + servicesList != null ? servicesList.size() : null);
        logger.info(" End of getClmData() ");
        return servicesList;
    }

    @Override
    public List<String> getServicesForUser(String userId) {
        logger.info(" Start of getServicesForUser() ");
        List<String> trimmedServiceList = new ArrayList<String>();
        String trimmedService = null;
        @SuppressWarnings({ "unchecked", "rawtypes" })
        List<String> servicesList =
            jdbcTemplate.queryForList(SqlQueryConstantsUtil.SQL_GET_SERVICES_FOR_USER, new Object[] { userId },
                                      String.class);
        for (String service : servicesList) {
            trimmedService = service.toUpperCase();
            trimmedService = trimmedService.replaceAll(" ", "");
            trimmedService = trimmedService.replace("-IAAS", "");
            trimmedServiceList.add(trimmedService);
        }

        logger.info("getServicesForUser size ===========> " + servicesList != null ? servicesList.size() : null);
        logger.info(" End of getServicesForUser() ");
        return trimmedServiceList;
    }
}
