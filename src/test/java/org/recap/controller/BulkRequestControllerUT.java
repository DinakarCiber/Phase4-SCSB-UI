package org.recap.controller;

import org.apache.shiro.authc.UsernamePasswordToken;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.recap.BaseTestCase;
import org.recap.RecapCommonConstants;
import org.recap.RecapConstants;
import org.recap.model.jpa.InstitutionEntity;
import org.recap.model.request.ItemRequestInformation;
import org.recap.model.search.*;
import org.recap.model.usermanagement.UserDetailsForm;
import org.recap.repository.jpa.InstitutionDetailsRepository;
import org.recap.service.BulkRequestService;
import org.recap.service.RestHeaderService;
import org.recap.util.SearchUtil;
import org.recap.util.UserAuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.support.BindingAwareModelMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

public class BulkRequestControllerUT extends BaseTestCase {

    @Mock
    Model model;

    @Autowired
    RestHeaderService restHeaderService;

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @Mock
    HttpSession session;

    @Value("${scsb.url}")
    private String scsbUrl;

    @Autowired
    BulkRequestController bulkRequestController;

    @Mock
    BulkRequestController mockedBulkRequest;

    @Mock
    MultipartFile file;



    @Test
    public void testBulkRequest() throws Exception{
        try{
        when(request.getSession(false)).thenReturn(session);
        String response = bulkRequestController.bulkRequest(model,request);
        assertNotNull(response);}
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void searchFirst() throws Exception{
        BulkRequestForm bulkRequestForm = new BulkRequestForm();
        ModelAndView modelAndView = bulkRequestController.searchFirst(bulkRequestForm,model);
        assertNotNull(modelAndView);
    }
    @Test
    public void searchNext() throws Exception{
        BulkRequestForm bulkRequestForm = getBulkRequestForm();
        ModelAndView modelAndView = bulkRequestController.searchNext(bulkRequestForm,model);
        assertNotNull(modelAndView);
    }

    @Test
    public void searchPrevious() throws Exception{
        BulkRequestForm bulkRequestForm = getBulkRequestForm();
        ModelAndView modelAndView = bulkRequestController.searchPrevious(bulkRequestForm,model);
        assertNotNull(modelAndView);
    }
    @Test
    public void searchLast() throws Exception {
        BulkRequestForm bulkRequestForm = getBulkRequestForm();
        ModelAndView modelAndView = bulkRequestController.searchLast(bulkRequestForm, model);
        assertNotNull(modelAndView);
    }
    @Test
    public void onPageSizeChange() throws Exception{
        BulkRequestForm bulkRequestForm = getBulkRequestForm();
        ModelAndView modelAndView = bulkRequestController.onPageSizeChange(bulkRequestForm,model);
        assertNotNull(modelAndView);
    }
    @Test
    public void searchRequest() throws Exception{
        BulkSearchResultRow bulkSearchResultRow = new BulkSearchResultRow();

        bulkSearchResultRow.setBulkRequestId(1);
        bulkSearchResultRow.setBulkRequestName("test");
        bulkSearchResultRow.setBulkRequestNotes("test");
        bulkSearchResultRow.setCreatedBy("test");
        bulkSearchResultRow.setCreatedDate(new Date());
        bulkSearchResultRow.setEmailAddress("test@gmail.com");
        bulkSearchResultRow.setDeliveryLocation("test");
        bulkSearchResultRow.setFileName("test");
        bulkSearchResultRow.setPatronBarcode("123");
        bulkSearchResultRow.setRequestingInstitution("PUL");
        bulkSearchResultRow.setStatus("SUCCESS");
        BulkRequestForm bulkRequestForm = getBulkRequestForm();
        bulkRequestForm.setBulkSearchResultRows(Arrays.asList(bulkSearchResultRow));
        ModelAndView modelAndView = bulkRequestController.searchRequest(bulkRequestForm,model);
        assertNotNull(modelAndView);
    }
    @Test
    public void searchRequest1() throws Exception{

        BulkRequestForm bulkRequestForm = getBulkRequestForm();
        ModelAndView modelAndView = bulkRequestController.searchRequest(bulkRequestForm,model);
        assertNotNull(modelAndView);
    }
    @Test
    public void createRequest() throws Exception{
        when(request.getSession(false)).thenReturn(session);
        BulkRequestForm bulkRequestForm = getBulkRequestForm();
        ModelAndView modelAndView = bulkRequestController.createRequest(bulkRequestForm,file,model,request);
        assertNotNull(modelAndView);
    }
    @Test
    public void loadCreateRequest(){
        BulkRequestForm bulkRequestForm = getBulkRequestForm();
        ModelAndView modelAndView = bulkRequestController.loadCreateRequest(bulkRequestForm,model);
        assertNotNull(modelAndView);
    }
    @Test
    public void loadSearchRequest(){
        BulkRequestForm bulkRequestForm = getBulkRequestForm();
        ModelAndView modelAndView = bulkRequestController.loadSearchRequest(model);
        assertNotNull(modelAndView);
    }
    @Test
    public void searchRequestByPatronBarcode(){
        BulkRequestForm bulkRequestForm = getBulkRequestForm();
        ModelAndView modelAndView = bulkRequestController.searchRequestByPatronBarcode(bulkRequestForm.getPatronBarcodeInRequest(),model);
        assertNotNull(modelAndView);
    }
    @Test
    public void loadCreateRequestForSamePatron(){
        BulkRequestForm bulkRequestForm = getBulkRequestForm();
        ModelAndView modelAndView = bulkRequestController.loadCreateRequestForSamePatron(bulkRequestForm,model);
        assertNotNull(modelAndView);
    }
    @Test
    public void populateDeliveryLocations(){
        BulkRequestForm bulkRequestForm = getBulkRequestForm();
        ModelAndView modelAndView = bulkRequestController.populateDeliveryLocations(bulkRequestForm,model);
        assertNotNull(modelAndView);
    }
    @Test
    public void downloadReports() throws Exception{
        BulkRequestForm bulkRequestForm = getBulkRequestForm();
        String bulkRequestId = bulkRequestForm.getRequestId().toString();
      //  bulkRequestController.downloadReports(bulkRequestId,response,model);

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
        bulkRequestForm.setInstitution("NYPL");
        bulkRequestForm.setItemBarcode("123");
        bulkRequestForm.setPatronBarcode("23456");
        bulkRequestForm.setPatronBarcodeInRequest("23456");
        bulkRequestForm.setItemOwningInstitution("PUL");
        bulkRequestForm.setRequestingInstitution("PUL");
        bulkRequestForm.setRequestingInstituionHidden("PUL");
        bulkRequestForm.setRequestId(1);
        bulkRequestForm.setRequestIdSearch("1");
        bulkRequestForm.setItemTitle("test");
        bulkRequestForm.setMessage("test");
        bulkRequestForm.setPageNumber(200);
        bulkRequestForm.setPageSize(20);
        bulkRequestForm.setTotalRecordsCount("40");
        bulkRequestForm.setTotalPageCount(2);
        bulkRequestForm.setStatus("PROCESSED");
        bulkRequestForm.setSubmitted(true);

        return bulkRequestForm;
    }

}
