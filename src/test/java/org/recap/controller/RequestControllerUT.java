package org.recap.controller;

import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.util.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.recap.RecapConstants;
import org.recap.model.jpa.*;
import org.recap.model.request.CancelRequestResponse;
import org.recap.model.request.ItemRequestInformation;
import org.recap.model.request.ItemResponseInformation;
import org.recap.model.search.RequestForm;
import org.recap.model.usermanagement.UserDetailsForm;
import org.recap.repository.jpa.*;
import org.recap.service.RequestService;
import org.recap.service.RestHeaderService;
import org.recap.util.RequestServiceUtil;
import org.recap.util.UserAuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.*;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BindingResult;
import org.springframework.validation.support.BindingAwareModelMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpSession;
import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
        Mockito.when(requestController.getRequestService()).thenReturn(requestService);
        Mockito.when(userAuthUtil.authorizedUser(RecapConstants.SCSB_SHIRO_REQUEST_URL,(UsernamePasswordToken)session.getAttribute(RecapConstants.USER_TOKEN))).thenReturn(true);
        Mockito.when(requestController.getUserAuthUtil()).thenReturn(userAuthUtil);
        Mockito.when(requestController.getInstitutionDetailsRepository()).thenReturn(institutionDetailsRepository);
        Mockito.when(requestController.getCustomerCodeDetailsRepository()).thenReturn(customerCodeDetailsRepository);
        Mockito.when(requestController.getRequestTypeDetailsRepository()).thenReturn(requestTypeDetailsRepository);
        UserDetailsForm userDetailsForm = getUserDetails();
        Mockito.when(requestController.getUserAuthUtil().getUserDetails(request.getSession(false), RecapConstants.REQUEST_PRIVILEGE)).thenReturn(userDetailsForm);
        Mockito.when(requestService.setDefaultsToCreateRequest(userDetailsForm,model)).thenCallRealMethod();
        InstitutionEntity institutionEntity = new InstitutionEntity();
        institutionEntity.setInstitutionCode("CUL");
        institutionEntity.setInstitutionId(2);
        List<InstitutionEntity> institutionEntityList = new ArrayList<>();
        institutionEntityList.add(institutionEntity);
        Mockito.when(requestService.getInstitutionDetailsRepository()).thenReturn(institutionDetailsRepository);
        Mockito.when(requestController.getRequestService().getInstitutionDetailsRepository().findAll()).thenReturn(Arrays.asList(institutionEntity));
        Mockito.when(requestService.getRequestTypeDetailsRepository()).thenReturn(requestTypeDetailsRepository);
        Mockito.when(requestService.setFormDetailsForRequest(model,request,userDetailsForm)).thenCallRealMethod();
        when(institutionDetailsRepository.findAll()).thenReturn(Collections.EMPTY_LIST);
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
        RequestForm requestForm = new RequestForm();
        Page<RequestItemEntity> requestItemEntities = new PageImpl<RequestItemEntity>(new ArrayList<>());
        when(requestController.getRequestServiceUtil()).thenReturn(requestServiceUtil);
        when(requestServiceUtil.searchRequests(requestForm)).thenReturn(requestItemEntities);
        when(requestController.searchRequests(requestForm, bindingResult, model)).thenCallRealMethod();
        ModelAndView modelAndView = requestController.searchRequests(requestForm, bindingResult, model);
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
        Mockito.when(requestController.getInstitutionDetailsRepository()).thenReturn(institutionDetailsRepository);
        Mockito.when(requestController.getCustomerCodeDetailsRepository()).thenReturn(customerCodeDetailsRepository);
        Mockito.when(requestController.getRequestTypeDetailsRepository()).thenReturn(requestTypeDetailsRepository);
        Mockito.when(requestController.getUserAuthUtil()).thenReturn(userAuthUtil);
        Mockito.when(requestController.getRequestService()).thenReturn(requestService);
        UserDetailsForm userDetailsForm = getUserDetails();
        Mockito.when(requestController.getUserAuthUtil().getUserDetails(request.getSession(false), RecapConstants.REQUEST_PRIVILEGE)).thenReturn(userDetailsForm);
        InstitutionEntity institutionEntity = new InstitutionEntity();
        institutionEntity.setInstitutionCode("CUL");
        institutionEntity.setInstitutionId(2);
        List<InstitutionEntity> institutionEntityList = new ArrayList<>();
        institutionEntityList.add(institutionEntity);
        Mockito.when(requestService.setDefaultsToCreateRequest(userDetailsForm,model)).thenCallRealMethod();
        Mockito.when(requestService.getInstitutionDetailsRepository()).thenReturn(institutionDetailsRepository);
        Mockito.when(requestController.getRequestService().getInstitutionDetailsRepository().findAll()).thenReturn(Arrays.asList(institutionEntity));
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

        Mockito.when(requestController.getInstitutionDetailsRepository()).thenReturn(institutionDetailsRepository);
        Mockito.when(requestController.getCustomerCodeDetailsRepository()).thenReturn(customerCodeDetailsRepository);
        Mockito.when(requestController.getRequestTypeDetailsRepository()).thenReturn(requestTypeDetailsRepository);
        Mockito.when(requestController.getUserAuthUtil()).thenReturn(userAuthUtil);
        Mockito.when(requestController.getRequestService()).thenReturn(requestService);
        UserDetailsForm userDetailsForm = getUserDetails();
        Mockito.when(requestController.getUserAuthUtil().getUserDetails(request.getSession(false), RecapConstants.REQUEST_PRIVILEGE)).thenReturn(userDetailsForm);
        InstitutionEntity institutionEntity = new InstitutionEntity();
        institutionEntity.setInstitutionCode("CUL");
        institutionEntity.setInstitutionId(2);
        List<InstitutionEntity> institutionEntityList = new ArrayList<>();
        institutionEntityList.add(institutionEntity);
        Mockito.when(requestService.setDefaultsToCreateRequest(userDetailsForm,model)).thenCallRealMethod();
        Mockito.when(requestService.getInstitutionDetailsRepository()).thenReturn(institutionDetailsRepository);
        Mockito.when(requestController.getRequestService().getInstitutionDetailsRepository().findAll()).thenReturn(Arrays.asList(institutionEntity));
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
        Mockito.when(requestController.getInstitutionDetailsRepository()).thenReturn(institutionDetailsRepository);
        Mockito.when(requestController.getRequestStatusDetailsRepository()).thenReturn(requestStatusDetailsRepository);
        Mockito.when(requestController.getRequestServiceUtil()).thenReturn(requestServiceUtil);
        Mockito.when(requestController.getRequestService()).thenReturn(requestService);
        Mockito.when(requestController.getUserAuthUtil()).thenReturn(userAuthUtil);
        UserDetailsForm userDetailsForm = getUserDetails();
        Mockito.when(requestController.getUserAuthUtil().getUserDetails(request.getSession(false), RecapConstants.REQUEST_PRIVILEGE)).thenReturn(userDetailsForm);
        when(requestServiceUtil.searchRequests(requestForm)).thenReturn(requestItemEntities);
        RequestStatusEntity requestStatusEntity = new RequestStatusEntity();
        requestStatusEntity.setRequestStatusDescription("RETRIEVAL ORDER PLACED");
        InstitutionEntity institutionEntity = new InstitutionEntity();
        institutionEntity.setInstitutionCode("PUL");
        Mockito.when(requestController.getRequestStatusDetailsRepository().findAll()).thenReturn(Arrays.asList(requestStatusEntity));
        Mockito.when(requestController.getInstitutionDetailsRepository().getInstitutionCodeForSuperAdmin()).thenReturn(Arrays.asList(institutionEntity));
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
        String barcode = bibliographicEntity.getItemEntities().get(0).getBarcode();
        requestForm.setItemBarcodeInRequest(barcode);
        Mockito.when(requestController.getItemDetailsRepository()).thenReturn(itemDetailsRepository);
        Mockito.when(requestController.getUserAuthUtil()).thenReturn(userAuthUtil);
        when(request.getSession()).thenReturn(session);
        Mockito.when(requestController.getRequestService()).thenReturn(requestService);
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
        requestTypeEntity.setRequestTypeId(1);
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
        Mockito.when(requestController.getInstitutionDetailsRepository()).thenCallRealMethod();
        Mockito.when(requestController.getRequestTypeDetailsRepository()).thenCallRealMethod();
        Mockito.when(requestController.getCustomerCodeDetailsRepository()).thenCallRealMethod();
        Mockito.when(requestController.getItemDetailsRepository()).thenCallRealMethod();
        Mockito.when(requestController.getScsbShiro()).thenCallRealMethod();
        Mockito.when(requestController.getScsbUrl()).thenCallRealMethod();
        Mockito.when(requestController.getRequestItemDetailsRepository()).thenCallRealMethod();
        Mockito.when(requestController.getRestTemplate()).thenCallRealMethod();
        Mockito.when(requestController.getRequestStatusDetailsRepository()).thenCallRealMethod();
        Mockito.when(requestController.getRequestService()).thenCallRealMethod();

        assertNotEquals(requestController.getRequestServiceUtil(),requestServiceUtil);
        assertNotEquals(requestController.getUserAuthUtil(),userAuthUtil);
        assertNotEquals(requestController.getInstitutionDetailsRepository(),institutionDetailsRepository);
        assertNotEquals(requestController.getRequestTypeDetailsRepository(),requestTypeDetailsRepository);
        assertNotEquals(requestController.getCustomerCodeDetailsRepository(),requestServiceUtil);
        assertNotEquals(requestController.getItemDetailsRepository(),itemDetailsRepository);
        assertNotEquals(requestController.getScsbShiro(),requestServiceUtil);
        assertNotEquals(requestController.getScsbUrl(),scsbUrl);
        assertNotEquals(requestController.getRequestItemDetailsRepository(),requestItemDetailsRepository);
        assertNotEquals(requestController.getRestTemplate(),restTemplate);
        assertNotEquals(requestController.getRequestStatusDetailsRepository(),requestStatusDetailsRepository);
        assertNotEquals(requestController.getRequestService(),requestService);
    }

    @Test
    public void testCreateRequest() throws Exception {
        RequestForm requestForm = getRequestForm();
        ResponseEntity responseEntity = new ResponseEntity(RecapConstants.VALID_REQUEST,HttpStatus.OK);
        ResponseEntity responseEntity1 = new ResponseEntity<ItemResponseInformation>(getItemResponseInformation(),HttpStatus.OK);
        when(request.getSession()).thenReturn(session);
        ItemRequestInformation itemRequestInformation = getItemRequestInformation();
        HttpEntity<ItemRequestInformation> requestEntity = new HttpEntity<>(itemRequestInformation, restHeaderService.getHttpHeaders());
        String validateRequestItemUrl = getScsbUrl() + RecapConstants.VALIDATE_REQUEST_ITEM_URL;
        String requestItemUrl = scsbUrl + RecapConstants.REQUEST_ITEM_URL;
        CustomerCodeEntity customerCodeEntity = new CustomerCodeEntity();
        customerCodeEntity.setCustomerCode("PB");
        Mockito.when(requestController.getItemRequestInformation()).thenReturn(itemRequestInformation);
        Mockito.when((String) session.getAttribute(RecapConstants.USER_NAME)).thenReturn("Admin");
        Mockito.when(requestController.getRestTemplate()).thenReturn(restTemplate);
        Mockito.when(requestController.getScsbShiro()).thenReturn(scsbShiro);
        Mockito.when(requestController.getScsbUrl()).thenReturn(scsbUrl);
        Mockito.when(requestController.getRestHeaderService()).thenReturn(restHeaderService);
        Mockito.when(requestController.getCustomerCodeDetailsRepository()).thenReturn(customerCodeDetailsRepository);
        Mockito.when(requestController.getCustomerCodeDetailsRepository().findByDescription(requestForm.getDeliveryLocationInRequest())).thenReturn(customerCodeEntity);
        Mockito.when(requestController.getRestTemplate().exchange(requestItemUrl, HttpMethod.POST, requestEntity, ItemResponseInformation.class)).thenReturn(responseEntity1);
        Mockito.when(requestController.getRestTemplate().exchange(validateRequestItemUrl, HttpMethod.POST, requestEntity, String.class)).thenReturn(responseEntity);
        Mockito.when(requestController.createRequest(requestForm,bindingResult,model,request)).thenCallRealMethod();
        ModelAndView modelAndView = requestController.createRequest(requestForm,bindingResult,model,request);
        assertNotNull(modelAndView);
    }

    @Test
    public void testCancelRequest() throws Exception {
        RequestForm requestForm = getRequestForm();
        HttpEntity requestEntity = new HttpEntity<>(restHeaderService.getHttpHeaders());
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(scsbUrl + RecapConstants.URL_REQUEST_CANCEL).queryParam(RecapConstants.REQUEST_ID, requestForm.getRequestId());
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
        Mockito.when(requestController.getRequestItemDetailsRepository()).thenReturn(requestItemDetailsRepository);
        Mockito.when(requestController.getRequestItemDetailsRepository().findByRequestId(requestForm.getRequestId())).thenReturn(requestItemEntity);
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
        Mockito.when(requestController.getRequestStatusDetailsRepository()).thenReturn(requestStatusDetailsRepository);
        Mockito.when(requestController.getUserAuthUtil()).thenReturn(userAuthUtil);
        Mockito.when(userAuthUtil.getUserDetails(Mockito.any(),Mockito.any())).thenReturn(userDetailsForm);
        Mockito.when(requestController.getInstitutionDetailsRepository()).thenReturn(institutionDetailsRepository);
        Mockito.when(requestController.getRequestStatusDetailsRepository().findAll()).thenReturn(Arrays.asList(requestStatusEntity));
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
        Mockito.when(requestItemDetailsRepository.findByRequestIdIn(Arrays.asList(requestItemEntity.getRequestId()))).thenReturn(Arrays.asList(requestItemEntity));
        Mockito.when(requestService.getRequestStatusDetailsRepository().findAllRequestStatusDescExceptProcessing()).thenReturn(Arrays.asList("RETRIEVAL ORDER PLACED","RECALL ORDER PLACED","EDD ORDER PLACED","REFILED","CANCELED","EXCEPTION","PENDING","INITIAL LOAD"));
        Mockito.doCallRealMethod().when(requestService).getRefreshedStatus(mockedRequest);
        String refreshedStatus = requestService.getRefreshedStatus(mockedRequest);
        Assert.notNull(refreshedStatus);
    }

    private RequestItemEntity getRequestItemEntity(){
        RequestStatusEntity requestStatusEntity=new RequestStatusEntity();
        requestStatusEntity.setRequestStatusDescription("RECALL");
        RequestItemEntity requestItemEntity = new RequestItemEntity();
        requestItemEntity.setRequestStatusId(15);
        requestItemEntity.setRequestId(16);
        requestItemEntity.setRequestStatusEntity(requestStatusEntity);
        return requestItemEntity;
    }

    private RequestForm getRequestForm(){
        RequestForm requestForm = new RequestForm();
        requestForm.setRequestId(1);
        requestForm.setPatronBarcode("43265854");
        requestForm.setSubmitted(true);
        requestForm.setItemBarcode("3324545547568535");
        requestForm.setStatus("Success");
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
        itemEntity.setBarcode("123");
        itemEntity.setCallNumber("x.12321");
        itemEntity.setCollectionGroupId(3);
        itemEntity.setCallNumberType("1");
        itemEntity.setCustomerCode("123");
        itemEntity.setCreatedDate(new Date());
        itemEntity.setCreatedBy("tst");
        itemEntity.setLastUpdatedBy("tst");
        itemEntity.setItemAvailabilityStatusId(1);
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
        requestStatusEntity.setRequestStatusId(1);
        requestStatusEntityList.add(requestStatusEntity.getRequestStatusCode());
        RequestStatusEntity requestStatusEntity1=new RequestStatusEntity();
        requestStatusEntity1.setRequestStatusCode("RECALL_ORDER_PLACED");
        requestStatusEntity1.setRequestStatusDescription("RECALL_ORDER_PLACED");
        requestStatusEntity1.setRequestStatusId(2);
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

}
