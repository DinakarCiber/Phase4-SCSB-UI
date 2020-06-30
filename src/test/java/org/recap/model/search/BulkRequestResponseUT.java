package org.recap.model.search;

import org.junit.Test;
import org.recap.BaseTestCase;

import static org.junit.Assert.assertNotNull;

public class BulkRequestResponseUT extends BaseTestCase {

    @Test
    public void testBulkRequestResponse(){

        BulkRequestResponse bulkRequestResponse = new BulkRequestResponse();
        bulkRequestResponse.setBulkRequestId(1);
        bulkRequestResponse.setScreenMessage("SUCCEESS");
        bulkRequestResponse.setSuccess(true);

        assertNotNull(bulkRequestResponse.getBulkRequestId());
        assertNotNull(bulkRequestResponse.getScreenMessage());
    }
}
