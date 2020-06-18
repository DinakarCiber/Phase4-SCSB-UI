package org.recap.model.search;

import lombok.Getter;
import lombok.Setter;
import org.recap.model.jpa.CustomerCodeEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rajeshbabuk on 22/7/16.
 */
@Setter
@Getter
public class BibliographicMarcForm {

    private Integer bibId;
    private String title;
    private String author;
    private String publisher;
    private String publishedDate;
    private String owningInstitution;
    private String callNumber;
    private String leaderMaterialType;
    private String tag000;
    private String controlNumber001;
    private String controlNumber005;
    private String controlNumber008;
    private String content;
    private List<BibDataField> bibDataFields = new ArrayList<>();
    private String errorMessage;
    private String warningMessage;

    private Integer itemId;
    private String availability;
    private String barcode;
    private String locationCode;
    private String useRestriction;
    private String monographCollectionGroupDesignation;
    private String collectionGroupDesignation;
    private String newCollectionGroupDesignation;
    private String cgdChangeNotes;
    private String customerCode;
    private String deaccessionType;
    private String deaccessionNotes;
    private List<CustomerCodeEntity> deliveryLocations = new ArrayList<>();
    private String deliveryLocation;
    private boolean shared = false;
    private boolean submitted = false;
    private String message;
    private String collectionAction;
    private boolean allowEdit;
    private String username;

}
