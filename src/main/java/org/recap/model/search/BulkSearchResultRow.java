package org.recap.model.search;

import java.util.Date;

/**
 * Created by akulak on 25/9/17.
 */
public class BulkSearchResultRow {

    private Integer bulkRequestId;
    private String bulkRequestName;
    private String fileName;
    private String patronBarcode;
    private String requestingInstitution;
    private String deliveryLocation;
    private String createdBy;
    private String emailAddress;
    private Date createdDate;
    private String status;
    private String bulkRequestNotes;

    public Integer getBulkRequestId() {
        return bulkRequestId;
    }

    public void setBulkRequestId(Integer bulkRequestId) {
        this.bulkRequestId = bulkRequestId;
    }

    public String getBulkRequestName() {
        return bulkRequestName;
    }

    public void setBulkRequestName(String bulkRequestName) {
        this.bulkRequestName = bulkRequestName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getPatronBarcode() {
        return patronBarcode;
    }

    public void setPatronBarcode(String patronBarcode) {
        this.patronBarcode = patronBarcode;
    }

    public String getRequestingInstitution() {
        return requestingInstitution;
    }

    public void setRequestingInstitution(String requestingInstitution) {
        this.requestingInstitution = requestingInstitution;
    }

    public String getDeliveryLocation() {
        return deliveryLocation;
    }

    public void setDeliveryLocation(String deliveryLocation) {
        this.deliveryLocation = deliveryLocation;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBulkRequestNotes() {
        return bulkRequestNotes;
    }

    public void setBulkRequestNotes(String bulkRequestNotes) {
        this.bulkRequestNotes = bulkRequestNotes;
    }
}
