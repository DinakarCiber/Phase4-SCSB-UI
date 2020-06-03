package org.recap.model.search;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by rajeshbabuk on 11/10/17.
 */
@Setter
@Getter
public class BulkRequestResponse {

    private Integer bulkRequestId;
    private String screenMessage;
    private boolean success;

}
