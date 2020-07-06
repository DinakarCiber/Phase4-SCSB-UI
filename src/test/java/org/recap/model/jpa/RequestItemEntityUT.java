package org.recap.model.jpa;

import org.junit.Test;
import org.recap.BaseTestCase;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

import static org.junit.Assert.assertNotNull;

public class RequestItemEntityUT extends BaseTestCase {

    @Test
    public void testRequestItemEntity(){
        RequestItemEntity requestItemEntity = new RequestItemEntity();
        requestItemEntity.setCreatedBy("Test");
        requestItemEntity.setCreatedDate(new Date());
        requestItemEntity.setEmailId("test@gmail.com");
        requestItemEntity.setItemId(1);
        requestItemEntity.setRequestExpirationDate(new Date());
        requestItemEntity.setLastUpdatedDate(new Date());
        requestItemEntity.setPatronId("123");
        assertNotNull(requestItemEntity.getCreatedBy());
        assertNotNull(requestItemEntity.getCreatedDate());
        assertNotNull(requestItemEntity.getLastUpdatedDate());
        assertNotNull(requestItemEntity.getEmailId());
        assertNotNull(requestItemEntity.getItemId());
        assertNotNull(requestItemEntity.getRequestExpirationDate());
    }
}
