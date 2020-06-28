package org.recap.model.request;

import org.junit.Test;
import org.recap.BaseTestCase;
import org.recap.RecapCommonConstants;
import org.recap.RecapConstants;
import org.recap.model.search.RequestForm;

import java.util.Arrays;
import java.util.Date;

import static org.junit.Assert.assertNotNull;

public class ReplaceRequestUT extends BaseTestCase {

    @Test
    public void testReplaceRequest(){

        ReplaceRequest replaceRequest = new ReplaceRequest();
        RequestForm requestForm = getRequestForm();
        replaceRequest.setEndRequestId("10");
        replaceRequest.setFromDate((new Date()).toString());
        replaceRequest.setToDate((new Date()).toString());
        replaceRequest.setReplaceRequestByType(RecapCommonConstants.REQUEST_IDS);
        replaceRequest.setRequestStatus(RecapConstants.EXCEPTION);
        String requestId = String.valueOf(requestForm.getRequestId());
        replaceRequest.setRequestIds(requestId);
        replaceRequest.setStartRequestId("1");

        assertNotNull(replaceRequest.getStartRequestId());
        assertNotNull(replaceRequest.getEndRequestId());
        assertNotNull(replaceRequest.getFromDate());
        assertNotNull(replaceRequest.getToDate());
        assertNotNull(replaceRequest.getReplaceRequestByType());
        assertNotNull(replaceRequest.getRequestIds());
        assertNotNull(replaceRequest.getRequestStatus());
    }
    private RequestForm getRequestForm(){
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
        return requestForm;
    }

}
