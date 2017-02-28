package com.hexicloud.portaldb.daoImpl;

import com.hexicloud.portaldb.bean.Step;
import com.hexicloud.portaldb.bean.StepFolder;
import com.hexicloud.portaldb.dao.StepFolderDAO;

import com.hexicloud.portaldb.util.SqlQueryConstantsUtil;

import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class StepFolderDAOImpl implements StepFolderDAO{
    
    private static final Logger logger = Logger.getLogger(StepFolderDAOImpl.class);
    private JdbcTemplate jdbcTemplate;
    private DataSource dataSource;
    
    @Override
    public List<StepFolder> retrieveStepFolderDetails(String stepId) {
        @SuppressWarnings("unchecked")
        List<StepFolder> listOfSteFolders =  jdbcTemplate.query(SqlQueryConstantsUtil.SQL_FIND_STEP_FOLDERS_BY_STEP_ID,  new Object[] { stepId },
                                                                new BeanPropertyRowMapper(StepFolder.class));
        return listOfSteFolders;
    }
    
    @Override
    public void addStepFolder(StepFolder stepFolder) {
        jdbcTemplate.update(SqlQueryConstantsUtil.SQL_CREATE_STEP_FOLDER, new Object[] {stepFolder.getStepId(),
                                                                                        stepFolder.getStepCode(),
                                                                                        stepFolder.getSubStepCode(),
                                                                                        stepFolder.getFolderId()});
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        jdbcTemplate = new JdbcTemplate(this.dataSource);
    }

    public DataSource getDataSource() {
        return dataSource;
    }
}
