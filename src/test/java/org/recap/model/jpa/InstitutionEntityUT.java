package org.recap.model.jpa;

import org.junit.Test;
import org.recap.BaseTestCase;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by hemalathas on 22/6/16.
 */
public class InstitutionEntityUT extends BaseTestCase {

    @Test
    public void institutionEntity(){
        InstitutionEntity institutionEntity = new InstitutionEntity();
        institutionEntity.setInstitutionCode("UC");
        institutionEntity.setInstitutionName("University of Chicago");
        InstitutionEntity entity = institutionDetailRepository.save(institutionEntity);
        assertNotNull(entity);
        System.out.println("Institution Id-->"+entity.getId());
        assertEquals(entity.getInstitutionCode(),"UC");
        assertEquals(entity.getInstitutionName(),"University of Chicago");
        institutionDetailRepository.delete(institutionEntity);
    }

}