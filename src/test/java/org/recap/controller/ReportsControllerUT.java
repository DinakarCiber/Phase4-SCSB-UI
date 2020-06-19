package org.recap.controller;

import org.apache.shiro.authc.UsernamePasswordToken;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.recap.RecapCommonConstants;
import org.recap.RecapConstants;
import org.recap.model.search.DeaccessionItemResultsRow;
import org.recap.model.search.IncompleteReportResultsRow;
import org.recap.model.search.ReportsForm;
import org.recap.repository.jpa.RequestItemDetailsRepository;
import org.recap.util.ReportsUtil;
import org.recap.util.UserAuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

/**
 * Created by rajeshbabuk on 21/10/16.
 */
public class ReportsControllerUT extends BaseControllerUT {

    @Mock
    Model model;

    @Mock
    BindingResult bindingResult;

    @Mock
    ReportsController reportsController;

    @Autowired
    ReportsController reportsControllerWired;

    @Mock
    ReportsController mockedReportsController;

    @Mock
    RequestItemDetailsRepository requestItemDetailsRepository;

    @Mock
    ReportsUtil reportsUtil;

    @Mock
    HttpSession session;

    @Mock
    HttpServletRequest request;

    @Mock
    public UserAuthUtil userAuthUtil;

    public UserAuthUtil getUserAuthUtil() {
        return userAuthUtil;
    }

    public void setUserAuthUtil(UserAuthUtil userAuthUtil) {
        this.userAuthUtil = userAuthUtil;
    }

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(reportsController).build();
    }


    @Test
    public void reports() throws Exception{
        when(request.getSession(false)).thenReturn(session);
        Mockito.when(getUserAuthUtil().authorizedUser(RecapConstants.SCSB_SHIRO_REPORT_URL,(UsernamePasswordToken)session.getAttribute(RecapConstants.USER_TOKEN))).thenReturn(true);
        Mockito.when(reportsController.getUserAuthUtil()).thenReturn(userAuthUtil);
        Mockito.when(reportsController.reports(model,request)).thenCallRealMethod();
        String response = reportsController.reports(model,request);
        assertNotNull(response);
        assertEquals("searchRecords",response);
    }

    @Test
    public void partnersRequest() throws Exception {
        ReportsForm reportsForm = new ReportsForm();
        reportsForm.setRequestType(RecapCommonConstants.REPORTS_REQUEST);
        reportsForm.setRequestFromDate("11/01/2016");
        reportsForm.setRequestToDate("12/01/2016");
        reportsForm.setShowBy(RecapCommonConstants.REPORTS_PARTNERS);
        ModelAndView modelAndView = reportsControllerWired.reportCounts(reportsForm,model);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(RecapCommonConstants.SIMPLE_DATE_FORMAT_REPORTS);
        String fromDate = reportsForm.getRequestFromDate();
        String toDate = reportsForm.getRequestToDate();
        Date requestFromDate = new Date();
        Date requestToDate =new Date();
        reportsUtil.populatePartnersCountForRequest(reportsForm, requestFromDate, requestToDate);
        assertNotNull(modelAndView);
        assertEquals("reports", modelAndView.getViewName());
    }

    @Test
    public void requestType() throws Exception {
        ReportsForm reportsForm = new ReportsForm();
        reportsForm.setRequestType(RecapCommonConstants.REPORTS_REQUEST);
        reportsForm.setRequestFromDate("11/01/2016");
        reportsForm.setRequestToDate("12/01/2016");
        reportsForm.setShowBy(RecapCommonConstants.REPORTS_REQUEST_TYPE);
        ModelAndView modelAndView = reportsControllerWired.reportCounts(reportsForm,model);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(RecapCommonConstants.SIMPLE_DATE_FORMAT_REPORTS);
        String fromDate = reportsForm.getRequestFromDate();
        String toDate = reportsForm.getRequestToDate();
        Date requestFromDate = new Date();
        Date requestToDate = new Date();
        reportsUtil.populateRequestTypeInformation(reportsForm, requestFromDate, requestToDate);
        assertNotNull(modelAndView);
        assertEquals("reports", modelAndView.getViewName());
    }

    @Test
    public void accessionDeaccessionReports() throws Exception {
        ReportsForm reportsForm = new ReportsForm();
        reportsForm.setRequestType(RecapCommonConstants.REPORTS_REQUEST);
        reportsForm.setRequestFromDate("11/01/2016");
        reportsForm.setRequestToDate("12/01/2016");
        reportsForm.setShowBy(RecapCommonConstants.REPORTS_ACCESSION_DEACCESSION);
        reportsForm.setShowNoteILBD(true);
        reportsForm.setShowNotePartners(true);
        reportsForm.setReportRequestType(new ArrayList<>());
        reportsForm.setOwningInstitutions(Arrays.asList("PUL"));
        reportsForm.setCollectionGroupDesignations(Arrays.asList("Shared"));
        reportsForm.setShowRequestTypeShow(true);
        reportsForm.setShowDeaccessionInformationTable(true);
        reportsForm.setDeaccessionOwnInst("CUL");
        reportsForm.setShowNoteRequestType(true);
        String fromDate = reportsForm.getAccessionDeaccessionFromDate();
        String toDate = reportsForm.getAccessionDeaccessionToDate();
        ModelAndView modelAndView = reportsControllerWired.reportCounts(reportsForm,model);
        reportsUtil.populateAccessionDeaccessionItemCounts(reportsForm);
        assertNotNull(modelAndView);
        assertEquals("reports", modelAndView.getViewName());
        assertNotNull(reportsForm.isShowNoteILBD());
        assertNotNull(reportsForm.isShowNotePartners());
        assertNotNull(reportsForm.isShowNoteRequestType());
        assertNotNull(reportsForm.getReportRequestType());
        assertNotNull(reportsForm.isShowRequestTypeShow());
        assertNotNull(reportsForm.isShowDeaccessionInformationTable());
    }


    @Test
    public void cgdCounts() throws Exception {
        ReportsForm reportsForm = new ReportsForm();
        ModelAndView modelAndView = reportsControllerWired.cgdCounts(reportsForm,model);
        reportsUtil.populateCGDItemCounts(reportsForm);
        assertNotNull(modelAndView);
        assertEquals("reports :: #cgdTable",modelAndView.getViewName());
    }

    @Test
    public void deaccessionInformation() throws Exception {
        ReportsForm reportsForm = new ReportsForm();
        List<DeaccessionItemResultsRow> deaccessionItemResultsRowList = new ArrayList<>();
        ModelAndView modelAndView = reportsControllerWired.deaccessionInformation(reportsForm,model);
        when((reportsUtil.deaccessionReportFieldsInformation(reportsForm))).thenReturn(deaccessionItemResultsRowList);
        assertNotNull(deaccessionItemResultsRowList);
        assertNotNull(modelAndView);
        assertEquals("reports :: #deaccessionInformation",modelAndView.getViewName());

    }

    @Test
    public void testDeaccessionItemResultRows() throws Exception {
        ReportsForm reportsForm = new ReportsForm();
        reportsForm.setAccessionDeaccessionFromDate(new Date().toString());
        reportsForm.setAccessionDeaccessionToDate(new Date().toString());
        List<DeaccessionItemResultsRow> deaccessionItemResultsRowList = new ArrayList<>();
        Mockito.when(mockedReportsController.getReportsUtil()).thenReturn(reportsUtil);
        DeaccessionItemResultsRow deaccessionItemResultsRow = new DeaccessionItemResultsRow();
        deaccessionItemResultsRow.setItemBarcode("123456");
        deaccessionItemResultsRow.setItemId(1);
        deaccessionItemResultsRow.setCgd("Shared");
        deaccessionItemResultsRow.setDeaccessionDate(new Date().toString());
        deaccessionItemResultsRow.setTitle("test");
        deaccessionItemResultsRow.setDeaccessionNotes("test");
        deaccessionItemResultsRow.setDeaccessionOwnInst("PUL");
        deaccessionItemResultsRowList.add(deaccessionItemResultsRow);
        Mockito.when(mockedReportsController.getReportsUtil().deaccessionReportFieldsInformation(reportsForm)).thenReturn(deaccessionItemResultsRowList);
        Mockito.when(mockedReportsController.deaccessionInformation(reportsForm,model)).thenCallRealMethod();
        ModelAndView modelAndView = mockedReportsController.deaccessionInformation(reportsForm,model);
        assertNotNull(modelAndView);
        assertEquals(modelAndView.getViewName(),"reports :: #deaccessionInformation");
        assertNotNull(deaccessionItemResultsRow.getCgd());
        assertNotNull(deaccessionItemResultsRow.getItemId());
        assertNotNull(deaccessionItemResultsRow.getDeaccessionDate());
        assertNotNull(deaccessionItemResultsRow.getTitle());
        assertNotNull(deaccessionItemResultsRow.getDeaccessionOwnInst());
        assertNotNull(deaccessionItemResultsRow.getItemBarcode());
        assertNotNull(deaccessionItemResultsRow.getDeaccessionNotes());
    }

    @Test
    public void searchPrevious() throws Exception{
        ReportsForm reportsForm = new ReportsForm();
        ModelAndView modelAndView = reportsControllerWired.searchPrevious(reportsForm,model);
        assertNotNull(modelAndView);
        assertEquals("reports :: #deaccessionInformation",modelAndView.getViewName());
    }

    @Test
    public void searchNext() throws Exception{
        ReportsForm reportsForm = new ReportsForm();
        ModelAndView modelAndView = reportsControllerWired.searchNext(reportsForm,model);
        assertNotNull(modelAndView);
        assertEquals("reports :: #deaccessionInformation",modelAndView.getViewName());
    }

    @Test
    public void searchFirst() throws Exception{
        ReportsForm reportsForm = new ReportsForm();
        ModelAndView modelAndView = reportsControllerWired.searchFirst(reportsForm,model);
        assertNotNull(modelAndView);
        assertEquals("reports :: #deaccessionInformation",modelAndView.getViewName());
    }

    @Test
    public void searchLast() throws Exception{
        ReportsForm reportsForm = new ReportsForm();
        ModelAndView modelAndView = reportsControllerWired.searchLast(reportsForm,model);
        assertNotNull(modelAndView);
        assertEquals("reports :: #deaccessionInformation",modelAndView.getViewName());
    }

    @Test
    public void incompleteRecordsReport() throws Exception{
        ReportsForm reportsForm = new ReportsForm();
        reportsForm.setIncompleteRequestingInstitution("PUL");
        List<IncompleteReportResultsRow> incompleteReportResultsRows = new ArrayList<>();
        Mockito.when(mockedReportsController.getReportsUtil()).thenReturn(reportsUtil);
        IncompleteReportResultsRow incompleteReportResult = new IncompleteReportResultsRow();
        incompleteReportResult.setBarcode("12345");
        incompleteReportResult.setOwningInstitution("PUl");
        incompleteReportResult.setCreatedDate("03/22/2016");
        incompleteReportResult.setAuthor("dummy");
        incompleteReportResult.setTitle("dummy");
        incompleteReportResult.setCustomerCode("PU");
        incompleteReportResultsRows.add(incompleteReportResult);
        Mockito.when(mockedReportsController.getReportsUtil().incompleteRecordsReportFieldsInformation(reportsForm)).thenReturn(incompleteReportResultsRows);
        Mockito.when(mockedReportsController.incompleteRecordsReport(reportsForm,model)).thenCallRealMethod();
        ModelAndView modelAndView = mockedReportsController.incompleteRecordsReport(reportsForm, model);
        assertNotNull(modelAndView);
        assertEquals("reports :: #IncompleteReporttableview",modelAndView.getViewName());
    }

    @Test
    public void getInstitutionForIncompletereport() throws Exception{
        ReportsForm reportsForm = new ReportsForm();
        ModelAndView modelAndView = reportsControllerWired.getInstitutionForIncompletereport(request, reportsForm);
        assertNotNull(modelAndView);
        List<String> incompleteShowByInst = reportsForm.getIncompleteShowByInst();
        assertNotNull(incompleteShowByInst);
        boolean instutions = incompleteShowByInst.containsAll(Arrays.asList("PUL", "CUL", "NYPL"));
        assertEquals(true,instutions);
        assertEquals("reports :: #incompleteShowBy",modelAndView.getViewName());
    }

    @Test
    public void incompleteReportPageSizeChange() throws Exception{
        ReportsForm reportsForm = new ReportsForm();
        reportsForm.setIncompleteRequestingInstitution("PUL");
        reportsForm.setIncompletePageSize(10);
        List<IncompleteReportResultsRow> incompleteReportResultsRows = new ArrayList<>();
        Mockito.when(mockedReportsController.getReportsUtil()).thenReturn(reportsUtil);
        IncompleteReportResultsRow incompleteReportResult = new IncompleteReportResultsRow();
        incompleteReportResult.setBarcode("12345");
        incompleteReportResult.setOwningInstitution("PUl");
        incompleteReportResult.setCreatedDate("03/22/2016");
        incompleteReportResult.setAuthor("dummy");
        incompleteReportResult.setTitle("dummy");
        incompleteReportResult.setCustomerCode("PU");
        incompleteReportResultsRows.add(incompleteReportResult);
        Mockito.when(mockedReportsController.getReportsUtil().incompleteRecordsReportFieldsInformation(reportsForm)).thenReturn(incompleteReportResultsRows);
        Mockito.when(mockedReportsController.incompleteReportPageSizeChange(reportsForm,model)).thenCallRealMethod();
        ModelAndView modelAndView = mockedReportsController.incompleteReportPageSizeChange(reportsForm, model);
        assertNotNull(modelAndView);
        assertEquals("reports :: #IncompleteReporttableview",modelAndView.getViewName());
    }
}
