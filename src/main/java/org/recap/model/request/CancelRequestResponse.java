package org.recap.model.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by sudhishk on 15/12/16.
 */
@Setter
@Getter
public class CancelRequestResponse {
    private String screenMessage;
    private boolean success;
}
