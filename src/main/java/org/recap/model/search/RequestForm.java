package org.recap.model.search;

import org.recap.model.jpa.CustomerCodeEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rajeshbabuk on 13/10/16.
 */
public class RequestForm {

    private Integer requestId;
    private String patronBarcode;
    private String itemBarcode;
    private String status;
    private String deliveryLocation;
    private String patronBarcodeInRequest;
    private String itemBarcodeInRequest;
    private String deliveryLocationInRequest;
    private String itemTitle;
    private String itemOwningInstitution;
    private String patronEmailAddress;
    private String requestingInstitution;
    private String requestType;
    private String requestNotes;
    private String startPage;
    private String endPage;
    private String volumeNumber;
    private String issue;
    private String articleAuthor;
    private String articleTitle;
    private String message;
    private String errorMessage;
    private String totalRecordsCount = "0";
    private Integer pageNumber = 0;
    private Integer pageSize = 10;
    private Integer totalPageCount = 0;
    private boolean submitted = false;
    private boolean showResults = false;
    private List<String> requestingInstitutions = new ArrayList<>();
    private List<String> requestTypes = new ArrayList<>();
    private List<CustomerCodeEntity> deliveryLocations = new ArrayList<>();
    private List<SearchResultRow> searchResultRows = new ArrayList<>();
    private List<String> requestStatuses = new ArrayList<>();
    private List<String> institutionList = new ArrayList<>();
    private boolean disableRequestingInstitution = false;
    private String onChange;
    private String institution;
    private boolean showRequestErrorMsg;
    private String requestingInstituionHidden;
    private String itemBarcodeHidden;
    private Boolean disableSearchInstitution = false;
    private String searchInstitutionHdn;

    /**
     * Gets request id.
     *
     * @return the request id
     */
    public Integer getRequestId() {
        return requestId;
    }

    /**
     * Sets request id.
     *
     * @param requestId the request id
     */
    public void setRequestId(Integer requestId) {
        this.requestId = requestId;
    }

    /**
     * Gets patron barcode.
     *
     * @return the patron barcode
     */
    public String getPatronBarcode() {
        return patronBarcode;
    }

    /**
     * Sets patron barcode.
     *
     * @param patronBarcode the patron barcode
     */
    public void setPatronBarcode(String patronBarcode) {
        this.patronBarcode = patronBarcode;
    }

    /**
     * Gets item barcode.
     *
     * @return the item barcode
     */
    public String getItemBarcode() {
        return itemBarcode;
    }

    /**
     * Sets item barcode.
     *
     * @param itemBarcode the item barcode
     */
    public void setItemBarcode(String itemBarcode) {
        this.itemBarcode = itemBarcode;
    }

    /**
     * Gets status.
     *
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets status.
     *
     * @param status the status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Gets delivery location.
     *
     * @return the delivery location
     */
    public String getDeliveryLocation() {
        return deliveryLocation;
    }

    /**
     * Sets delivery location.
     *
     * @param deliveryLocation the delivery location
     */
    public void setDeliveryLocation(String deliveryLocation) {
        this.deliveryLocation = deliveryLocation;
    }

    /**
     * Gets patron barcode in request.
     *
     * @return the patron barcode in request
     */
    public String getPatronBarcodeInRequest() {
        return patronBarcodeInRequest;
    }

    /**
     * Sets patron barcode in request.
     *
     * @param patronBarcodeInRequest the patron barcode in request
     */
    public void setPatronBarcodeInRequest(String patronBarcodeInRequest) {
        this.patronBarcodeInRequest = patronBarcodeInRequest;
    }

    /**
     * Gets item barcode in request.
     *
     * @return the item barcode in request
     */
    public String getItemBarcodeInRequest() {
        return itemBarcodeInRequest;
    }

    /**
     * Sets item barcode in request.
     *
     * @param itemBarcodeInRequest the item barcode in request
     */
    public void setItemBarcodeInRequest(String itemBarcodeInRequest) {
        this.itemBarcodeInRequest = itemBarcodeInRequest;
    }

    /**
     * Gets delivery location in request.
     *
     * @return the delivery location in request
     */
    public String getDeliveryLocationInRequest() {
        return deliveryLocationInRequest;
    }

    /**
     * Sets delivery location in request.
     *
     * @param deliveryLocationInRequest the delivery location in request
     */
    public void setDeliveryLocationInRequest(String deliveryLocationInRequest) {
        this.deliveryLocationInRequest = deliveryLocationInRequest;
    }

    /**
     * Gets item title.
     *
     * @return the item title
     */
    public String getItemTitle() {
        return itemTitle;
    }

    /**
     * Sets item title.
     *
     * @param itemTitle the item title
     */
    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }

    /**
     * Gets item owning institution.
     *
     * @return the item owning institution
     */
    public String getItemOwningInstitution() {
        return itemOwningInstitution;
    }

    /**
     * Sets item owning institution.
     *
     * @param itemOwningInstitution the item owning institution
     */
    public void setItemOwningInstitution(String itemOwningInstitution) {
        this.itemOwningInstitution = itemOwningInstitution;
    }

    /**
     * Gets patron email address.
     *
     * @return the patron email address
     */
    public String getPatronEmailAddress() {
        return patronEmailAddress;
    }

    /**
     * Sets patron email address.
     *
     * @param patronEmailAddress the patron email address
     */
    public void setPatronEmailAddress(String patronEmailAddress) {
        this.patronEmailAddress = patronEmailAddress;
    }

    /**
     * Gets requesting institution.
     *
     * @return the requesting institution
     */
    public String getRequestingInstitution() {
        return requestingInstitution;
    }

    /**
     * Sets requesting institution.
     *
     * @param requestingInstitution the requesting institution
     */
    public void setRequestingInstitution(String requestingInstitution) {
        this.requestingInstitution = requestingInstitution;
    }

    /**
     * Gets request type.
     *
     * @return the request type
     */
    public String getRequestType() {
        return requestType;
    }

    /**
     * Sets request type.
     *
     * @param requestType the request type
     */
    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    /**
     * Gets request notes.
     *
     * @return the request notes
     */
    public String getRequestNotes() {
        return requestNotes;
    }

    /**
     * Sets request notes.
     *
     * @param requestNotes the request notes
     */
    public void setRequestNotes(String requestNotes) {
        this.requestNotes = requestNotes;
    }

    /**
     * Gets start page.
     *
     * @return the start page
     */
    public String getStartPage() {
        return startPage;
    }

    /**
     * Sets start page.
     *
     * @param startPage the start page
     */
    public void setStartPage(String startPage) {
        this.startPage = startPage;
    }

    /**
     * Gets end page.
     *
     * @return the end page
     */
    public String getEndPage() {
        return endPage;
    }

    /**
     * Sets end page.
     *
     * @param endPage the end page
     */
    public void setEndPage(String endPage) {
        this.endPage = endPage;
    }

    /**
     * Gets volume number.
     *
     * @return the volume number
     */
    public String getVolumeNumber() {
        return volumeNumber;
    }

    /**
     * Sets volume number.
     *
     * @param volumeNumber the volume number
     */
    public void setVolumeNumber(String volumeNumber) {
        this.volumeNumber = volumeNumber;
    }

    /**
     * Gets issue.
     *
     * @return the issue
     */
    public String getIssue() {
        return issue;
    }

    /**
     * Sets issue.
     *
     * @param issue the issue
     */
    public void setIssue(String issue) {
        this.issue = issue;
    }

    /**
     * Gets article author.
     *
     * @return the article author
     */
    public String getArticleAuthor() {
        return articleAuthor;
    }

    /**
     * Sets article author.
     *
     * @param articleAuthor the article author
     */
    public void setArticleAuthor(String articleAuthor) {
        this.articleAuthor = articleAuthor;
    }

    /**
     * Gets article title.
     *
     * @return the article title
     */
    public String getArticleTitle() {
        return articleTitle;
    }

    /**
     * Sets article title.
     *
     * @param articleTitle the article title
     */
    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    /**
     * Gets message.
     *
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets message.
     *
     * @param message the message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Gets error message.
     *
     * @return the error message
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * Sets error message.
     *
     * @param errorMessage the error message
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * Gets total records count.
     *
     * @return the total records count
     */
    public String getTotalRecordsCount() {
        return totalRecordsCount;
    }

    /**
     * Sets total records count.
     *
     * @param totalRecordsCount the total records count
     */
    public void setTotalRecordsCount(String totalRecordsCount) {
        this.totalRecordsCount = totalRecordsCount;
    }

    /**
     * Gets page number.
     *
     * @return the page number
     */
    public Integer getPageNumber() {
        return pageNumber;
    }

    /**
     * Sets page number.
     *
     * @param pageNumber the page number
     */
    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    /**
     * Gets page size.
     *
     * @return the page size
     */
    public Integer getPageSize() {
        return pageSize;
    }

    /**
     * Sets page size.
     *
     * @param pageSize the page size
     */
    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * Gets total page count.
     *
     * @return the total page count
     */
    public Integer getTotalPageCount() {
        return totalPageCount;
    }

    /**
     * Sets total page count.
     *
     * @param totalPageCount the total page count
     */
    public void setTotalPageCount(Integer totalPageCount) {
        this.totalPageCount = totalPageCount;
    }

    /**
     * Gets submitted.
     *
     * @return the boolean
     */
    public boolean isSubmitted() {
        return submitted;
    }

    /**
     * Sets submitted.
     *
     * @param submitted the submitted
     */
    public void setSubmitted(boolean submitted) {
        this.submitted = submitted;
    }

    /**
     * Gets show results.
     *
     * @return the boolean
     */
    public boolean isShowResults() {
        return showResults;
    }

    /**
     * Sets show results.
     *
     * @param showResults the show results
     */
    public void setShowResults(boolean showResults) {
        this.showResults = showResults;
    }

    /**
     * Gets requesting institutions.
     *
     * @return the requesting institutions
     */
    public List<String> getRequestingInstitutions() {
        return requestingInstitutions;
    }

    /**
     * Sets requesting institutions.
     *
     * @param requestingInstitutions the requesting institutions
     */
    public void setRequestingInstitutions(List<String> requestingInstitutions) {
        this.requestingInstitutions = requestingInstitutions;
    }

    /**
     * Gets request types.
     *
     * @return the request types
     */
    public List<String> getRequestTypes() {
        return requestTypes;
    }

    /**
     * Sets request types.
     *
     * @param requestTypes the request types
     */
    public void setRequestTypes(List<String> requestTypes) {
        this.requestTypes = requestTypes;
    }

    /**
     * Gets delivery locations.
     *
     * @return the delivery locations
     */
    public List<CustomerCodeEntity> getDeliveryLocations() {
        return deliveryLocations;
    }

    /**
     * Sets delivery locations.
     *
     * @param deliveryLocations the delivery locations
     */
    public void setDeliveryLocations(List<CustomerCodeEntity> deliveryLocations) {
        this.deliveryLocations = deliveryLocations;
    }

    /**
     * Gets search result rows.
     *
     * @return the search result rows
     */
    public List<SearchResultRow> getSearchResultRows() {
        return searchResultRows;
    }

    /**
     * Sets search result rows.
     *
     * @param searchResultRows the search result rows
     */
    public void setSearchResultRows(List<SearchResultRow> searchResultRows) {
        this.searchResultRows = searchResultRows;
    }

    /**
     * Gets request statuses.
     *
     * @return the request statuses
     */
    public List<String> getRequestStatuses() {
        return requestStatuses;
    }

    /**
     * Sets request statuses.
     *
     * @param requestStatuses the request statuses
     */
    public void setRequestStatuses(List<String> requestStatuses) {
        this.requestStatuses = requestStatuses;
    }

    /**
     * Reset page number.
     */
    public void resetPageNumber() {
        this.pageNumber = 0;
    }

    /**
     * Gets on change.
     *
     * @return the on change
     */
    public String getOnChange() {
        return onChange;
    }

    /**
     * Sets on change.
     *
     * @param onChange the on change
     */
    public void setOnChange(String onChange) {
        this.onChange = onChange;
    }

    /**
     * Gets disable requesting institution.
     *
     * @return the boolean
     */
    public boolean isDisableRequestingInstitution() {
        return disableRequestingInstitution;
    }

    /**
     * Sets disable requesting institution.
     *
     * @param disableRequestingInstitution the disable requesting institution
     */
    public void setDisableRequestingInstitution(boolean disableRequestingInstitution) {
        this.disableRequestingInstitution = disableRequestingInstitution;
    }

    /**
     * Gets institution list.
     *
     * @return the institution list
     */
    public List<String> getInstitutionList() {
        return institutionList;
    }

    /**
     * Sets institution list.
     *
     * @param institutionList the institution list
     */
    public void setInstitutionList(List<String> institutionList) {
        this.institutionList = institutionList;
    }

    /**
     * Gets institution.
     *
     * @return the institution
     */
    public String getInstitution() {
        return institution;
    }

    /**
     * Sets institution.
     *
     * @param institution the institution
     */
    public void setInstitution(String institution) {
        this.institution = institution;
    }

    /**
     * Gets show request error msg.
     *
     * @return the boolean
     */
    public boolean isShowRequestErrorMsg() {
        return showRequestErrorMsg;
    }

    /**
     * Sets show request error msg.
     *
     * @param showRequestErrorMsg the show request error msg
     */
    public void setShowRequestErrorMsg(boolean showRequestErrorMsg) {
        this.showRequestErrorMsg = showRequestErrorMsg;
    }

    /**
     * Gets requesting instituion hidden.
     *
     * @return the requesting instituion hidden
     */
    public String getRequestingInstituionHidden() {
        return requestingInstituionHidden;
    }

    /**
     * Sets requesting instituion hidden.
     *
     * @param requestingInstituionHidden the requesting instituion hidden
     */
    public void setRequestingInstituionHidden(String requestingInstituionHidden) {
        this.requestingInstituionHidden = requestingInstituionHidden;
    }

    /**
     * Gets disable search institution.
     *
     * @return the disable search institution
     */
    public Boolean getDisableSearchInstitution() {
        return disableSearchInstitution;
    }

    /**
     * Sets disable search institution.
     *
     * @param disableSearchInstitution the disable search institution
     */
    public void setDisableSearchInstitution(Boolean disableSearchInstitution) {
        this.disableSearchInstitution = disableSearchInstitution;
    }

    /**
     * Gets search institution hdn.
     *
     * @return the search institution hdn
     */
    public String getSearchInstitutionHdn() {
        return searchInstitutionHdn;
    }

    /**
     * Sets search institution hdn.
     *
     * @param searchInstitutionHdn the search institution hdn
     */
    public void setSearchInstitutionHdn(String searchInstitutionHdn) {
        this.searchInstitutionHdn = searchInstitutionHdn;
    }

    /**
     * Gets item barcode hidden.
     *
     * @return the item barcode hidden
     */
    public String getItemBarcodeHidden() {
        return itemBarcodeHidden;
    }

    /**
     * Sets item barcode hidden.
     *
     * @param itemBarcodeHidden the item barcode hidden
     */
    public void setItemBarcodeHidden(String itemBarcodeHidden) {
        this.itemBarcodeHidden = itemBarcodeHidden;
    }
}