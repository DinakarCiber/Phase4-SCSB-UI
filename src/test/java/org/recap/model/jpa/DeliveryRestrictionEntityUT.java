package org.recap.model.jpa;

import org.junit.Test;
import org.recap.BaseTestCase;

import java.util.Arrays;

import static org.junit.Assert.assertNotNull;

/**
 * Created by hemalathas on 17/7/17.
 */
public class DeliveryRestrictionEntityUT extends BaseTestCase{

    @Test
    public void testDeliveryRestrictionEntity(){
        DeliveryRestrictionEntity deliveryRestrictionEntity = new DeliveryRestrictionEntity();
        deliveryRestrictionEntity.setId(1);
        deliveryRestrictionEntity.setDeliveryRestriction("Test");
        deliveryRestrictionEntity.setInstitutionEntity(new InstitutionEntity());
        deliveryRestrictionEntity.setCustomerCodeEntityList(Arrays.asList(new CustomerCodeEntity()));
        assertNotNull(deliveryRestrictionEntity.getCustomerCodeEntityList());
        assertNotNull(deliveryRestrictionEntity.getDeliveryRestriction());
        assertNotNull(deliveryRestrictionEntity.getId());
        assertNotNull(deliveryRestrictionEntity.getInstitutionEntity());
    }

}