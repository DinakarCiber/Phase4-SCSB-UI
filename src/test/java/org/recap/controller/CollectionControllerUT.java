package org.recap.controller;

import org.apache.shiro.authc.UsernamePasswordToken;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.recap.RecapConstants;
import org.recap.model.jpa.*;
import org.recap.model.search.BibliographicMarcForm;
import org.recap.model.search.CollectionForm;
import org.recap.model.usermanagement.UserDetailsForm;
import org.recap.model.usermanagement.UserForm;
import org.recap.repository.jpa.RequestItemDetailsRepository;
import org.recap.security.UserManagementService;
import org.recap.util.CollectionServiceUtil;
import org.recap.util.MarcRecordViewUtil;
import org.recap.util.UserAuthUtil;
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

    @InjectMocks
    CollectionController collectionController;

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
    RequestItemDetailsRepository requestItemDetailsRepository;

    public UserAuthUtil getUserAuthUtil() {
        return userAuthUtil;
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
        Mockito.when(getUserAuthUtil().authorizedUser(RecapConstants.SCSB_SHIRO_COLLECTION_URL,(UsernamePasswordToken)session.getAttribute(RecapConstants.USER_TOKEN))).thenReturn(true);
        Mockito.when(getCollectionController.getUserAuthUtil()).thenReturn(userAuthUtil);
        Mockito.when(getCollectionController.collection(model,request)).thenCallRealMethod();
        String response = getCollectionController.collection(model,request);
        assertNotNull(response);
        assertEquals("searchRecords",response);
    }

    @Test
    public void displayRecords() throws Exception {
        CollectionForm collectionForm = new CollectionForm();
        collectionForm.setItemBarcodes("");
        Mockito.when(getCollectionController.displayRecords(collectionForm, bindingResult, model)).thenCallRealMethod();
        ModelAndView modelAndView = getCollectionController.displayRecords(collectionForm, bindingResult, model);
        assertNotNull(modelAndView);
        assertEquals("searchRecords", modelAndView.getViewName());
    }

    @Test
    public void openMarcRecord() throws Exception {
        CollectionForm collectionForm = new CollectionForm();
        UserDetailsForm userDetailsForm= new UserDetailsForm();
        userDetailsForm.setSuperAdmin(false);
        userDetailsForm.setLoginInstitutionId(2);
        userDetailsForm.setRecapUser(false);
        when(request.getSession(false)).thenReturn(session);
        usersSessionAttributes();
        Mockito.when(getUserAuthUtil().getUserDetails(request.getSession(false),RecapConstants.BARCODE_RESTRICTED_PRIVILEGE)).thenReturn(userDetailsForm);
        Mockito.when(getCollectionController.getUserAuthUtil()).thenReturn(userAuthUtil);
        Mockito.when(getCollectionController.getMarcRecordViewUtil()).thenReturn(marcRecordViewUtil);
        Mockito.when(getCollectionController.openMarcView(collectionForm, bindingResult, model,request)).thenCallRealMethod();
        Mockito.when(marcRecordViewUtil.buildBibliographicMarcForm(collectionForm.getBibId(), collectionForm.getItemId(),userDetailsForm)).thenReturn(new BibliographicMarcForm());
        ModelAndView modelAndView = getCollectionController.openMarcView(collectionForm, bindingResult, model,request);
        assertNotNull(modelAndView);
        assertEquals("collection :: #collectionUpdateModal", modelAndView.getViewName());
    }

    @Test
    public void collectionUpdate() throws Exception {
        CollectionForm collectionForm = new CollectionForm();
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
        when(getRequestItemDetailsRepository().findByItemBarcodeAndRequestStaCode(itemBarcode, RecapConstants.REQUEST_STATUS_RETRIEVAL_ORDER_PLACED)).thenReturn(getMockRequestItemEntity());
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
        collectionForm.setSearchResultRows(new ArrayList<>());
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
}
