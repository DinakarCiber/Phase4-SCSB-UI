package org.recap.model.search;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Created by akulak on 25/9/17.
 */
@Setter
@Getter
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

}
