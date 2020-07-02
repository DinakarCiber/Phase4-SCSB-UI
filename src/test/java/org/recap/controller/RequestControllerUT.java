package org.recap.controller;

import org.apache.shiro.authc.UsernamePasswordToken;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.recap.RecapCommonConstants;
import org.recap.RecapConstants;
import org.recap.model.jpa.BibliographicEntity;
import org.recap.model.jpa.HoldingsEntity;
import org.recap.model.jpa.ItemEntity;
import org.recap.model.jpa.InstitutionEntity;
import org.recap.model.jpa.RequestItemEntity;
import org.recap.model.jpa.RequestTypeEntity;
import org.recap.model.jpa.CustomerCodeEntity;
import org.recap.model.jpa.RequestStatusEntity;
import org.recap.model.CancelRequestResponse;
import org.recap.model.request.ItemRequestInformation;
import org.recap.model.request.ItemResponseInformation;
import org.recap.model.request.ReplaceRequest;
import org.recap.model.search.RequestForm;
import org.recap.model.search.SearchResultRow;
import org.recap.model.usermanagement.UserDetailsForm;
import org.recap.repository.jpa.InstitutionDetailsRepository;
import org.recap.repository.jpa.RequestTypeDetailsRepository;
import org.recap.repository.jpa.CustomerCodeDetailsRepository;
import org.recap.repository.jpa.ItemDetailsRepository;
import org.recap.repository.jpa.RequestItemDetailsRepository;
import org.recap.repository.jpa.RequestStatusDetailsRepository;
import org.recap.service.RequestService;
import org.recap.service.RestHeaderService;
import org.recap.util.RequestServiceUtil;
import org.recap.util.UserAuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BindingResult;
import org.springframework.validation.support.BindingAwareModelMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import static org.mockito.Mockito.when;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by rajeshbabuk on 21/10/16.
 */
public class RequestControllerUT extends BaseControllerUT {


    @Mock
    BindingAwareModelMap model;

    @Mock
    RequestService requestService;

    @Mock
    BindingResult bindingResult;

    @Mock
    RequestController requestController;

    @Autowired
    RequestController requestControllerWired;

    @Mock
    RequestServiceUtil requestServiceUtil;

    @Mock
    InstitutionDetailsRepository institutionDetailsRepository;

    @Mock
    RequestTypeDetailsRepository requestTypeDetailsRepository;

    @Mock
    CustomerCodeDetailsRepository customerCodeDetailsRepository;

    @Mock
    HttpSession session;

    @Mock
    javax.servlet.http.HttpServletRequest request;

    @Mock
    private UserAuthUtil userAuthUtil;

    @Mock
    ItemDetailsRepository itemDetailsRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Mock
    RestTemplate restTemplate;

    @Value("${scsb.url}")
    String scsbUrl;

    @Value("${scsb.shiro}")
    String scsbShiro;

    @Autowired
    RestHeaderService restHeaderService;

    @Mock
    RequestItemDetailsRepository requestItemDetailsRepository;

    @Mock
    RequestStatusDetailsRepository requestStatusDetailsRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(requestController).build();
    }

    public BindingAwareModelMap getModel() {
        return model;
    }

    public String getScsbUrl() {
        return scsbUrl;
    }

    public String getScsbShiro() {
        return scsbShiro;
    }

    @Test
    public void request() throws Exception{
        Mockito.when(request.getSession(false)).thenReturn(session);
        Mockito.when(userAuthUtil.authorizedUser(RecapConstants.SCSB_SHIRO_REQUEST_URL,(UsernamePasswordToken)session.getAttribute(RecapConstants.USER_TOKEN))).thenReturn(true);
        Mockito.when(requestController.getUserAuthUtil()).thenReturn(userAuthUtil);
        UserDetailsForm userDetailsForm = getUserDetails();
        Mockito.when(requestController.getUserAuthUtil().getUserDetails(request.getSession(false), RecapConstants.REQUEST_PRIVILEGE)).thenReturn(userDetailsForm);
        Mockito.when(requestService.setDefaultsToCreateRequest(userDetailsForm,model)).thenCallRealMethod();
        InstitutionEntity institutionEntity = new InstitutionEntity();
        institutionEntity.setInstitutionCode("CUL");
        institutionEntity.setId(2);
        List<InstitutionEntity> institutionEntityList = new ArrayList<>();
        institutionEntityList.add(institutionEntity);
        Mockito.when(requestService.getInstitutionDetailsRepository()).thenReturn(institutionDetailsRepository);
        Mockito.when(requestService.getRequestTypeDetailsRepository()).thenReturn(requestTypeDetailsRepository);
        Mockito.when(requestService.setFormDetailsForRequest(model,request,userDetailsForm)).thenCallRealMethod();
        Mockito.when(requestServiceUtil.searchRequests(getRequestForm())).thenCallRealMethod();
        Mockito.when(institutionDetailsRepository.findByInstitutionCode(institutionEntity.getInstitutionCode())).thenReturn(institutionEntity);        when(institutionDetailsRepository.findAll()).thenReturn(Collections.EMPTY_LIST);
        when(requestTypeDetailsRepository.findAll()).thenReturn(Collections.EMPTY_LIST);
        when(customerCodeDetailsRepository.findAll()).thenReturn(Collections.EMPTY_LIST);
        when(((BindingAwareModelMap) model).get("requestedBarcode")).thenReturn("test");
        when(requestController.populateItem(new RequestForm(), null, model,request)).thenReturn("");
        when(requestController.request(model,request)).thenCallRealMethod();
        String response = requestController.request(model,request);
        assertNotNull(response);
        assertEquals("searchRecords",response);
    }

    @Test
    public void searchRequests() throws Exception {
        RequestForm requestForm = getRequestForm();
        String institution ="PUL";
        Page<RequestItemEntity> requestItemEntities = new PageImpl<RequestItemEntity>(new ArrayList<>());
        InstitutionEntity institutionEntity = new InstitutionEntity();
        institutionEntity.setInstitutionCode("PUL");
        institutionEntity.setInstitutionName("Princeton");
        when(requestController.getRequestServiceUtil()).thenReturn(requestServiceUtil);
        //when(requestController.getRequestServiceUtil().searchRequests(requestForm)).thenCallRealMethod();
       // Mockito.when(institutionDetailsRepository.findByInstitutionCode(institution)).thenReturn(institutionEntity);
      /*  Mockito.when(requestServiceUtil.searchRequests(requestForm)).thenCallRealMethod();
        when(requestController.searchRequests(requestForm, bindingResult, model)).thenCallRealMethod();*/
        ModelAndView modelAndView = requestControllerWired.searchRequests(requestForm, bindingResult, model);
        assertNotNull(modelAndView);
        assertEquals("request :: #searchRequestsSection", modelAndView.getViewName());
    }

    @Test
    public void searchPrevious() throws Exception {
        RequestForm requestForm = new RequestForm();
        Page<RequestItemEntity> requestItemEntities = new PageImpl<RequestItemEntity>(new ArrayList<>());
        when(requestController.getRequestServiceUtil()).thenReturn(requestServiceUtil);
        when(requestServiceUtil.searchRequests(requestForm)).thenReturn(requestItemEntities);
        when(requestController.searchPrevious(requestForm, bindingResult, model)).thenCallRealMethod();
        ModelAndView modelAndView = requestController.searchPrevious(requestForm, bindingResult, model);
        assertNotNull(modelAndView);
        assertEquals("request :: #searchRequestsSection", modelAndView.getViewName());
    }

    @Test
    public void searchNext() throws Exception {
        RequestForm requestForm = new RequestForm();
        Page<RequestItemEntity> requestItemEntities = new PageImpl<RequestItemEntity>(new ArrayList<>());
        when(requestController.getRequestServiceUtil()).thenReturn(requestServiceUtil);
        when(requestServiceUtil.searchRequests(requestForm)).thenReturn(requestItemEntities);
        when(requestController.searchNext(requestForm, bindingResult, model)).thenCallRealMethod();
        ModelAndView modelAndView = requestController.searchNext(requestForm, bindingResult, model);
        assertNotNull(modelAndView);
        assertEquals("request :: #searchRequestsSection", modelAndView.getViewName());
    }
    @Test
    public void onRequestPageSizeChange() throws Exception {
        RequestForm requestForm = new RequestForm();
        Page<RequestItemEntity> requestItemEntities = new PageImpl<RequestItemEntity>(new ArrayList<>());
        when(requestController.getRequestServiceUtil()).thenReturn(requestServiceUtil);
        when(requestServiceUtil.searchRequests(requestForm)).thenReturn(requestItemEntities);
        when(requestController.onRequestPageSizeChange(requestForm, bindingResult, model)).thenCallRealMethod();
        ModelAndView modelAndView = requestController.onRequestPageSizeChange(requestForm, bindingResult, model);
        assertNotNull(modelAndView);
    }
    @Test
    public void searchFirst() throws Exception {
        RequestForm requestForm = new RequestForm();
        Page<RequestItemEntity> requestItemEntities = new PageImpl<RequestItemEntity>(new ArrayList<>());
        when(requestController.getRequestServiceUtil()).thenReturn(requestServiceUtil);
        when(requestServiceUtil.searchRequests(requestForm)).thenReturn(requestItemEntities);
        when(requestController.searchFirst(requestForm, bindingResult, model)).thenCallRealMethod();
        ModelAndView modelAndView = requestController.searchFirst(requestForm, bindingResult, model);
        assertNotNull(modelAndView);
        assertEquals("request :: #searchRequestsSection", modelAndView.getViewName());
    }

    @Test
    public void searchLast() throws Exception {
        RequestForm requestForm = new RequestForm();
        Page<RequestItemEntity> requestItemEntities = new PageImpl<RequestItemEntity>(new ArrayList<>());
        when(requestController.getRequestServiceUtil()).thenReturn(requestServiceUtil);
        when(requestServiceUtil.searchRequests(requestForm)).thenReturn(requestItemEntities);
        when(requestController.searchLast(requestForm, bindingResult, model)).thenCallRealMethod();
        ModelAndView modelAndView = requestController.searchLast(requestForm, bindingResult, model);
        assertNotNull(modelAndView);
        assertEquals("request :: #searchRequestsSection", modelAndView.getViewName());
    }

    @Test
    public void loadCreateRequest() throws Exception {
        Mockito.when(requestController.getUserAuthUtil()).thenReturn(userAuthUtil);
        UserDetailsForm userDetailsForm = getUserDetails();
        Mockito.when(requestController.getUserAuthUtil().getUserDetails(request.getSession(false), RecapConstants.REQUEST_PRIVILEGE)).thenReturn(userDetailsForm);
        InstitutionEntity institutionEntity = new InstitutionEntity();
        institutionEntity.setInstitutionCode("CUL");
        institutionEntity.setId(2);
        List<InstitutionEntity> institutionEntityList = new ArrayList<>();
        institutionEntityList.add(institutionEntity);
        Mockito.when(requestService.setDefaultsToCreateRequest(userDetailsForm,model)).thenCallRealMethod();
        Mockito.when(requestService.getInstitutionDetailsRepository()).thenReturn(institutionDetailsRepository);
        Mockito.when(requestService.getRequestTypeDetailsRepository()).thenReturn(requestTypeDetailsRepository);
        when(institutionDetailsRepository.findAll()).thenReturn(Collections.EMPTY_LIST);
        when(requestTypeDetailsRepository.findAll()).thenReturn(Collections.EMPTY_LIST);
        when(customerCodeDetailsRepository.findAll()).thenReturn(Collections.EMPTY_LIST);
        when(userAuthUtil.getUserDetails(request.getSession(),RecapConstants.REQUEST_ITEM_PRIVILEGE)).thenReturn(getUserDetails());
        when(requestController.loadCreateRequest(model,request)).thenCallRealMethod();
        when(request.getSession()).thenReturn(session);
        ModelAndView modelAndView = requestController.loadCreateRequest(model,request);
        assertNotNull(modelAndView);
        assertEquals("request", modelAndView.getViewName());
    }

    @Test
    public void testLoadCreateRequestForSamePatron(){

        UserDetailsForm userDetailsForm = getUserDetails();
        Mockito.when(requestController.getUserAuthUtil().getUserDetails(request.getSession(false), RecapConstants.REQUEST_PRIVILEGE)).thenReturn(userDetailsForm);
        InstitutionEntity institutionEntity = new InstitutionEntity();
        institutionEntity.setInstitutionCode("CUL");
        institutionEntity.setId(2);
        List<InstitutionEntity> institutionEntityList = new ArrayList<>();
        institutionEntityList.add(institutionEntity);
        Mockito.when(requestService.setDefaultsToCreateRequest(userDetailsForm,model)).thenCallRealMethod();
        Mockito.when(requestService.getInstitutionDetailsRepository()).thenReturn(institutionDetailsRepository);
        Mockito.when(requestService.getRequestTypeDetailsRepository()).thenReturn(requestTypeDetailsRepository);

        when(institutionDetailsRepository.findAll()).thenReturn(Collections.EMPTY_LIST);
        when(requestTypeDetailsRepository.findAll()).thenReturn(Collections.EMPTY_LIST);
        when(customerCodeDetailsRepository.findAll()).thenReturn(Collections.EMPTY_LIST);
        when(userAuthUtil.getUserDetails(request.getSession(),RecapConstants.REQUEST_ITEM_PRIVILEGE)).thenReturn(getUserDetails());

        when(requestController.loadCreateRequestForSamePatron(model,request)).thenCallRealMethod();
        when(request.getSession()).thenReturn(session);
        ModelAndView modelAndView = requestController.loadCreateRequestForSamePatron(model,request);
        assertNotNull(modelAndView);
        assertEquals("request", modelAndView.getViewName());
    }

    @Test
    public void goToSearchRequest(){
        RequestForm requestForm = new RequestForm();
        Page<RequestItemEntity> requestItemEntities = new PageImpl<RequestItemEntity>(new ArrayList<>());
        Mockito.when(requestController.getUserAuthUtil()).thenReturn(userAuthUtil);
        UserDetailsForm userDetailsForm = getUserDetails();
        Mockito.when(requestController.getUserAuthUtil().getUserDetails(request.getSession(false), RecapConstants.REQUEST_PRIVILEGE)).thenReturn(userDetailsForm);
        when(requestServiceUtil.searchRequests(requestForm)).thenReturn(requestItemEntities);
        RequestStatusEntity requestStatusEntity = new RequestStatusEntity();
        requestStatusEntity.setRequestStatusDescription("RETRIEVAL ORDER PLACED");
        InstitutionEntity institutionEntity = new InstitutionEntity();
        institutionEntity.setInstitutionCode("PUL");
        List<String> requestStatuses=new ArrayList<>();
        List<String> institutionList=new ArrayList<>();
        Mockito.doCallRealMethod().when(requestService).findAllRequestStatusExceptProcessing(requestStatuses);
        Mockito.doCallRealMethod().when(requestService).findAllRequestStatusExceptProcessing(institutionList);
        Mockito.when(requestController.goToSearchRequest(requestForm,"45678912",bindingResult, model,request)).thenCallRealMethod();
        ModelAndView modelAndView = requestController.goToSearchRequest(requestForm,"45678912",bindingResult, model,request);
        assertNotNull(modelAndView);
        assertEquals(modelAndView.getViewName(),"request :: #requestContentId");
    }


    @Test
    public void populateItem() throws Exception {
        RequestForm requestForm = new RequestForm();
        BibliographicEntity bibliographicEntity = saveBibSingleHoldingsSingleItem();
        CustomerCodeEntity customerCodeEntity = new CustomerCodeEntity();
        customerCodeEntity.setCustomerCode("CU12513083");
        customerCodeEntity.setId(5);
        customerCodeEntity.setOwningInstitutionId(1);
        customerCodeEntity.setDeliveryRestrictions("PG,QP");
        customerCodeEntity.setDescription("Rare Books");
        customerCodeEntity.setPwdDeliveryRestrictions("QC");
        String barcode = bibliographicEntity.getItemEntities().get(0).getBarcode();
        String customerCode = bibliographicEntity.getItemEntities().get(0).getCustomerCode();
        requestForm.setItemBarcodeInRequest(barcode);
        List<ItemEntity> itemEntityArrayList;
        itemEntityArrayList = bibliographicEntity.getItemEntities();
        UserDetailsForm userDetailsForm = getUserDetails();
        Mockito.when(requestController.getUserAuthUtil().getUserDetails(request.getSession(false), RecapConstants.REQUEST_PRIVILEGE)).thenReturn(userDetailsForm);
        Mockito.when(requestService.populateItemForRequest(requestForm,request)).thenCallRealMethod();
        Mockito.when(requestController.getUserAuthUtil().getUserDetails(request.getSession(),RecapConstants.REQUEST_PRIVILEGE)).thenReturn(getUserDetails());
        Mockito.when(requestService.getItemDetailsRepository()).thenReturn(itemDetailsRepository);
        Mockito.when(requestService.getRequestTypeDetailsRepository()).thenReturn(requestTypeDetailsRepository);
        List<RequestTypeEntity> requestTypeEntityList=new ArrayList<>();
        RequestTypeEntity requestTypeEntity = new RequestTypeEntity();
        requestTypeEntity.setRequestTypeCode("RETRIEVAL");
        requestTypeEntity.setRequestTypeDesc("RETRIEVAL");
        requestTypeEntity.setId(1);
        requestTypeEntityList.add(requestTypeEntity);
        Mockito.when(requestService.getRequestTypeDetailsRepository().findAllExceptBorrowDirect()).thenReturn(Arrays.asList(requestTypeEntity));
        Mockito.when(requestService.getRequestTypeDetailsRepository().findAllExceptEDDAndBorrowDirect()).thenReturn(Arrays.asList(requestTypeEntity));
        when(requestController.populateItem(requestForm, bindingResult, model,request)).thenCallRealMethod();
        String response = requestController.populateItem(requestForm, bindingResult, model,request);
        assertNotNull(response);
    }

    @Test
    public void checkGetterServices(){
        Mockito.when(requestController.getRequestServiceUtil()).thenCallRealMethod();
        Mockito.when(requestController.getUserAuthUtil()).thenCallRealMethod();
        Mockito.when(requestController.getScsbShiro()).thenCallRealMethod();
        Mockito.when(requestController.getScsbUrl()).thenCallRealMethod();
        Mockito.when(requestController.getRestTemplate()).thenCallRealMethod();
        assertNotEquals(requestController.getRequestServiceUtil(),requestServiceUtil);
        assertNotEquals(requestController.getUserAuthUtil(),userAuthUtil);
        assertNotEquals(requestController.getScsbShiro(),requestServiceUtil);
        assertNotEquals(requestController.getScsbUrl(),scsbUrl);
        assertNotEquals(requestController.getRestTemplate(),restTemplate);
    }

    @Test
    public void testCreateRequest() throws Exception {
        RequestForm requestForm = getRequestForm();
        ResponseEntity responseEntity = new ResponseEntity(RecapCommonConstants.VALID_REQUEST,HttpStatus.OK);
        ResponseEntity responseEntity1 = new ResponseEntity<ItemResponseInformation>(getItemResponseInformation(),HttpStatus.OK);
        Mockito.when(request.getSession(false)).thenReturn(session);
        ItemRequestInformation itemRequestInformation = getItemRequestInformation();
        HttpEntity<ItemRequestInformation> requestEntity = new HttpEntity<>(itemRequestInformation, restHeaderService.getHttpHeaders());
        String validateRequestItemUrl = getScsbUrl() + RecapConstants.VALIDATE_REQUEST_ITEM_URL;
        String requestItemUrl = scsbUrl + RecapConstants.REQUEST_ITEM_URL;

        CustomerCodeEntity customerCodeEntity = new CustomerCodeEntity();
        customerCodeEntity.setCustomerCode("PG");
        Mockito.when(requestController.getItemRequestInformation()).thenReturn(itemRequestInformation);
        Mockito.when((String) session.getAttribute(RecapConstants.USER_NAME)).thenReturn("Admin");
        Mockito.when(requestController.getRestTemplate()).thenReturn(restTemplate);
        Mockito.when(requestController.getScsbShiro()).thenReturn(scsbShiro);
        Mockito.when(requestController.getScsbUrl()).thenReturn(scsbUrl);
        Mockito.when(requestController.getRestHeaderService()).thenReturn(restHeaderService);
        Mockito.when(requestService.populateItemForRequest(requestForm, request)).thenCallRealMethod();
        Mockito.when(requestController.populateItem(requestForm,bindingResult,model,request)).thenCallRealMethod();
        Mockito.when(requestController.getRestTemplate().exchange(requestItemUrl, HttpMethod.POST, requestEntity, ItemResponseInformation.class)).thenReturn(responseEntity1);
        Mockito.when(requestController.getRestTemplate().exchange(validateRequestItemUrl, HttpMethod.POST, requestEntity, String.class)).thenReturn(responseEntity);
        Mockito.when(requestController.createRequest(requestForm,bindingResult,model,request)).thenCallRealMethod();
        ModelAndView modelAndView = requestController.createRequest(requestForm,bindingResult,model,request);
        assertNotNull(modelAndView);
    }

    @Test
    public void testRequestResubmit()throws Exception{
        ReplaceRequest replaceRequest = getReplaceRequest();
        RequestForm requestForm = getRequestForm();
        ItemRequestInformation itemRequestInfo = new ItemRequestInformation();
        HttpEntity requestEntity = new HttpEntity<>(restHeaderService.getHttpHeaders());
        ResponseEntity responseEntity1 = new ResponseEntity<ReplaceRequest>(replaceRequest,HttpStatus.OK);
        Mockito.when(requestController.getItemRequestInformation()).thenReturn(itemRequestInfo);
        String requestItemUrl = scsbUrl + RecapConstants.URL_REQUEST_RESUBMIT;
        Mockito.when(requestController.getRestTemplate()).thenReturn(restTemplate);
        Mockito.when(requestController.getScsbShiro()).thenReturn(scsbShiro);
        Mockito.when(requestController.getScsbUrl()).thenReturn(scsbUrl);
        Mockito.when(requestController.getRestHeaderService()).thenReturn(restHeaderService);
        Mockito.when(requestController.getRestTemplate().postForEntity( scsbUrl + RecapConstants.URL_REQUEST_RESUBMIT,itemRequestInfo,Map.class)).thenThrow(new RestClientException("Exception occured"));
        Mockito.when(requestController.getRestTemplate().exchange(requestItemUrl, HttpMethod.POST, requestEntity, ReplaceRequest.class)).thenReturn(responseEntity1);
        Mockito.when(requestController.resubmitRequest(requestForm,bindingResult,model)).thenCallRealMethod();
        String response = requestController.resubmitRequest(requestForm,bindingResult,model);
        assertNotNull(response);
    }

    @Test
    public void testCancelRequest() throws Exception {
        RequestForm requestForm = getRequestForm();
        HttpEntity requestEntity = new HttpEntity<>(restHeaderService.getHttpHeaders());
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(scsbUrl + RecapConstants.URL_REQUEST_CANCEL).queryParam(RecapCommonConstants.REQUEST_ID, requestForm.getRequestId());
        CancelRequestResponse cancelRequestResponse = new CancelRequestResponse();
        cancelRequestResponse.setSuccess(true);
        cancelRequestResponse.setScreenMessage("Request cancelled.");
        ResponseEntity<CancelRequestResponse> responseEntity = new ResponseEntity<CancelRequestResponse>(cancelRequestResponse,HttpStatus.OK);
        RequestItemEntity requestItemEntity = new RequestItemEntity();
        RequestStatusEntity requestStatusEntity = new RequestStatusEntity();
        requestStatusEntity.setRequestStatusDescription("Cancelled");
        requestItemEntity.setRequestStatusEntity(requestStatusEntity);
        Mockito.when(requestController.getRestTemplate()).thenReturn(restTemplate);
        Mockito.when(requestController.getScsbShiro()).thenReturn(scsbShiro);
        Mockito.when(requestController.getScsbUrl()).thenReturn(scsbUrl);
        Mockito.when(requestController.getRestHeaderService()).thenReturn(restHeaderService);
        Optional<RequestItemEntity> requestItemEntity1 = requestController.getRequestItemDetailsRepository().findById(requestForm.getRequestId());
        Mockito.when(requestItemEntity1).thenReturn(requestItemEntity1);
        Mockito.when(requestController.getRestTemplate().exchange(builder.build().encode().toUri(), HttpMethod.POST, requestEntity, CancelRequestResponse.class)).thenReturn(responseEntity);
        Mockito.when(requestController.cancelRequest(requestForm,bindingResult,model)).thenCallRealMethod();
        String response = requestController.cancelRequest(requestForm,bindingResult,model);
        assertNotNull(response);
        assertTrue(response.contains("Request cancelled."));
    }
    @Test
    public void testLoadSearchRequest(){
        UserDetailsForm userDetailsForm = new UserDetailsForm();
        userDetailsForm.setRecapPermissionAllowed(true);
        userDetailsForm.setRecapUser(true);
        userDetailsForm.setSuperAdmin(true);
        RequestStatusEntity requestStatusEntity = new RequestStatusEntity();
        requestStatusEntity.setRequestStatusDescription("RETRIEVAL ORDER PLACED");
        InstitutionEntity institutionEntity = new InstitutionEntity();
        institutionEntity.setInstitutionCode("PUL");
        Mockito.when(requestController.getRequestService()).thenReturn(requestService);
        List<String> requestStatusCodeList = getRequestStatusCodeList();
        List<String> institutionCodeList = getInstitutionCodeList();
        Mockito.doCallRealMethod().when(requestService).findAllRequestStatusExceptProcessing(requestStatusCodeList);
        Mockito.doCallRealMethod().when(requestService).getInstitutionForSuperAdmin(institutionCodeList);
        Mockito.when(requestController.getUserAuthUtil()).thenReturn(userAuthUtil);
        Mockito.when(userAuthUtil.getUserDetails(Mockito.any(),Mockito.any())).thenReturn(userDetailsForm);
        Mockito.when(requestController.getInstitutionDetailsRepository()).thenReturn(institutionDetailsRepository);
        Mockito.when(requestController.getInstitutionDetailsRepository().getInstitutionCodeForSuperAdmin()).thenReturn(Arrays.asList(institutionEntity));
        Mockito.when(requestController.loadSearchRequest(model,request)).thenCallRealMethod();
        ModelAndView modelAndView = requestController.loadSearchRequest(model,request);
        assertNotNull(modelAndView);
        assertEquals(modelAndView.getViewName(),"request");
    }

    @Test
    public void testRefreshStatus(){
        Mockito.when(requestController.getRequestService()).thenReturn(requestService);
        String status="status[]";
        String[] statusValue={"16-0"};
        MockHttpServletRequest mockedRequest = new MockHttpServletRequest();
        mockedRequest.addParameter(status, statusValue);
        RequestItemEntity requestItemEntity=getRequestItemEntity();
        Mockito.when(requestService.getRequestStatusDetailsRepository()).thenReturn(requestStatusDetailsRepository);
        Mockito.when(requestService.getRequestItemDetailsRepository()).thenReturn(requestItemDetailsRepository);
        Mockito.when(requestItemDetailsRepository.findByIdIn(Arrays.asList(requestItemEntity.getId()))).thenReturn(Arrays.asList(requestItemEntity));
        Mockito.when(requestService.getRequestStatusDetailsRepository().findAllRequestStatusDescExceptProcessing()).thenReturn(Arrays.asList("RETRIEVAL ORDER PLACED","RECALL ORDER PLACED","EDD ORDER PLACED","REFILED","CANCELED","EXCEPTION","PENDING","INITIAL LOAD"));
        //Mockito.doCallRealMethod().when(requestService).getRefreshedStatus(mockedRequest);
        //String refreshedStatus = requestService.getRefreshedStatus(mockedRequest);
        //Assert.notNull(refreshedStatus);
        Mockito.doCallRealMethod().when(requestController).refreshStatus(mockedRequest);
        String response = requestController.refreshStatus(mockedRequest);
    }

    private RequestItemEntity getRequestItemEntity(){
        RequestStatusEntity requestStatusEntity=new RequestStatusEntity();
        requestStatusEntity.setRequestStatusDescription("RECALL");
        RequestItemEntity requestItemEntity = new RequestItemEntity();
        requestItemEntity.setRequestStatusId(15);
        requestItemEntity.setId(16);
        requestItemEntity.setRequestStatusEntity(requestStatusEntity);
        return requestItemEntity;
    }

    private ReplaceRequest getReplaceRequest(){
        ReplaceRequest replaceRequest = new ReplaceRequest();
        RequestForm requestForm = getRequestForm();
        replaceRequest.setEndRequestId("10");
        replaceRequest.setFromDate((new Date()).toString());
        replaceRequest.setToDate((new Date()).toString());
        replaceRequest.setReplaceRequestByType(RecapCommonConstants.REQUEST_IDS);
        replaceRequest.setRequestStatus(RecapConstants.EXCEPTION);
        String requestId = String.valueOf(requestForm.getRequestId());
        replaceRequest.setRequestIds(requestId);
        replaceRequest.setStartRequestId("1");
        return replaceRequest;
    }
    private RequestForm getRequestForm(){
        RequestForm requestForm = new RequestForm();
        requestForm.setRequestId(1);
        requestForm.setPatronBarcode("43265854");
        requestForm.setSubmitted(true);
        requestForm.setItemBarcode("32101074849843");
        requestForm.setStatus("active");
        requestForm.setDeliveryLocation("PB");
        requestForm.setVolumeNumber("1");
        requestForm.setMessage("testing");
        requestForm.setErrorMessage("error");
        requestForm.setIssue("issue");
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
        requestForm.setInstitution("PUL");
        requestForm.setItemTitle("test");
        requestForm.setItemOwningInstitution("PUL");
        requestForm.setRequestType("recall");
        requestForm.setRequestNotes("test");
        requestForm.setStartPage("2");
        requestForm.setEndPage("5");
        requestForm.setArticleAuthor("john");
        requestForm.setArticleTitle("test");
        requestForm.setDeliveryLocationInRequest("PB");
        return requestForm;
    }

    private ItemRequestInformation getItemRequestInformation(){
        ItemRequestInformation itemRequestInformation = new ItemRequestInformation();
        itemRequestInformation.setUsername("Admin");
        itemRequestInformation.setItemBarcodes(Arrays.asList("123"));
        itemRequestInformation.setPatronBarcode("46259871");
        itemRequestInformation.setRequestingInstitution("PUL");
        itemRequestInformation.setEmailAddress("hemalatha.s@htcindia.com");
        itemRequestInformation.setTitle("test");
        itemRequestInformation.setTitleIdentifier("test");
        itemRequestInformation.setItemOwningInstitution("PUL");
        itemRequestInformation.setRequestType("recall");
        itemRequestInformation.setRequestNotes("test");
        itemRequestInformation.setStartPage("2");
        itemRequestInformation.setEndPage("5");
        itemRequestInformation.setAuthor("john");
        itemRequestInformation.setChapterTitle("test");
        itemRequestInformation.setDeliveryLocation("PB");
        return itemRequestInformation;
    }

    private ItemResponseInformation getItemResponseInformation(){
        ItemResponseInformation itemResponseInformation = new ItemResponseInformation();
        itemResponseInformation.setPatronBarcode("46259871");
        itemResponseInformation.setItemBarcode("123");
        itemResponseInformation.setSuccess(true);
        return itemResponseInformation;
    }

    private UserDetailsForm getUserDetails(){
        UserDetailsForm userDetailsForm=new UserDetailsForm();
        userDetailsForm.setLoginInstitutionId(2);
        userDetailsForm.setSuperAdmin(false);
        userDetailsForm.setRecapPermissionAllowed(false);
        userDetailsForm.setRecapUser(false);
        return userDetailsForm;
    }

    public BibliographicEntity saveBibSingleHoldingsSingleItem() throws Exception {
        Random random = new Random();
        BibliographicEntity bibliographicEntity = new BibliographicEntity();
        bibliographicEntity.setContent("mock Content".getBytes());
        bibliographicEntity.setCreatedDate(new Date());
        bibliographicEntity.setLastUpdatedDate(new Date());
        bibliographicEntity.setCreatedBy("tst");
        bibliographicEntity.setLastUpdatedBy("tst");
        bibliographicEntity.setOwningInstitutionId(1);
        bibliographicEntity.setOwningInstitutionBibId(String.valueOf(random.nextInt()));

        HoldingsEntity holdingsEntity = new HoldingsEntity();
        holdingsEntity.setContent("mock holdings".getBytes());
        holdingsEntity.setCreatedDate(new Date());
        holdingsEntity.setLastUpdatedDate(new Date());
        holdingsEntity.setCreatedBy("tst");
        holdingsEntity.setLastUpdatedBy("tst");
        holdingsEntity.setOwningInstitutionId(1);
        holdingsEntity.setOwningInstitutionHoldingsId(String.valueOf(random.nextInt()));

        ItemEntity itemEntity = new ItemEntity();
        itemEntity.setLastUpdatedDate(new Date());
        itemEntity.setOwningInstitutionItemId(String.valueOf(random.nextInt()));
        itemEntity.setOwningInstitutionId(1);
        itemEntity.setBarcode("CU12513083");
        itemEntity.setCallNumber("x.12321");
        itemEntity.setCollectionGroupId(3);
        itemEntity.setCallNumberType("1");
        itemEntity.setCustomerCode("PG");
        itemEntity.setCreatedDate(new Date());
        itemEntity.setCreatedBy("tst");
        itemEntity.setLastUpdatedBy("tst");
        itemEntity.setItemAvailabilityStatusId(1);
        itemEntity.setCatalogingStatus("Complete");
        itemEntity.setHoldingsEntities(Arrays.asList(holdingsEntity));
        itemEntity.setBibliographicEntities(Arrays.asList(bibliographicEntity));
        bibliographicEntity.setHoldingsEntities(Arrays.asList(holdingsEntity));
        bibliographicEntity.setItemEntities(Arrays.asList(itemEntity));

        /*BibliographicEntity savedBibliographicEntity = bibliographicDetailsRepository.saveAndFlush(bibliographicEntity);
        entityManager.refresh(savedBibliographicEntity);*/
        return bibliographicEntity;

    }

    private List<String> getRequestStatusCodeList(){
        List<String> requestStatusEntityList=new ArrayList<>();
        RequestStatusEntity requestStatusEntity=new RequestStatusEntity();
        requestStatusEntity.setRequestStatusCode("RETRIEVAL_ORDER_PLACED");
        requestStatusEntity.setRequestStatusDescription("RETRIEVAL ORDER PLACED");
        requestStatusEntity.setId(1);
        requestStatusEntityList.add(requestStatusEntity.getRequestStatusCode());
        RequestStatusEntity requestStatusEntity1=new RequestStatusEntity();
        requestStatusEntity1.setRequestStatusCode("RECALL_ORDER_PLACED");
        requestStatusEntity1.setRequestStatusDescription("RECALL_ORDER_PLACED");
        requestStatusEntity1.setId(2);
        requestStatusEntityList.add(requestStatusEntity1.getRequestStatusCode());
        return requestStatusEntityList;
    }

    private List<String> getInstitutionCodeList(){
        List<String> institutionCodeList=new ArrayList<>();
        InstitutionEntity institutionEntity=new InstitutionEntity();
        institutionEntity.setInstitutionCode("PUL");
        institutionCodeList.add(institutionEntity.getInstitutionCode());
        InstitutionEntity institutionEntity1=new InstitutionEntity();
        institutionEntity1.setInstitutionCode("CUL");
        institutionCodeList.add(institutionEntity1.getInstitutionCode());
        InstitutionEntity institutionEntity2=new InstitutionEntity();
        institutionEntity2.setInstitutionCode("NYPL");
        institutionCodeList.add(institutionEntity2.getInstitutionCode());
        return institutionCodeList;
    }

    private SearchResultRow getSearchResultRow(){
        SearchResultRow searchResultRow = new SearchResultRow();
        RequestItemEntity requestItemEntity = getRequestItemEntity();
        searchResultRow.setBarcode(requestItemEntity.getItemEntity().getBarcode());
        searchResultRow.setAvailability(requestItemEntity.getItemEntity().getItemStatusEntity().getStatusCode());
        searchResultRow.setCreatedDate(requestItemEntity.getCreatedDate());
        searchResultRow.setLastUpdatedDate(requestItemEntity.getLastUpdatedDate());
        searchResultRow.setOwningInstitution(requestItemEntity.getInstitutionEntity().getInstitutionCode());
        searchResultRow.setRequestId(requestItemEntity.getId());
        searchResultRow.setRequestType(requestItemEntity.getRequestTypeEntity().getRequestTypeCode());
        searchResultRow.setRequestNotes(requestItemEntity.getNotes());
        searchResultRow.setStatus(requestItemEntity.getRequestStatusEntity().getRequestStatusDescription());

        return  searchResultRow;
    }


}