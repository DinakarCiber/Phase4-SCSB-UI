package org.recap.controller;

import org.recap.model.jpa.RequestItemEntity;
import org.recap.model.search.RequestForm;
import org.recap.model.search.SearchResultRow;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.validation.Valid;

public class RecapController extends AbstractController {

    public SearchResultRow setSearchResultRow(RequestItemEntity requestItemEntity) {
        SearchResultRow searchResultRow = new SearchResultRow();
        searchResultRow.setRequestId(requestItemEntity.getId());
        searchResultRow.setRequestingInstitution(requestItemEntity.getInstitutionEntity().getInstitutionCode());
        searchResultRow.setBarcode(requestItemEntity.getItemEntity().getBarcode());
        searchResultRow.setOwningInstitution(requestItemEntity.getItemEntity().getInstitutionEntity().getInstitutionCode());
        searchResultRow.setRequestType(requestItemEntity.getRequestTypeEntity().getRequestTypeCode());
        searchResultRow.setAvailability(requestItemEntity.getItemEntity().getItemStatusEntity().getStatusCode());
        searchResultRow.setCreatedDate(requestItemEntity.getCreatedDate());
        searchResultRow.setLastUpdatedDate(requestItemEntity.getLastUpdatedDate());
        searchResultRow.setStatus(requestItemEntity.getRequestStatusEntity().getRequestStatusDescription());
        searchResultRow.setRequestNotes(requestItemEntity.getNotes());
        return  searchResultRow;
    }

    public void disableRequestSearchInstitutionDropDown(@Valid @ModelAttribute("requestForm") RequestForm requestForm) {
        if (requestForm.getInstitutionList().size() == 1){
            requestForm.setDisableSearchInstitution(true);
            requestForm.setInstitution(requestForm.getSearchInstitutionHdn());
        }
    }
}
