package org.recap.controller;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.recap.RecapConstants;
import org.recap.model.jpa.BulkRequestItemEntity;
import org.recap.model.jpa.InstitutionEntity;
import org.recap.model.search.BulkRequestForm;
import org.recap.repository.jpa.BulkRequestDetailsRepository;
import org.recap.repository.jpa.InstitutionDetailsRepository;
import org.recap.security.UserManagementService;
import org.recap.service.BulkRequestService;
import org.recap.util.UserAuthUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by akulak on 19/9/17.
 */
@Controller
public class BulkRequestController {

    private static final Logger logger = LoggerFactory.getLogger(BulkRequestController.class);

    @Autowired
    private UserAuthUtil userAuthUtil;

    @Autowired
    private BulkRequestService bulkRequestService;

    @Autowired
    InstitutionDetailsRepository institutionDetailsRepository;

    @Autowired
    private BulkRequestDetailsRepository bulkRequestDetailsRepository;

    @RequestMapping("/bulkRequest")
    public String bulkRequest(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        boolean authenticated = userAuthUtil.authorizedUser(RecapConstants.SCSB_SHIRO_BULK_REQUEST_URL, (UsernamePasswordToken) session.getAttribute(RecapConstants.USER_TOKEN));
        if (authenticated) {
            BulkRequestForm bulkRequestForm = new BulkRequestForm();
            loadCreateRequestPage(bulkRequestForm);
            model.addAttribute(RecapConstants.BULK_REQUEST_FORM, bulkRequestForm);
            model.addAttribute(RecapConstants.TEMPLATE, RecapConstants.BULK_REQUEST);
            return RecapConstants.VIEW_SEARCH_RECORDS;
        } else {
            return UserManagementService.unAuthorizedUser(session, "BulkRequest", logger);
        }
    }

    @PostMapping("/bulkRequest/loadSearchRequest")
    public ModelAndView loadSearchRequest(Model model) {
        BulkRequestForm bulkRequestForm = new BulkRequestForm();
        loadSearchRequestPage(bulkRequestForm);
        model.addAttribute(RecapConstants.TEMPLATE, RecapConstants.BULK_REQUEST);
        return new ModelAndView(RecapConstants.BULK_REQUEST, RecapConstants.BULK_REQUEST_FORM, bulkRequestForm);
    }

    @PostMapping("/bulkRequest/loadCreateRequest")
    public ModelAndView loadCreateRequest(@Valid @ModelAttribute("bulkRequestForm") BulkRequestForm bulkRequestForm, Model model) {
        loadCreateRequestPage(bulkRequestForm);
        model.addAttribute(RecapConstants.TEMPLATE, RecapConstants.BULK_REQUEST);
        return new ModelAndView(RecapConstants.BULK_REQUEST, RecapConstants.BULK_REQUEST_FORM, bulkRequestForm);
    }

    @PostMapping("/bulkRequest/createBulkRequest")
    public ModelAndView createRequest(@Valid @ModelAttribute("bulkRequestForm") BulkRequestForm bulkRequestForm, MultipartFile file, Model model, HttpServletRequest request) {
        loadCreateRequestPage(bulkRequestForm);
        bulkRequestService.processCreateBulkRequest(bulkRequestForm, request);
        bulkRequestService.processDeliveryLocations(bulkRequestForm);
        model.addAttribute(RecapConstants.TEMPLATE, RecapConstants.BULK_REQUEST);
        return new ModelAndView(RecapConstants.BULK_REQUEST, RecapConstants.BULK_REQUEST_FORM, bulkRequestForm);
    }

    @PostMapping("/bulkRequest/searchRequest")
    public ModelAndView searchRequest(@Valid @ModelAttribute("bulkRequestForm") BulkRequestForm bulkRequestForm, Model model) {
        bulkRequestService.processSearchRequest(bulkRequestForm);
        model.addAttribute(RecapConstants.TEMPLATE, RecapConstants.BULK_REQUEST);
        return new ModelAndView(RecapConstants.BULK_SEARCH_REQUEST_SECTION, RecapConstants.BULK_REQUEST_FORM, bulkRequestForm);
    }


    @PostMapping("/bulkRequest/onPageSizeChange")
    public ModelAndView onPageSizeChange(@Valid @ModelAttribute("bulkRequestForm") BulkRequestForm bulkRequestForm, Model model) {
        bulkRequestService.processOnPageSizeChange(bulkRequestForm);
        model.addAttribute(RecapConstants.TEMPLATE, RecapConstants.BULK_REQUEST);
        return new ModelAndView(RecapConstants.BULK_SEARCH_REQUEST_SECTION, RecapConstants.BULK_REQUEST_FORM, bulkRequestForm);
    }

    @PostMapping("/bulkRequest/searchFirst")
    public ModelAndView searchFirst(@Valid @ModelAttribute("bulkRequestForm") BulkRequestForm bulkRequestForm, Model model) {
        bulkRequestForm.setPageNumber(0);
        bulkRequestService.getPaginatedSearchResults(bulkRequestForm);
        model.addAttribute(RecapConstants.TEMPLATE, RecapConstants.BULK_REQUEST);
        return new ModelAndView(RecapConstants.BULK_SEARCH_REQUEST_SECTION, RecapConstants.BULK_REQUEST_FORM, bulkRequestForm);
    }

    @PostMapping("/bulkRequest/searchPrevious")
    public ModelAndView searchPrevious(@Valid @ModelAttribute("bulkRequestForm") BulkRequestForm bulkRequestForm, Model model) {
        bulkRequestForm.setPageNumber(bulkRequestForm.getPageNumber() -1);
        bulkRequestService.getPaginatedSearchResults(bulkRequestForm);
        model.addAttribute(RecapConstants.TEMPLATE, RecapConstants.BULK_REQUEST);
        return new ModelAndView(RecapConstants.BULK_SEARCH_REQUEST_SECTION, RecapConstants.BULK_REQUEST_FORM, bulkRequestForm);
    }

    @PostMapping("/bulkRequest/searchNext")
    public ModelAndView searchNext(@Valid @ModelAttribute("bulkRequestForm") BulkRequestForm bulkRequestForm, Model model) {
        bulkRequestForm.setPageNumber(bulkRequestForm.getPageNumber() + 1);
        bulkRequestService.getPaginatedSearchResults(bulkRequestForm);
        model.addAttribute(RecapConstants.TEMPLATE, RecapConstants.BULK_REQUEST);
        return new ModelAndView(RecapConstants.BULK_SEARCH_REQUEST_SECTION, RecapConstants.BULK_REQUEST_FORM, bulkRequestForm);
    }

    @PostMapping("/bulkRequest/searchLast")
    public ModelAndView searchLast(@Valid @ModelAttribute("bulkRequestForm") BulkRequestForm bulkRequestForm, Model model) {
        bulkRequestForm.setPageNumber(bulkRequestForm.getTotalPageCount() -1);
        bulkRequestService.getPaginatedSearchResults(bulkRequestForm);
        model.addAttribute(RecapConstants.TEMPLATE, RecapConstants.BULK_REQUEST);
        return new ModelAndView(RecapConstants.BULK_SEARCH_REQUEST_SECTION, RecapConstants.BULK_REQUEST_FORM, bulkRequestForm);
    }

    @GetMapping("/bulkRequest/goToSearchRequest")
    public ModelAndView searchRequestByPatronBarcode(String patronBarcodeInRequest,Model model) {
        BulkRequestForm bulkRequestForm = new BulkRequestForm();
        bulkRequestForm.setPatronBarcodeSearch(patronBarcodeInRequest);
        bulkRequestService.processSearchRequest(bulkRequestForm);
        loadSearchRequestPage(bulkRequestForm);
        model.addAttribute(RecapConstants.TEMPLATE, RecapConstants.BULK_REQUEST);
        return new ModelAndView(RecapConstants.BULK_REQUEST, RecapConstants.BULK_REQUEST_FORM, bulkRequestForm);
    }

    @PostMapping("/bulkRequest/loadCreateRequestForSamePatron")
    public ModelAndView loadCreateRequestForSamePatron(@Valid @ModelAttribute("bulkRequestForm") BulkRequestForm bulkRequestForm,Model model) {
        loadCreateRequestPage(bulkRequestForm);
        bulkRequestForm.setSubmitted(false);
        bulkRequestForm.setRequestingInstitution(bulkRequestForm.getRequestingInstituionHidden());
        bulkRequestService.processDeliveryLocations(bulkRequestForm);
        model.addAttribute(RecapConstants.TEMPLATE, RecapConstants.BULK_REQUEST);
        return new ModelAndView(RecapConstants.BULK_REQUEST, RecapConstants.BULK_REQUEST_FORM, bulkRequestForm);
    }

    @PostMapping("/bulkRequest/populateDeliveryLocations")
    public ModelAndView populateDeliveryLocations(@Valid @ModelAttribute("bulkRequestForm") BulkRequestForm bulkRequestForm,Model model){
        bulkRequestService.processDeliveryLocations(bulkRequestForm);
        model.addAttribute(RecapConstants.TEMPLATE, RecapConstants.BULK_REQUEST);
        return new ModelAndView("bulkRequest::#deliveryLocation", RecapConstants.BULK_REQUEST_FORM, bulkRequestForm);
    }

    @GetMapping("/bulkRequest/downloadReports/{bulkRequestId}")
    public void downloadReports(@PathVariable String bulkRequestId, HttpServletResponse response, Model model) throws Exception {
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        BulkRequestItemEntity bulkRequestItemEntity = bulkRequestService.saveUpadatedRequestStatus(Integer.valueOf(bulkRequestId));
        String fileNameWithExtension = "Results_" + StringUtils.substringBefore(bulkRequestItemEntity.getBulkRequestFileName(), ".csv") + dateFormat.format(new Date()) + ".csv";
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileNameWithExtension + "\"");
        response.setContentLength(bulkRequestItemEntity.getBulkRequestFileData().length);
        FileCopyUtils.copy(bulkRequestItemEntity.getBulkRequestFileData(), response.getOutputStream());
        model.addAttribute(RecapConstants.TEMPLATE, RecapConstants.BULK_REQUEST);
    }
    
    private void loadCreateRequestPage(BulkRequestForm bulkRequestForm) {
        bulkRequestForm.setRequestingInstitutions(getInstitutions());
    }

    private void loadSearchRequestPage(BulkRequestForm bulkRequestForm) {
        bulkRequestForm.setInstitutionList(getInstitutions());
    }

    private List<String> getInstitutions() {
        return institutionDetailsRepository.getInstitutionCodeForSuperAdmin().stream().map(InstitutionEntity::getInstitutionCode).collect(Collectors.toList());
    }

}
