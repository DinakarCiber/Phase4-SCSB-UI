package org.recap.model.search;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rajeshbabuk on 2/1/17.
 */
@Setter
@Getter
public class SearchRecordsResponse {

    private List<SearchResultRow> searchResultRows = new ArrayList<>();
    private Integer totalPageCount = 0;
    private String totalBibRecordsCount = "0";
    private String totalItemRecordsCount = "0";
    private String totalRecordsCount = "0";
    private boolean showTotalCount;
    private String errorMessage;

}
