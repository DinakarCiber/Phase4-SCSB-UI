package org.recap.service;

import org.junit.Test;
import org.recap.BaseTestCase;
import org.recap.model.jpa.BulkRequestItemEntity;
import org.recap.model.search.BulkRequestForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import static org.junit.Assert.assertNotNull;


public class BulkSearchRequestServiceUT extends BaseTestCase {

    @Autowired
    BulkSearchRequestService bulkSearchRequestService;

    @Test
    public void testProcessSearch(){
        BulkRequestForm bulkRequestForm = getBulkRequestForm();
        bulkRequestForm.setRequestIdSearch("123");
        bulkRequestForm.setRequestNameSearch("Test");
        bulkRequestForm.setPatronBarcodeSearch("");
        Page<BulkRequestItemEntity> bulkRequestItemEntities = bulkSearchRequestService.processSearchRequest(bulkRequestForm);
        assertNotNull(bulkRequestItemEntities);
    }
    @Test
    public void testProcessSearch2(){
        BulkRequestForm bulkRequestForm = getBulkRequestForm();
        bulkRequestForm.setRequestIdSearch("1");
        bulkRequestForm.setRequestNameSearch("");
        bulkRequestForm.setPatronBarcodeSearch("");
        Page<BulkRequestItemEntity> bulkRequestItemEntities = bulkSearchRequestService.processSearchRequest(bulkRequestForm);
        assertNotNull(bulkRequestItemEntities);
    }
    @Test
    public void testProcessSearch3(){
        BulkRequestForm bulkRequestForm = getBulkRequestForm();
        bulkRequestForm.setRequestIdSearch("");
        bulkRequestForm.setRequestNameSearch("Test");
        bulkRequestForm.setPatronBarcodeSearch("");
        Page<BulkRequestItemEntity> bulkRequestItemEntities = bulkSearchRequestService.processSearchRequest(bulkRequestForm);
        assertNotNull(bulkRequestItemEntities);
    }
    public void testProcessSearch4(){
        BulkRequestForm bulkRequestForm = getBulkRequestForm();
        bulkRequestForm.setRequestIdSearch("");
        bulkRequestForm.setRequestNameSearch("");
        bulkRequestForm.setPatronBarcodeSearch("123");
        Page<BulkRequestItemEntity> bulkRequestItemEntities = bulkSearchRequestService.processSearchRequest(bulkRequestForm);
        assertNotNull(bulkRequestItemEntities);
    }

    @Test
    public void testProcessSearch5(){
        BulkRequestForm bulkRequestForm = getBulkRequestForm();
        bulkRequestForm.setRequestIdSearch("123");
        bulkRequestForm.setRequestNameSearch("");
        bulkRequestForm.setPatronBarcodeSearch("123");
        Page<BulkRequestItemEntity> bulkRequestItemEntities = bulkSearchRequestService.processSearchRequest(bulkRequestForm);
        assertNotNull(bulkRequestItemEntities);
    }
    @Test
    public void testProcessSearch6(){
        BulkRequestForm bulkRequestForm = getBulkRequestForm();
        bulkRequestForm.setRequestIdSearch("");
        bulkRequestForm.setRequestNameSearch("Test");
        bulkRequestForm.setPatronBarcodeSearch("123");
        Page<BulkRequestItemEntity> bulkRequestItemEntities = bulkSearchRequestService.processSearchRequest(bulkRequestForm);
        assertNotNull(bulkRequestItemEntities);
    }
    @Test
    public void testProcessSearch7(){
        BulkRequestForm bulkRequestForm = getBulkRequestForm();
        bulkRequestForm.setRequestIdSearch("123");
        bulkRequestForm.setRequestNameSearch("Test");
        bulkRequestForm.setPatronBarcodeSearch("123");
        Page<BulkRequestItemEntity> bulkRequestItemEntities = bulkSearchRequestService.processSearchRequest(bulkRequestForm);
        assertNotNull(bulkRequestItemEntities);
    }
    private BulkRequestForm getBulkRequestForm(){
        BulkRequestForm bulkRequestForm = new BulkRequestForm();
        bulkRequestForm.setBulkRequestName("Test");
        bulkRequestForm.setDeliveryLocation("test");
        bulkRequestForm.setDeliveryLocationInRequest("test");
        bulkRequestForm.setDisableRequestingInstitution(false);
        bulkRequestForm.setDisableSearchInstitution(true);
        bulkRequestForm.setErrorMessage("ERROR");
        bulkRequestForm.setFileName("test");
        bulkRequestForm.setInstitution("NYPL");
        bulkRequestForm.setItemBarcode("123");
        bulkRequestForm.setItemOwningInstitution("NYPL");
        bulkRequestForm.setItemTitle("test");
        bulkRequestForm.setMessage("test");
        bulkRequestForm.setPageNumber(200);
        bulkRequestForm.setPageSize(20);
        bulkRequestForm.setStatus("SUCCESS");
        bulkRequestForm.setSubmitted(true);
        return bulkRequestForm;
    }



}
