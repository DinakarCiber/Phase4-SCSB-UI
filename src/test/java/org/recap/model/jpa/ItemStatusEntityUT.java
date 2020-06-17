package org.recap.model.jpa;

import org.junit.Test;
import org.recap.BaseTestCase;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by hemalathas on 22/6/16.
 */
public class ItemStatusEntityUT extends BaseTestCase {

    @Test
    public void itemStatus(){
        ItemStatusEntity itemStatusEntity = new ItemStatusEntity();
        itemStatusEntity.setStatusCode("RecentlyReturned");
        itemStatusEntity.setStatusDescription("RecentlyReturned");
        ItemStatusEntity itemStatusEntity1 = itemStatusDetailsRepository.save(itemStatusEntity);
        assertNotNull(itemStatusEntity1);
        System.out.println(itemStatusEntity1.getId());
        assertEquals(itemStatusEntity1.getStatusCode(),"RecentlyReturned");
        assertEquals(itemStatusEntity1.getStatusDescription(),"RecentlyReturned");
        itemStatusDetailsRepository.delete(itemStatusEntity);
    }

}