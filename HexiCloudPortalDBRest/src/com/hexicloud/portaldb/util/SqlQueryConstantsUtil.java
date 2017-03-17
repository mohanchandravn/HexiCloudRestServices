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
        "SELECT USER_ID, EMAIL, USER_ROLE, FIRST_NAME, LAST_NAME, ACTIVE, PWD_LAST_CHANGED, LAST_LOGGED_IN, REGISTRY_ID FROM USERS WHERE USER_ID = ?";
    public static final String SQL_UPDATE_USER_LAST_LOGGED_IN =
        "UPDATE USERS SET LAST_LOGGED_IN = SYSDATE WHERE USER_ID = ?";
    public static final String SQL_GET_CLM_DATA =
        "SELECT REGISTRY_ID, CUSTOMER_NAME, COUNTRY, DIVISION, CLUSTER_NAME, REGION, SUBSCRIPTION_ID, SUBSCRIPTION_PLAN_NUMBER, CONTRACT_NUMBER, SUBSCRIPTION_LINE_STATUS, CSI_NUM, LINE_START_DATE, LINE_END_DATE, SUBSCRIPTION_TYPE, FISCAL_QUARTER, FISCAL_YEAR, PRODUCT_TIER4, PRODUCT_TIER5, PRODUCT_TIER6, PRODUCT_TIER7, PRODUCT_TIER8, PRODUCT_TIER9, ITEM_DESCRIPTION, PART_NUMBER, TOTAL_CONTRACT_VALUE_TCV_CD, ADJUSTED_ARR_CD, ENGAGEMENTS, ADDITIONAL_INFO FROM CLM_DATA WHERE REGISTRY_ID = ?";
    public static final String SQL_FIND_STEP_FOLDERS_BY_STEP_ID =
        "SELECT STEP_ID, SUB_STEP_CODE, FOLDER_ID FROM STEP_FOLDERS WHERE STEP_ID = ?";
    public static final String SQL_CREATE_STEP_FOLDER =
        "INSERT INTO STEP_FOLDERS (STEP_ID, STEP_CODE, SUB_STEP_CODE ,FOLDER_ID) VALUES(?, ?, ?, ?)";
    public static final String SQL_UPDATE_EMAIL_RESOLUTION =
        "UPDATE USER_EMAILS SET IS_RESOLVED = ? ,RESOLUTION_COMMENTS = ? WHERE SR_ID = ?";
    
    
    public static final String SQL_CREATE_USER = "INSERT INTO USERS (USER_ID,PASSWORD,EMAIL,USER_ROLE,FIRST_NAME,LAST_NAME) VALUES (?, ?, ?, ?, ?, ?)";
    public static final String SQL_CHECK_USER_ID_EXISTS = "SELECT USER_ID FROM USERS WHERE USER_ID = ?";
    public static final String SQL_UPDATE_USER_PASSWORD = "UPDATE USERS SET PASSWORD = ?, PWD_LAST_CHANGED = SYSDATE WHERE USER_ID = ?";
    public static final String SQL_GET_USER_FOR_AUTHENTICATION =
        "SELECT USER_ID, PASSWORD, FIRST_NAME, LAST_NAME, AUTHORITY FROM USERS WHERE USER_ID = ?";
}
