package org.recap.util;

import org.junit.Test;
import org.recap.BaseTestCase;
import org.recap.model.jpa.CustomerCodeEntity;
import org.recap.model.jpa.RequestItemEntity;
import org.recap.model.search.RequestForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertNotNull;

/**
 * Created by rajeshbabuk on 29/10/16.
 */
public class RequestServiceUtilUT extends BaseTestCase {

    @Autowired
    RequestServiceUtil requestServiceUtil;

    private RequestForm getRequestForm() {
        RequestForm requestForm = new RequestForm();
        requestForm.setRequestId(1);
        requestForm.setPatronBarcode("43265854");
        requestForm.setSubmitted(true);
        requestForm.setItemBarcode("3324545547568535");
        requestForm.setStatus("active");
        requestForm.setDeliveryLocation("PB");
        requestForm.setVolumeNumber("1");
        requestForm.setMessage("testing");
        requestForm.setErrorMessage("testing");
        requestForm.setIssue("test");
        requestForm.setTotalPageCount(1);
        requestForm.setTotalRecordsCount("10");
        requestForm.setPageSize(1);
        requestForm.setPageNumber(1);
        requestForm.setRequestingInstitutions(Arrays.asList("PUL"));
        requestForm.setRequestTypes(Arrays.asList("Recall"));
        requestForm.setItemBarcodeInRequest("123");
        requestForm.setPatronBarcodeInRequest("46259871");
        requestForm.setRequestingInstitution("PUL");
        requestForm.setPatronEmailAddress("hemalatha.s@htcindia.com");
        requestForm.setItemTitle("test");
        requestForm.setItemOwningInstitution("PUL");
        requestForm.setInstitution("PUL");
        requestForm.setRequestType("recall");
        requestForm.setRequestNotes("test");
        requestForm.setStartPage("2");
        requestForm.setEndPage("5");
        requestForm.setArticleAuthor("john");
        requestForm.setArticleTitle("test");
        requestForm.setDeliveryLocationInRequest("PB");
        requestForm.setDeliveryLocations(new ArrayList<CustomerCodeEntity>());
        requestForm.setSearchResultRows(new ArrayList<>());
        requestForm.setShowResults(true);
        return requestForm;
    }
    @Test
    public void searchRequests()throws Exception {
        RequestForm requestForm = getRequestForm();
        Page<RequestItemEntity> requestItemEntities = requestServiceUtil.searchRequests(requestForm);
        assertNotNull(requestItemEntities);
    }
    @Test
    public void searchRequests1()throws Exception {
        RequestForm requestForm = getRequestForm();
        requestForm.setItemBarcode("");
        requestForm.setInstitution("");
        requestForm.setStatus("inactive");
        Page<RequestItemEntity> requestItemEntities = requestServiceUtil.searchRequests(requestForm);
        assertNotNull(requestItemEntities);
    }
    @Test
    public void searchRequests2()throws Exception {
        RequestForm requestForm = getRequestForm();
        requestForm.setStatus("");
        Page<RequestItemEntity> requestItemEntities = requestServiceUtil.searchRequests(requestForm);
        assertNotNull(requestItemEntities);
    }
    @Test
    public void searchRequests3()throws Exception {
        RequestForm requestForm = getRequestForm();
        requestForm.setStatus("inactive");
        Page<RequestItemEntity> requestItemEntities = requestServiceUtil.searchRequests(requestForm);
        assertNotNull(requestItemEntities);
    }
    @Test
    public void searchRequests4()throws Exception {
        RequestForm requestForm = getRequestForm();
        requestForm.setInstitution("");
        Page<RequestItemEntity> requestItemEntities = requestServiceUtil.searchRequests(requestForm);
        assertNotNull(requestItemEntities);
    }
    @Test
    public void searchRequests5()throws Exception {
        RequestForm requestForm = getRequestForm();
        requestForm.setInstitution("");
        requestForm.setStatus("inactive");
        Page<RequestItemEntity> requestItemEntities = requestServiceUtil.searchRequests(requestForm);
        assertNotNull(requestItemEntities);
    }
    @Test
    public void searchRequests6()throws Exception {
        RequestForm requestForm = getRequestForm();
        requestForm.setInstitution("");
        requestForm.setStatus("");
        Page<RequestItemEntity> requestItemEntities = requestServiceUtil.searchRequests(requestForm);
        assertNotNull(requestItemEntities);
    }
    @Test
    public void searchRequests7()throws Exception {
        RequestForm requestForm = getRequestForm();
        requestForm.setItemBarcode("");
        requestForm.setInstitution("");
        Page<RequestItemEntity> requestItemEntities = requestServiceUtil.searchRequests(requestForm);
        assertNotNull(requestItemEntities);
    }
    @Test
    public void searchRequests8()throws Exception {
        RequestForm requestForm = getRequestForm();
        requestForm.setItemBarcode("");
        requestForm.setInstitution("");
        requestForm.setStatus("inactive");
        Page<RequestItemEntity> requestItemEntities = requestServiceUtil.searchRequests(requestForm);
        assertNotNull(requestItemEntities);
    }
    @Test
    public void searchRequests9()throws Exception {
        RequestForm requestForm = getRequestForm();
        requestForm.setPatronBarcode("");
        requestForm.setInstitution("");
        requestForm.setStatus("inactive");
        Page<RequestItemEntity> requestItemEntities = requestServiceUtil.searchRequests(requestForm);
        assertNotNull(requestItemEntities);
    }
    @Test
    public void searchRequests10()throws Exception {
        RequestForm requestForm = getRequestForm();
        requestForm.setPatronBarcode("");
        requestForm.setInstitution("");
        Page<RequestItemEntity> requestItemEntities = requestServiceUtil.searchRequests(requestForm);
        assertNotNull(requestItemEntities);
    }
    @Test
    public void searchRequests11()throws Exception {
        RequestForm requestForm = getRequestForm();
        requestForm.setPatronBarcode("");
        requestForm.setStatus("inactive");
        Page<RequestItemEntity> requestItemEntities = requestServiceUtil.searchRequests(requestForm);
        assertNotNull(requestItemEntities);
    }
    @Test
    public void searchRequests12()throws Exception {
        RequestForm requestForm = getRequestForm();
        requestForm.setPatronBarcode("");
        Page<RequestItemEntity> requestItemEntities = requestServiceUtil.searchRequests(requestForm);
        assertNotNull(requestItemEntities);
    }
    @Test
    public void searchRequests13()throws Exception {
        RequestForm requestForm = getRequestForm();
        requestForm.setPatronBarcode("");
        requestForm.setStatus("");
        requestForm.setItemBarcode("");
        Page<RequestItemEntity> requestItemEntities = requestServiceUtil.searchRequests(requestForm);
        assertNotNull(requestItemEntities);
    }
    @Test
    public void searchRequests14()throws Exception {
        RequestForm requestForm = getRequestForm();
        requestForm.setPatronBarcode("");
        requestForm.setStatus("");
        requestForm.setItemBarcode("");
        Page<RequestItemEntity> requestItemEntities = requestServiceUtil.searchRequests(requestForm);
        assertNotNull(requestItemEntities);
    }
    @Test
    public void searchRequests15()throws Exception {
        RequestForm requestForm = getRequestForm();
        requestForm.setPatronBarcode("");
        requestForm.setItemBarcode("");
        Page<RequestItemEntity> requestItemEntities = requestServiceUtil.searchRequests(requestForm);
        assertNotNull(requestItemEntities);
    }
    @Test
    public void searchRequests16()throws Exception {
        RequestForm requestForm = getRequestForm();
        requestForm.setPatronBarcode("");
        requestForm.setStatus("inactive");
        requestForm.setItemBarcode("");
        Page<RequestItemEntity> requestItemEntities = requestServiceUtil.searchRequests(requestForm);
        assertNotNull(requestItemEntities);
    }
    @Test
    public void searchRequests17()throws Exception {
        RequestForm requestForm = getRequestForm();
        requestForm.setPatronBarcode("");
        requestForm.setItemBarcode("");
        requestForm.setInstitution("");
        Page<RequestItemEntity> requestItemEntities = requestServiceUtil.searchRequests(requestForm);
        assertNotNull(requestItemEntities);
    }
    @Test
    public void searchRequests18()throws Exception {
        RequestForm requestForm = getRequestForm();
        requestForm.setPatronBarcode("");
        requestForm.setStatus("inactive");
        requestForm.setItemBarcode("");
        requestForm.setInstitution("");
        Page<RequestItemEntity> requestItemEntities = requestServiceUtil.searchRequests(requestForm);
        assertNotNull(requestItemEntities);
    }
    @Test
    public void searchRequests19()throws Exception {
        RequestForm requestForm = getRequestForm();
        requestForm.setPatronBarcode("");
        requestForm.setStatus("");
        Page<RequestItemEntity> requestItemEntities = requestServiceUtil.searchRequests(requestForm);
        assertNotNull(requestItemEntities);
    }
    @Test
    public void searchRequests20()throws Exception {
        RequestForm requestForm = getRequestForm();
        requestForm.setPatronBarcode("");
        requestForm.setStatus("");
        requestForm.setInstitution("");
        Page<RequestItemEntity> requestItemEntities = requestServiceUtil.searchRequests(requestForm);
        assertNotNull(requestItemEntities);
    }
    @Test
    public void searchRequests21()throws Exception {
        RequestForm requestForm = getRequestForm();
        requestForm.setItemBarcode("");
        requestForm.setStatus("");
        Page<RequestItemEntity> requestItemEntities = requestServiceUtil.searchRequests(requestForm);
        assertNotNull(requestItemEntities);
    }
    @Test
    public void searchRequests22()throws Exception {
        RequestForm requestForm = getRequestForm();
        requestForm.setItemBarcode("");
        requestForm.setStatus("");
        requestForm.setInstitution("");
        Page<RequestItemEntity> requestItemEntities = requestServiceUtil.searchRequests(requestForm);
        assertNotNull(requestItemEntities);
    }
    @Test
    public void searchRequests23()throws Exception {
        RequestForm requestForm = getRequestForm();
        requestForm.setItemBarcode("");
        requestForm.setStatus("inactive");
        Page<RequestItemEntity> requestItemEntities = requestServiceUtil.searchRequests(requestForm);
        assertNotNull(requestItemEntities);
    }
    @Test
    public void searchRequests24()throws Exception {
        RequestForm requestForm = getRequestForm();
        requestForm.setItemBarcode("");
        Page<RequestItemEntity> requestItemEntities = requestServiceUtil.searchRequests(requestForm);
        assertNotNull(requestItemEntities);
    }
}