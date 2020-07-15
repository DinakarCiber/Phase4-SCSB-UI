package org.recap.controller;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.recap.RecapCommonConstants;
import org.recap.RecapConstants;
import org.recap.model.jpa.InstitutionEntity;
import org.recap.model.search.SearchItemResultRow;
import org.recap.model.search.SearchRecordsRequest;
import org.recap.model.search.SearchResultRow;
import org.recap.model.usermanagement.UserDetailsForm;
import org.recap.repository.jpa.InstitutionDetailsRepository;
import org.recap.security.UserManagementService;
import org.recap.util.CsvUtil;
import org.recap.util.HelperUtil;
import org.recap.util.SearchUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.File;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Created by rajeshbabuk on 6/7/16.
 */

@Controller
public class SearchRecordsController extends RecapController {

    private static final Logger logger = LoggerFactory.getLogger(SearchRecordsController.class);

    @Autowired
    private SearchUtil searchUtil;

    @Autowired
    private CsvUtil csvUtil;

    @Autowired
    private InstitutionDetailsRepository institutionDetailsRepository;

    /**
     * Gets search util.
     *
     * @return the search util
     */
    public SearchUtil getSearchUtil() {
        return searchUtil;
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
     * Render the search UI page for the scsb application.
     *
     * @param model   the model
     * @param request the request
     * @return the string
     */
    @GetMapping("/search")
    public String searchRecords(Model model, HttpServletRequest request) {
        HttpSession session=request.getSession(false);
        boolean authenticated = getUserAuthUtil().isAuthenticated(request, RecapConstants.SCSB_SHIRO_SEARCH_URL);
        if(authenticated)
        {
            SearchRecordsRequest searchRecordsRequest = new SearchRecordsRequest();
            model.addAttribute(RecapConstants.VIEW_SEARCH_RECORDS_REQUEST, searchRecordsRequest);
            model.addAttribute(RecapCommonConstants.TEMPLATE, RecapCommonConstants.SEARCH);
            return RecapConstants.VIEW_SEARCH_RECORDS;
        }else{
            return UserManagementService.unAuthorizedUser(session,"Search",logger);
        }

    }

    /**
     * Performs search on solr and returns the results as rows to get displayed in the search UI page.
     *
     * @param searchRecordsRequest the search records request
     * @param result               the result
     * @param model                the model
     * @return the model and view
     */
    @ResponseBody
    @PostMapping(value = "/search", params = "action=search")
    public ModelAndView search(@Valid @ModelAttribute("searchRecordsRequest") SearchRecordsRequest searchRecordsRequest,
                                  BindingResult result,
                                  Model model) {
        return searchUtil.searchRecord(searchRecordsRequest, model);
    }

    /**
     * Performs search on solr and returns the previous page results as rows to get displayed in the search UI page.
     *
     * @param searchRecordsRequest the search records request
     * @param result               the result
     * @param model                the model
     * @return the model and view
     */
    @ResponseBody
    @PostMapping(value = "/search", params = "action=previous")
    public ModelAndView searchPrevious(@Valid @ModelAttribute("searchRecordsRequest") SearchRecordsRequest searchRecordsRequest,
                               BindingResult result,
                               Model model) {
        return searchRecordsPage(searchRecordsRequest, model);
    }

    /**
     *  Performs search on solr and returns the next page results as rows to get displayed in the search UI page.
     *
     * @param searchRecordsRequest the search records request
     * @param result               the result
     * @param model                the model
     * @return the model and view
     */
    @ResponseBody
    @PostMapping(value = "/search", params = "action=next")
    public ModelAndView searchNext(@Valid @ModelAttribute("searchRecordsRequest") SearchRecordsRequest searchRecordsRequest,
                                   BindingResult result,
                                   Model model) {
        return searchRecordsPage(searchRecordsRequest, model);
    }

    /**
     * Performs search on solr and returns the first page results as rows to get displayed in the search UI page.
     *
     * @param searchRecordsRequest the search records request
     * @param result               the result
     * @param model                the model
     * @return the model and view
     */
    @ResponseBody
    @PostMapping(value = "/search", params = "action=first")
    public ModelAndView searchFirst(@Valid @ModelAttribute("searchRecordsRequest") SearchRecordsRequest searchRecordsRequest,
                                       BindingResult result,
                                       Model model) {
        return searchUtil.searchRecord(searchRecordsRequest, model);
    }

    /**
     * Performs search on solr and returns the last page results as rows to get displayed in the search UI page.
     *
     * @param searchRecordsRequest the search records request
     * @param result               the result
     * @param model                the model
     * @return the model and view
     */
    @ResponseBody
    @PostMapping(value = "/search", params = "action=last")
    public ModelAndView searchLast(@Valid @ModelAttribute("searchRecordsRequest") SearchRecordsRequest searchRecordsRequest,
                                       BindingResult result,
                                       Model model) {
        searchRecordsRequest.setPageNumber(searchRecordsRequest.getTotalPageCount() - 1);
        return searchRecordsPage(searchRecordsRequest, model);
    }

    @ResponseBody
    @PostMapping(value = "/search", params = "action=clear")
    public ModelAndView clear(@Valid @ModelAttribute("searchRecordsRequest") SearchRecordsRequest searchRecordsRequest,
                              BindingResult result,
                              Model model) {

        searchRecordsRequest.setFieldValue("");
        searchRecordsRequest.setOwningInstitutions(new ArrayList<>());
        searchRecordsRequest.setCollectionGroupDesignations(new ArrayList<>());
        searchRecordsRequest.setAvailability(new ArrayList<>());
        searchRecordsRequest.setMaterialTypes(new ArrayList<>());
        searchRecordsRequest.setUseRestrictions(new ArrayList<>());
        searchRecordsRequest.setShowResults(false);
        model.addAttribute(RecapCommonConstants.TEMPLATE, RecapCommonConstants.SEARCH);
        return new ModelAndView(RecapConstants.VIEW_SEARCH_RECORDS);
    }

    /**
     *Clear all the input fields and the search result rows in the search UI page.
     *
     * @param model the model
     * @return the model and view
     */
    @ResponseBody
    @PostMapping(value = "/search", params = "action=newSearch")
    public ModelAndView newSearch(Model model) {
        SearchRecordsRequest searchRecordsRequest = new SearchRecordsRequest();
        model.addAttribute(RecapConstants.VIEW_SEARCH_RECORDS_REQUEST, searchRecordsRequest);
        model.addAttribute(RecapCommonConstants.TEMPLATE, RecapCommonConstants.SEARCH);
        return new ModelAndView(RecapConstants.VIEW_SEARCH_RECORDS);
    }

    /**
     * This method redirects to request UI page with the selected items information in the search results.
     *
     * @param searchRecordsRequest   the search records request
     * @param result                 the result
     * @param model                  the model
     * @param request                the request
     * @param redirectAttributes     the redirect attributes
     * @return the model and view
     */
    @ResponseBody
    @PostMapping(value = "/search", params = "action=request")
    public ModelAndView requestRecords(@Valid @ModelAttribute("searchRecordsRequest") SearchRecordsRequest searchRecordsRequest,
                                       BindingResult result,
                                       Model model,
                                       HttpServletRequest request,
                                       RedirectAttributes redirectAttributes) {
        UserDetailsForm userDetailsForm = getUserAuthUtil().getUserDetails(request.getSession(false), RecapConstants.REQUEST_PRIVILEGE);
        processRequest(searchRecordsRequest, userDetailsForm, redirectAttributes);
        if (StringUtils.isNotBlank(searchRecordsRequest.getErrorMessage())) {
            searchRecordsRequest.setShowResults(true);
            model.addAttribute("searchRecordsRequest", searchRecordsRequest);
            model.addAttribute(RecapCommonConstants.TEMPLATE, RecapCommonConstants.SEARCH);
            return new ModelAndView("searchRecords");
        }
        model.addAttribute(RecapCommonConstants.TEMPLATE, RecapCommonConstants.REQUEST);
        return new ModelAndView(new RedirectView("/request",true));
    }


    /**
     * Selected items in the search UI page will be exported to a csv file.
     *
     * @param searchRecordsRequest the search records request
     * @param response             the response
     * @param result               the result
     * @param model                the model
     * @return the byte [ ]
     * @throws Exception the exception
     */
    @ResponseBody
    @PostMapping(value = "/search", params = "action=export")
    public byte[] exportRecords(@Valid @ModelAttribute("searchRecordsRequest") SearchRecordsRequest searchRecordsRequest, HttpServletResponse response,
                                  BindingResult result,
                                  Model model) throws Exception {
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String fileNameWithExtension = "ExportRecords_" + dateFormat.format(new Date()) + ".csv";
        File csvFile = csvUtil.writeSearchResultsToCsv(searchRecordsRequest.getSearchResultRows(), fileNameWithExtension);
        return HelperUtil.getFileContent(csvFile, model, response, fileNameWithExtension, RecapCommonConstants.SEARCH);
    }

    /**
     * Performs search on solr on changing the page size. The number of results returned will be equal to the selected page size.
     *
     * @param searchRecordsRequest the search records request
     * @param result               the result
     * @param model                the model
     * @return the model and view
     * @throws Exception the exception
     */
    @ResponseBody
    @PostMapping(value = "/search", params = "action=pageSizeChange")
    public ModelAndView onPageSizeChange(@Valid @ModelAttribute("searchRecordsRequest") SearchRecordsRequest searchRecordsRequest,
                                         BindingResult result,
                                         Model model) throws Exception {
        searchRecordsRequest.setPageNumber(getPageNumberOnPageSizeChange(searchRecordsRequest));
        return searchRecordsPage(searchRecordsRequest, model);
    }

    /**
     * To get the page number based on the total number of records in result set and the selected page size.
     *
     * @param searchRecordsRequest the search records request
     * @return the integer
     */
    public Integer getPageNumberOnPageSizeChange(SearchRecordsRequest searchRecordsRequest) {
        int totalRecordsCount;
        Integer pageNumber = searchRecordsRequest.getPageNumber();
        try {
            if (isEmptyField(searchRecordsRequest)) {
                totalRecordsCount = NumberFormat.getNumberInstance().parse(searchRecordsRequest.getTotalRecordsCount()).intValue();
            } else if (isItemField(searchRecordsRequest)) {
                totalRecordsCount = NumberFormat.getNumberInstance().parse(searchRecordsRequest.getTotalItemRecordsCount()).intValue();
            } else {
                totalRecordsCount = NumberFormat.getNumberInstance().parse(searchRecordsRequest.getTotalBibRecordsCount()).intValue();
            }
            int totalPagesCount = (int) Math.ceil((double) totalRecordsCount / (double) searchRecordsRequest.getPageSize());
            if (totalPagesCount > 0 && pageNumber >= totalPagesCount) {
                pageNumber = totalPagesCount - 1;
            }
        } catch (ParseException e) {
            logger.error(e.getMessage());
        }
        return pageNumber;
    }

    private boolean isEmptyField(SearchRecordsRequest searchRecordsRequest) {
        return StringUtils.isBlank(searchRecordsRequest.getFieldName()) && StringUtils.isNotBlank(searchRecordsRequest.getFieldValue());
    }

    private boolean isItemField(SearchRecordsRequest searchRecordsRequest) {
        return (StringUtils.isNotBlank(searchRecordsRequest.getFieldName())
                && (searchRecordsRequest.getFieldName().equalsIgnoreCase(RecapCommonConstants.BARCODE) ||
                searchRecordsRequest.getFieldName().equalsIgnoreCase(RecapCommonConstants.CALL_NUMBER)));
    }



    private void processRequest(SearchRecordsRequest searchRecordsRequest, UserDetailsForm userDetailsForm, RedirectAttributes redirectAttributes) {
        String userInstitution = null ;
        Optional<InstitutionEntity> institutionEntity = getInstitutionDetailsRepository().findById(userDetailsForm.getLoginInstitutionId());


        if (institutionEntity.isPresent()) {
            userInstitution = institutionEntity.get().getInstitutionCode();
        }
        List<SearchResultRow> searchResultRows = searchRecordsRequest.getSearchResultRows();
        Set<String> barcodes = new HashSet<>();
        Set<String> itemTitles = new HashSet<>();
        Set<String> itemOwningInstitutions = new HashSet<>();
        Set<String> itemAvailability = new HashSet<>();
        for (SearchResultRow searchResultRow : searchResultRows) {
            if (searchResultRow.isSelected()) {
                if (RecapCommonConstants.PRIVATE.equals(searchResultRow.getCollectionGroupDesignation()) && !userDetailsForm.isSuperAdmin() && !userDetailsForm.isRecapUser() && StringUtils.isNotBlank(userInstitution) && !userInstitution.equals(searchResultRow.getOwningInstitution())) {
                    searchRecordsRequest.setErrorMessage(RecapConstants.REQUEST_PRIVATE_ERROR_USER_NOT_PERMITTED);
                } else if (!userDetailsForm.isRecapPermissionAllowed()) {
                    searchRecordsRequest.setErrorMessage(RecapConstants.REQUEST_ERROR_USER_NOT_PERMITTED);
                } else {
                    processBarcodesForSearchResultRow(barcodes, itemTitles, itemOwningInstitutions, searchResultRow,itemAvailability);
                }
            } else if (!CollectionUtils.isEmpty(searchResultRow.getSearchItemResultRows())) {
                for (SearchItemResultRow searchItemResultRow : searchResultRow.getSearchItemResultRows()) {
                    if (searchItemResultRow.isSelectedItem()) {
                        if (RecapCommonConstants.PRIVATE.equals(searchItemResultRow.getCollectionGroupDesignation()) && !userDetailsForm.isSuperAdmin() && !userDetailsForm.isRecapUser() && StringUtils.isNotBlank(userInstitution) && !userInstitution.equals(searchResultRow.getOwningInstitution())) {
                            searchRecordsRequest.setErrorMessage(RecapConstants.REQUEST_PRIVATE_ERROR_USER_NOT_PERMITTED);
                        } else if (!userDetailsForm.isRecapPermissionAllowed()) {
                            searchRecordsRequest.setErrorMessage(RecapConstants.REQUEST_ERROR_USER_NOT_PERMITTED);
                        } else {
                            processBarcodeForSearchItemResultRow(barcodes, itemTitles, itemOwningInstitutions, searchItemResultRow, searchResultRow,itemAvailability);
                        }
                    }
                }
            }
        }
        redirectAttributes.addFlashAttribute(RecapConstants.REQUESTED_BARCODE, StringUtils.join(barcodes, ","));
        redirectAttributes.addFlashAttribute(RecapConstants.REQUESTED_ITEM_TITLE, StringUtils.join(itemTitles, " || "));
        redirectAttributes.addFlashAttribute(RecapConstants.REQUESTED_ITEM_OWNING_INSTITUTION, StringUtils.join(itemOwningInstitutions, ","));
        redirectAttributes.addFlashAttribute(RecapConstants.REQUESTED_ITEM_AVAILABILITY,itemAvailability);
    }

    private void processBarcodeForSearchItemResultRow(Set<String> barcodes, Set<String> titles, Set<String> itemInstitutions, SearchItemResultRow searchItemResultRow, SearchResultRow searchResultRow,Set<String> itemAvailabilty) {
        String barcode = searchItemResultRow.getBarcode();
        processTitleAndItemInstitution(barcodes, titles, itemInstitutions, searchResultRow, barcode,itemAvailabilty);
    }

    private void processBarcodesForSearchResultRow(Set<String> barcodes, Set<String> titles, Set<String> itemInstitutions, SearchResultRow searchResultRow,Set<String> itemAvailabilty) {
        String barcode = searchResultRow.getBarcode();
        processTitleAndItemInstitution(barcodes, titles, itemInstitutions, searchResultRow, barcode,itemAvailabilty);
    }

    private void processTitleAndItemInstitution(Set<String> barcodes, Set<String> titles, Set<String> itemInstitutions, SearchResultRow searchResultRow, String barcode,Set<String> itemAvailabilty) {
        String title = searchResultRow.getTitle();
        String owningInstitution = searchResultRow.getOwningInstitution();
        itemAvailabilty.add(searchResultRow.getAvailability());
        if (StringUtils.isNotBlank(barcode)) {
            barcodes.add(barcode);
        }
        if (StringUtils.isNotBlank(title)) {
            titles.add(title);
        }
        if (StringUtils.isNotBlank(owningInstitution)) {
            itemInstitutions.add(owningInstitution);
        }
    }



    /**
     * Add up for srring thymeleaf and spring binding.
     *
     * @param binder the binder
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setAutoGrowCollectionLimit(1048576);
    }

    private ModelAndView searchRecordsPage(SearchRecordsRequest searchRecordsRequest, Model model) {
        searchUtil.searchAndSetResults(searchRecordsRequest);
        model.addAttribute( RecapCommonConstants.TEMPLATE,  RecapCommonConstants.SEARCH);
        return new ModelAndView(RecapConstants.VIEW_SEARCH_RECORDS, RecapConstants.VIEW_SEARCH_RECORDS_REQUEST, searchRecordsRequest);
    }
}
