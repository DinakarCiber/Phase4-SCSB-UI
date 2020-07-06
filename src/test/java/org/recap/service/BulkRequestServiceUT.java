package org.recap.service;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.recap.BaseTestCase;
import org.recap.model.search.BulkRequestForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.mockito.Mockito.when;

public class BulkRequestServiceUT extends BaseTestCase {

    @Mock
    HttpServletRequest request;

    @Mock
    HttpSession session;

    @Autowired
    RestHeaderService restHeaderService;

    @Autowired
    BulkRequestService bulkRequestService;

    @Mock
    BulkRequestService mockedBulkRequestService;

    @Mock
    RestTemplate restTemplate;

    @Value("${scsb.url}")
    String scsbUrl;

    @Value("${scsb.shiro}")
    String scsbShiro;




    @Test
    public void testProcessCreateBulkRequest(){
        BulkRequestForm bulkRequestForm = getBulkRequestForm();
        when(request.getSession()).thenReturn(session);
        bulkRequestService.processCreateBulkRequest(bulkRequestForm,request);
    }

    @Test
    public void testProcessSearchRequest(){
        BulkRequestForm bulkRequestForm = getBulkRequestForm();
        bulkRequestService.processSearchRequest(bulkRequestForm);
    }

    @Test
    public void testProcessOnPageSizeChange(){
        BulkRequestForm bulkRequestForm = getBulkRequestForm();
        bulkRequestService.processOnPageSizeChange(bulkRequestForm);
    }

    @Test
    public void testGetPaginatedSearchResults(){
        BulkRequestForm bulkRequestForm = getBulkRequestForm();
        bulkRequestService.getPaginatedSearchResults(bulkRequestForm);
    }

    @Test
    public void testProcessDeliveryLocations(){
        BulkRequestForm bulkRequestForm = getBulkRequestForm();
        bulkRequestService.processDeliveryLocations(bulkRequestForm);
    }

    @Test
    public void testSaveUpadatedRequestStatus() throws Exception{
        BulkRequestForm bulkRequestForm = getBulkRequestForm();
        bulkRequestService.saveUpadatedRequestStatus(bulkRequestForm.getRequestId());
    }


    private BulkRequestForm getBulkRequestForm(){
        BulkRequestForm bulkRequestForm = new BulkRequestForm();
        bulkRequestForm.setBulkRequestName("Test");
        bulkRequestForm.setDeliveryLocation("test");
        bulkRequestForm.setDeliveryLocationInRequest("test");
        bulkRequestForm.setDisableRequestingInstitution(false);
        bulkRequestForm.setDisableSearchInstitution(true);
        bulkRequestForm.setErrorMessage("ERROR");
        bulkRequestForm.setFileName("test");
        bulkRequestForm.setInstitution("PUL");
        bulkRequestForm.setItemBarcode("123");
        bulkRequestForm.setItemOwningInstitution("PUL");
        bulkRequestForm.setItemTitle("test");
        bulkRequestForm.setMessage("test");
        bulkRequestForm.setPageNumber(1);
        bulkRequestForm.setPageSize(20);
        bulkRequestForm.setTotalRecordsCount("40");
        bulkRequestForm.setTotalPageCount(20);
        bulkRequestForm.setStatus("SUCCESS");
        bulkRequestForm.setSubmitted(true);
        bulkRequestForm.setRequestingInstitution("PUL");
        bulkRequestForm.setRequestId(1);
        bulkRequestForm.setRequestIdSearch("1");
        return bulkRequestForm;
    }
}
