package org.recap.service;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.marc4j.marc.Record;
import org.recap.RecapConstants;
import org.recap.model.jpa.BibliographicEntity;
import org.recap.model.jpa.InstitutionEntity;
import org.recap.model.jpa.ItemEntity;
import org.recap.model.jpa.CustomerCodeEntity;
import org.recap.model.jpa.RequestStatusEntity;
import org.recap.model.jpa.RequestTypeEntity;
import org.recap.model.jpa.RequestItemEntity;
import org.recap.model.jpa.DeliveryRestrictionEntity;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.support.BindingAwareModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Collections;
import java.util.Set;
import java.util.HashSet;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Comparator;
import java.util.Iterator;

/**
 * Created by akulak on 20/4/17.
 */
@Service
public class RequestService {

    private static final Logger logger = LoggerFactory.getLogger(RequestService.class);

    @Autowired
    private RequestServiceUtil requestServiceUtil;

    @Autowired
    private InstitutionDetailsRepository institutionDetailsRepository;

    @Autowired
    private RequestTypeDetailsRepository requestTypeDetailsRepository;

    @Autowired
    private CustomerCodeDetailsRepository customerCodeDetailsRepository;

    @Autowired
    private ItemDetailsRepository itemDetailsRepository;

    @Autowired
    private RequestStatusDetailsRepository requestStatusDetailsRepository;

    @Autowired
    private RequestItemDetailsRepository requestItemDetailsRepository;

    @Autowired
    private UserAuthUtil userAuthUtil;

    @Autowired
    private RequestService requestService;

    /**
     * Gets request item details repository.
     *
     * @return the request item details repository
     */
    public RequestItemDetailsRepository getRequestItemDetailsRepository() {
        return requestItemDetailsRepository;
    }

    /**
     * Gets request service util.
     *
     * @return the request service util
     */
    public RequestServiceUtil getRequestServiceUtil() {
        return requestServiceUtil;
    }


    /**
     * Gets user auth util.
     *
     * @return the user auth util
     */
    public UserAuthUtil getUserAuthUtil() {
        return userAuthUtil;
    }

    /**
     * Gets institution details repository.
     *
     * @return the institution details repository
     */
    public InstitutionDetailsRepository getInstitutionDetailsRepository() {
        return institutionDetailsRepository;
    }


    /**
     * Gets request type details repository.
     *
     * @return the request type details repository
     */
    public RequestTypeDetailsRepository getRequestTypeDetailsRepository() {
        return requestTypeDetailsRepository;
    }


    /**
     * Gets customer code details repository.
     *
     * @return the customer code details repository
     */
    public CustomerCodeDetailsRepository getCustomerCodeDetailsRepository() {
        return customerCodeDetailsRepository;
    }


    /**
     * Gets item details repository.
     *
     * @return the item details repository
     */
    public ItemDetailsRepository getItemDetailsRepository() {
        return itemDetailsRepository;
    }

    /**
     * Gets request service.
     *
     * @return the request service
     */
    public RequestService getRequestService() {
        return requestService;
    }

    /**
     * Gets request status details repository.
     *
     * @return the request status details repository
     */
    public RequestStatusDetailsRepository getRequestStatusDetailsRepository() {
        return requestStatusDetailsRepository;
    }


    /**
     * Get delivery locations from scsb database for the given customer code and
     * populate those values to get displayed in the request UI page for delivery locations drop down.
     *
     * @param requestForm          the request form
     * @param deliveryLocationsMap the delivery locations map
     * @param userDetailsForm      the user details form
     * @param itemEntity           the item entity
     * @param institutionId        the institution id
     */
    public void processCustomerAndDeliveryCodes(RequestForm requestForm, Map<String, String> deliveryLocationsMap, UserDetailsForm userDetailsForm, ItemEntity itemEntity, Integer institutionId) {
        String customerCode = itemEntity.getCustomerCode();
        CustomerCodeEntity customerCodeEntity = customerCodeDetailsRepository.findByCustomerCodeAndOwningInstitutionId(customerCode, institutionId);
        if(requestForm.getItemOwningInstitution().equals(requestForm.getRequestingInstitution())){
            if (customerCodeEntity != null) {
                String deliveryRestrictions = customerCodeEntity.getDeliveryRestrictions();
                if (StringUtils.isNotBlank(deliveryRestrictions)) {
                    String[] deliverLocationsArray = deliveryRestrictions.split(",");
                    addDeliveryLocations(deliveryLocationsMap,deliverLocationsArray);
                }
            }
        }
        else{
            addDeliveryLocationsForCrossPartner(requestForm, deliveryLocationsMap,customerCodeEntity);
        }
        addRecapDeliveryRestrictions(deliveryLocationsMap, userDetailsForm, customerCodeEntity);
    }

    private void addDeliveryLocationsForCrossPartner(RequestForm requestForm, Map<String, String> deliveryLocationsMap,CustomerCodeEntity customerCodeEntity) {
        if (customerCodeEntity != null) {
            List<DeliveryRestrictionEntity> deliveryRestrictionEntityList = customerCodeEntity.getDeliveryRestrictionEntityList();
            if(CollectionUtils.isNotEmpty(deliveryRestrictionEntityList)){
                for (DeliveryRestrictionEntity deliveryRestrictionEntity : deliveryRestrictionEntityList) {
                    if(requestForm.getRequestingInstitution().equals(deliveryRestrictionEntity.getInstitutionEntity().getInstitutionCode())){
                        String deliveryRestriction = deliveryRestrictionEntity.getDeliveryRestriction();
                        String[] splitDeliveryLocation = StringUtils.split(deliveryRestriction, ",");
                        addDeliveryLocations(deliveryLocationsMap,splitDeliveryLocation);
                    }
                }
            }
        }
    }

    private void addDeliveryLocations(Map<String, String> deliveryLocationsMap, String[] deliveryRestrictions) {
        String[] deliveryRestrictionsArray = Arrays.stream(deliveryRestrictions).map(String::trim).toArray(String[]::new);
        List<CustomerCodeEntity> deliveryRestrictionsList = customerCodeDetailsRepository.findByCustomerCodeIn(Arrays.asList(deliveryRestrictionsArray));
        if (CollectionUtils.isNotEmpty(deliveryRestrictionsList)) {
            Collections.sort(deliveryRestrictionsList);
            for (CustomerCodeEntity customerCodeEntity : deliveryRestrictionsList) {
                if (customerCodeEntity != null){
                    deliveryLocationsMap.put(customerCodeEntity.getCustomerCode(), customerCodeEntity.getDescription());
                }
            }
        }
    }

    private void addRecapDeliveryRestrictions(Map<String, String> deliveryLocationsMap, UserDetailsForm userDetailsForm, CustomerCodeEntity customerCodeEntity) {
        if(userDetailsForm.isRecapUser()){
            String recapDeliveryRestrictions = customerCodeEntity.getRecapDeliveryRestrictions();
            String[] recapDeliveryRestrictionsArray = recapDeliveryRestrictions.split(",");
            addDeliveryLocations(deliveryLocationsMap, recapDeliveryRestrictionsArray);
        }
    }

    /**
     * Sort the given delivery locations in natural order.
     *
     * @param deliveryLocationsMap the delivery locations map
     * @return the map
     */
    public Map<String,String> sortDeliveryLocations(Map<String, String> deliveryLocationsMap){
        LinkedHashMap<String,String> sortedDeliverLocationMap = new LinkedHashMap<>();
        Set<Map.Entry<String, String>> entries = deliveryLocationsMap.entrySet();
        Comparator<Map.Entry<String, String>> valueComparator = (e1, e2) -> {
            String v1 = e1.getValue();
            String v2 = e2.getValue();
            return v1.compareTo(v2);
        };
        List<Map.Entry<String, String>> listOfEntries = new ArrayList<>(entries);
        Collections.sort(listOfEntries, valueComparator);
        LinkedHashMap<String, String> sortedByValue = new LinkedHashMap<>(listOfEntries.size());
        for (Map.Entry<String, String> entry : listOfEntries) {
            sortedByValue.put(entry.getKey(), entry.getValue());
        }
        Set<Map.Entry<String, String>> entrySetSortedByValue = sortedByValue.entrySet();
        for (Map.Entry<String, String> mapping : entrySetSortedByValue) {
            sortedDeliverLocationMap.put(mapping.getKey(), mapping.getValue());
        }
        return sortedDeliverLocationMap;
    }

    /**
     *This method is called asynchronously whenever there is a processing status for an item in request search UI page and
     * fetch the status from the scsb database for that requested item and
     * update that status value for that requested item in the request search UI page.
     *
     * @param request the request
     * @return the refreshed status
     */
    public String getRefreshedStatus(HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();
        Map<Integer,Integer> map = new HashMap<>();
        Map<String,String> responseMap =  new HashMap<>();
        Map<String,String> responseMapForNotes =  new HashMap<>();
        List<Integer> requestIdList = new ArrayList<>();
        List<String> listOfRequestStatusDesc = getRequestStatusDetailsRepository().findAllRequestStatusDescExceptProcessing();
        String[] parameterValues = request.getParameterValues("status[]");
        for (String parameterValue : parameterValues) {
            String[] split = StringUtils.split(parameterValue, "-");
            map.put(Integer.valueOf(split[0]),Integer.valueOf(split[1]));
            requestIdList.add(Integer.valueOf(split[0]));
        }
        List<RequestItemEntity> requestItemEntityList = getRequestItemDetailsRepository().findByIdIn(requestIdList);
        for (RequestItemEntity requestItemEntity : requestItemEntityList) {
            Integer rowUpdateNum = map.get(requestItemEntity.getId());
            for(String requestStatusDescription : listOfRequestStatusDesc) {
                if (requestStatusDescription.equals(requestItemEntity.getRequestStatusEntity().getRequestStatusDescription())) {
                    responseMap.put(String.valueOf(rowUpdateNum), requestItemEntity.getRequestStatusEntity().getRequestStatusDescription());
                    responseMapForNotes.put(String.valueOf(rowUpdateNum), requestItemEntity.getNotes());
                }
            }
        }
        try {
            jsonObject.put(RecapConstants.STATUS,responseMap);
            jsonObject.put(RecapConstants.NOTES,responseMapForNotes);
        } catch (JSONException e) {
            logger.error(RecapConstants.LOG_ERROR,e);
        }
        return jsonObject.toString();
    }

    /**
     * When an item barcode is submitted to place a request in the request UI page , this method populates the information about that item in the request UI page.
     *
     * @param requestForm the request form
     * @param request     the request
     * @return the string
     * @throws JSONException the json exception
     */
    public String populateItemForRequest(@Valid @ModelAttribute("requestForm") RequestForm requestForm, HttpServletRequest request) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        Boolean multipleItemBarcodes= false;
        Map<String,String> deliveryLocationsMap = new LinkedHashMap<>();
        if (StringUtils.isNotBlank(requestForm.getItemBarcodeInRequest())) {
            List<String> itemBarcodes = Arrays.asList(requestForm.getItemBarcodeInRequest().split(","));
            if(itemBarcodes.size()>1){
                multipleItemBarcodes=true;
            }
            List<String> invalidBarcodes = new ArrayList<>();
            List<String> notAvailableBarcodes = new ArrayList<>();
            Set<String> itemTitles = new HashSet<>();
            Set<String> itemOwningInstitutions = new HashSet<>();
            List<String> requestTypes=new ArrayList<>();
            Boolean showEDD=false;
            UserDetailsForm userDetailsForm;
            for (String itemBarcode : itemBarcodes) {
                String barcode = itemBarcode.trim();
                if (StringUtils.isNotBlank(barcode)) {
                    List<ItemEntity> itemEntities = getItemDetailsRepository().findByBarcodeAndCatalogingStatusAndIsDeletedFalse(barcode, RecapConstants.COMPLETE_STATUS);
                    if (CollectionUtils.isNotEmpty(itemEntities)) {
                        for (ItemEntity itemEntity : itemEntities) {
                            CustomerCodeEntity customerCodeEntity = getCustomerCodeDetailsRepository().findByCustomerCodeAndRecapDeliveryRestrictionLikeEDD(itemEntity.getCustomerCode());
                            if(customerCodeEntity!=null) {
                                showEDD = true;
                            }
                            if (null != itemEntity && CollectionUtils.isNotEmpty(itemEntity.getBibliographicEntities())) {
                                userDetailsForm = getUserAuthUtil().getUserDetails(request.getSession(false), RecapConstants.REQUEST_PRIVILEGE);
                                if (itemEntity.getCollectionGroupId() == RecapConstants.CGD_PRIVATE && !userDetailsForm.isSuperAdmin() && !userDetailsForm.isRecapUser() && !userDetailsForm.getLoginInstitutionId().equals(itemEntity.getOwningInstitutionId())) {
                                    jsonObject.put(RecapConstants.NO_PERMISSION_ERROR_MESSAGE, RecapConstants.REQUEST_PRIVATE_ERROR_USER_NOT_PERMITTED);
                                    return jsonObject.toString();
                                } else if (!userDetailsForm.isRecapPermissionAllowed()) {
                                    jsonObject.put(RecapConstants.NO_PERMISSION_ERROR_MESSAGE, RecapConstants.REQUEST_ERROR_USER_NOT_PERMITTED);
                                    return jsonObject.toString();
                                } else {
                                    if (null != itemEntity.getItemStatusEntity() && itemEntity.getItemStatusEntity().getStatusCode().equals(RecapConstants.NOT_AVAILABLE)) {
                                        notAvailableBarcodes.add(barcode);
                                    }
                                    Integer institutionId = itemEntity.getInstitutionEntity().getId();
                                    String institutionCode = itemEntity.getInstitutionEntity().getInstitutionCode();
                                    requestForm.setItemOwningInstitution(institutionCode);
                                    for (BibliographicEntity bibliographicEntity : itemEntity.getBibliographicEntities()) {
                                        String bibContent = new String(bibliographicEntity.getContent());
                                        BibJSONUtil bibJSONUtil = new BibJSONUtil();
                                        List<Record> records = bibJSONUtil.convertMarcXmlToRecord(bibContent);
                                        Record marcRecord = records.get(0);
                                        itemTitles.add(bibJSONUtil.getTitle(marcRecord));
                                        itemOwningInstitutions.add(institutionCode);
                                    }
                                    if(StringUtils.isNotBlank(requestForm.getRequestingInstituionHidden())){
                                        String replaceReqInst = requestForm.getRequestingInstituionHidden();
                                        requestForm.setRequestingInstitution(replaceReqInst);
                                    }
                                    if("true".equals(requestForm.getOnChange()) && StringUtils.isNotBlank(requestForm.getRequestingInstitution())){
                                        getRequestService().processCustomerAndDeliveryCodes(requestForm,deliveryLocationsMap,userDetailsForm,itemEntity,institutionId);
                                        deliveryLocationsMap = sortDeliveryLocationForRecapUser(deliveryLocationsMap, userDetailsForm);
                                    }
                                }
                            }
                        }
                    } else {
                        invalidBarcodes.add(barcode);
                    }
                }
            }
            if (CollectionUtils.isNotEmpty(itemTitles)) {
                jsonObject.put(RecapConstants.REQUESTED_ITEM_TITLE, StringUtils.join(itemTitles, " || "));
            }
            if (CollectionUtils.isNotEmpty(itemOwningInstitutions)) {
                jsonObject.put(RecapConstants.REQUESTED_ITEM_OWNING_INSTITUTION, StringUtils.join(itemOwningInstitutions, ","));
            }
            if((!multipleItemBarcodes && showEDD) && !(RecapConstants.RECALL.equals(requestForm.getRequestType()))){
                List<RequestTypeEntity> requestTypeEntities = getRequestTypeDetailsRepository().findAllExceptBorrowDirect();
                for (RequestTypeEntity requestTypeEntity : requestTypeEntities) {
                    requestTypes.add(requestTypeEntity.getRequestTypeCode());
                }
            }
            else if(!(RecapConstants.RECALL.equals(requestForm.getRequestType()))){
                List<RequestTypeEntity> requestTypeEntityList = getRequestTypeDetailsRepository().findAllExceptEDDAndBorrowDirect();
                for (RequestTypeEntity requestTypeEntity : requestTypeEntityList) {
                    requestTypes.add(requestTypeEntity.getRequestTypeCode());
                }
            }
            jsonObject.put(RecapConstants.REQUEST_TYPES,requestTypes);
            jsonObject.put(RecapConstants.SHOW_EDD, showEDD);
            jsonObject.put(RecapConstants.MULTIPLE_BARCODES, multipleItemBarcodes);

            if (CollectionUtils.isNotEmpty(invalidBarcodes)) {
                jsonObject.put(RecapConstants.ERROR_MESSAGE, RecapConstants.BARCODES_NOT_FOUND + " - " + StringUtils.join(invalidBarcodes, ","));
            }
            if (CollectionUtils.isNotEmpty(notAvailableBarcodes)) {
                jsonObject.put(RecapConstants.NOT_AVAILABLE_ERROR_MESSAGE, RecapConstants.BARCODES_NOT_AVAILABLE + " - " + StringUtils.join(notAvailableBarcodes, ","));
            }
            if (null != deliveryLocationsMap) {
                jsonObject.put(RecapConstants.DELIVERY_LOCATION, deliveryLocationsMap);
            }
        }
        else{
            String replaceReqInst = requestForm.getRequestingInstitution().replace(",", "");
            if(StringUtils.isBlank(replaceReqInst)){
                deliveryLocationsMap.put("","");
                jsonObject.put(RecapConstants.DELIVERY_LOCATION,deliveryLocationsMap);
            }
        }
        return jsonObject.toString();
    }

    private Map<String, String> sortDeliveryLocationForRecapUser(Map<String, String> deliveryLocationsMap, UserDetailsForm userDetailsForm) {
        if(userDetailsForm.isRecapUser()){
            deliveryLocationsMap = getRequestService().sortDeliveryLocations(deliveryLocationsMap);
        }
        return deliveryLocationsMap;
    }

    /**
     * When requesting a item in search UI page, this method populates information about that item in the request UI page.
     *
     * @param model           the model
     * @param request         the request
     * @param userDetailsForm the user details form
     * @return the form details for request
     * @throws JSONException the json exception
     */
    public RequestForm setFormDetailsForRequest(Model model, HttpServletRequest request, UserDetailsForm userDetailsForm) throws JSONException {
        RequestForm requestForm = setDefaultsToCreateRequest(userDetailsForm,model);
        Object requestedBarcode = ((BindingAwareModelMap) model).get(RecapConstants.REQUESTED_BARCODE);
        if (requestedBarcode != null) {
            requestForm.setOnChange("true");
            requestForm.setItemBarcodeInRequest((String) requestedBarcode);
            String stringJson = populateItemForRequest(requestForm,request);
            if (stringJson != null) {
                JSONObject jsonObject = new JSONObject(stringJson);
                Object itemTitle = jsonObject.has(RecapConstants.REQUESTED_ITEM_TITLE) ? jsonObject.get(RecapConstants.REQUESTED_ITEM_TITLE) : null;
                Object itemOwningInstitution = jsonObject.has(RecapConstants.REQUESTED_ITEM_OWNING_INSTITUTION) ? jsonObject.get(RecapConstants.REQUESTED_ITEM_OWNING_INSTITUTION) : null;
                Object deliveryLocations = jsonObject.has(RecapConstants.DELIVERY_LOCATION) ? jsonObject.get(RecapConstants.DELIVERY_LOCATION) : null;
                Object requestTypes = jsonObject.has(RecapConstants.REQUEST_TYPES) ? jsonObject.get(RecapConstants.REQUEST_TYPES) : null;
                List<CustomerCodeEntity> customerCodeEntities = new ArrayList<>();
                List<String> requestTypeList=new ArrayList<>();
                if (itemTitle != null && itemOwningInstitution != null && deliveryLocations != null) {
                    requestForm.setItemTitle((String) itemTitle);
                    requestForm.setItemOwningInstitution((String) itemOwningInstitution);
                    JSONObject deliveryLocationsJson = (JSONObject) deliveryLocations;
                    Iterator iterator = deliveryLocationsJson.keys();
                    while (iterator.hasNext()) {
                        String customerCode = (String) iterator.next();
                        String description = (String) deliveryLocationsJson.get(customerCode);
                        CustomerCodeEntity customerCodeEntity = new CustomerCodeEntity();
                        customerCodeEntity.setCustomerCode(customerCode);
                        customerCodeEntity.setDescription(description);
                        customerCodeEntities.add(customerCodeEntity);
                    }
                    requestForm.setDeliveryLocations(customerCodeEntities);
                }
                if(!(RecapConstants.RECALL.equals(requestForm.getRequestType())) && requestTypes!=null) {
                    JSONArray requestTypeArray = (JSONArray) requestTypes;
                    for (int i = 0; i < requestTypeArray.length(); i++) {
                        requestTypeList.add(requestTypeArray.getString(i));
                    }
                    requestForm.setRequestTypes(requestTypeList);
                }
            }
        }
        return requestForm;
    }

    /**
     * When the request UI page is loaded, this method populates default values to request type drop down and
     * based on the logged in user it populates value to the requesting institution drop down.
     *
     * @param userDetailsForm the user details form
     * @param model           the model
     * @return the defaults to create request
     */
    public RequestForm setDefaultsToCreateRequest(UserDetailsForm userDetailsForm,Model model) {
        RequestForm requestForm = new RequestForm();
        Boolean addOnlyRecall = false;
        Boolean addAllRequestType = false;
        Object availabilty = ((BindingAwareModelMap) model).get(RecapConstants.REQUESTED_ITEM_AVAILABILITY);
        if (availabilty != null){
            HashSet<String> str = (HashSet<String>) availabilty;
            for (String itemAvailability : str){
                if(RecapConstants.NOT_AVAILABLE.equalsIgnoreCase(itemAvailability)){
                    addOnlyRecall = true;
                }
                if(RecapConstants.AVAILABLE.equalsIgnoreCase(itemAvailability)){
                    addAllRequestType = true;
                }
            }
        }

        List<String> requestingInstitutions = new ArrayList<>();
        List<String> requestTypes = new ArrayList<>();

        Iterable<InstitutionEntity> institutionEntities = getInstitutionDetailsRepository().findAll();
        for (Iterator iterator = institutionEntities.iterator(); iterator.hasNext(); ) {
            InstitutionEntity institutionEntity = (InstitutionEntity) iterator.next();
            if (userDetailsForm.getLoginInstitutionId() == institutionEntity.getId() && (!userDetailsForm.isRecapUser()) && (!userDetailsForm.isSuperAdmin()) && (!RecapConstants.HTC.equals(institutionEntity.getInstitutionCode())) ) {
                requestingInstitutions.add(institutionEntity.getInstitutionCode());
                requestForm.setRequestingInstitutions(requestingInstitutions);
                requestForm.setInstitutionList(requestingInstitutions);
                requestForm.setRequestingInstitution(institutionEntity.getInstitutionCode());
                requestForm.setRequestingInstituionHidden(institutionEntity.getInstitutionCode());
                requestForm.setDisableRequestingInstitution(true);
                requestForm.setOnChange("true");
            }
            if ((userDetailsForm.isRecapUser() || userDetailsForm.isSuperAdmin()) && (!RecapConstants.HTC.equals(institutionEntity.getInstitutionCode()))) {
                requestingInstitutions.add(institutionEntity.getInstitutionCode());
                requestForm.setRequestingInstitutions(requestingInstitutions);
                requestForm.setInstitutionList(requestingInstitutions);
                requestForm.setRequestingInstitution("");
                requestForm.setDisableRequestingInstitution(false);
            }
        }

        if(addOnlyRecall &&(addAllRequestType == false)){
            RequestTypeEntity requestTypeEntity = getRequestTypeDetailsRepository().findByRequestTypeCode(RecapConstants.RECALL);
            requestTypes.add(requestTypeEntity.getRequestTypeCode());
            requestForm.setRequestType(requestTypeEntity.getRequestTypeCode());
        }
        if (!addOnlyRecall || addAllRequestType) {
            Iterable<RequestTypeEntity> requestTypeEntities = getRequestTypeDetailsRepository().findAll();
            for (Iterator iterator = requestTypeEntities.iterator(); iterator.hasNext(); ) {
                RequestTypeEntity requestTypeEntity = (RequestTypeEntity) iterator.next();
                if (!RecapConstants.BORROW_DIRECT.equals(requestTypeEntity.getRequestTypeCode())) {
                    requestTypes.add(requestTypeEntity.getRequestTypeCode());
                }
            }
            requestForm.setRequestType(RecapConstants.RETRIEVAL);
        }
        requestForm.setRequestTypes(requestTypes);
        return requestForm;
    }

    /**
     * Adds all the request status description in scsb into the request statuses list.
     *
     * @param requestStatuses the request statuses
     */
    public void findAllRequestStatusExceptProcessing(List<String> requestStatuses) {
        Iterable<RequestStatusEntity> requestStatusEntities = getRequestStatusDetailsRepository().findAllExceptProcessing();
        for (Iterator iterator = requestStatusEntities.iterator(); iterator.hasNext(); ) {
            RequestStatusEntity requestStatusEntity = (RequestStatusEntity) iterator.next();
            requestStatuses.add(requestStatusEntity.getRequestStatusDescription());
        }
    }

    /**
     * Adds the institution code into the institution list for super admin role.
     *
     * @param institutionList the institution list
     */
    public void getInstitutionForSuperAdmin(List<String> institutionList) {
        Iterable<InstitutionEntity> institutionEntities = getInstitutionDetailsRepository().getInstitutionCodeForSuperAdmin();
        for (Iterator iterator = institutionEntities.iterator(); iterator.hasNext();) {
            InstitutionEntity institutionEntity=(InstitutionEntity)iterator.next();
            institutionList.add(institutionEntity.getInstitutionCode());
        }
    }

}
