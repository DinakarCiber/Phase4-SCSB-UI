package org.recap.model.request;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by rajeshbabuk on 15/11/17.
 */
@Setter
@Getter
public class ReplaceRequest {

    private String replaceRequestByType;
    private String requestStatus;
    private String requestIds;
    private String startRequestId;
    private String endRequestId;
    private String fromDate;
    private String toDate;

}
