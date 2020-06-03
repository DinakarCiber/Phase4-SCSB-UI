package org.recap.model.search;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by akulak on 8/3/17.
 */
@Setter
@Getter
public class IncompleteReportResultsRow {
    private String author;
    private String createdDate;
    private String title;
    private String owningInstitution;
    private String customerCode;
    private String barcode;

}
