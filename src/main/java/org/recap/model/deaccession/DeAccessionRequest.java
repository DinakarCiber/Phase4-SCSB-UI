package org.recap.model.deaccession;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenchulakshmig on 11/10/16.
 */
@Getter
@Setter
public class DeAccessionRequest {

    private List<DeAccessionItem> deAccessionItems = new ArrayList<>();
    private String username;
    private String notes;

}
