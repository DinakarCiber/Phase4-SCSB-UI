package org.recap.util;

import org.junit.Test;
import org.recap.BaseTestCase;
import org.recap.model.jpa.RequestItemEntity;
import org.recap.model.search.RequestForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import static org.junit.Assert.assertNotNull;

/**
 * Created by rajeshbabuk on 29/10/16.
 */
public class RequestServiceUtilUT extends BaseTestCase {

    @Autowired
    RequestServiceUtil requestServiceUtil;

    private RequestForm getRequestForm() {
        RequestForm requestForm = new RequestForm();
        requestForm.setRequestId(01);
        requestForm.setItemOwningInstitution("PUL");
        requestForm.setRequestingInstitution("PUL");
        requestForm.setRequestType("RETRIVAL");
        return requestForm;
    }
    @Test
    public void searchRequests()throws Exception {
        RequestForm requestForm = getRequestForm();
        Page<RequestItemEntity> requestItemEntities = requestServiceUtil.searchRequests(requestForm);
        assertNotNull(requestItemEntities);
    }
}
