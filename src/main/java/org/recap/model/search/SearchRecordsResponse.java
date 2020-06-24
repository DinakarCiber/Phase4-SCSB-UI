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
public class SearchRecordsResponse extends SearchRecordsCommonResponse {

    private List<SearchResultRow> searchResultRows = new ArrayList<>();
}
