package org.recap.model.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by rajeshbabuk on 27/12/16.
 */
//@JsonIgnoreProperties({"itemBarcodes", "titleIdentifier", "patronBarcode","patronBarcode","emailAddress","requestingInstitution","requestingInstitution","requestType","deliveryLocation","requestNotes","startPage","endPage","chapterTitle"})
@Setter
@Getter
public class ItemRequestInformation {

    private List<String> itemBarcodes;
    private String titleIdentifier;
    private String itemOwningInstitution=""; // PUL, CUL, NYPL
    private String patronBarcode="";
    private String emailAddress="";
    private String requestingInstitution=""; // PUL, CUL, NYPL
    private String requestType=""; // Retrieval,EDD, Hold, Recall, Borrow Direct
    private String deliveryLocation="";
    private String requestNotes="";
    private String trackingId; // NYPL - trackingId
    private String title; // NYPL - title
    private String author; // NYPL - author
    private String callNumber; // NYPL - callNumber

    /**
     * EDD Request
     */
    private String startPage;
    private String endPage;
    private String chapterTitle="";
    private String expirationDate;
    private String bibId;
    private String username;
    private String issue;
    private String volume;

}
