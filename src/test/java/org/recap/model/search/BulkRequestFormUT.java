package org.recap.model.search;


import org.junit.Test;
import org.recap.BaseTestCase;

import static org.junit.Assert.assertNotNull;


public class BulkRequestFormUT extends BaseTestCase {

    @Test
    public void testBulkRequestForm(){

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

        assertNotNull(bulkRequestForm.getMessage());
        assertNotNull(bulkRequestForm.getErrorMessage());
        assertNotNull(bulkRequestForm.getBulkRequestName());
        assertNotNull(bulkRequestForm.getDeliveryLocation());
        assertNotNull(bulkRequestForm.getDeliveryLocationInRequest());
        assertNotNull(bulkRequestForm.getDisableSearchInstitution());
        assertNotNull(bulkRequestForm.getFileName());
        assertNotNull(bulkRequestForm.getInstitution());
        assertNotNull(bulkRequestForm.getItemBarcode());
        assertNotNull(bulkRequestForm.getItemOwningInstitution());
        assertNotNull(bulkRequestForm.getItemTitle());
        assertNotNull(bulkRequestForm.getPageNumber());
        assertNotNull(bulkRequestForm.getPageSize());
        assertNotNull(bulkRequestForm.getStatus());

    }
}
