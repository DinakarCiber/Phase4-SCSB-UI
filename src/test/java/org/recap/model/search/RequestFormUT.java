package org.recap.model.search;

import org.junit.Test;
import org.recap.BaseTestCase;
import org.recap.model.jpa.CustomerCodeEntity;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertNotNull;

/**
 * Created by hemalathas on 29/3/17.
 */
public class RequestFormUT extends BaseTestCase{

    @Test
    public void testRequestForm(){
        RequestForm requestForm = new RequestForm();
        requestForm.setRequestId(1);
        requestForm.setPatronBarcode("43265854");
        requestForm.setSubmitted(true);
        requestForm.setItemBarcode("3324545547568535");
        requestForm.setStatus("Success");
        requestForm.setDeliveryLocation("PB");
        requestForm.setVolumeNumber("1");
        requestForm.setMessage("testing");
        requestForm.setErrorMessage("testing");
        requestForm.setIssue("test");
        requestForm.setTotalPageCount(1);
        requestForm.setTotalRecordsCount("10");
        requestForm.setPageSize(1);
        requestForm.setPageNumber(1);
        requestForm.setRequestingInstitutions(Arrays.asList("PUL"));
        requestForm.setRequestTypes(Arrays.asList("Recall"));
        requestForm.setItemBarcodeInRequest("123");
        requestForm.setPatronBarcodeInRequest("46259871");
        requestForm.setRequestingInstitution("PUL");
        requestForm.setPatronEmailAddress("hemalatha.s@htcindia.com");
        requestForm.setItemTitle("test");
        requestForm.setItemOwningInstitution("PUL");
        requestForm.setRequestType("recall");
        requestForm.setRequestNotes("test");
        requestForm.setStartPage("2");
        requestForm.setEndPage("5");
        requestForm.setArticleAuthor("john");
        requestForm.setArticleTitle("test");
        requestForm.setDeliveryLocationInRequest("PB");
        requestForm.setDeliveryLocations(new ArrayList<CustomerCodeEntity>());
        requestForm.setSearchResultRows(new ArrayList<>());
        requestForm.setShowResults(true);

        assertNotNull(requestForm.getRequestId());
        assertNotNull(requestForm.getPatronBarcode());
        assertNotNull(requestForm.getItemBarcode());
        assertNotNull(requestForm.getStatus());
        assertNotNull(requestForm.getDeliveryLocation());
        assertNotNull(requestForm.getPatronBarcodeInRequest());
        assertNotNull(requestForm.getItemBarcodeInRequest());
        assertNotNull(requestForm.getDeliveryLocationInRequest());
        assertNotNull(requestForm.getItemTitle());
        assertNotNull(requestForm.getItemOwningInstitution());
        assertNotNull(requestForm.getPatronEmailAddress());
        assertNotNull(requestForm.getRequestingInstitution());
        assertNotNull(requestForm.getRequestType());
        assertNotNull(requestForm.getRequestNotes());
        assertNotNull(requestForm.getStartPage());
        assertNotNull(requestForm.getEndPage());
        assertNotNull(requestForm.getVolumeNumber());
        assertNotNull(requestForm.getIssue());
        assertNotNull(requestForm.getArticleAuthor());
        assertNotNull(requestForm.getArticleTitle());
        assertNotNull(requestForm.getMessage());
        assertNotNull(requestForm.getErrorMessage());
        assertNotNull(requestForm.getTotalRecordsCount());
        assertNotNull(requestForm.getPageNumber());
        assertNotNull(requestForm.getPageSize());
        assertNotNull(requestForm.getTotalPageCount());
        assertNotNull(requestForm.isSubmitted());
        assertNotNull(requestForm.isShowResults());
        assertNotNull(requestForm.getRequestingInstitutions());
        assertNotNull(requestForm.getRequestTypes());
        assertNotNull(requestForm.getDeliveryLocations());
        assertNotNull(requestForm.getSearchResultRows());
        assertNotNull(requestForm.getRequestStatuses());
    }


}