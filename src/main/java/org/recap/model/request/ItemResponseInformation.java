package org.recap.model.request;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by rajeshbabuk on 27/12/16.
 */
@Setter
@Getter
public class ItemResponseInformation {

    private String patronBarcode;
    private String itemBarcode;
    private String requestType;
    private String deliveryLocation;
    private String requestingInstitution;
    private String bibliographicId;
    private String expirationDate;
    private String itemId;
    private String screenMessage;
    private boolean success;
    private String emailAddress;
    private Integer startPage;
    private Integer endPage;
    private String titleIdentifier;
    private String bibiid;
    private String dueDate;

}
