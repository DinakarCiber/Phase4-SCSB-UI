package org.recap.controller;

import org.apache.shiro.authc.UsernamePasswordToken;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.recap.RecapConstants;
import org.recap.model.CancelRequestResponse;
import org.recap.model.jpa.InstitutionEntity;
import org.recap.model.search.SearchItemResultRow;
import org.recap.model.search.SearchRecordsRequest;
import org.recap.model.search.SearchRecordsResponse;
import org.recap.model.search.SearchResultRow;
import org.recap.model.usermanagement.UserDetailsForm;
import org.recap.model.usermanagement.UserForm;
import org.recap.repository.jpa.InstitutionDetailsRepository;
import org.recap.util.CsvUtil;
import org.recap.util.SearchUtil;
import org.recap.util.UserAuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

/**
 * Created by premkb on 2/8/16.
 */
public class SearchRecordsControllerUT extends BaseControllerUT{

    @Mock
    Model model;

    @Mock
    BindingResult bindingResult;

    @Mock
    HttpServletResponse httpServletResponse;

    @Mock
    SearchRecordsController searchRecordsController;

    @Autowired
    SearchRecordsController searchRecordsControllerWired;

    @Mock
    private CsvUtil csvUtil;

    @Mock
    HttpSession session;

    @Mock
    HttpServletRequest request;

    @Mock
    public UserAuthUtil userAuthUtil;

    @Mock
    SearchUtil searchUtil;

    @Mock
    SearchRecordsRequest searchRecordsRequest;

    @Mock
    RedirectAttributes redirectAttributes;

    @Mock
    InstitutionDetailsRepository institutionDetailsRepository;

    @Before
    public void setUp() throws Exception {
        when(request.getSession()).thenReturn(session);
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(searchRecordsController).build();
        Map searchRecordsMap = new HashMap();
    }


    @Test
    public void searchRecords() throws Exception{
        when(request.getSession(false)).thenReturn(session);
        SearchRecordsResponse searchRecordsResponse=new SearchRecordsResponse();
        when(userAuthUtil.authorizedUser(RecapConstants.SCSB_SHIRO_SEARCH_URL,(UsernamePasswordToken)session.getAttribute(RecapConstants.USER_TOKEN))).thenReturn(true);
        when(searchUtil.requestSearchResults(searchRecordsRequest)).thenReturn(searchRecordsResponse);
        when(searchRecordsController.getUserAuthUtil()).thenReturn(userAuthUtil);
        when(searchRecordsController.getSearchUtil()).thenReturn(searchUtil);
        when(searchRecordsController.searchRecords(model,request)).thenCallRealMethod();
        String response = searchRecordsController.searchRecords(model,request);
        assertNotNull(response);
//        assertEquals("searchRecords",response);
    }

    @Test
    public void search() throws Exception{
        SearchRecordsResponse searchRecordsResponse=new SearchRecordsResponse();
        searchRecordsRequest = new SearchRecordsRequest();
        when(searchUtil.requestSearchResults(searchRecordsRequest)).thenReturn(searchRecordsResponse);
        when(searchRecordsController.getSearchUtil()).thenReturn(searchUtil);
        when(searchRecordsController.search(searchRecordsRequest,bindingResult,model)).thenCallRealMethod();
        ModelAndView modelAndView = searchRecordsControllerWired.search(searchRecordsRequest,bindingResult,model);
        assertNotNull(modelAndView);
        assertEquals("searchRecords",modelAndView.getViewName());
    }

    @Test
    public void searchPrevious() throws Exception{
        SearchRecordsResponse searchRecordsResponse=new SearchRecordsResponse();
        searchRecordsRequest = new SearchRecordsRequest();
        when(searchUtil.requestSearchResults(searchRecordsRequest)).thenReturn(searchRecordsResponse);
        when(searchRecordsController.getSearchUtil()).thenReturn(searchUtil);
        when(searchRecordsController.searchPrevious(searchRecordsRequest,bindingResult,model)).thenCallRealMethod();
        ModelAndView modelAndView = searchRecordsControllerWired.searchPrevious(searchRecordsRequest,bindingResult,model);
        assertNotNull(modelAndView);
        assertEquals("searchRecords",modelAndView.getViewName());
    }

    @Test
    public void searchNext() throws Exception{
        SearchRecordsResponse searchRecordsResponse=new SearchRecordsResponse();
        searchRecordsRequest = new SearchRecordsRequest();
        when(searchUtil.requestSearchResults(searchRecordsRequest)).thenReturn(searchRecordsResponse);
        when(searchRecordsController.getSearchUtil()).thenReturn(searchUtil);
        when(searchRecordsController.searchNext(searchRecordsRequest,bindingResult,model)).thenCallRealMethod();
        ModelAndView modelAndView = searchRecordsControllerWired.searchNext(searchRecordsRequest,bindingResult,model);
        assertNotNull(modelAndView);
        assertEquals("searchRecords",modelAndView.getViewName());
    }

    @Test
    public void searchFirst() throws Exception{
        SearchRecordsResponse searchRecordsResponse=new SearchRecordsResponse();
        searchRecordsRequest = new SearchRecordsRequest();
        when(searchUtil.requestSearchResults(searchRecordsRequest)).thenReturn(searchRecordsResponse);
        when(searchRecordsController.getSearchUtil()).thenReturn(searchUtil);
        when(searchRecordsController.searchFirst(searchRecordsRequest,bindingResult,model)).thenCallRealMethod();
        ModelAndView modelAndView = searchRecordsControllerWired.searchFirst(searchRecordsRequest,bindingResult,model);
        assertNotNull(modelAndView);
        assertEquals("searchRecords",modelAndView.getViewName());
    }

    @Test
    public void searchLast() throws Exception{
        SearchRecordsResponse searchRecordsResponse=new SearchRecordsResponse();
        searchRecordsRequest = new SearchRecordsRequest();
        when(searchUtil.requestSearchResults(searchRecordsRequest)).thenReturn(searchRecordsResponse);
        when(searchRecordsController.getSearchUtil()).thenReturn(searchUtil);
        when(searchRecordsController.searchLast(searchRecordsRequest,bindingResult,model)).thenCallRealMethod();
        ModelAndView modelAndView = searchRecordsControllerWired.searchLast(searchRecordsRequest,bindingResult,model);
        assertNotNull(modelAndView);
        assertEquals("searchRecords",modelAndView.getViewName());
    }

    @Test
    public void clear() throws Exception{
        searchRecordsRequest = new SearchRecordsRequest();
        when(searchRecordsController.clear(searchRecordsRequest,bindingResult,model)).thenCallRealMethod();
        ModelAndView modelAndView = searchRecordsController.clear(searchRecordsRequest,bindingResult,model);
        assertNotNull(modelAndView);
        assertEquals("searchRecords",modelAndView.getViewName());
    }

    @Test
    public void newSearch() throws Exception{
        searchRecordsRequest = new SearchRecordsRequest();
        when(searchRecordsController.newSearch(model)).thenCallRealMethod();
        ModelAndView modelAndView = searchRecordsController.newSearch(model);
        assertNotNull(modelAndView);
        assertEquals("searchRecords",modelAndView.getViewName());
    }

    public void requestRecords() throws Exception{
        searchRecordsRequest = new SearchRecordsRequest();
        searchRecordsRequest.setErrorMessage("test");
        SearchResultRow searchResultRow = new SearchResultRow();
        searchResultRow.setRequestId(1);
        searchResultRow.setSelected(true);
        searchResultRow.setCollectionGroupDesignation("Private");
        searchResultRow.setOwningInstitution("PUL");
        searchRecordsRequest.setSearchResultRows(Arrays.asList(searchResultRow));
        UserDetailsForm userDetailsForm = new UserDetailsForm();
        userDetailsForm.setSuperAdmin(false);
        userDetailsForm.setLoginInstitutionId(1);
        String privilege = RecapConstants.REQUEST_PRIVILEGE;
        usersSessionAttributes();
        InstitutionEntity institutionEntity = new InstitutionEntity();
        institutionEntity.setInstitutionCode("UC");
        institutionEntity.setInstitutionName("University of Chicago");
        Mockito.when(searchRecordsController.getUserAuthUtil()).thenReturn(userAuthUtil);
        Mockito.when(searchRecordsController.getInstitutionDetailsRepository()).thenReturn(institutionDetailsRepository);
        Mockito.when(searchRecordsController.getInstitutionDetailsRepository().findById(userDetailsForm.getLoginInstitutionId())).thenReturn(Optional.of(institutionEntity));
        Mockito.when(searchRecordsController.getUserAuthUtil().getUserDetails(request.getSession(false),RecapConstants.REQUEST_PRIVILEGE)).thenReturn(userDetailsForm);
        Mockito.when(userAuthUtil.getUserDetails(session, privilege)).thenCallRealMethod();
        Mockito.when(searchRecordsController.requestRecords(searchRecordsRequest,bindingResult,model, request, redirectAttributes)).thenCallRealMethod();
        ModelAndView modelAndView = searchRecordsController.requestRecords(searchRecordsRequest,bindingResult,model, request, redirectAttributes);
        assertNotNull(modelAndView);
        assertEquals("searchRecords",modelAndView.getViewName());
    }
    @Test
    public void requestRecords2() throws Exception{
        searchRecordsRequest = new SearchRecordsRequest();
        searchRecordsRequest.setErrorMessage("");
        SearchResultRow searchResultRow = new SearchResultRow();
        searchResultRow.setRequestId(1);
        searchResultRow.setSelected(true);
        searchResultRow.setCollectionGroupDesignation("Private");
        searchResultRow.setOwningInstitution("PUL");
        searchRecordsRequest.setSearchResultRows(Arrays.asList(searchResultRow));
        UserDetailsForm userDetailsForm = new UserDetailsForm();
        userDetailsForm.setSuperAdmin(true);
        userDetailsForm.setLoginInstitutionId(1);
        String privilege = RecapConstants.REQUEST_PRIVILEGE;
        usersSessionAttributes();
        InstitutionEntity institutionEntity = new InstitutionEntity();
        institutionEntity.setInstitutionCode("UC");
        institutionEntity.setInstitutionName("University of Chicago");
        Mockito.when(searchRecordsController.getUserAuthUtil()).thenReturn(userAuthUtil);
        Mockito.when(searchRecordsController.getInstitutionDetailsRepository()).thenReturn(institutionDetailsRepository);
        Mockito.when(searchRecordsController.getInstitutionDetailsRepository().findById(userDetailsForm.getLoginInstitutionId())).thenReturn(Optional.of(institutionEntity));
        Mockito.when(searchRecordsController.getUserAuthUtil().getUserDetails(request.getSession(false),RecapConstants.REQUEST_PRIVILEGE)).thenReturn(userDetailsForm);
        Mockito.when(userAuthUtil.getUserDetails(session, privilege)).thenCallRealMethod();
        Mockito.when(searchRecordsController.requestRecords(searchRecordsRequest,bindingResult,model, request, redirectAttributes)).thenCallRealMethod();
        ModelAndView modelAndView = searchRecordsController.requestRecords(searchRecordsRequest,bindingResult,model, request, redirectAttributes);
        assertNotNull(modelAndView);
    }
    @Test
    public void requestRecords3() throws Exception{
        searchRecordsRequest = new SearchRecordsRequest();
        searchRecordsRequest.setErrorMessage("error");
        SearchResultRow searchResultRow = new SearchResultRow();
        searchResultRow.setRequestId(1);
        searchResultRow.setSelected(false);
        searchResultRow.setCollectionGroupDesignation("Private");
        searchResultRow.setOwningInstitution("PUL");
        searchResultRow.setTitle("test");
        searchResultRow.setBarcode("23456");
        SearchItemResultRow searchItemResultRow = new SearchItemResultRow();
        searchItemResultRow.setSelectedItem(true);
        searchResultRow.setSearchItemResultRows(Arrays.asList(searchItemResultRow));
        searchRecordsRequest.setSearchResultRows(Arrays.asList(searchResultRow));
        UserDetailsForm userDetailsForm = new UserDetailsForm();
        userDetailsForm.setSuperAdmin(true);
        userDetailsForm.setRecapPermissionAllowed(true);
        userDetailsForm.setLoginInstitutionId(1);
        String privilege = RecapConstants.REQUEST_PRIVILEGE;
        usersSessionAttributes();
        InstitutionEntity institutionEntity = new InstitutionEntity();
        institutionEntity.setInstitutionCode("UC");
        institutionEntity.setInstitutionName("University of Chicago");
        Mockito.when(searchRecordsController.getUserAuthUtil()).thenReturn(userAuthUtil);
        Mockito.when(searchRecordsController.getInstitutionDetailsRepository()).thenReturn(institutionDetailsRepository);
        Mockito.when(searchRecordsController.getInstitutionDetailsRepository().findById(userDetailsForm.getLoginInstitutionId())).thenReturn(Optional.of(institutionEntity));
        Mockito.when(searchRecordsController.getUserAuthUtil().getUserDetails(request.getSession(false),RecapConstants.REQUEST_PRIVILEGE)).thenReturn(userDetailsForm);
        Mockito.when(userAuthUtil.getUserDetails(session, privilege)).thenCallRealMethod();
        Mockito.when(searchRecordsController.requestRecords(searchRecordsRequest,bindingResult,model, request, redirectAttributes)).thenCallRealMethod();
        ModelAndView modelAndView = searchRecordsController.requestRecords(searchRecordsRequest,bindingResult,model, request, redirectAttributes);
        assertNotNull(modelAndView);
        assertEquals("searchRecords",modelAndView.getViewName());

    }
    @Test
    public void requestRecords4() throws Exception{
        searchRecordsRequest = new SearchRecordsRequest();
        searchRecordsRequest.setErrorMessage("error");
        SearchResultRow searchResultRow = new SearchResultRow();
        searchResultRow.setRequestId(1);
        searchResultRow.setSelected(true);
        searchResultRow.setCollectionGroupDesignation("Private");
        searchResultRow.setOwningInstitution("PUL");
        searchResultRow.setTitle("test");
        searchResultRow.setBarcode("23456");
        searchRecordsRequest.setSearchResultRows(Arrays.asList(searchResultRow));
        UserDetailsForm userDetailsForm = new UserDetailsForm();
        userDetailsForm.setSuperAdmin(true);
        userDetailsForm.setRecapPermissionAllowed(true);
        userDetailsForm.setLoginInstitutionId(1);
        String privilege = RecapConstants.REQUEST_PRIVILEGE;
        usersSessionAttributes();
        InstitutionEntity institutionEntity = new InstitutionEntity();
        institutionEntity.setInstitutionCode("UC");
        institutionEntity.setInstitutionName("University of Chicago");
        Mockito.when(searchRecordsController.getUserAuthUtil()).thenReturn(userAuthUtil);
        Mockito.when(searchRecordsController.getInstitutionDetailsRepository()).thenReturn(institutionDetailsRepository);
        Mockito.when(searchRecordsController.getInstitutionDetailsRepository().findById(userDetailsForm.getLoginInstitutionId())).thenReturn(Optional.of(institutionEntity));
        Mockito.when(searchRecordsController.getUserAuthUtil().getUserDetails(request.getSession(false),RecapConstants.REQUEST_PRIVILEGE)).thenReturn(userDetailsForm);
        Mockito.when(userAuthUtil.getUserDetails(session, privilege)).thenCallRealMethod();
        Mockito.when(searchRecordsController.requestRecords(searchRecordsRequest,bindingResult,model, request, redirectAttributes)).thenCallRealMethod();
        ModelAndView modelAndView = searchRecordsController.requestRecords(searchRecordsRequest,bindingResult,model, request, redirectAttributes);
        assertNotNull(modelAndView);
        assertEquals("searchRecords",modelAndView.getViewName());

    }
    @Test
    public void requestRecords5() throws Exception{
        searchRecordsRequest = new SearchRecordsRequest();
        searchRecordsRequest.setErrorMessage("error");
        SearchResultRow searchResultRow = new SearchResultRow();
        searchResultRow.setRequestId(1);
        searchResultRow.setSelected(false);
        searchResultRow.setCollectionGroupDesignation("Private");
        searchResultRow.setOwningInstitution("PUL");
        searchResultRow.setTitle("test");
        searchResultRow.setBarcode("23456");
        SearchItemResultRow searchItemResultRow = new SearchItemResultRow();
        searchItemResultRow.setSelectedItem(true);
        searchResultRow.setSearchItemResultRows(Arrays.asList(searchItemResultRow));
        searchRecordsRequest.setSearchResultRows(Arrays.asList(searchResultRow));
        UserDetailsForm userDetailsForm = new UserDetailsForm();
        userDetailsForm.setSuperAdmin(true);
        userDetailsForm.setRecapPermissionAllowed(false);
        userDetailsForm.setLoginInstitutionId(1);
        String privilege = RecapConstants.REQUEST_PRIVILEGE;
        usersSessionAttributes();
        InstitutionEntity institutionEntity = new InstitutionEntity();
        institutionEntity.setInstitutionCode("UC");
        institutionEntity.setInstitutionName("University of Chicago");
        Mockito.when(searchRecordsController.getUserAuthUtil()).thenReturn(userAuthUtil);
        Mockito.when(searchRecordsController.getInstitutionDetailsRepository()).thenReturn(institutionDetailsRepository);
        Mockito.when(searchRecordsController.getInstitutionDetailsRepository().findById(userDetailsForm.getLoginInstitutionId())).thenReturn(Optional.of(institutionEntity));
        Mockito.when(searchRecordsController.getUserAuthUtil().getUserDetails(request.getSession(false),RecapConstants.REQUEST_PRIVILEGE)).thenReturn(userDetailsForm);
        Mockito.when(userAuthUtil.getUserDetails(session, privilege)).thenCallRealMethod();
        Mockito.when(searchRecordsController.requestRecords(searchRecordsRequest,bindingResult,model, request, redirectAttributes)).thenCallRealMethod();
        ModelAndView modelAndView = searchRecordsController.requestRecords(searchRecordsRequest,bindingResult,model, request, redirectAttributes);
        assertNotNull(modelAndView);
        assertEquals("searchRecords",modelAndView.getViewName());

    }

    @Test
    public void checkGetterServices(){

        Mockito.when(searchRecordsController.getUserAuthUtil()).thenCallRealMethod();
        Mockito.when(searchRecordsController.getInstitutionDetailsRepository()).thenCallRealMethod();
        Mockito.when(searchRecordsController.getSearchUtil()).thenCallRealMethod();
        Mockito.doCallRealMethod().when(searchRecordsController).setUserAuthUtil(userAuthUtil);
        assertNotEquals(searchRecordsController.getUserAuthUtil(),userAuthUtil);
        assertNotEquals(searchRecordsController.getInstitutionDetailsRepository(),institutionDetailsRepository);
    }

    @Test
    public void exportRecords() throws Exception {
        byte[] fileContent = searchRecordsControllerWired.exportRecords(buildRequestWithResultRows(), httpServletResponse, bindingResult, model);
        assertNotNull(fileContent);
    }

    @Test
    public void onPageSizeChange() throws Exception{
        SearchRecordsResponse searchRecordsResponse=getSearchRecordsResponse();
        searchRecordsRequest = new SearchRecordsRequest();
        searchRecordsRequest.setPageNumber(20);
        when(searchUtil.requestSearchResults(searchRecordsRequest)).thenReturn(searchRecordsResponse);
        when(searchRecordsController.getSearchUtil()).thenReturn(searchUtil);
        when(searchRecordsController.onPageSizeChange(searchRecordsRequest,bindingResult,model)).thenCallRealMethod();
        when(searchRecordsController.getPageNumberOnPageSizeChange(searchRecordsRequest)).thenCallRealMethod();
        ModelAndView modelAndView = searchRecordsControllerWired.onPageSizeChange(searchRecordsRequest,bindingResult,model);
        assertNotNull(modelAndView);
        assertEquals("searchRecords",modelAndView.getViewName());
        assertNotNull(searchRecordsResponse.getSearchResultRows());
        assertNotNull(searchRecordsResponse.getTotalPageCount());
        assertNotNull(searchRecordsResponse.getTotalBibRecordsCount());
        assertNotNull(searchRecordsResponse.getTotalItemRecordsCount());
        assertNotNull(searchRecordsResponse.getTotalRecordsCount());
        assertNotNull(searchRecordsResponse.isShowTotalCount());
        assertNotNull(searchRecordsResponse.getErrorMessage());
    }
    @Test
    public void onPageSizeChange2() throws Exception{
        SearchRecordsResponse searchRecordsResponse=getSearchRecordsResponse();
        searchRecordsRequest = new SearchRecordsRequest();
        searchRecordsRequest.setPageNumber(20);
        searchRecordsRequest.setFieldName("BARCODE");
        when(searchUtil.requestSearchResults(searchRecordsRequest)).thenReturn(searchRecordsResponse);
        when(searchRecordsController.getSearchUtil()).thenReturn(searchUtil);
        when(searchRecordsController.onPageSizeChange(searchRecordsRequest,bindingResult,model)).thenCallRealMethod();
        when(searchRecordsController.getPageNumberOnPageSizeChange(searchRecordsRequest)).thenCallRealMethod();
        ModelAndView modelAndView = searchRecordsControllerWired.onPageSizeChange(searchRecordsRequest,bindingResult,model);
        assertNotNull(modelAndView);
        assertEquals("searchRecords",modelAndView.getViewName());

    }
    private SearchRecordsResponse getSearchRecordsResponse(){
        SearchRecordsResponse searchRecordsResponse = new SearchRecordsResponse();
        searchRecordsResponse.setSearchResultRows(new ArrayList<>());
        searchRecordsResponse.setTotalPageCount(1);
        searchRecordsResponse.setTotalBibRecordsCount("1");
        searchRecordsResponse.setTotalItemRecordsCount("1");
        searchRecordsResponse.setTotalRecordsCount("1");
        searchRecordsResponse.setShowTotalCount(false);
        searchRecordsResponse.setErrorMessage("test");
        return searchRecordsResponse;
    }

    private SearchRecordsRequest buildRequestWithResultRows() {
        searchRecordsRequest = new SearchRecordsRequest();
        List<SearchResultRow> searchResultRows = new ArrayList<>();
        SearchResultRow searchResultRow1 = new SearchResultRow();
        searchResultRow1.setTitle("Title1");
        searchResultRow1.setAuthor("Author1");
        searchResultRow1.setPublisher("publisher1");
        searchResultRow1.setOwningInstitution("NYPL");
        searchResultRow1.setCollectionGroupDesignation("Shared");
        searchResultRow1.setSelected(true);

        SearchResultRow searchResultRow2 = new SearchResultRow();
        searchResultRow2.setTitle("Title2");
        searchResultRow2.setAuthor("Author2");
        searchResultRow2.setPublisher("publisher2");
        searchResultRow2.setOwningInstitution("NYPL");
        searchResultRow2.setCollectionGroupDesignation("Shared");
        searchResultRow2.setSelectAllItems(true);

        List<SearchItemResultRow> searchItemResultRows = new ArrayList<>();
        SearchItemResultRow searchItemResultRow1 = new SearchItemResultRow();
        searchItemResultRow1.setCallNumber("call number1");
        searchItemResultRow1.setChronologyAndEnum("Chn Enum1");
        searchItemResultRow1.setCustomerCode("Code 1");
        searchItemResultRow1.setBarcode("1");
        searchItemResultRow1.setUseRestriction("In Library use");
        searchItemResultRow1.setCollectionGroupDesignation("Shared");
        searchItemResultRow1.setAvailability("Available");
        searchItemResultRows.add(searchItemResultRow1);
        searchResultRow2.setSearchItemResultRows(searchItemResultRows);

        searchResultRows.add(searchResultRow1);
        searchResultRows.add(searchResultRow2);
        searchRecordsRequest.setSearchResultRows(searchResultRows);
        return searchRecordsRequest;
    }
    private void usersSessionAttributes() throws Exception {
        when(request.getSession()).thenReturn(session);
        UserForm userForm = new UserForm();
        userForm.setUsername("kholi");
        userForm.setInstitution("3");
        userForm.setPassword("12345");
        UsernamePasswordToken token=new UsernamePasswordToken(userForm.getUsername()+ RecapConstants.TOKEN_SPLITER +userForm.getInstitution(),userForm.getPassword(),true);
        userAuthUtil.doAuthentication(token);
        when(session.getAttribute(RecapConstants.USER_TOKEN)).thenReturn(token);
        when(session.getAttribute(RecapConstants.USER_ID)).thenReturn(3);
        when(session.getAttribute(RecapConstants.SUPER_ADMIN_USER)).thenReturn(true);
        when(session.getAttribute(RecapConstants.RECAP_USER)).thenReturn(false);
        when(session.getAttribute(RecapConstants.BARCODE_RESTRICTED_PRIVILEGE)).thenReturn(false);
        when(session.getAttribute(RecapConstants.USER_INSTITUTION)).thenReturn(1);
        when(session.getAttribute(RecapConstants.REQUEST_ALL_PRIVILEGE)).thenReturn(false);
        userAuthUtil.getUserDetails(session,RecapConstants.BARCODE_RESTRICTED_PRIVILEGE);
    }

}