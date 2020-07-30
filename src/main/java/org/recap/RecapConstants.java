package org.recap;

/**
 * Created by SheikS on 6/20/2016.
 */
public class RecapConstants {

    //Matching Algorithm Constants
    public static final String CSV_MATCHING_ALGO_REPORT_Q = "scsbactivemq:queue:csvMatchingAlgoReportQ";
    public static final String CSV_SUMMARY_ALGO_REPORT_Q = "scsbactivemq:queue:csvSummaryAlgoReportQ";
    public static final String FTP_MATCHING_ALGO_REPORT_Q = "scsbactivemq:queue:ftpMatchingAlgoReportQ";
    public static final String FTP_SUMMARY_ALGO_REPORT_Q = "scsbactivemq:queue:ftpSummaryAlgoReportQ";

    public static final String CSV_MATCHING_ALGO_REPORT_ROUTE_ID = "csvMatchingAlgoReportRoute";
    public static final String CSV_SUMMARY_ALGO_REPORT_ROUTE_ID = "csvSummaryAlgoReportRoute";
    public static final String FTP_MATCHING_ALGO_REPORT_ROUTE_ID = "ftpMatchingAlgoReportRoute";
    public static final String FTP_SUMMARY_ALGO_REPORT_ROUTE_ID = "ftpSummaryAlgoReportRoute";
    public static final String FS_SUBMIT_COLLECTION_REJECTION_REPORT_ID = "fsSubmitCollectionRejectionReportQ";
    public static final String FTP_SUBMIT_COLLECTION_REJECTION_REPORT_ID = "ftpSubmitCollectionRejectionReportQ";

    public static final String MATCHING_ALGO_OCLC_FILE_NAME = "Matching_Algo_OCLC";
    public static final String MATCHING_ALGO_ISBN_FILE_NAME = "Matching_Algo_ISBN";
    public static final String MATCHING_ALGO_ISSN_FILE_NAME = "Matching_Algo_ISSN";
    public static final String MATCHING_ALGO_LCCN_FILE_NAME = "Matching_Algo_LCCN";

    public static final String EXCEPTION_REPORT_FILE_NAME = "Exception_Report";
    public static final String EXCEPTION_REPORT_OCLC_FILE_NAME = "Exception_Report_OCLC";
    public static final String EXCEPTION_REPORT_ISBN_FILE_NAME = "Exception_Report_ISBN";
    public static final String EXCEPTION_REPORT_ISSN_FILE_NAME = "Exception_Report_ISSN";
    public static final String EXCEPTION_REPORT_LCCN_FILE_NAME = "Exception_Report_LCCN";

    public static final String SUMMARY_REPORT_FILE_NAME = "Summary_Report_Phase1";
    public static final String SUMMARY_REPORT_OCLC_FILE_NAME = "Summary_Report_OCLC";
    public static final String SUMMARY_REPORT_ISBN_FILE_NAME = "Summary_Report_ISBN";
    public static final String SUMMARY_REPORT_ISSN_FILE_NAME = "Summary_Report_ISSN";
    public static final String SUMMARY_REPORT_LCCN_FILE_NAME = "Summary_Report_LCCN";

    public static final String DATE_FORMAT_FOR_FILE_NAME = "ddMMMyyyy";

    public static final String MATCHING_BIB_ID = "BibId";
    public static final String MATCHING_TITLE = "Title";
    public static final String MATCHING_BARCODE = "Barcode";
    public static final String MATCHING_VOLUME_PART_YEAR = "VolumePartYear";
    public static final String MATCHING_INSTITUTION_ID = "InstitutionId";
    public static final String MATCHING_OCLC = "Oclc";
    public static final String MATCHING_ISBN = "Isbn";
    public static final String MATCHING_ISSN = "Issn";
    public static final String MATCHING_LCCN = "Lccn";
    public static final String MATCHING_USE_RESTRICTIONS = "UseRestrictions";
    public static final String MATCHING_SUMMARY_HOLDINGS = "SummaryHoldings";
    public static final String MATCHING_MATERIAL_TYPE = "MaterialType";

    public static final String SUMMARY_NUM_BIBS_IN_TABLE = "CountOfBibsInTable";
    public static final String SUMMARY_NUM_ITEMS_IN_TABLE = "CountOfItemsInTable";
    public static final String SUMMARY_MATCHING_KEY_FIELD = "MatchingKeyField";
    public static final String SUMMARY_MATCHING_BIB_COUNT = "CountOfBibMatches";
    public static final String SUMMARY_NUM_ITEMS_AFFECTED = "CountOfItemAffected";

    public static final String MATCHING_LOCAL_BIB_ID = "LocalBibId";

    //Report Types
    public static final String MATCHING_TYPE = "Matching";
    public static final String EXCEPTION_TYPE = "Exception";
    public static final String SUMMARY_TYPE = "Summary";

    //Transmission Types
    public static final String MATCHING_EXCEPTION_OCCURED = "MatchingExceptionOccurred";

    public static final String MATCHING_REPORT_ENTITY_MAP = "matchingReportEntityMap";
    public static final String EXCEPTION_REPORT_ENTITY_MAP = "exceptionReportEntityMap";

    //Error Message
    public static final String RECORD_NOT_AVAILABLE = "Database may be empty or Bib table does not contains this record";
    public static final String SERVER_ERROR_MSG = "Server is down for Maintenance Please Try again Later.";
    public static final String EMPTY_FACET_ERROR_MSG = "At least one Bib Facet Box and one Item Facet Box needs to be checked to get results.";


    //Collection
    public static final String COLLECTION_FORM = "collectionForm";

    public static final String BARCODES_NOT_AVAILABLE = "Barcode(s) not available";
    public static final String BARCODE_LIMIT_ERROR = "Only ten items can be processed. Items not processed:";
    public static final String DEACCESSION_ERROR_REQUEST_CANCEL = "The active request associated with the item has been cancelled.";

    //Request
    public static final String REQUEST_FORM="requestForm";
    public static final String CREATE_REQUEST_SECTION="request :: #createRequestSection";

    public static final String REQUEST_PRIVATE_ERROR_USER_NOT_PERMITTED = "User is not permitted to request private item(s) from other partners";
    public static final String REQUEST_ERROR_USER_NOT_PERMITTED = "User is not permitted to request item(s)";

    public static final String ITEM_BARCDE_DOESNOT_EXIST = "Item Barcode doesn't exist in SCSB database.";

    public static final String FTP_DE_ACCESSION_SUMMARY_REPORT_Q = "scsbactivemq:queue:ftpDeAccessionSummaryReportQ";

    public static final String FS_DE_ACCESSION_SUMMARY_REPORT_ID = "fsDeAccessionSummaryReportQ";
    public static final String FTP_DE_ACCESSION_SUMMARY_REPORT_ID = "ftpDeAccessionSummaryReportQ";

    public static final String FS_ACCESSION_SUMMARY_REPORT_Q = "scsbactivemq:queue:fsAccessionSummaryReportQ";

    public static final String VALIDATE_REQUEST_ITEM_URL = "requestItem/validateItemRequestInformations";
    public static final String REQUEST_ITEM_URL = "requestItem/requestItem";
    public static final String BULK_REQUEST_ITEM_URL = "requestItem/bulkRequest";
    public static final String URL_REQUEST_CANCEL = "requestItem/cancelRequest";
    public static final String URL_REQUEST_RESUBMIT = "requestItem/replaceRequest";

    public static final String ITEM_TITLE = "itemTitle";
    public static final String PATRON_BARCODE = "patronBarcode";
    public static final String PATRON_EMAIL_ADDRESS = "patronEmailAddress";
    public static final String REQUESTING_INSTITUTION = "requestingInstitution";
    public static final String REQUEST_TYPE = "requestType";
    public static final String DELIVERY_LOCATION = "deliveryLocation";
    public static final String REQUEST_NOTES = "requestNotes";
    public static final String ERROR_MESSAGE = "errorMessage";
    public static final String NOT_AVAILABLE_ERROR_MESSAGE = "notAvailableErrorMessage";
    public static final String NO_PERMISSION_ERROR_MESSAGE = "noPermissionErrorMessage";

    public static final String USER_ROLES_SEARCH ="userRolesSearch";
    public static final Integer CGD_PRIVATE=3;

    public static final String REJECTION_REPORT = "Rejection";
    public static final String SUBMIT_COLLECTION_REPORT = "Submit_Collection_Report";
    public static final String ITEM_BARCODE_NOT_FOUND_MSG = "Item Barcode not found";
    public static final String DUMMY_CALL_NUMBER = "dummycallnumbertype";

    public static final String REQUEST_TYPES = "requestTypes";
    public static final String SHOW_EDD = "showEDD";
    public static final String MULTIPLE_BARCODES = "multipleBarcodes";

    //Reports
    public static final String REPORTS_FORM="reportsForm";
    public static final String REPORTS_VIEW_DEACCESSION_INFORMARION ="reports :: #deaccessionInformation";
    public static final String REPORTS_VIEW_CGD_TABLE ="reports :: #cgdTable";
    public static final Integer PUL_INST_ID = 1;
    public static final Integer CUL_INST_ID = 2;
    public static final Integer NYPL_INST_ID = 3;
    public static final Integer CGD_SHARED = 1;
    public static final Integer CGD_OPEN = 2;

    public static final String SCSB_SEARCH_SERVICE_URL = "searchService/search";
    public static final String SCSB_UPDATE_CGD_URL = "updateCgdService/updateCgd";
    public static final String SCSB_DEACCESSION_URL = "deAccessionService/deaccession";

    public static final String SCSB_REPORTS_ACCESSION_DEACCESSION_COUNTS_URL = "reportsService/accessionDeaccessionCounts";
    public static final String SCSB_REPORTS_CGD_ITEM_COUNTS_URL = "reportsService/cgdItemCounts";
    public static final String SCSB_REPORTS_DEACCESSION_RESULTS_URL = "reportsService/deaccessionResults";

    public static final String SCSB_SHIRO_AUTHENTICATE_URL="userAuth/authService";
    public static final String SCSB_SHIRO_SEARCH_URL="auth/search";
    public static final String SCSB_SHIRO_TOUCH_EXISTIN_SESSION_URL="auth/touchExistingSession";
    public static final String SCSB_SHIRO_REQUEST_URL="auth/request";
    public static final String SCSB_SHIRO_COLLECTION_URL="auth/collection";
    //Monitoring and Logging
    public static final String SCSB_SHIRO_MONITORING_URL="auth/monitoring";
    public static final String SCSB_SHIRO_LOGGING_URL="auth/logging";
    public static final String SCSB_SHIRO_REPORT_URL="auth/reports";
    public static final String SCSB_SHIRO_USER_ROLE_URL="auth/userRoles";
    public static final String SCSB_SHIRO_ROLE_URL="auth/roles";
    public static final String SCSB_SHIRO_LOGOUT_URL="userAuth/logout";
    public static final String SCSB_SHIRO_UI_VALUES="authentication/permissions";
    public static final String LOG_USER_NOT_VALID=": User not valid :";
    public static final String LOG_USER_LOGOUT_SUCCUSS="User logout succussfully";
    public static final String PERMISSIONS_MAP ="PERMISSIONS_MAP";
    public static final String LOGOUT="logout";

    public static final String IS_USER_AUTHENTICATED = "isAuthenticated";
    public static final String HTC = "HTC";

    public static final String USER_ISAUTHENTICATED ="true";

    public static final String USER_ID="id";

    public static final String USER_INSTITUTION="userInstitution";

    public static final String REQUEST_PRIVILEGE="isRequestAllowed";

    public static final String COLLECTION_PRIVILEGE="isCollectionAllowed";

    public static final String REPORTS_PRIVILEGE="isReportAllowed";

    public static final String SEARCH_PRIVILEGE = "isSearchAllowed";

    public static final String USER_ROLE_PRIVILEGE="isUserRoleAllowed";

    //ROLES
    public static final String ROLES = "roles";
    public static final String ROLES_FORM = "rolesForm";
    public static final String INVALID_ROLE_NAME = "Please give one valid role name";
    public static final String WRONG_PERMISSION = "This permission is not given to this role";
    public static final String INVALID_PERMISSION = "Please give one valid permission name";
    public static final String SPECIAL_CHARACTERS_NOT_ALLOWED = "Special characters and spaces are not allowed";
    public static final String SPECIAL_CHARACTERS_NOT_ALLOWED_CREATE = "Special characters and spaces are not allowed in Role Name";
    public static final String ROLES_SUPER_ADMIN = "Super Admin";

    public static final String EDITED_AND_SAVED = " edited and saved successfully";
    public static final String DELETED_SUCCESSFULLY = " deleted successfully";
    public static final String ALREADY_EXISTS = " already exists";
    public static final String ADDED_SUCCESSFULLY = " added Successfully";
    public static final String EDITED_SUCCESSFULLY = " edited Successfully";
    //USERS
    public static final String NETWORK_LOGIN_ID_DOES_NOT_EXIST = "Network Login Id does not exist";
    public static final String EMAILID_ID_DOES_NOT_EXIST = "User email ID does not exist";
    public static final String NETWORK_LOGIN_ID_AND_EMAILID_ID_DOES_NOT_EXIST = "Network ID and email ID do not exist";

    //populating Item Barcode, Title and Institution for Request
    public static final String REQUESTED_BARCODE = "requestedBarcode";
    public static final String REQUESTED_ITEM_TITLE = "itemTitle";
    public static final String REQUESTED_ITEM_OWNING_INSTITUTION = "itemOwningInstitution";

    public static final String OWNING_INSTITUTION = "owningInstitution";

    public static final String ADMIN = "ROLE_ADMIN";
    public static final String USER = "ROLE_USER";
    public static final String ROLE_ANONYMOUS = "ROLE_ANONYMOUS";
    public static final String ANONYMOUS_USER = "anonymousUser";
    public static final String RECAP_INSTITUTION_CODE = "RECAP_INSTITUTION_CODE";
    public static final String CAS = "cas.";
    public static final String SERVICE_LOGIN = ".service.login";
    public static final String SERVICE_LOGOUT = ".service.logout";
    public static final String URL_PREFIX = ".url.prefix";
    public static final String REDIRECT_URI = "logout.redirect.uri";

    public static final String SEARCH_REQUEST_ACTIVE = "active";
    public static final String WARNING_MESSAGE_RETRIEVAL_CROSS_BORROWED_ITEM = "Warning : This item has an active retrieval request made by other institution.";
    public static final String WARNING_MESSAGE_RECALL_CROSS_BORROWED_ITEM = "Warning : This item has an active recall request made by other institution.";
    public static final String WARNING_MESSAGE_RETRIEVAL_BORROWED_ITEM = "Warning : This item has an active retrieval request.";
    public static final String WARNING_MESSAGE_RECALL_BORROWED_ITEM = "Warning : This item has an active recall request.";
    public static final String WARNING_MESSAGE_REQUEST_BORROWED_ITEM = "Warning : This item has an active retrieval request and recall request.";
    public static final String WARNING_MESSAGE_DEACCESSION_REQUEST_BORROWED_ITEM = "Deaccessioning the item will cancel the request.";

    //UserRoleController
    public static final String VIEW_LOGIN="login";
    public static final String VIEW_HOME="/";
    public static final String VIEW_REQUEST_RESULT_TABLE="userRolesSearch :: #request-result-table";
    public static final String USER_ROLE_FORM="userRoleForm";

    //View
    public static final String VIEW_SEARCH_RECORDS="searchRecords";
    public static final String VIEW_SEARCH_RECORDS_REQUEST="searchRecordsRequest";
    public static final String VIEW_SEARCH_REQUESTS_SECTION="request :: #searchRequestsSection";
    public static final String VIEW_SCHEDULE_JOB_SECTION="scheduleJobs :: #scheduleJobs";

    public static final String USERS_TAB_CLICKED = "Users Tab Clicked";

    //Incomplete Record Reports
    public static final String SCSB_REPORTS_INCOMPLETE_RESULTS_URL = "reportsService/incompleteRecords";
    public static final String REPORTS_INCOMPLETE_RECORDS="IncompleteRecordsReport";
    public static final String REPORTS_INCOMPLETE_RECORDS_NOT_FOUND="No Records Found";
    public static final String REPORTS_INCOMPLETE_RECORDS_VIEW="reports :: #IncompleteReporttableview";
    public static final String REPORTS_INCOMPLETE_SHOW_BY_VIEW="reports :: #incompleteShowBy";
    public static final String REPORTS_INCOMPLETE_EXPORT_FILE_NAME="ExportIncompleteRecords_";

    //UserManagementService
    public static final String USER_AUTH="user_auth";
    public static final String ROLE_ID="roleId";
    public static final String USER_TOKEN="token";
    public static final String REQUEST_ALL_PRIVILEGE="isRequestAllAllowed";
    public static final String REQUEST_ITEM_PRIVILEGE="isRequestItemAllowed";
    public static final String BARCODE_RESTRICTED_PRIVILEGE="isBarcodeRestricted";
    public static final String DEACCESSION_PRIVILEGE="isDeaccessionAllowed";
    public static final String SUPER_ADMIN_USER="isSuperAdmin";
    public static final String RECAP_USER ="isRecapUser";
    public static final String USER_AUTH_ERRORMSG="authErrorMsg";
    public static final String USER_NAME="userName";
    public static final String ROLE_FOR_SUPER_ADMIN="isRoleAllowed";
    public static final String TOKEN_SPLITER=":";

    public static final String OCLC_NUMBER_PATTERN="[^0-9]";

    public static final String TOKEN = "token";
    public static final String ERROR_CODE_ERROR_MESSAGE= "error.code.exception";

    public static final String LOGGED_IN_INSTITUTION= "loggedInInstitution";

    public static final String SCHEDULE_JOBS = "scheduleJobs";
    public static final String SCHEDULE_JOBS_FORM = "scheduleJobsForm";
    public static final String UNSCHEDULE= "Unschedule";
    public static final String UNSCHEDULED= "Unscheduled";
    public static final String SCHEDULED= "Scheduled";

    public static final String REQUESTED_ITEM_AVAILABILITY="requestedItemAvailabilty";

    public static final String BULK_REQUEST_FORM = "bulkRequestForm";
    public static final String BULK_REQUEST= "bulkRequest";
    public static final String BULK_CREATE_REQUEST_SECTION="bulkRequest";
    public static final String BULK_SEARCH_REQUEST_SECTION="bulkRequest::#searchRequestsSection";
    public static final String IN_PROCESS = "IN PROCESS";

    public static final String SCSB_SHIRO_BULK_REQUEST_URL="auth/bulkRequest";

    public static final String EXCEPTION = "EXCEPTION";
    public static final String REQUESTS_PLACED_ON_SCH = "REQUESTS PLACED ON SCH";

    public static final String APP_MONITORING = "monitoring";
    public static final String MONITORING_FORM = "monitoringForm";
    public static final String APP_LOGGING = "logging";
    public static final String LOGGING_FORM = "loggingForm";
    public static final String MONITORING = "isMonitoring";
    public static final String LOGGING = "isLogging";

    public static final String SCSB_UI = "scsb_ui";
    public static final String SCSB_AUTH = "scsb_auth";
    public static final String SCSB_GATEWAY = "scsb_gateway";
    public static final String SCSB_DOC = "scsb_doc";
    public static final String SCSB_CIRC = "scsb_circ";
    public static final String SCSB_BATCH = "scsb_batch_scheduler";

    private RecapConstants(){
        //Do nothing
    }
}
