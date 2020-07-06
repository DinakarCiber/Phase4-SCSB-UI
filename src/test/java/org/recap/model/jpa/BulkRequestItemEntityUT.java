package org.recap.model.jpa;

import org.junit.Test;
import org.recap.BaseTestCase;
import java.util.Date;
import static junit.framework.TestCase.assertNotNull;
public class BulkRequestItemEntityUT extends BaseTestCase {

    @Test
    public void testBulkRequestItemEntity(){

        BulkRequestItemEntity bulkRequestItemEntity = new BulkRequestItemEntity();

        bulkRequestItemEntity.setBulkRequestFileName("test");
        bulkRequestItemEntity.setBulkRequestName("test");
        bulkRequestItemEntity.setBulkRequestStatus("SUCCESS");
        bulkRequestItemEntity.setCreatedBy("test");
        bulkRequestItemEntity.setCreatedDate(new Date());
        bulkRequestItemEntity.setEmailId("test@gmail.com");
        bulkRequestItemEntity.setLastUpdatedDate(new Date());
        bulkRequestItemEntity.setNotes("test");
        bulkRequestItemEntity.setPatronId("123");
        bulkRequestItemEntity.setRequestingInstitutionId(1);
        bulkRequestItemEntity.setStopCode("test");

        assertNotNull(bulkRequestItemEntity.getBulkRequestFileName());
        assertNotNull(bulkRequestItemEntity.getBulkRequestName());
        assertNotNull(bulkRequestItemEntity.getBulkRequestStatus());
        assertNotNull(bulkRequestItemEntity.getCreatedBy());
        assertNotNull(bulkRequestItemEntity.getCreatedDate());
        assertNotNull(bulkRequestItemEntity.getEmailId());
        assertNotNull(bulkRequestItemEntity.getLastUpdatedDate());
        assertNotNull(bulkRequestItemEntity.getNotes());
        assertNotNull(bulkRequestItemEntity.getPatronId());
        assertNotNull(bulkRequestItemEntity.getRequestingInstitutionId());
        assertNotNull(bulkRequestItemEntity.getStopCode());
    }
}
