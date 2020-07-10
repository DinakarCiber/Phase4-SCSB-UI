package org.recap.service;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.util.Assert;
import org.codehaus.jettison.json.JSONException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;
import org.marc4j.marc.Record;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.recap.BaseTestCase;
import org.recap.RecapCommonConstants;
import org.recap.RecapConstants;
import org.recap.controller.RequestController;
import org.recap.model.jpa.*;
import org.recap.model.search.RequestForm;
import org.recap.model.usermanagement.UserDetailsForm;
import org.recap.repository.jpa.InstitutionDetailsRepository;
import org.recap.repository.jpa.RequestTypeDetailsRepository;
import org.recap.repository.jpa.CustomerCodeDetailsRepository;
import org.recap.repository.jpa.ItemDetailsRepository;
import org.recap.repository.jpa.RequestItemDetailsRepository;
import org.recap.repository.jpa.RequestStatusDetailsRepository;
import org.recap.util.BibJSONUtil;
import org.recap.util.RequestServiceUtil;
import org.recap.util.UserAuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.validation.BindingResult;
import org.springframework.validation.support.BindingAwareModelMap;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

/**
 * Created by akulak on 24/4/17.
 */
public class RequestServiceUT extends BaseTestCase {

    @PersistenceContext
    private EntityManager entityManager;

    @Mock
    RequestService requestServiceMocked;

    @Autowired
    RequestService requestService;

    @Mock
    javax.servlet.http.HttpServletRequest request;

    @Mock
    BindingAwareModelMap model;

    @Mock
    HttpSession session;

    @Mock
    BindingResult bindingResult;

    @Mock
    RequestServiceUtil requestServiceUtil;

    @Mock
    private UserAuthUtil userAuthUtil;

    @Mock
    RequestController requestController;

    @Autowired
    CustomerCodeDetailsRepository customerCodeDetailsRepository;

    @Mock
    CustomerCodeDetailsRepository mockedCustomerCodeDetailsRepository;

    @Mock
    InstitutionDetailsRepository institutionDetailsRepository;

    @Mock
    RequestItemDetailsRepository requestItemDetailsRepository;

    @Mock
    ItemDetailsRepository itemDetailsRepository;

    @Mock
    RequestTypeDetailsRepository requestTypeDetailsRepository;

    @Mock
    BibJSONUtil bibJSONUtil;

    @Mock
    RequestStatusDetailsRepository requestStatusDetailsRepository;
    Map<String, String> deliveryLocations = new HashMap<>();
    public BindingAwareModelMap getModel() {
        return model;
    }
    @Test
    public void testDeliveryLocations() throws Exception{
        RequestForm requestForm = getRequestForm2();
        ItemEntity itemEntity = getItemEntity();
        UserDetailsForm userDetailsForm = getUserDetailsForm(true);
        Map<String, String> deliveryLocationsMap = new HashMap<>();
        requestService.processCustomerAndDeliveryCodes(requestForm,deliveryLocationsMap,userDetailsForm,itemEntity,1);
        List<String> deliveryLocationList = new ArrayList<>();
        for(String deliveryLocation : deliveryLocationsMap.keySet()){
            deliveryLocationList.add(deliveryLocation);
        }
        deliveryLocations.putAll(deliveryLocationsMap);
        CustomerCodeEntity CustomerCode = customerCodeDetailsRepository.findByCustomerCode(itemEntity.getCustomerCode());
        String deliveryRestrictions = CustomerCode.getDeliveryRestrictions();
        String[] splitDeliveryLocation = StringUtils.split(deliveryRestrictions, ",");
        String[] deliveryRestrictionsArray = Arrays.stream(splitDeliveryLocation).map(String::trim).toArray(String[]::new);
        assertNotNull(deliveryRestrictionsArray);
    }
    @Test
    public void testDeliveryLocationsWithSameInstitutions() throws Exception{
        RequestForm requestForm = getRequestForm();
        ItemEntity itemEntity = getItemEntity();
        UserDetailsForm userDetailsForm = getUserDetailsForm(true);
        Map<String, String> deliveryLocationsMap = new HashMap<>();
        requestService.processCustomerAndDeliveryCodes(requestForm,deliveryLocationsMap,userDetailsForm,itemEntity,1);
        List<String> deliveryLocationList = new ArrayList<>();
        for(String deliveryLocation : deliveryLocationsMap.keySet()){
            deliveryLocationList.add(deliveryLocation);
        }
        deliveryLocations.putAll(deliveryLocationsMap);
        CustomerCodeEntity CustomerCode = customerCodeDetailsRepository.findByCustomerCode(itemEntity.getCustomerCode());
        String deliveryRestrictions = CustomerCode.getDeliveryRestrictions();
        String[] splitDeliveryLocation = StringUtils.split(deliveryRestrictions, ",");
        String[] deliveryRestrictionsArray = Arrays.stream(splitDeliveryLocation).map(String::trim).toArray(String[]::new);
        assertNotNull(deliveryRestrictionsArray);
    }
    @Test
    public void testSortDeliveryLocations() throws Exception{
        deliveryLocations.put("2","PA");
        deliveryLocations.put("4","CU");
        deliveryLocations.put("3","BA");
        deliveryLocations.put("1","QX");
        requestService.sortDeliveryLocations(deliveryLocations);
        List<String> deliveryLocationList = new ArrayList<>();
        for(String deliveryLocation : deliveryLocations.keySet()){
            deliveryLocationList.add(deliveryLocation);
        }
        assertNotNull(deliveryLocations);
    }

    @Test
    public void checkGetterServices(){
        Mockito.when(requestServiceMocked.getRequestItemDetailsRepository()).thenCallRealMethod();
        Mockito.when(requestServiceMocked.getRequestServiceUtil()).thenCallRealMethod();
        Mockito.when(requestServiceMocked.getUserAuthUtil()).thenCallRealMethod();
        Mockito.when(requestServiceMocked.getInstitutionDetailsRepository()).thenCallRealMethod();
        Mockito.when(requestServiceMocked.getRequestTypeDetailsRepository()).thenCallRealMethod();
        Mockito.when(requestServiceMocked.getCustomerCodeDetailsRepository()).thenCallRealMethod();
        Mockito.when(requestServiceMocked.getItemDetailsRepository()).thenCallRealMethod();
        Mockito.when(requestServiceMocked.getRequestService()).thenCallRealMethod();
        Mockito.when(requestServiceMocked.getRequestStatusDetailsRepository()).thenCallRealMethod();

        assertNotEquals(requestServiceMocked.getRequestItemDetailsRepository(),requestItemDetailsRepository);
        assertNotEquals(requestServiceMocked.getItemDetailsRepository(),itemDetailsRepository);
        assertNotEquals(requestServiceMocked.getCustomerCodeDetailsRepository(),mockedCustomerCodeDetailsRepository);
        assertNotEquals(requestServiceMocked.getRequestStatusDetailsRepository(),requestStatusDetailsRepository);
        assertNotEquals(requestServiceMocked.getInstitutionDetailsRepository(),institutionDetailRepository);
        assertNotEquals(requestServiceMocked.getRequestTypeDetailsRepository(),requestTypeDetailsRepository);
        assertNotEquals(requestServiceMocked.getRequestService(),requestServiceMocked);
        assertNotEquals(requestServiceMocked.getRequestServiceUtil(),requestServiceUtil);
        assertNotEquals(requestServiceMocked.getUserAuthUtil(),userAuthUtil);

    }

    @Test
    public void testDeliveryLocationsForRecapUser() throws Exception{
        RequestForm requestForm = getRequestForm();
        ItemEntity itemEntity = getItemEntity();
        UserDetailsForm userDetailsForm = getUserDetailsForm(true);
        Map<String, String> deliveryLocationsMap = new HashMap<>();
//        requestService.processCustomerAndDeliveryCodes(requestForm,deliveryLocationsMap,userDetailsForm,itemEntity,1);
        List<String> deliveryLocationList = new ArrayList<>();
        for(String deliveryLocation : deliveryLocationsMap.keySet()){
            deliveryLocationList.add(deliveryLocation);
        }
       /* CustomerCodeEntity customerCode = customerCodeDetailsRepository.findByCustomerCode(itemEntity.getCustomerCode());
        String deliveryRestrictions = customerCode.getDeliveryRestrictions();
        String recapDeliveryRestrictions = customerCode.getRecapDeliveryRestrictions();
        String[] deliveryRestrictionSplit = StringUtils.split(deliveryRestrictions, ",");
        String[] recapDeliveryLocationSplit = StringUtils.split(recapDeliveryRestrictions, ",");
        String[] deliveryRestrictionsArray = Arrays.stream(deliveryRestrictionSplit).map(String::trim).toArray(String[]::new);
        String[] recapDeliveryRestrictionsArray = Arrays.stream(recapDeliveryLocationSplit).map(String::trim).toArray(String[]::new);
        List<String> deliveryLocationsList= new ArrayList<>(Arrays.asList(deliveryRestrictionsArray));
        deliveryLocationsList.addAll(Arrays.asList(recapDeliveryRestrictionsArray));
        assertTrue(deliveryLocationList.containsAll(deliveryLocationsList) && deliveryLocationsList.containsAll(deliveryLocationList));*/
    }

    @Test
    public void populateItem() throws Exception {
        RequestForm requestForm = new RequestForm();
        BibliographicEntity bibliographicEntity = saveBibSingleHoldingsSingleItem();
        String barcode = bibliographicEntity.getItemEntities().get(0).getBarcode();
        requestForm.setItemBarcodeInRequest(barcode);
        CustomerCodeEntity customerCodeEntity = getCustomerCodeEntity();
        Mockito.when(requestServiceMocked.getItemDetailsRepository()).thenReturn(itemDetailsRepository);
        Mockito.when(requestServiceMocked.getUserAuthUtil()).thenReturn(userAuthUtil);
        when(request.getSession()).thenReturn(session);
        UserDetailsForm userDetailsForm = getUserDetailsForm();
        userDetailsForm.setRecapPermissionAllowed(true);
        Mockito.when(requestServiceMocked.populateItemForRequest(requestForm,request)).thenCallRealMethod();
        Mockito.when(requestServiceMocked.getItemDetailsRepository()).thenReturn(itemDetailsRepository);
        List<ItemEntity> itemEntityList=new ArrayList<>();
        ItemEntity itemEntity= getItemEntity();
        itemEntity.setCustomerCode("PG");
        itemEntity.setCollectionGroupId(2);
        itemEntityList.add(itemEntity);
        List<Record> records = new ArrayList<Record>();
        Mockito.when(requestServiceMocked.getRequestTypeDetailsRepository()).thenReturn(requestTypeDetailsRepository);
        Mockito.when(requestServiceMocked.getCustomerCodeDetailsRepository()).thenReturn(mockedCustomerCodeDetailsRepository);
        List<RequestTypeEntity> requestTypeEntityList=new ArrayList<>();
        RequestTypeEntity requestTypeEntity = new RequestTypeEntity();
        requestTypeEntity.setRequestTypeCode("RETRIEVAL");
        requestTypeEntity.setRequestTypeDesc("RETRIEVAL");
        requestTypeEntity.setId(1);
        requestTypeEntityList.add(requestTypeEntity);
        //Mockito.when(getBibJSONUtil().convertMarcXmlToRecord(bibContent)).thenReturn(records);
        Mockito.when(requestServiceMocked.getUserAuthUtil().getUserDetails(request.getSession(false), RecapConstants.REQUEST_PRIVILEGE)).thenReturn(userDetailsForm);
        Mockito.when(requestServiceMocked.getItemDetailsRepository().findByBarcodeAndCatalogingStatusAndIsDeletedFalse(barcode, RecapCommonConstants.COMPLETE_STATUS)).thenReturn(itemEntityList);
        Mockito.when(requestServiceMocked.getCustomerCodeDetailsRepository().findByCustomerCodeAndRecapDeliveryRestrictionLikeEDD(itemEntity.getCustomerCode())).thenReturn(new CustomerCodeEntity());
        Mockito.doCallRealMethod().when(requestServiceMocked).populateItemForRequest(requestForm,request);
        //String response = requestServiceMocked.populateItemForRequest(requestForm,request);
       // assertNotNull(response);
    }
    @Test
    public void populateItem1() throws Exception {
        RequestForm requestForm = new RequestForm();
        BibliographicEntity bibliographicEntity = saveBibSingleHoldingsSingleItem();
        String barcode = bibliographicEntity.getItemEntities().get(0).getBarcode();
        requestForm.setItemBarcodeInRequest(barcode);
        CustomerCodeEntity customerCodeEntity = getCustomerCodeEntity();
        Mockito.when(requestServiceMocked.getItemDetailsRepository()).thenReturn(itemDetailsRepository);
        Mockito.when(requestServiceMocked.getUserAuthUtil()).thenReturn(userAuthUtil);
        when(request.getSession()).thenReturn(session);
        UserDetailsForm userDetailsForm = getUserDetailsForm();
        Mockito.when(requestServiceMocked.populateItemForRequest(requestForm,request)).thenCallRealMethod();
        Mockito.when(requestServiceMocked.getItemDetailsRepository()).thenReturn(itemDetailsRepository);
        List<ItemEntity> itemEntityList=new ArrayList<>();
        ItemEntity itemEntity= getItemEntity();
        itemEntity.setCustomerCode("PG");
        itemEntity.setCollectionGroupId(3);
        itemEntityList.add(itemEntity);
        Mockito.when(requestServiceMocked.getRequestTypeDetailsRepository()).thenReturn(requestTypeDetailsRepository);
        Mockito.when(requestServiceMocked.getCustomerCodeDetailsRepository()).thenReturn(mockedCustomerCodeDetailsRepository);
        List<RequestTypeEntity> requestTypeEntityList=new ArrayList<>();
        RequestTypeEntity requestTypeEntity = new RequestTypeEntity();
        requestTypeEntity.setRequestTypeCode("RETRIEVAL");
        requestTypeEntity.setRequestTypeDesc("RETRIEVAL");
        requestTypeEntity.setId(1);
        requestTypeEntityList.add(requestTypeEntity);
        Mockito.when(requestServiceMocked.getUserAuthUtil().getUserDetails(request.getSession(false), RecapConstants.REQUEST_PRIVILEGE)).thenReturn(userDetailsForm);
        Mockito.when(requestServiceMocked.getItemDetailsRepository().findByBarcodeAndCatalogingStatusAndIsDeletedFalse(barcode, RecapCommonConstants.COMPLETE_STATUS)).thenReturn(itemEntityList);
        Mockito.when(requestServiceMocked.getCustomerCodeDetailsRepository().findByCustomerCodeAndRecapDeliveryRestrictionLikeEDD(itemEntity.getCustomerCode())).thenReturn(new CustomerCodeEntity());
        Mockito.doCallRealMethod().when(requestServiceMocked).populateItemForRequest(requestForm,request);
        String response = requestServiceMocked.populateItemForRequest(requestForm,request);
        assertNotNull(response);
    }

    @Test
    public void testRefreshingStatus(){
        String status="status[]";
        String[] statusValue={"16-0"};
        MockHttpServletRequest mockedRequest = new MockHttpServletRequest();
        mockedRequest.addParameter(status, statusValue);
        RequestItemEntity requestItemEntity=getRequestItemEntity();
        Mockito.when(requestServiceMocked.getRequestStatusDetailsRepository()).thenReturn(requestStatusDetailsRepository);
        Mockito.when(requestServiceMocked.getRequestItemDetailsRepository()).thenReturn(requestItemDetailsRepository);
        Mockito.when(requestServiceMocked.getRequestStatusDetailsRepository().findAllRequestStatusDescExceptProcessing()).thenReturn(Arrays.asList("RETRIEVAL ORDER PLACED","RECALL ORDER PLACED","EDD ORDER PLACED","REFILED","CANCELED","EXCEPTION","PENDING","INITIAL LOAD"));
        Mockito.when(requestItemDetailsRepository.findByIdIn(Arrays.asList(requestItemEntity.getId()))).thenReturn(Arrays.asList(requestItemEntity));
        Mockito.when(requestServiceMocked.getRefreshedStatus(mockedRequest)).thenCallRealMethod();
        String refreshedStatus = requestServiceMocked.getRefreshedStatus(mockedRequest);
        assertNotNull(refreshedStatus);
    }

    @Test
    public void testSetFormDetailsForRequest() throws Exception {
        RequestForm requestForm = getRequestForm();
        LinkedHashSet<String> requestedItemAvailabilty = new LinkedHashSet<>();
        JSONObject json = new JSONObject();
        JSONObject jsonObject = new JSONObject();
        JSONObject jsonObject1 = new JSONObject();
        JSONArray array = new JSONArray();
        jsonObject.put("1","PA");
        jsonObject.put("2","PB");
        array.put("RECALL");
        array.put("RETRIEVE");
        json.put("error", "No Error");
        json.put("noPermissionErrorMessage", "No");
        json.put("itemTitle", "testName");
        json.put("itemOwningInstitution", "CUL");
        json.put("deliveryLocation",jsonObject);
        json.put("requestTypes",array);
        String message = json.toString();
        requestedItemAvailabilty.add("Available");
        requestedItemAvailabilty.add("Not Available");
        UserDetailsForm userDetailsForm = getUserDetailsForm();
        Mockito.when(requestServiceMocked.getInstitutionDetailsRepository()).thenReturn(institutionDetailRepository);
        Mockito.when(requestServiceMocked.getRequestTypeDetailsRepository()).thenReturn(requestTypeDetailsRepository);
        Object barcode="123";
        Mockito.when(((BindingAwareModelMap) model).get(RecapConstants.REQUESTED_ITEM_AVAILABILITY)).thenReturn(requestedItemAvailabilty);
        Mockito.when(((BindingAwareModelMap) model).get(RecapConstants.REQUESTED_BARCODE)).thenReturn(barcode);
        Mockito.when(requestServiceMocked.setFormDetailsForRequest(model,request,userDetailsForm)).thenCallRealMethod();
        Mockito.doReturn(message).when(requestServiceMocked).populateItemForRequest(requestForm,request);
        Mockito.when(requestServiceMocked.setDefaultsToCreateRequest(userDetailsForm,model)).thenCallRealMethod();
        RequestForm requestForm1 = requestServiceMocked.setFormDetailsForRequest(model, request, userDetailsForm);
        Assert.notNull(requestForm1);
    }

    @Test
    public void testSetDefaultsToCreateRequest(){
        LinkedHashSet<String> requestedItemAvailabilty = new LinkedHashSet<>();
        requestedItemAvailabilty.add("Available");
        requestedItemAvailabilty.add("Not Available");
        UserDetailsForm userDetailsForm = getUserDetailsForm();
        Mockito.when(requestServiceMocked.getInstitutionDetailsRepository()).thenReturn(institutionDetailRepository);
        Mockito.when(requestServiceMocked.getRequestTypeDetailsRepository()).thenReturn(requestTypeDetailsRepository);
        Mockito.when(((BindingAwareModelMap) model).get(RecapConstants.REQUESTED_ITEM_AVAILABILITY)).thenReturn(requestedItemAvailabilty);
        Mockito.when(requestServiceMocked.setDefaultsToCreateRequest(userDetailsForm,model)).thenCallRealMethod();
        RequestForm requestForm = requestServiceMocked.setDefaultsToCreateRequest(userDetailsForm, model);
        Assert.notNull(requestForm);
    }
    @Test
    public void testSetDefaultsToCreateRequest1(){
        LinkedHashSet<String> requestedItemAvailabilty = new LinkedHashSet<>();
        requestedItemAvailabilty.add("Available");
        UserDetailsForm userDetailsForm = getUserDetailsForm();
        userDetailsForm.setSuperAdmin(true);
        Mockito.when(requestServiceMocked.getInstitutionDetailsRepository()).thenReturn(institutionDetailRepository);
        Mockito.when(requestServiceMocked.getRequestTypeDetailsRepository()).thenReturn(requestTypeDetailsRepository);
        Mockito.when(((BindingAwareModelMap) model).get(RecapConstants.REQUESTED_ITEM_AVAILABILITY)).thenReturn(requestedItemAvailabilty);
        Mockito.when(requestServiceMocked.setDefaultsToCreateRequest(userDetailsForm,model)).thenCallRealMethod();
        RequestForm requestForm = requestServiceMocked.setDefaultsToCreateRequest(userDetailsForm, model);
        Assert.notNull(requestForm);
    }

    @Test
    public void testFindAllRequestStatusExceptProcessing(){
        List<RequestStatusEntity> allExceptProcessing = getRequestStatusEntityList();
        Mockito.when(requestServiceMocked.getRequestStatusDetailsRepository()).thenReturn(requestStatusDetailsRepository);
        Mockito.when(requestStatusDetailsRepository.findAllExceptProcessing()).thenReturn(allExceptProcessing);
        List<String> requestStatuses=new ArrayList<>();
        Mockito.doCallRealMethod().when(requestServiceMocked).findAllRequestStatusExceptProcessing(requestStatuses);
        requestServiceMocked.findAllRequestStatusExceptProcessing(requestStatuses);
    }

    @Test
    public void testGetInstitutionForSuperAdmin(){
        Mockito.when(requestServiceMocked.getInstitutionDetailsRepository()).thenReturn(institutionDetailRepository);
        List<String> institutionList=new ArrayList<>();
        Mockito.doCallRealMethod().when(requestServiceMocked).getInstitutionForSuperAdmin(institutionList);
        requestServiceMocked.getInstitutionForSuperAdmin(institutionList);
    }


    public BibliographicEntity saveBibSingleHoldingsSingleItem() throws Exception {
        Random random = new Random();
        BibliographicEntity bibliographicEntity = new BibliographicEntity();

        //bibliographicEntity.setContent(bibContent.getBytes());
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

        return bibliographicEntity;

    }
    public File getUnicodeContentFile() throws URISyntaxException {
        URL resource = getClass().getResource("UnicodeBibContent.xml");
        return new File(resource.toURI());
    }

    private ItemEntity getItemEntity() throws Exception {
        BibliographicEntity bibliographicEntity = new BibliographicEntity();
       // File bibContentFile = getUnicodeContentFile();
       // String sourceBibContent = FileUtils.readFileToString(bibContentFile, "UTF-8");
       // bibliographicEntity.setContent(sourceBibContent.getBytes());
        bibliographicEntity.setCreatedDate(new Date());
        bibliographicEntity.setLastUpdatedDate(new Date());
        bibliographicEntity.setCreatedBy("tst");
        bibliographicEntity.setLastUpdatedBy("tst");
        bibliographicEntity.setOwningInstitutionId(1);
        InstitutionEntity institutionEntity = new InstitutionEntity();
        institutionEntity.setId(1);
        institutionEntity.setInstitutionCode("PUL");
        institutionEntity.setInstitutionName("Princeton");
        ItemEntity itemEntity = new ItemEntity();
        itemEntity.setLastUpdatedDate(new Date());
        itemEntity.setOwningInstitutionId(1);
        itemEntity.setBarcode("123");
        itemEntity.setCallNumber("x.12321");
        itemEntity.setCallNumberType("1");
        itemEntity.setCustomerCode("PA");
        itemEntity.setCreatedDate(new Date());
        itemEntity.setCreatedBy("tst");
        itemEntity.setLastUpdatedBy("tst");
        itemEntity.setItemAvailabilityStatusId(1);
        itemEntity.setInstitutionEntity(institutionEntity);
        itemEntity.setBibliographicEntities(Arrays.asList(bibliographicEntity));
        return itemEntity;
    }

    private RequestForm getRequestForm() {
        RequestForm requestForm = new RequestForm();
        requestForm.setItemOwningInstitution("PUL");
        requestForm.setRequestingInstitution("PUL");
        return requestForm;
    }
    private RequestForm getRequestForm2() {
        RequestForm requestForm = new RequestForm();
        requestForm.setItemOwningInstitution("PUL");
        requestForm.setRequestingInstitution("NYPL");
        return requestForm;
    }

    private UserDetailsForm getUserDetailsForm(boolean recapUser) {
        UserDetailsForm userDetailsForm = new UserDetailsForm();
        userDetailsForm.setRecapUser(recapUser);
        return userDetailsForm;
    }

    private UserDetailsForm getUserDetailsForm(){
        UserDetailsForm userDetailsForm=new UserDetailsForm();
        userDetailsForm.setLoginInstitutionId(2);
        userDetailsForm.setSuperAdmin(false);
        userDetailsForm.setRecapPermissionAllowed(false);
        userDetailsForm.setRecapUser(false);
        return userDetailsForm;
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

    private List<RequestStatusEntity> getRequestStatusEntityList(){
        List<RequestStatusEntity> allExceptProcessing = new ArrayList<>();
        RequestStatusEntity requestStatusEntity =new RequestStatusEntity();
        requestStatusEntity.setId(1);
        requestStatusEntity.setRequestStatusCode("RETRIEVAL_ORDER_PLACED");
        requestStatusEntity.setRequestStatusDescription("RETRIEVAL ORDER PLACED");
        allExceptProcessing.add(requestStatusEntity);
        RequestStatusEntity requestStatusEntity1 =new RequestStatusEntity();
        requestStatusEntity1.setId(2);
        requestStatusEntity1.setRequestStatusCode("RECALL_ORDER_PLACED");
        requestStatusEntity1.setRequestStatusDescription("RECALL ORDER PLACED");
        allExceptProcessing.add(requestStatusEntity1);
        RequestStatusEntity requestStatusEntity2 =new RequestStatusEntity();
        requestStatusEntity2.setId(3);
        requestStatusEntity2.setRequestStatusCode("EDD_ORDER_PLACED");
        requestStatusEntity2.setRequestStatusDescription("EDD ORDER PLACED");
        allExceptProcessing.add(requestStatusEntity2);
        return allExceptProcessing;
    }

    private CustomerCodeEntity getCustomerCodeEntity(){
        CustomerCodeEntity customerCodeEntity = new CustomerCodeEntity();
        customerCodeEntity.setPwdDeliveryRestrictions("PA");
        customerCodeEntity.setDeliveryRestrictions("PA");
        customerCodeEntity.setDescription("Test");
        customerCodeEntity.setOwningInstitutionId(1);
        customerCodeEntity.setId(1);
        customerCodeEntity.setCustomerCode("CUS@001");
        return customerCodeEntity;
    }
    public BibJSONUtil getBibJSONUtil() {
        return bibJSONUtil;
    }
}
