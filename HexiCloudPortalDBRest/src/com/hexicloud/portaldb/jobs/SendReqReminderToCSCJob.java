package com.hexicloud.portaldb.jobs;

import com.hexicloud.portaldb.quartz.BaseJob;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import org.springframework.stereotype.Component;

@Component
public class SendReqReminderToCSCJob extends BaseJob implements Job {

    private static final Logger logger = Logger.getLogger(SendReqReminderToCSCJob.class);

    //    @Autowired
    //    JobConfigurationDAO jobConfigurationDAO;


    @Override
    public void execute(JobExecutionContext jec) throws JobExecutionException {
        logger.info(" Start of execute in SendReqReminderToCSCJob : " + jec.getJobDetail()
                                                                           .getKey()
                                                                           .getName());
        Context initContext;
        CallableStatement stmt = null;
        DataSource ds;
        Connection conn = null;
        try {
            /*Adding the data source code here for now, should be moved to any util class if
            going ahead with datasource, also the job history updation is not changed to datasource and invoked,
            else if using Spring scheduler, this code should be removed. */
            initContext = new InitialContext();
            ds = (DataSource) initContext.lookup("jdbc/HexiCloudDS");
            conn = ds.getConnection();
            logger.info("Conn : " + conn);
            stmt = conn.prepareCall("{ call PKG_RULES_ENGINE.PRC_SEND_REMINDER_TO_CSM(?) }");
            stmt.setString(1, jec.getJobDetail()
                                 .getKey()
                                 .getName());
            stmt.execute();

            //            jobConfigurationDAO.runCSCRequestReminderJob(jec.getJobDetail()
            //                                                            .getKey()
            //                                                            .getName());
        } catch (NamingException e) {
            logger.error("Naming exception : " + e.getMessage(), e);
        } catch (SQLException e) {
            logger.error("SQL exception : " + e.getMessage(), e);
        } catch (Exception e) {
            logger.error("Exception : " + e.getMessage(), e);
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                logger.error("SQL exception when closing connection : " + e.getMessage(), e);
            }
        }
        logger.info(" End of execute in SendReqReminderToCSCJob");
    }
}
