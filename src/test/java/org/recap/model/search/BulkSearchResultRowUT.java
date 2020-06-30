package org.recap.model.search;

import org.junit.Test;
import org.recap.BaseTestCase;

import java.util.Date;

import static junit.framework.TestCase.assertNotNull;

public class BulkSearchResultRowUT extends BaseTestCase {

    @Test
    public void testBulkSearchTResultRow(){

        BulkSearchResultRow bulkSearchResultRow = new BulkSearchResultRow();

        bulkSearchResultRow.setBulkRequestId(1);
        bulkSearchResultRow.setBulkRequestName("test");
        bulkSearchResultRow.setBulkRequestNotes("test");
        bulkSearchResultRow.setCreatedBy("test");
        bulkSearchResultRow.setCreatedDate(new Date());
        bulkSearchResultRow.setEmailAddress("test@gmail.com");
        bulkSearchResultRow.setDeliveryLocation("test");
        bulkSearchResultRow.setFileName("test");
        bulkSearchResultRow.setPatronBarcode("123");
        bulkSearchResultRow.setRequestingInstitution("PUL");
        bulkSearchResultRow.setStatus("SUCCESS");

        assertNotNull(bulkSearchResultRow.getBulkRequestId());
        assertNotNull(bulkSearchResultRow.getBulkRequestName());
        assertNotNull(bulkSearchResultRow.getBulkRequestNotes());
        assertNotNull(bulkSearchResultRow.getCreatedBy());
        assertNotNull(bulkSearchResultRow.getCreatedDate());
        assertNotNull(bulkSearchResultRow.getDeliveryLocation());
        assertNotNull(bulkSearchResultRow.getEmailAddress());
        assertNotNull(bulkSearchResultRow.getFileName());
        assertNotNull(bulkSearchResultRow.getPatronBarcode());
        assertNotNull(bulkSearchResultRow.getRequestingInstitution());
        assertNotNull(bulkSearchResultRow.getStatus());
    }
}
