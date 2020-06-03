package org.recap.model.reports;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rajeshbabuk on 13/1/17.
 */
@Getter
@Setter
public class ReportsRequest {

    private String accessionDeaccessionFromDate;
    private String accessionDeaccessionToDate;
    private List<String> owningInstitutions = new ArrayList<>();
    private List<String> collectionGroupDesignations = new ArrayList<>();
    private Integer pageNumber = 0;
    private Integer pageSize = 10;
    private String deaccessionOwningInstitution;
    //IncompleteRecordsReport
    private String incompleteRequestingInstitution;
    private Integer incompletePageNumber = 0;
    private Integer incompletePageSize = 10;
    private boolean export;
}
