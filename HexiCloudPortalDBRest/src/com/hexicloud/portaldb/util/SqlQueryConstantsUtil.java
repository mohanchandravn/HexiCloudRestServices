package com.hexicloud.portaldb.util;


public class SqlQueryConstantsUtil {

    public static final String SQL_FIND_APPLICATION_STEPS =
        "SELECT S.STEP_ID, S.STEP_CODE, S.DESCRIPTION, S.IS_ROLE_SELELCTION, S.IS_DECISION_MAKING, S.NON_REDIRECT_STEP, S.STEP_LABEL, S.HAS_SUB_STEP, SS.SUB_STEP_CODE, SS.SUB_STEP_LABEL, SS.SUB_STEP_DESC FROM STEPS S, SUB_STEPS SS WHERE S.STEP_ID = SS.STEP_ID(+)";
    public static final String SQL_FIND_STEP_DOCS_BY_STEP_ID =
        "SELECT STEP_ID, STEP_CODE, DOC_TYPE, DOC_TYPE_EXTN, DOC_FILE_ID, DOC_META_DATA, FILE_NAME, PUBLIC_LINK_ID FROM STEP_DOCUMENTS WHERE STEP_ID = ?";

    public static final String SQL_FIND_STEP_DOCS_BY_STEP_CODE =
        "SELECT STEP_ID, STEP_CODE, SUB_STEP_CODE, DOC_TYPE, DOC_TYPE_EXTN, DOC_FILE_ID, DOC_META_DATA, FILE_NAME, PUBLIC_LINK_ID, APP_LINK_URL, ACCESS_TOKEN, REFRESH_TOKEN, APP_LINK_ID, DOC_CS_ROLE, DISPLAY_ORDER, DISPLAY_LABEL  FROM STEP_DOCUMENTS WHERE STEP_CODE = ? AND SUB_STEP_CODE IS NULL ORDER BY DISPLAY_ORDER";

    public static final String SQL_FIND_STEP_DOCS_BY_STEP_CODE_AND_SUB_STEP =
        "SELECT STEP_ID, STEP_CODE, SUB_STEP_CODE, DOC_TYPE, DOC_TYPE_EXTN, DOC_FILE_ID, DOC_META_DATA, FILE_NAME, PUBLIC_LINK_ID, APP_LINK_URL, ACCESS_TOKEN, REFRESH_TOKEN, APP_LINK_ID, DOC_CS_ROLE, DISPLAY_ORDER, DISPLAY_LABEL  FROM STEP_DOCUMENTS WHERE STEP_CODE = ? AND SUB_STEP_CODE = ?";
    public static final String SQL_ADD_DOC_BY_STEP_ID =
        "INSERT INTO STEP_DOCUMENTS (STEP_ID, STEP_CODE, DOC_TYPE, DOC_TYPE_EXTN, DOC_FILE_ID, DOC_META_DATA, FILE_NAME, PUBLIC_LINK_ID, " +
        "SUB_STEP_CODE, APP_LINK_URL, ACCESS_TOKEN, REFRESH_TOKEN, APP_LINK_ID, DISPLAY_ORDER, DISPLAY_LABEL, TOKEN_CREATED_DATE, TOKEN_UPDATED_DATE, DOC_CREATION_DATE, DOC_UPDATION_DATE) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, SYSDATE, SYSDATE, SYSDATE, SYSDATE)";
    public static final String SQL_FIND_USER_CURRENT_STEP =
        "SELECT USER_ID, USER_ROLE, CUR_STEP_ID, CUR_STEP_CODE, PRE_STEP_ID, PRE_STEP_CODE, CREATED_DATE, UPDATED_DATE FROM USER_STEPS WHERE USER_ID = ?";
    public static final String SQL_CREATE_USER_STEP =
        "INSERT INTO USER_STEPS (USER_ID, USER_ROLE, CUR_STEP_ID, CUR_STEP_CODE, PRE_STEP_ID, PRE_STEP_CODE, CREATED_DATE, UPDATED_DATE) VALUES (?, ?, ?, ?, ?, ?, SYSDATE, SYSDATE)";
    public static final String SQL_UPDATE_USER_STEP =
        "UPDATE USER_STEPS SET USER_ROLE = ?, CUR_STEP_ID = ?, CUR_STEP_CODE = ?, PRE_STEP_ID = ?, PRE_STEP_CODE = ?, UPDATED_DATE = SYSDATE WHERE USER_ID = ?";
    public static final String SQL_FIND_USER_EMAILS =
        "SELECT SR_ID, USER_ID, SUBJECT, MESSAGE, CREATED_DATE, SENT_TO, SENT_CC, SENT_BCC, IS_RESOLVED, CSM_MAIL_COUNT, RESOLUTION_COMMENTS FROM USER_EMAILS";
    public static final String SQL_SAVE_USER_EMAIL =
        "INSERT INTO USER_EMAILS (USER_ID, SUBJECT, MESSAGE, CREATED_DATE, SENT_TO,SENT_CC,SENT_BCC) VALUES (?,?,?,SYSDATE,?,?,?)";
    public static final String SQL_GET_USER =
        "SELECT USER_ID, PASSWORD, EMAIL, USER_ROLE, FIRST_NAME, LAST_NAME, ACTIVE, PWD_LAST_CHANGED, LAST_LOGGED_IN, REGISTRY_ID, PHONE FROM USERS WHERE USER_ID = ?";
    public static final String SQL_UPDATE_USER_LAST_LOGGED_IN =
        "UPDATE USERS SET LAST_LOGGED_IN = SYSDATE WHERE USER_ID = ?";
    //    public static final String SQL_GET_CLM_DATA =
    //        "SELECT REGISTRY_ID, CUSTOMER_NAME, COUNTRY, DIVISION, CLUSTER_NAME, REGION, SUBSCRIPTION_ID, SUBSCRIPTION_PLAN_NUMBER, CONTRACT_NUMBER, SUBSCRIPTION_LINE_STATUS, CSI_NUM, LINE_START_DATE, LINE_END_DATE, SUBSCRIPTION_TYPE, FISCAL_QUARTER, FISCAL_YEAR, PRODUCT_TIER4, PRODUCT_TIER5, PRODUCT_TIER6, PRODUCT_TIER7, PRODUCT_TIER8, PRODUCT_TIER9, ITEM_DESCRIPTION, PART_NUMBER, TOTAL_CONTRACT_VALUE_TCV_CD, ADJUSTED_ARR_CD, ENGAGEMENTS, ADDITIONAL_INFO FROM CLM_DATA WHERE REGISTRY_ID = ?";
    public static final String SQL_FIND_STEP_FOLDERS_BY_STEP_ID =
        "SELECT STEP_ID, SUB_STEP_CODE, FOLDER_ID FROM STEP_FOLDERS WHERE STEP_ID = ?";
    public static final String SQL_CREATE_STEP_FOLDER =
        "INSERT INTO STEP_FOLDERS (STEP_ID, STEP_CODE, SUB_STEP_CODE ,FOLDER_ID) VALUES(?, ?, ?, ?)";
    public static final String SQL_UPDATE_EMAIL_RESOLUTION =
        "UPDATE USER_EMAILS SET IS_RESOLVED = ? ,RESOLUTION_COMMENTS = ? WHERE SR_ID = ?";

    public static final String SQL_FIND_SERVICE_BENEFIT_FILE_ID =
        "SELECT ID,SERVICE_CODE, SERVICE_DESC,FILEID,PUBLICLINKID FROM SERVICE_BENEFITS WHERE UPPER(SERVICE_CODE) = ?";

    public static final String SQL_FIND_SERVICE_BENEFIT_FILE_ID1 =
        "SELECT FILEID FROM SERVICE_BENEFITS WHERE UPPER(SERVICE_CODE) = ?";
    public static final String SQL_FIND_USECASE_FILE_ID = "SELECT FILEID FROM USECASES WHERE UPPER(USECASE_CODE) = ?";
    public static final String SQL_GET_ALL_USECASES =
        "SELECT ID,USECASE_CODE, USECASE_NAME, USECASE_DESC,FILEID,PUBLICLINKID FROM USECASES";


    public static final String SQL_CREATE_USER =
        "INSERT INTO USERS (USER_ID, PASSWORD, EMAIL, USER_ROLE, FIRST_NAME, LAST_NAME, REGISTRY_ID, PHONE) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    public static final String SQL_CHECK_USER_ID_EXISTS = "SELECT USER_ID FROM USERS WHERE USER_ID = ?";
    public static final String SQL_UPDATE_USER_PASSWORD =
        "UPDATE USERS SET PASSWORD = ?, PWD_LAST_CHANGED = SYSDATE WHERE USER_ID = ?";
    public static final String SQL_GET_USER_FOR_AUTHENTICATION =
        "SELECT USER_ID, PASSWORD, FIRST_NAME, LAST_NAME, AUTHORITY FROM USERS WHERE USER_ID = ?";

    // CLM Data
    public static final String SQL_GET_CLM_DATA =
        "SELECT CUST_REGISTRY_ID, FPH_DESCRIPTION_TIER_4, FPH_DESCRIPTION_TIER_5, FPH_DESCRIPTION_TIER_6, QUANTITY, QUANTITY_UNIT_OF_MEASURE FROM CLM_DATA WHERE CUST_REGISTRY_ID = (SELECT REGISTRY_ID FROM USERS WHERE USER_ID = ?) AND FPH_DESCRIPTION_TIER_4 = 'Public Cloud - IaaS' AND SUB_LINE_STATUS = 'ACTIVE' AND FPH_DESCRIPTION_TIER_6 NOT LIKE 'IaaS Public Cloud%'";
    public static final String SQL_GET_CUSTOMER_REGISTRY_DATA =
        "SELECT DISTINCT(CUST_REGISTRY_ID) AS REGISTRY_ID, (CUSTOMER_NAME_TRANSL || ' - ' || CUST_REGISTRY_ID) as CUSTOMER_REGISTRY, CUSTOMER_NAME_TRANSL FROM CLM_DATA ORDER BY CUSTOMER_NAME_TRANSL";
    public static final String SQL_RULE_CONFIGURATION =
        "SELECT RULE_KEY, RULE_TYPE, RULE_VALUE FROM RULE_CONFIGURATION WHERE RULE_KEY = ?";
    public static final String SQL_USER_QUERY =
        "SELECT USER_ID, EMAIL, USER_ROLE, FIRST_NAME, LAST_NAME, REGISTRY_ID, PHONE FROM USERS";
    public static final String SQL_UPDATE_USER_QUERY =
        "UPDATE USERS SET EMAIL = ?, USER_ROLE = ? , FIRST_NAME = ?, LAST_NAME = ? , REGISTRY_ID = ?, PHONE = ? WHERE USER_ID = ?";

    public static final String SQL_GET_JOB_CONFIGURATION =
        "SELECT JOB_ID, JOB_NAME, JOB_DESCRIPTION, JOB_FREQUENCY, JOB_FREQUENCY_TYPE, CLASS_NAME, JOB_FREQUENCY_HOUR, JOB_FREQUENCY_MINUTE, JOB_STATUS FROM JOB_CONFIGURATION";
    public static final String SQL_GET_JOB_HISTORY_FOR_JOB =
        "SELECT JOB_ID, JOB_HISTORY_ID, START_DATE, END_DATE, JOB_STATUS, JOB_FAILED_REASON, SUCCESSFUL_RUN_REPORT_DATE FROM JOB_HISTORY WHERE JOB_ID = ?";

    public static final String SQL_ADD_JOB_HISTORY =
        "INSERT INTO JOB_HISTORY (JOB_HISTORY_ID,JOB_ID,START_DATE,JOB_STATUS) VALUES (?,?,?,?)";

    public static final String SQL_GET_JOB_ID_SEQ = "SELECT JOB_HISTORY_ID_S.NEXTVAL FROM DUAL";
    
    public static final String SQL_UPDATE_JOB_HISTORY = "UPDATE JOB_HISTORY SET JOB_STATUS = ?, END_DATE = ?, JOB_FAILED_REASON, SUCCESSFUL_RUN_REPORT_DATE = ? WHERE JOB_HISTORY_ID = ? ";
}
