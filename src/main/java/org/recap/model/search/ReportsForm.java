package org.recap.model.search;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rajeshbabuk on 13/10/16.
 */
@Setter
@Getter
public class ReportsForm {

    private String showBy;
    private String requestType;
    private String requestFromDate;
    private String requestToDate;
    private String accessionDeaccessionFromDate;
    private String accessionDeaccessionToDate;

    private long retrievalRequestPulCount;
    private long retrievalRequestCulCount;
    private long retrievalRequestNyplCount;

    private long recallRequestPulCount;
    private long recallRequestCulCount;
    private long recallRequestNyplCount;

    private long physicalPrivatePulCount;
    private long physicalPrivateCulCount;
    private long physicalPrivateNyplCount;

    private long physicalSharedPulCount;
    private long physicalSharedCulCount;
    private long physicalSharedNyplCount;

    private long eddPrivatePulCount;
    private long eddPrivateCulCount;
    private long eddPrivateNyplCount;

    private long eddSharedOpenPulCount;
    private long eddSharedOpenCulCount;
    private long eddSharedOpenNyplCount;

    private long accessionPrivatePulCount;
    private long accessionPrivateCulCount;
    private long accessionPrivateNyplCount;
    private long accessionSharedPulCount;
    private long accessionSharedCulCount;
    private long accessionSharedNyplCount;
    private long accessionOpenPulCount;
    private long accessionOpenCulCount;
    private long accessionOpenNyplCount;

    private long deaccessionPrivatePulCount;
    private long deaccessionPrivateCulCount;
    private long deaccessionPrivateNyplCount;
    private long deaccessionSharedPulCount;
    private long deaccessionSharedCulCount;
    private long deaccessionSharedNyplCount;
    private long deaccessionOpenPulCount;
    private long deaccessionOpenCulCount;
    private long deaccessionOpenNyplCount;

    private long openPulCgdCount;
    private long openCulCgdCount;
    private long openNyplCgdCount;
    private long sharedPulCgdCount;
    private long sharedCulCgdCount;
    private long sharedNyplCgdCount;
    private long privatePulCgdCount;
    private long privateCulCgdCount;
    private long privateNyplCgdCount;

    private boolean showILBDResults=false;
    private boolean showPartners=false;
    private boolean showRequestTypeTable=false;
    private boolean showAccessionDeaccessionTable=false;
    private boolean showReportResultsText=false;
    private boolean showNoteILBD=false;
    private boolean showNotePartners=false;
    private boolean showNoteRequestType=false;

    private boolean showRetrievalTable=false;
    private boolean showRecallTable=false;
    private boolean showRequestTypeShow=false;

    private List<String> reportRequestType=new ArrayList<>();
    private List<String> owningInstitutions = new ArrayList<>();
    private List<String> collectionGroupDesignations = new ArrayList<>();
    private List<DeaccessionItemResultsRow> deaccessionItemResultsRows = new ArrayList<>();

    private boolean showDeaccessionInformationTable = false;

    private String totalRecordsCount = "0";
    private Integer pageNumber = 0;
    private Integer pageSize = 10;
    private Integer totalPageCount = 0;
    private String deaccessionOwnInst;

    //IncompleteRecordsReport
    private String incompleteRequestingInstitution;
    private Integer incompletePageNumber = 0;
    private Integer incompletePageSize = 10;
    private String incompleteTotalRecordsCount = "0";
    private Integer incompleteTotalPageCount = 0;
    private List<IncompleteReportResultsRow> incompleteReportResultsRows = new ArrayList<>();
    private List<String> incompleteShowByInst =  new ArrayList<>();
    private boolean showIncompleteResults = false;
    private String errorMessage;
    private boolean showIncompletePagination = false;
    private boolean export = false;

    private long physicalPartnerSharedPulCount;
    private long physicalPartnerSharedCulCount;
    private long physicalPartnerSharedNyplCount;
    private long eddPartnerSharedOpenPulCount;
    private long eddPartnerSharedOpenCulCount;
    private long eddPartnerSharedOpenNyplCount;
    private long eddRequestPulCount;
    private long eddRequestCulCount;
    private long eddRequestNyplCount;


    /**
     * Instantiates a new Reports form.
     */
    public ReportsForm() {
        this.getOwningInstitutions().add("NYPL");
        this.getOwningInstitutions().add("CUL");
        this.getOwningInstitutions().add("PUL");

        this.getCollectionGroupDesignations().add("Shared");
        this.getCollectionGroupDesignations().add("Private");
        this.getCollectionGroupDesignations().add("Open");
    }


}
