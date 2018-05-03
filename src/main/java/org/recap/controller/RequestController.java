package org.recap.controller;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.recap.RecapConstants;
import org.recap.model.jpa.CustomerCodeEntity;
import org.recap.model.jpa.InstitutionEntity;
import org.recap.model.jpa.ItemEntity;
import org.recap.model.jpa.RequestItemEntity;
import org.recap.model.request.CancelRequestResponse;
import org.recap.model.request.ItemRequestInformation;
import org.recap.model.request.ItemResponseInformation;
import org.recap.model.request.ReplaceRequest;
import org.recap.model.search.RequestForm;
import org.recap.model.search.SearchResultRow;
import org.recap.model.usermanagement.UserDetailsForm;
import org.recap.repository.jpa.*;
import org.recap.security.UserManagementService;
import org.recap.service.RequestService;
import org.recap.service.RestHeaderService;
import org.recap.util.RequestServiceUtil;
import org.recap.util.SecurityUtil;
import org.recap.util.UserAuthUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.text.NumberFormat;
import java.util.*;

/**
 * Created by rajeshbabuk on 13/10/16.
 */

@Controller
public class RequestController {

    private static final Logger logger = LoggerFactory.getLogger(RequestController.class);

    @Value("${scsb.url}")
    private String scsbUrl;

    @Value("${scsb.shiro}")
    private String scsbShiro;

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

    @Autowired
    RestHeaderService restHeaderService;

    @Autowired
    private SecurityUtil securityUtil;

    public RestHeaderService getRestHeaderService(){return restHeaderService;}

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
     * Gets scsb shiro.
     *
     * @return the scsb shiro
     */
    public String getScsbShiro() {
        return scsbShiro;
    }

    /**
     * Gets scsb url.
     *
     * @return the scsb url
     */
    public String getScsbUrl() {
        return scsbUrl;
    }

    /**
     * Gets request item details repository.
     *
     * @return the request item details repository
     */
    public RequestItemDetailsRepository getRequestItemDetailsRepository() {
        return requestItemDetailsRepository;
    }

    /**
     * Gets rest template.
     *
     * @return the rest template
     */
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
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
     * Gets request service.
     *
     * @return the request service
     */
    public RequestService getRequestService() {
        return requestService;
    }

    /**
     * Render the request UI page for the scsb application.
     *
     * @param model   the model
     * @param request the request
     * @return the string
     * @throws JSONException the json exception
     */
    @RequestMapping("/request")
    public String request(Model model, HttpServletRequest request) throws JSONException {
        HttpSession session = request.getSession(false);
        boolean authenticated = getUserAuthUtil().authorizedUser(RecapConstants.SCSB_SHIRO_REQUEST_URL, (UsernamePasswordToken) session.getAttribute(RecapConstants.USER_TOKEN));
        if (authenticated) {
            UserDetailsForm userDetailsForm = getUserAuthUtil().getUserDetails(session, RecapConstants.REQUEST_PRIVILEGE);
            RequestForm requestForm = getRequestService().setFormDetailsForRequest(model, request, userDetailsForm);
            model.addAttribute(RecapConstants.REQUEST_FORM, requestForm);
            model.addAttribute(RecapConstants.TEMPLATE, RecapConstants.REQUEST);
            return RecapConstants.VIEW_SEARCH_RECORDS;
        } else {
            return UserManagementService.unAuthorizedUser(session, "Request", logger);
        }
    }

    /**
     * Get results from scsb database and display them as row based on the search conditions provided in the search request UI page.
     *
     * @param requestForm the request form
     * @param result      the result
     * @param model       the model
     * @return the model and view
     */
    @ResponseBody
    @RequestMapping(value = "/request", method = RequestMethod.POST, params = "action=searchRequests")
    public ModelAndView searchRequests(@Valid @ModelAttribute("requestForm") RequestForm requestForm,
                                       BindingResult result,
                                       Model model) {
        try {
            disableRequestSearchInstitutionDropDown(requestForm);
            requestForm.resetPageNumber();
            searchAndSetResults(requestForm);
            model.addAttribute(RecapConstants.TEMPLATE, RecapConstants.REQUEST);
        } catch (Exception exception) {
            logger.error(RecapConstants.LOG_ERROR, exception);
            logger.debug(exception.getMessage());
        }
        return new ModelAndView(RecapConstants.VIEW_SEARCH_REQUESTS_SECTION, RecapConstants.REQUEST_FORM, requestForm);
    }

    /**
     *To know the request information of an item once the request is placed through the create request UI page.
     *
     * @param requestForm            the request form
     * @param patronBarcodeInRequest the patron barcode in request
     * @param result                 the result
     * @param model                  the model
     * @return the model and view
     */
    @ResponseBody
    @RequestMapping(value = "/request/goToSearchRequest", method = RequestMethod.GET)
    public ModelAndView goToSearchRequest(@Valid @ModelAttribute("requestForm") RequestForm requestForm,String patronBarcodeInRequest,
                                       BindingResult result,
                                       Model model,HttpServletRequest request) {
        try {
            UserDetailsForm userDetails = getUserAuthUtil().getUserDetails(request.getSession(false), RecapConstants.REQUEST_PRIVILEGE);
            requestForm.resetPageNumber();
            requestForm.setPatronBarcode(patronBarcodeInRequest);
            List<String> requestStatuses = new ArrayList<>();
            List<String> institutionList = new ArrayList<>();
            getRequestService().findAllRequestStatusExceptProcessing(requestStatuses);
            requestForm.setRequestStatuses(requestStatuses);
            setFormValuesToDisableSearchInstitution(requestForm, userDetails, institutionList);
            requestForm.setStatus("");
            searchAndSetResults(requestForm);
            model.addAttribute(RecapConstants.TEMPLATE, RecapConstants.REQUEST);
        } catch (Exception exception) {
            logger.error(RecapConstants.LOG_ERROR, exception);
            logger.debug(exception.getMessage());
        }
        return new ModelAndView("request :: #requestContentId", RecapConstants.REQUEST_FORM, requestForm);
    }

    /**
     * Get first page results from scsb database and display them as row in the search request UI page.
     *
     * @param requestForm the request form
     * @param result      the result
     * @param model       the model
     * @return the model and view
     */
    @ResponseBody
    @RequestMapping(value = "/request", method = RequestMethod.POST, params = "action=first")
    public ModelAndView searchFirst(@Valid @ModelAttribute("requestForm") RequestForm requestForm,
                                    BindingResult result,
                                    Model model) {
        disableRequestSearchInstitutionDropDown(requestForm);
        requestForm.resetPageNumber();
        searchAndSetResults(requestForm);
        model.addAttribute(RecapConstants.TEMPLATE, RecapConstants.REQUEST);
        return new ModelAndView(RecapConstants.VIEW_SEARCH_REQUESTS_SECTION, RecapConstants.REQUEST_FORM, requestForm);
    }

    /**
     * Get last page results from scsb database and display them as row in the search request UI page.
     *
     * @param requestForm the request form
     * @param result      the result
     * @param model       the model
     * @return the model and view
     */
    @ResponseBody
    @RequestMapping(value = "/request", method = RequestMethod.POST, params = "action=last")
    public ModelAndView searchLast(@Valid @ModelAttribute("requestForm") RequestForm requestForm,
                                   BindingResult result,
                                   Model model) {
        disableRequestSearchInstitutionDropDown(requestForm);
        requestForm.setPageNumber(requestForm.getTotalPageCount() - 1);
        searchAndSetResults(requestForm);
        model.addAttribute(RecapConstants.TEMPLATE, RecapConstants.REQUEST);
        return new ModelAndView(RecapConstants.VIEW_SEARCH_REQUESTS_SECTION, RecapConstants.REQUEST_FORM, requestForm);
    }

    /**
     * Get previous page results from scsb database and display them as rows in the search request UI page.
     *
     * @param requestForm the request form
     * @param result      the result
     * @param model       the model
     * @return the model and view
     */
    @ResponseBody
    @RequestMapping(value = "/request", method = RequestMethod.POST, params = "action=previous")
    public ModelAndView searchPrevious(@Valid @ModelAttribute("requestForm") RequestForm requestForm,
                                       BindingResult result,
                                       Model model) {
        disableRequestSearchInstitutionDropDown(requestForm);
        searchAndSetResults(requestForm);
        model.addAttribute(RecapConstants.TEMPLATE, RecapConstants.REQUEST);
        return new ModelAndView(RecapConstants.VIEW_SEARCH_REQUESTS_SECTION, RecapConstants.REQUEST_FORM, requestForm);
    }

    /**
     * Get next page results from scsb database and display them as rows in the search request UI page.
     *
     * @param requestForm the request form
     * @param result      the result
     * @param model       the model
     * @return the model and view
     */
    @ResponseBody
    @RequestMapping(value = "/request", method = RequestMethod.POST, params = "action=next")
    public ModelAndView searchNext(@Valid @ModelAttribute("requestForm") RequestForm requestForm,
                                   BindingResult result,
                                   Model model) {
        disableRequestSearchInstitutionDropDown(requestForm);
        searchAndSetResults(requestForm);
        model.addAttribute(RecapConstants.TEMPLATE, RecapConstants.REQUEST);
        return new ModelAndView(RecapConstants.VIEW_SEARCH_REQUESTS_SECTION, RecapConstants.REQUEST_FORM, requestForm);
    }

    /**
     *Based on the selected page size value that many request search results will be displayed in the search request UI page.
     *
     * @param requestForm the request form
     * @param result      the result
     * @param model       the model
     * @return the model and view
     */
    @ResponseBody
    @RequestMapping(value = "/request", method = RequestMethod.POST, params = "action=requestPageSizeChange")
    public ModelAndView onRequestPageSizeChange(@Valid @ModelAttribute("requestForm") RequestForm requestForm,
                                                BindingResult result,
                                                Model model) {
        disableRequestSearchInstitutionDropDown(requestForm);
        requestForm.setPageNumber(getPageNumberOnPageSizeChange(requestForm));
        searchAndSetResults(requestForm);
        model.addAttribute(RecapConstants.TEMPLATE, RecapConstants.REQUEST);
        return new ModelAndView(RecapConstants.VIEW_SEARCH_REQUESTS_SECTION, RecapConstants.REQUEST_FORM, requestForm);
    }

    /**
     *Populate default values to the request type and requesting institution drop downs in the create request UI page.
     *
     * @param model   the model
     * @param request the request
     * @return the model and view
     */
    @ResponseBody
    @RequestMapping(value = "/request", method = RequestMethod.POST, params = "action=loadCreateRequest")
    public ModelAndView loadCreateRequest(Model model, HttpServletRequest request) {
        UserDetailsForm userDetailsForm = getUserAuthUtil().getUserDetails(request.getSession(false), RecapConstants.REQUEST_PRIVILEGE);
        RequestForm requestForm = getRequestService().setDefaultsToCreateRequest(userDetailsForm,model);
        model.addAttribute(RecapConstants.REQUEST_FORM, requestForm);
        model.addAttribute(RecapConstants.TEMPLATE, RecapConstants.REQUEST);
        return new ModelAndView(RecapConstants.REQUEST, RecapConstants.REQUEST_FORM, requestForm);
    }

    /**
     *Retains patron's barcode , patron email address and requesting institution values in the create request UI page when request is going to be placed for the same patron.
     *
     * @param model   the model
     * @param request the request
     * @return the model and view
     */
    @ResponseBody
    @RequestMapping(value = "/request", method = RequestMethod.POST, params = "action=loadCreateRequestForSamePatron")
    public ModelAndView loadCreateRequestForSamePatron(Model model, HttpServletRequest request) {
        UserDetailsForm userDetailsForm = getUserAuthUtil().getUserDetails(request.getSession(false), RecapConstants.REQUEST_PRIVILEGE);
        RequestForm requestForm = getRequestService().setDefaultsToCreateRequest(userDetailsForm,model);
        requestForm.setOnChange("true");
        model.addAttribute(RecapConstants.REQUEST_FORM, requestForm);
        model.addAttribute(RecapConstants.TEMPLATE, RecapConstants.REQUEST);
        return new ModelAndView(RecapConstants.REQUEST, RecapConstants.REQUEST_FORM, requestForm);
    }

    /**
     * Populate default values to the status and institution drop downs in search request UI page.
     *
     * @param model   the model
     * @param request the request
     * @return the model and view
     */
    @ResponseBody
    @RequestMapping(value = "/request", method = RequestMethod.POST, params = "action=loadSearchRequest")
    public ModelAndView loadSearchRequest(Model model, HttpServletRequest request) {
        UserDetailsForm userDetails = getUserAuthUtil().getUserDetails(request.getSession(false), RecapConstants.REQUEST_PRIVILEGE);
        RequestForm requestForm = new RequestForm();
        List<String> requestStatuses = new ArrayList<>();
        List<String> institutionList = new ArrayList<>();
        getRequestService().findAllRequestStatusExceptProcessing(requestStatuses);
        requestForm.setRequestStatuses(requestStatuses);
        setFormValuesToDisableSearchInstitution(requestForm, userDetails, institutionList);
        model.addAttribute(RecapConstants.REQUEST_FORM, requestForm);
        model.addAttribute(RecapConstants.TEMPLATE, RecapConstants.REQUEST);
        return new ModelAndView(RecapConstants.REQUEST, RecapConstants.REQUEST_FORM, requestForm);
    }


    /**
     * Based on the given barcode, this method gets the item information from scsb database to display it in the create request UI page.
     *
     * @param requestForm the request form
     * @param result      the result
     * @param model       the model
     * @param request     the request
     * @return the string
     * @throws JSONException the json exception
     */
    @ResponseBody
    @RequestMapping(value = "/request", method = RequestMethod.POST, params = "action=populateItem")
    public String populateItem(@Valid @ModelAttribute("requestForm") RequestForm requestForm,
                               BindingResult result,
                               Model model, HttpServletRequest request) throws JSONException {
        return getRequestService().populateItemForRequest(requestForm, request);
    }


    /**
     * This method passes information about the requesting item to the scsb-circ micro service to place a request in scsb.
     *
     * @param requestForm the request form
     * @param result      the result
     * @param model       the model
     * @param request     the request
     * @return the model and view
     * @throws JSONException the json exception
     */
    @ResponseBody
    @RequestMapping(value = "/request", method = RequestMethod.POST, params = "action=createRequest")
    public ModelAndView createRequest(@Valid @ModelAttribute("requestForm") RequestForm requestForm,
                                BindingResult result,
                                Model model, HttpServletRequest request) throws JSONException {

        try {
            HttpSession session = request.getSession(false);
            String username = (String) session.getAttribute(RecapConstants.USER_NAME);
            String stringJson = populateItem(requestForm, null, model, request);
            if (stringJson != null) {
                JSONObject responseJsonObject = new JSONObject(stringJson);
                Object errorMessage = responseJsonObject.has(RecapConstants.ERROR_MESSAGE) ? responseJsonObject.get(RecapConstants.ERROR_MESSAGE) : null;
                Object noPermissionErrorMessage = responseJsonObject.has(RecapConstants.NO_PERMISSION_ERROR_MESSAGE) ? responseJsonObject.get(RecapConstants.NO_PERMISSION_ERROR_MESSAGE) : null;
                Object itemTitle = responseJsonObject.has(RecapConstants.REQUESTED_ITEM_TITLE) ? responseJsonObject.get(RecapConstants.REQUESTED_ITEM_TITLE) : null;
                Object itemOwningInstitution = responseJsonObject.has(RecapConstants.REQUESTED_ITEM_OWNING_INSTITUTION) ? responseJsonObject.get(RecapConstants.REQUESTED_ITEM_OWNING_INSTITUTION) : null;
                Object deliveryLocations = responseJsonObject.has(RecapConstants.DELIVERY_LOCATION) ? responseJsonObject.get(RecapConstants.DELIVERY_LOCATION) : null;
                Object requestTypes = responseJsonObject.has(RecapConstants.REQUEST_TYPES) ? responseJsonObject.get(RecapConstants.REQUEST_TYPES) : null;
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
                if (noPermissionErrorMessage != null) {
                    requestForm.setErrorMessage((String) noPermissionErrorMessage);
                    requestForm.setShowRequestErrorMsg(true);
                    return new ModelAndView(RecapConstants.CREATE_REQUEST_SECTION, RecapConstants.REQUEST_FORM, requestForm);
                } else if (errorMessage != null) {
                    requestForm.setErrorMessage((String) errorMessage);
                    requestForm.setShowRequestErrorMsg(true);
                    return new ModelAndView(RecapConstants.CREATE_REQUEST_SECTION, RecapConstants.REQUEST_FORM, requestForm);
                }
            }

            String requestItemUrl = getScsbUrl() + RecapConstants.REQUEST_ITEM_URL;

            ItemRequestInformation itemRequestInformation = getItemRequestInformation();
            itemRequestInformation.setUsername(username);
            itemRequestInformation.setItemBarcodes(Arrays.asList(requestForm.getItemBarcodeInRequest().split(",")));
            itemRequestInformation.setPatronBarcode(requestForm.getPatronBarcodeInRequest());
            itemRequestInformation.setRequestingInstitution(requestForm.getRequestingInstitution());
            itemRequestInformation.setEmailAddress(requestForm.getPatronEmailAddress());
            itemRequestInformation.setTitle(requestForm.getItemTitle());
            itemRequestInformation.setTitleIdentifier(requestForm.getItemTitle());
            itemRequestInformation.setItemOwningInstitution(requestForm.getItemOwningInstitution());
            itemRequestInformation.setRequestType(requestForm.getRequestType());
            itemRequestInformation.setRequestNotes(requestForm.getRequestNotes());
            itemRequestInformation.setStartPage(requestForm.getStartPage());
            itemRequestInformation.setEndPage(requestForm.getEndPage());
            itemRequestInformation.setAuthor(requestForm.getArticleAuthor());
            itemRequestInformation.setChapterTitle(requestForm.getArticleTitle());
            itemRequestInformation.setIssue(requestForm.getIssue());
            if (requestForm.getVolumeNumber() != null) {
                itemRequestInformation.setVolume(requestForm.getVolumeNumber());
            } else {
                itemRequestInformation.setVolume("");
            }

            if (StringUtils.isNotBlank(requestForm.getDeliveryLocationInRequest())) {
                CustomerCodeEntity customerCodeEntity = getCustomerCodeDetailsRepository().findByCustomerCode(requestForm.getDeliveryLocationInRequest());
                if (null != customerCodeEntity) {
                    itemRequestInformation.setDeliveryLocation(customerCodeEntity.getCustomerCode());
                }
            }

            HttpEntity<ItemRequestInformation> requestEntity = new HttpEntity<>(itemRequestInformation, getRestHeaderService().getHttpHeaders());
            ResponseEntity<ItemResponseInformation> itemResponseEntity = getRestTemplate().exchange(requestItemUrl, HttpMethod.POST, requestEntity, ItemResponseInformation.class);
            ItemResponseInformation itemResponseInformation = itemResponseEntity.getBody();
            if (null != itemResponseInformation && !itemResponseInformation.isSuccess()) {
                requestForm.setErrorMessage(itemResponseInformation.getScreenMessage());
                requestForm.setDisableRequestingInstitution(false);
                requestForm.setShowRequestErrorMsg(true);
            }
        } catch (HttpClientErrorException httpException) {
            logger.error(RecapConstants.LOG_ERROR, httpException);
            String responseBodyAsString = httpException.getResponseBodyAsString();
            requestForm.setErrorMessage(responseBodyAsString);
            requestForm.setShowRequestErrorMsg(true);
        } catch (Exception exception) {
            logger.error(RecapConstants.LOG_ERROR, exception);
            requestForm.setErrorMessage(exception.getMessage());
            requestForm.setShowRequestErrorMsg(true);
        }

        requestForm.setRequestingInstitutions(requestForm.getInstitutionList());
        if(requestForm.getInstitutionList().size()==1){
            requestForm.setDisableRequestingInstitution(true);
        }
        if(requestForm.getErrorMessage()==null){
        requestForm.setSubmitted(true);
        requestForm.setDisableRequestingInstitution(true);
        }
        return new ModelAndView(RecapConstants.CREATE_REQUEST_SECTION, RecapConstants.REQUEST_FORM, requestForm);
    }

    /**
     * Cancel the request which is placed in scsb.
     *
     * @param requestForm the request form
     * @param result      the result
     * @param model       the model
     * @return the string
     */
    @ResponseBody
    @RequestMapping(value = "/request", method = RequestMethod.POST, params = "action=cancelRequest")
    public String cancelRequest(@Valid @ModelAttribute("requestForm") RequestForm requestForm,
                                BindingResult result,
                                Model model) {
        JSONObject jsonObject = new JSONObject();
        String requestStatus = null;
        String requestNotes = null;
        try {
            HttpEntity requestEntity = new HttpEntity<>(getRestHeaderService().getHttpHeaders());
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getScsbUrl() + RecapConstants.URL_REQUEST_CANCEL).queryParam(RecapConstants.REQUEST_ID, requestForm.getRequestId());
            HttpEntity<CancelRequestResponse> responseEntity = getRestTemplate().exchange(builder.build().encode().toUri(), HttpMethod.POST, requestEntity, CancelRequestResponse.class);
            CancelRequestResponse cancelRequestResponse = responseEntity.getBody();
            jsonObject.put(RecapConstants.MESSAGE, cancelRequestResponse.getScreenMessage());
            jsonObject.put(RecapConstants.STATUS, cancelRequestResponse.isSuccess());
            RequestItemEntity requestItemEntity = getRequestItemDetailsRepository().findByRequestId(requestForm.getRequestId());
            if (null != requestItemEntity) {
                requestStatus = requestItemEntity.getRequestStatusEntity().getRequestStatusDescription();
                requestNotes = requestItemEntity.getNotes();
            }
            jsonObject.put(RecapConstants.REQUEST_STATUS, requestStatus);
            jsonObject.put(RecapConstants.REQUEST_NOTES, requestNotes);
        } catch (Exception exception) {
            logger.error(RecapConstants.LOG_ERROR, exception);
            logger.debug(exception.getMessage());
        }
        return jsonObject.toString();
    }

    /**
     * Resubmit the exception request. Creates a new request with the same data.
     * @param requestForm
     * @param result
     * @param model
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/request", method = RequestMethod.POST, params = "action=resubmitRequest")
    public String resubmitRequest(@Valid @ModelAttribute("requestForm") RequestForm requestForm,
                                BindingResult result,
                                Model model) {
        JSONObject jsonObject = new JSONObject();
        try {
            ReplaceRequest replaceRequest = new ReplaceRequest();
            replaceRequest.setReplaceRequestByType(RecapConstants.REQUEST_IDS);
            replaceRequest.setRequestStatus(RecapConstants.EXCEPTION);
            String requestId = String.valueOf(requestForm.getRequestId());
            replaceRequest.setRequestIds(requestId);
            HttpEntity request = new HttpEntity<>(replaceRequest, getRestHeaderService().getHttpHeaders());
            Map resultMap = getRestTemplate().postForObject(scsbUrl + RecapConstants.URL_REQUEST_RESUBMIT, request, Map.class);
            jsonObject.put(RecapConstants.BARCODE, requestForm.getItemBarcodeHidden());
            if (resultMap.containsKey(requestId)) {
                String message = (String) resultMap.get(requestId);
                jsonObject.put(RecapConstants.MESSAGE, resultMap.get(requestId));
                if (StringUtils.isNotBlank(message) && message.contains(RecapConstants.SUCCESS)) {
                    jsonObject.put(RecapConstants.STATUS, true);
                } else {
                    jsonObject.put(RecapConstants.STATUS, false);
                }
            } else if (resultMap.containsKey(RecapConstants.INVALID_REQUEST)) {
                jsonObject.put(RecapConstants.MESSAGE, resultMap.get(RecapConstants.INVALID_REQUEST));
                jsonObject.put(RecapConstants.STATUS, false);
            } else if (resultMap.containsKey(RecapConstants.FAILURE)) {
                jsonObject.put(RecapConstants.MESSAGE, resultMap.get(RecapConstants.FAILURE));
                jsonObject.put(RecapConstants.STATUS, false);
            }
        } catch (Exception exception) {
            logger.error(RecapConstants.LOG_ERROR, exception);
            logger.debug(exception.getMessage());
        }
        logger.info(jsonObject.toString());
        return jsonObject.toString();
    }

    private void searchAndSetResults(RequestForm requestForm) {
        Page<RequestItemEntity> requestItemEntities = getRequestServiceUtil().searchRequests(requestForm);
        List<SearchResultRow> searchResultRows = buildSearchResultRows(requestItemEntities.getContent(),requestForm);
        if (CollectionUtils.isNotEmpty(searchResultRows)) {
            requestForm.setSearchResultRows(searchResultRows);
            requestForm.setTotalRecordsCount(NumberFormat.getNumberInstance().format(requestItemEntities.getTotalElements()));
            requestForm.setTotalPageCount(requestItemEntities.getTotalPages());
        } else {
            requestForm.setSearchResultRows(Collections.emptyList());
            requestForm.setMessage(RecapConstants.SEARCH_RESULT_ERROR_NO_RECORDS_FOUND);
        }
        requestForm.setShowResults(true);
    }

    private List<SearchResultRow> buildSearchResultRows(List<RequestItemEntity> requestItemEntities,RequestForm requestForm) {
        if (CollectionUtils.isNotEmpty(requestItemEntities)) {
            List<SearchResultRow> searchResultRows = new ArrayList<>();
            for (RequestItemEntity requestItemEntity : requestItemEntities) {
                ItemEntity itemEntity = requestItemEntity.getItemEntity();
                try {
                if(requestForm.getInstitutionList().size()==1 && (itemEntity.getInstitutionEntity().getInstitutionCode().equalsIgnoreCase(requestForm.getInstitution()) && !itemEntity.getOwningInstitutionId().equals(requestItemEntity.getRequestingInstitutionId()))){
                    populateRequestResultsForRecall(searchResultRows, requestItemEntity);
                }
                else {
                    populateRequestResults(searchResultRows, requestItemEntity);
                    }
                }
                catch (Exception e) {
                    logger.error(RecapConstants.LOG_ERROR, e);
                }
            }
            return searchResultRows;
        }
        return Collections.emptyList();
    }

    private void populateRequestResultsForRecall(List<SearchResultRow> searchResultRows, RequestItemEntity requestItemEntity) {
        SearchResultRow searchResultRow = new SearchResultRow();
        searchResultRow.setRequestId(requestItemEntity.getRequestId());
        searchResultRow.setRequestingInstitution(requestItemEntity.getInstitutionEntity().getInstitutionCode());
        searchResultRow.setBarcode(requestItemEntity.getItemEntity().getBarcode());
        searchResultRow.setOwningInstitution(requestItemEntity.getItemEntity().getInstitutionEntity().getInstitutionCode());
        searchResultRow.setRequestType(requestItemEntity.getRequestTypeEntity().getRequestTypeCode());
        searchResultRow.setAvailability(requestItemEntity.getItemEntity().getItemStatusEntity().getStatusCode());
        searchResultRow.setCreatedDate(requestItemEntity.getCreatedDate());
        searchResultRow.setLastUpdatedDate(requestItemEntity.getLastUpdatedDate());
        searchResultRow.setStatus(requestItemEntity.getRequestStatusEntity().getRequestStatusDescription());
        searchResultRow.setRequestNotes(requestItemEntity.getNotes());
        searchResultRow.setShowItems(false);
        ItemEntity itemEntity = requestItemEntity.getItemEntity();
        if (null != itemEntity && CollectionUtils.isNotEmpty(itemEntity.getBibliographicEntities())) {
            searchResultRow.setBibId(itemEntity.getBibliographicEntities().get(0).getBibliographicId());
        }
        searchResultRows.add(searchResultRow);
    }

    private void populateRequestResults(List<SearchResultRow> searchResultRows, RequestItemEntity requestItemEntity) {
        SearchResultRow searchResultRow = new SearchResultRow();
        searchResultRow.setRequestId(requestItemEntity.getRequestId());
        searchResultRow.setPatronBarcode(requestItemEntity.getPatronId());
        searchResultRow.setRequestingInstitution(requestItemEntity.getInstitutionEntity().getInstitutionCode());
        searchResultRow.setBarcode(requestItemEntity.getItemEntity().getBarcode());
        searchResultRow.setOwningInstitution(requestItemEntity.getItemEntity().getInstitutionEntity().getInstitutionCode());
        searchResultRow.setDeliveryLocation(requestItemEntity.getStopCode());
        searchResultRow.setRequestType(requestItemEntity.getRequestTypeEntity().getRequestTypeCode());
        searchResultRow.setRequestCreatedBy(requestItemEntity.getCreatedBy());
        searchResultRow.setAvailability(requestItemEntity.getItemEntity().getItemStatusEntity().getStatusCode());
        searchResultRow.setShowItems(true);
        if(StringUtils.isNotBlank(requestItemEntity.getEmailId())){
            searchResultRow.setPatronEmailId(securityUtil.getDecryptedValue(requestItemEntity.getEmailId()));
        }else {
            searchResultRow.setPatronEmailId(requestItemEntity.getEmailId());
        }
        searchResultRow.setRequestNotes(requestItemEntity.getNotes());
        searchResultRow.setCreatedDate(requestItemEntity.getCreatedDate());
        searchResultRow.setLastUpdatedDate(requestItemEntity.getLastUpdatedDate());
        searchResultRow.setStatus(requestItemEntity.getRequestStatusEntity().getRequestStatusDescription());
        ItemEntity itemEntity = requestItemEntity.getItemEntity();
        if (null != itemEntity && CollectionUtils.isNotEmpty(itemEntity.getBibliographicEntities())) {
            searchResultRow.setBibId(itemEntity.getBibliographicEntities().get(0).getBibliographicId());
        }
        searchResultRows.add(searchResultRow);
    }

    private Integer getPageNumberOnPageSizeChange(RequestForm requestForm) {
        int totalRecordsCount;
        Integer pageNumber = requestForm.getPageNumber();
        try {
            totalRecordsCount = NumberFormat.getNumberInstance().parse(requestForm.getTotalRecordsCount()).intValue();
            int totalPagesCount = (int) Math.ceil((double) totalRecordsCount / (double) requestForm.getPageSize());
            if (totalPagesCount > 0 && pageNumber >= totalPagesCount) {
                pageNumber = totalPagesCount - 1;
            }
        } catch (Exception e) {
            logger.error(RecapConstants.LOG_ERROR, e);
        }
        return pageNumber;
    }

    /**
     * Gets item request information.
     *
     * @return the item request information
     */
    public ItemRequestInformation getItemRequestInformation() {
        return new ItemRequestInformation();
    }

    /**
     * To change the status information of requested item asynchronously in the search request UI page.
     *
     * @param request the request
     * @return the string
     */
    @ResponseBody
    @RequestMapping(value = "/request/refreshStatus", method = RequestMethod.GET)
    public String refreshStatus(HttpServletRequest request) {
        return getRequestService().getRefreshedStatus(request);
    }

    private void setFormValuesToDisableSearchInstitution(@Valid @ModelAttribute("requestForm") RequestForm requestForm, UserDetailsForm userDetails, List<String> institutionList) {
        InstitutionEntity institutionEntity = getInstitutionDetailsRepository().findByInstitutionId(userDetails.getLoginInstitutionId());
        if(userDetails.isSuperAdmin() || userDetails.isRecapUser() || institutionEntity.getInstitutionCode().equalsIgnoreCase("HTC")){
            getRequestService().getInstitutionForSuperAdmin(institutionList);
            requestForm.setInstitutionList(institutionList);
        }else {
            requestForm.setDisableSearchInstitution(true);
            requestForm.setInstitutionList(Arrays.asList(institutionEntity.getInstitutionCode()));
            requestForm.setInstitution(institutionEntity.getInstitutionCode());
            requestForm.setSearchInstitutionHdn(institutionEntity.getInstitutionCode());
        }
    }

    private void disableRequestSearchInstitutionDropDown(@Valid @ModelAttribute("requestForm") RequestForm requestForm) {
        if (requestForm.getInstitutionList().size() == 1){
            requestForm.setDisableSearchInstitution(true);
            requestForm.setInstitution(requestForm.getSearchInstitutionHdn());
        }
    }
}


