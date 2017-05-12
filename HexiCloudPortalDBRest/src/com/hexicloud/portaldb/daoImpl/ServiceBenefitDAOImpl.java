package com.hexicloud.portaldb.daoImpl;

import com.hexicloud.portaldb.bean.UsecaseOld;
import com.hexicloud.portaldb.dao.ServiceBenefitDAO;
import com.hexicloud.portaldb.util.SqlQueryConstantsUtil;

import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ServiceBenefitDAOImpl implements ServiceBenefitDAO {
    public ServiceBenefitDAOImpl() {
        super();
    }
    private static final Logger logger = Logger.getLogger(ServiceBenefitDAOImpl.class);
    private JdbcTemplate jdbcTemplate;
    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        jdbcTemplate = new JdbcTemplate(this.dataSource);
    }


    @Override
    public String getServiceBenefitFileID(String serviceName) {
        logger.info(" Begining of getServiceBenefitFileID() ");
        logger.info("serviceName from DAOIMPL " + serviceName);
        /*
        String fileId="";

        @SuppressWarnings({ "unchecked", "rawtypes" })
        List<ServiceBenefit> serviceBenefitsList =
            jdbcTemplate.query(SqlQueryConstantsUtil.SQL_FIND_SERVICE_BENEFIT_FILE_ID1,
                                            new Object[] { serviceName.toUpperCase() }, new BeanPropertyRowMapper(ServiceBenefit.class));

            System.out.println("serviceBenefitsList.size() "+serviceBenefitsList.size());
            if(serviceBenefitsList.size() >0){
                ServiceBenefit benefit = serviceBenefitsList.get(0);
                fileId = benefit.getFileId();
            }
*/

        String fileId = null;
        try {
            fileId =
                jdbcTemplate.queryForObject(SqlQueryConstantsUtil.SQL_FIND_SERVICE_BENEFIT_FILE_ID1,
                                            new Object[] { serviceName.toUpperCase() }, String.class);
        } catch (EmptyResultDataAccessException erdae) {
            logger.error("Did not get any result while expecting a single row, returning null" + erdae);
            return fileId;
        }
        logger.info("fileId from DAOIMPL " + fileId);
        logger.info(" End of getServiceBenefitFileID() ");
        return fileId;
    }

    @Override
    public List<UsecaseOld> getUsecases() {
        logger.info("getUsecases from DAOIMPL ");
        @SuppressWarnings({ "unchecked", "rawtypes" })
        List<UsecaseOld> usecaseList =
            jdbcTemplate.query(SqlQueryConstantsUtil.SQL_GET_ALL_USECASES, new BeanPropertyRowMapper(UsecaseOld.class));
        return usecaseList;
    }

    @Override
    public String getUsecaseFileID(String usecaseCode) {
        logger.info(" Begining of getUsecaseFileID() ");
        logger.info("serviceName from DAOIMPL " + usecaseCode);
        String fileId = null;
        try {

            fileId =
                jdbcTemplate.queryForObject(SqlQueryConstantsUtil.SQL_FIND_USECASE_FILE_ID,
                                            new Object[] { usecaseCode.toUpperCase() }, String.class);
        } catch (EmptyResultDataAccessException erdae) {
            logger.error("Did not get any result while expecting a single row, returning null" + erdae);
            return fileId;
        }
        logger.info("fileId from DAOIMPL " + fileId);
        logger.info(" End of getUsecaseFileID() ");
        return fileId;
    }
}
