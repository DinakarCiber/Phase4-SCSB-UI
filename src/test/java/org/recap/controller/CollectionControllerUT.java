package org.recap.controller;

import org.apache.shiro.authc.UsernamePasswordToken;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.recap.RecapCommonConstants;
import org.recap.RecapConstants;
import org.recap.model.jpa.*;
import org.recap.model.search.*;
import org.recap.model.usermanagement.UserDetailsForm;
import org.recap.model.usermanagement.UserForm;
import org.recap.repository.jpa.RequestItemDetailsRepository;
import org.recap.util.CollectionServiceUtil;
import org.recap.util.MarcRecordViewUtil;
import org.recap.util.SearchUtil;
import org.recap.util.UserAuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

/**
 * Created by rajeshbabuk on 12/10/16.
 */
public class CollectionControllerUT extends BaseControllerUT {

    @Value("${scsb.shiro}")
    String scsbShiro;

    public String getScsbShiro() {
        return scsbShiro;
    }

    public void setScsbShiro(String scsbShiro) {
        this.scsbShiro = scsbShiro;
    }

    @Mock
    Model model;

    @Mock
    BindingResult bindingResult;

    @Mock
    SearchUtil searchUtil;

    @Mock
    SearchRecordsRequest searchRecordsRequest;

    @InjectMocks
    CollectionController collectionController;

    @Autowired
    CollectionController collectionController1;

    @Mock
    CollectionController getCollectionController;

    @Mock
    MarcRecordViewUtil marcRecordViewUtil;

    @Mock
    HttpSession session;

    @Mock
    CollectionServiceUtil collectionServiceUtil;

    @Mock
    javax.servlet.http.HttpServletRequest request;

    @Mock
    private UserAuthUtil userAuthUtil;

    @Mock
    private SearchRecordsResponse searchRecordsResponse;

    @Mock
    RequestItemDetailsRepository requestItemDetailsRepository;

    public UserAuthUtil getUserAuthUtil() {
        return userAuthUtil;
    }

    public SearchUtil getSearchUtil(){
        return searchUtil;
    }

    public void setUserAuthUtil(UserAuthUtil userAuthUtil) {
        this.userAuthUtil = userAuthUtil;
    }

    public RequestItemDetailsRepository getRequestItemDetailsRepository() {
        return requestItemDetailsRepository;
    }

    public void setRequestItemDetailsRepository(RequestItemDetailsRepository requestItemDetailsRepository) {
        this.requestItemDetailsRepository = requestItemDetailsRepository;
    }

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(getCollectionController).build();
    }



    @Test
    public void collection() throws Exception{
        when(request.getSession(false)).thenReturn(session);
        Mockito.when(getUserAuthUtil().isAuthenticated(request, RecapConstants.SCSB_SHIRO_COLLECTION_URL)).thenReturn(true);
        Mockito.when(getCollectionController.getUserAuthUtil()).thenReturn(userAuthUtil);
        Mockito.when(getSearchUtil().requestSearchResults(searchRecordsRequest)).thenReturn(searchRecordsResponse);
        Mockito.doCallRealMethod().when(getCollectionController).collection(model,request);
        String response = getCollectionController.collection(model,request);
        assertNotNull(response);
        assertEquals("searchRecords",response);
    }

    @Test
    public void collection2() throws Exception{
        when(request.getSession(false)).thenReturn(session);
        Mockito.when(getUserAuthUtil().isAuthenticated(request, RecapConstants.SCSB_SHIRO_COLLECTION_URL)).thenReturn(false);
        Mockito.when(getCollectionController.getUserAuthUtil()).thenReturn(userAuthUtil);
        Mockito.when(getCollectionController.collection(model,request)).thenCallRealMethod();
        Mockito.when(searchUtil.requestSearchResults(searchRecordsRequest)).thenReturn(searchRecordsResponse);
        String response = getCollectionController.collection(model,request);
        assertNotNull(response);
    }

    @Test
    public void displayRecords() throws Exception {
        SearchRecordsRequest searchRecordsRequest = getSearchRecordsRequest();
        SearchRecordsResponse searchRecordsResponse = getSearchRecordsResponse();
        CollectionForm collectionForm = getCollectionForm();
        collectionForm.setErrorMessage("No results found.");
        Mockito.when(getSearchUtil().requestSearchResults(searchRecordsRequest)).thenReturn(searchRecordsResponse);
        Mockito.doCallRealMethod().when(getCollectionController).displayRecords(collectionForm,bindingResult,model);
        ModelAndView modelAndView = collectionController1.displayRecords(collectionForm, bindingResult, model);
        assertNotNull(modelAndView);
        assertEquals("searchRecords", modelAndView.getViewName());
    }


    @Test
    public void openMarcRecord() throws Exception {
        CollectionForm collectionForm = new CollectionForm();
        collectionForm.setBibId(1);
        collectionForm.setItemId(1);
        BibliographicMarcForm bibliographicMarcForm = getBibilographicMarcForm();
        UserDetailsForm userDetailsForm= new UserDetailsForm();
        userDetailsForm.setSuperAdmin(false);
        userDetailsForm.setLoginInstitutionId(2);
        userDetailsForm.setRecapUser(false);
        when(request.getSession(false)).thenReturn(session);
        usersSessionAttributes();
        Mockito.when(getCollectionController.getUserAuthUtil()).thenReturn(userAuthUtil);
        Mockito.when(getCollectionController.getUserAuthUtil().getUserDetails(request.getSession(false), RecapConstants.BARCODE_RESTRICTED_PRIVILEGE)).thenReturn(userDetailsForm);
        Mockito.when(getCollectionController.getMarcRecordViewUtil()).thenReturn(marcRecordViewUtil);
        Mockito.when(getCollectionController.getMarcRecordViewUtil().buildBibliographicMarcForm(collectionForm.getBibId(), collectionForm.getItemId(),userDetailsForm)).thenReturn(bibliographicMarcForm);
        Mockito.when(getCollectionController.openMarcView(collectionForm, bindingResult, model,request)).thenCallRealMethod();
        ModelAndView modelAndView = getCollectionController.openMarcView(collectionForm, bindingResult, model,request);
        assertNotNull(modelAndView);
        assertEquals("collection :: #collectionUpdateModal", modelAndView.getViewName());
    }

    @Test
    public void getMarcRecordViewUtil(){
        MarcRecordViewUtil recordViewUtil = collectionController1.getMarcRecordViewUtil();
        assertNotNull(recordViewUtil);
    }
    @Test
    public void getUserAuthUtil1(){
        UserAuthUtil userAuthUtil = collectionController1.getUserAuthUtil();
        assertNotNull(userAuthUtil);
    }

    @Test
    public void getCollectionServiceUtil(){
        CollectionServiceUtil collectionServiceUtil = collectionController1.getCollectionServiceUtil();
    }

    @Test
    public void collectionUpdate() throws Exception {
        CollectionForm collectionForm = new CollectionForm();
        collectionForm.setCollectionAction("Update CGD");
        when(request.getSession(false)).thenReturn(session);
        usersSessionAttributes();
        Mockito.when(getCollectionController.getCollectionServiceUtil()).thenReturn(collectionServiceUtil);
        Mockito.when(getCollectionController.collectionUpdate(collectionForm, bindingResult, model, request)).thenCallRealMethod();
        ModelAndView modelAndView = getCollectionController.collectionUpdate(collectionForm, bindingResult, model, request);
        assertNotNull(modelAndView);
        assertEquals("collection :: #itemDetailsSection", modelAndView.getViewName());
    }
    @Test
    public void collectionUpdate1() throws Exception {
        CollectionForm collectionForm = new CollectionForm();
        collectionForm.setCollectionAction("Deaccession");
        when(request.getSession(false)).thenReturn(session);
        usersSessionAttributes();
        Mockito.when(getCollectionController.getCollectionServiceUtil()).thenReturn(collectionServiceUtil);
        Mockito.when(getCollectionController.collectionUpdate(collectionForm, bindingResult, model, request)).thenCallRealMethod();
        ModelAndView modelAndView = getCollectionController.collectionUpdate(collectionForm, bindingResult, model, request);
        assertNotNull(modelAndView);
        assertEquals("collection :: #itemDetailsSection", modelAndView.getViewName());
    }
    private void usersSessionAttributes() throws Exception {
        when(request.getSession()).thenReturn(session);
        UserForm userForm = new UserForm();
        userForm.setUsername("SuperAdmin");
        userForm.setInstitution("1");
        userForm.setPassword("12345");
        UsernamePasswordToken token=new UsernamePasswordToken(userForm.getUsername()+ RecapConstants.TOKEN_SPLITER+userForm.getInstitution(),userForm.getPassword(),true);
        when(session.getAttribute(RecapConstants.USER_TOKEN)).thenReturn(token);
        when(session.getAttribute(RecapConstants.USER_ID)).thenReturn(3);
        when(session.getAttribute(RecapConstants.SUPER_ADMIN_USER)).thenReturn(false);
        when(session.getAttribute(RecapConstants.BARCODE_RESTRICTED_PRIVILEGE)).thenReturn(false);
        when(session.getAttribute(RecapConstants.REQUEST_ITEM_PRIVILEGE)).thenReturn(false);
        when(session.getAttribute(RecapConstants.USER_INSTITUTION)).thenReturn(1);
        when(session.getAttribute(RecapConstants.REQUEST_ALL_PRIVILEGE)).thenReturn(false);
    }

    @Test
    public void checkCrossInstitutionBorrowed() throws Exception {
        String itemBarcode = "123";
        CollectionForm collectionForm = getCollectionForm();
        collectionForm.setBarcode(itemBarcode);
        collectionForm.setCollectionAction("Update CGD");
        when(getRequestItemDetailsRepository().findByItemBarcodeAndRequestStaCode(itemBarcode, RecapCommonConstants.REQUEST_STATUS_RETRIEVAL_ORDER_PLACED)).thenReturn(getMockRequestItemEntity());
        ModelAndView modelAndView = collectionController.checkCrossInstitutionBorrowed(collectionForm, bindingResult, model);
        assertNotNull(modelAndView);
        assertEquals("collection :: #itemDetailsSection", modelAndView.getViewName());
        assertNotNull(collectionForm.getErrorMessage());
        assertNotNull(collectionForm.getItemBarcodes());
        assertNotNull(collectionForm.isShowResults());
        assertNotNull(collectionForm.isSelectAll());
        assertNotNull(collectionForm.getBarcodesNotFoundErrorMessage());
        assertNotNull(collectionForm.getIgnoredBarcodesErrorMessage());
        assertNotNull(collectionForm.getSearchResultRows());
        assertNotNull(collectionForm.isShowModal());
    }

    @Test
    public void checkCrossInstitutionBorrowed2() throws Exception {
        String itemBarcode = "123";
        CollectionForm collectionForm = getCollectionForm();
        collectionForm.setBarcode(itemBarcode);
        collectionForm.setCollectionAction("Deaccession");
        when(getRequestItemDetailsRepository().findByItemBarcodeAndRequestStaCode(itemBarcode, RecapCommonConstants.REQUEST_STATUS_RETRIEVAL_ORDER_PLACED)).thenReturn(getMockRequestItemEntity());
        ModelAndView modelAndView = collectionController.checkCrossInstitutionBorrowed(collectionForm, bindingResult, model);
        assertNotNull(modelAndView);
        assertEquals("collection :: #itemDetailsSection", modelAndView.getViewName());
        assertNotNull(collectionForm.getErrorMessage());
        assertNotNull(collectionForm.getItemBarcodes());
        assertNotNull(collectionForm.isShowResults());
        assertNotNull(collectionForm.isSelectAll());
        assertNotNull(collectionForm.getBarcodesNotFoundErrorMessage());
        assertNotNull(collectionForm.getIgnoredBarcodesErrorMessage());
        assertNotNull(collectionForm.getSearchResultRows());
        assertNotNull(collectionForm.isShowModal());
    }

    private CollectionForm getCollectionForm(){
        CollectionForm collectionForm = new CollectionForm();
        collectionForm.setErrorMessage("test");
        collectionForm.setItemBarcodes("335454575437");

        collectionForm.setShowResults(false);
        collectionForm.setSelectAll(false);
        collectionForm.setBarcodesNotFoundErrorMessage("test");
        collectionForm.setIgnoredBarcodesErrorMessage("test");
        SearchResultRow searchResultRow = getSearchResultRow();
        collectionForm.setSearchResultRows(Arrays.asList(searchResultRow));
        collectionForm.setShowModal(false);
        return collectionForm;
    }

    private RequestItemEntity getMockRequestItemEntity() {
        InstitutionEntity institutionEntity1 = new InstitutionEntity();
        institutionEntity1.setId(1);
        institutionEntity1.setInstitutionCode("PUL");
        institutionEntity1.setInstitutionName("Princeton");

        RequestTypeEntity requestTypeEntity1 = new RequestTypeEntity();
        requestTypeEntity1.setId(1);
        requestTypeEntity1.setRequestTypeCode("RETRIEVAL");
        requestTypeEntity1.setRequestTypeDesc("RETRIEVAL");

        RequestStatusEntity requestStatusEntity1 = new RequestStatusEntity();
        requestStatusEntity1.setId(1);
        requestStatusEntity1.setRequestStatusCode("RETRIEVAL_ORDER_PLACED");
        requestStatusEntity1.setRequestStatusDescription("RETRIEVAL_ORDER_PLACED");

        ItemEntity itemEntity1 = new ItemEntity();
        itemEntity1.setItemId(1);
        itemEntity1.setBarcode("123");
        itemEntity1.setOwningInstitutionId(1);
        itemEntity1.setInstitutionEntity(institutionEntity1);

        BibliographicEntity bibliographicEntity1 = new BibliographicEntity();
        bibliographicEntity1.setOwningInstitutionBibId("345");
        itemEntity1.setBibliographicEntities(Arrays.asList(bibliographicEntity1));

        RequestItemEntity requestItemEntity1 = new RequestItemEntity();
        requestItemEntity1.setId(1);
        requestItemEntity1.setItemId(2);
        requestItemEntity1.setRequestTypeId(1);
        requestItemEntity1.setRequestingInstitutionId(1);
        requestItemEntity1.setPatronId("123");
        requestItemEntity1.setEmailId("test1@gmail.com");
        requestItemEntity1.setRequestExpirationDate(new Date());
        requestItemEntity1.setCreatedBy("Test");
        requestItemEntity1.setCreatedDate(new Date());
        requestItemEntity1.setStopCode("PA");
        requestItemEntity1.setId(1);
        requestItemEntity1.setNotes("Test Notes");
        requestItemEntity1.setInstitutionEntity(institutionEntity1);
        requestItemEntity1.setRequestTypeEntity(requestTypeEntity1);
        requestItemEntity1.setRequestStatusEntity(requestStatusEntity1);
        requestItemEntity1.setItemEntity(itemEntity1);

        return requestItemEntity1;
    }

    private SearchResultRow getSearchResultRow(){
        SearchResultRow searchResultRow = new SearchResultRow();
        searchResultRow.setShowItems(true);
        searchResultRow.setStatus("SUCCESS");
        searchResultRow.setRequestNotes("search");
        searchResultRow.setRequestType("search");
        searchResultRow.setRequestId(1);
        searchResultRow.setOwningInstitution("NYPL");
        searchResultRow.setDeliveryLocation("BA");
        searchResultRow.setItemId(1);
        searchResultRow.setLastUpdatedDate(new Date());
        searchResultRow.setCreatedDate(new Date());
        searchResultRow.setAuthor("test");
        return searchResultRow;
    }
    private BibliographicMarcForm getBibilographicMarcForm(){
        BibliographicMarcForm bibliographicMarcForm = new BibliographicMarcForm();
        bibliographicMarcForm.setTitle("test");
        bibliographicMarcForm.setAuthor("test");
        bibliographicMarcForm.setPublisher("test");
        bibliographicMarcForm.setPublishedDate(new Date().toString());
        bibliographicMarcForm.setOwningInstitution("PUL");
        bibliographicMarcForm.setCallNumber("123");
        bibliographicMarcForm.setMonographCollectionGroupDesignation("Complete");
        bibliographicMarcForm.setTag000("TAG000");
        bibliographicMarcForm.setControlNumber001("TAG001");
        bibliographicMarcForm.setControlNumber005("TAG005");
        bibliographicMarcForm.setControlNumber008("TAG008");
        BibDataField bibDataField = new BibDataField();
        bibDataField.setDataFieldValue("test");
        bibDataField.setDataFieldTag("TAG001");
        bibDataField.setIndicator1('y');
        bibDataField.setIndicator2('N');
        bibliographicMarcForm.setBibDataFields(Arrays.asList(bibDataField));
        bibliographicMarcForm.setAvailability("AVAILABLE");
        bibliographicMarcForm.setBarcode("12345");
        bibliographicMarcForm.setLocationCode("PA");
        bibliographicMarcForm.setUseRestriction("Allowed");
        bibliographicMarcForm.setCollectionGroupDesignation("Complete");
        bibliographicMarcForm.setNewCollectionGroupDesignation("Pending");
        bibliographicMarcForm.setCgdChangeNotes("CGDCHANGE#002");
        bibliographicMarcForm.setCustomerCode("3456");
        bibliographicMarcForm.setDeaccessionType("DENIED");
        bibliographicMarcForm.setDeaccessionNotes("test");
        CustomerCodeEntity customerCodeEntity = new CustomerCodeEntity();
        customerCodeEntity.setDeliveryRestrictions("No");
        bibliographicMarcForm.setDeliveryLocations(Arrays.asList(customerCodeEntity));
        bibliographicMarcForm.setDeliveryLocation("PA");
        bibliographicMarcForm.setShared(true);
        bibliographicMarcForm.setSubmitted(true);
        bibliographicMarcForm.setMessage("SUCCESS");
        bibliographicMarcForm.setErrorMessage("ERROR");
        bibliographicMarcForm.setWarningMessage("WARN");
        bibliographicMarcForm.setCollectionAction("ACTIVE");
        bibliographicMarcForm.setAllowEdit(true);
        return bibliographicMarcForm;
    }

    private SearchRecordsRequest getSearchRecordsRequest(){
        SearchRecordsRequest searchRecordsRequest = new SearchRecordsRequest();
        searchRecordsRequest.setFieldValue("23456");
        searchRecordsRequest.setFieldValue("BARCODE");
        searchRecordsRequest.setPageNumber(20);
        searchRecordsRequest.setSearchResultRows(Arrays.asList(getSearchResultRow()));
        searchRecordsRequest.setCatalogingStatus("Complete");
        searchRecordsRequest.setDeleted(false);
        searchRecordsRequest.setErrorMessage("ERROR");
        searchRecordsRequest.setTotalPageCount(1);
        return searchRecordsRequest;
    }
    private SearchRecordsResponse getSearchRecordsResponse(){
        SearchRecordsResponse searchRecordsResponse = new SearchRecordsResponse();
        SearchResultRow searchResultRow1 = new SearchResultRow();
        searchResultRow1.setTitle("Title1");
        searchResultRow1.setAuthor("Author1");
        searchResultRow1.setPublisher("publisher1");
        searchResultRow1.setOwningInstitution("NYPL");
        searchResultRow1.setCollectionGroupDesignation("Shared");
        searchResultRow1.setSelected(true);
        searchRecordsResponse.setSearchResultRows(Arrays.asList(searchResultRow1));
        searchRecordsResponse.setTotalPageCount(2);
        searchRecordsResponse.setTotalBibRecordsCount("1");
        searchRecordsResponse.setTotalItemRecordsCount("1");
        searchRecordsResponse.setTotalRecordsCount("1");
        searchRecordsResponse.setShowTotalCount(false);
        searchRecordsResponse.setErrorMessage("test");
        return searchRecordsResponse;
    }
}
