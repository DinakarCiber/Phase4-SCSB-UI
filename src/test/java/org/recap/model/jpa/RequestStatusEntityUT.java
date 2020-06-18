package org.recap.model.jpa;

import org.junit.Test;
import org.recap.BaseTestCase;
import org.recap.repository.jpa.RequestStatusDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertNotNull;

/**
 * Created by hemalathas on 20/2/17.
 */
public class RequestStatusEntityUT extends BaseTestCase{

    @Autowired
    RequestStatusDetailsRepository requestStatusDetailsRepository;

    @Test
    public void testRequestStatus(){
        RequestStatusEntity requestStatusEntity = new RequestStatusEntity();
        requestStatusEntity.setRequestStatusCode("recall");
        requestStatusEntity.setRequestStatusDescription("recall");
        RequestStatusEntity savedRequestStatusEntity = requestStatusDetailsRepository.save(requestStatusEntity);
        assertNotNull(savedRequestStatusEntity);
        assertNotNull(savedRequestStatusEntity.getId());
    }

}