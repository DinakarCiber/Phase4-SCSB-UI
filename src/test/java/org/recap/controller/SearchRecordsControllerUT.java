package org.recap.controller;

import org.apache.shiro.authc.UsernamePasswordToken;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.recap.RecapConstants;
import org.recap.model.search.SearchItemResultRow;
import org.recap.model.search.SearchRecordsRequest;
import org.recap.model.search.SearchRecordsResponse;
import org.recap.model.search.SearchResultRow;
import org.recap.security.UserManagement;
import org.recap.util.CsvUtil;
import org.recap.util.SearchUtil;
import org.recap.util.UserAuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
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

    @Before
    public void setUp() throws Exception {
        when(request.getSession()).thenReturn(session);
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(searchRecordsController).build();
        Map searchRecordsMap = new HashMap();
    }


    @Test
    public void searchRecords() throws Exception{
        when(request.getSession()).thenReturn(session);
        SearchRecordsResponse searchRecordsResponse=new SearchRecordsResponse();
        when(userAuthUtil.authorizedUser(RecapConstants.SCSB_SHIRO_SEARCH_URL,(UsernamePasswordToken)session.getAttribute(UserManagement.USER_TOKEN))).thenReturn(true);
        when(searchUtil.requestSearchResults(searchRecordsRequest)).thenReturn(searchRecordsResponse);
        when(searchRecordsController.getUserAuthUtil()).thenReturn(userAuthUtil);
        when(searchRecordsController.getSearchUtil()).thenReturn(searchUtil);
        when(searchRecordsController.searchRecords(model,request)).thenCallRealMethod();
        String response = searchRecordsController.searchRecords(model,request);
        assertNotNull(response);
        assertEquals("searchRecords",response);
    }

    @Test
    public void search() throws Exception{
        SearchRecordsResponse searchRecordsResponse=new SearchRecordsResponse();
        searchRecordsRequest = new SearchRecordsRequest();
        when(searchUtil.requestSearchResults(searchRecordsRequest)).thenReturn(searchRecordsResponse);
        when(searchRecordsController.getSearchUtil()).thenReturn(searchUtil);
        when(searchRecordsController.search(searchRecordsRequest,bindingResult,model)).thenCallRealMethod();
        ModelAndView modelAndView = searchRecordsController.search(searchRecordsRequest,bindingResult,model);
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
        ModelAndView modelAndView = searchRecordsController.searchPrevious(searchRecordsRequest,bindingResult,model);
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
        ModelAndView modelAndView = searchRecordsController.searchNext(searchRecordsRequest,bindingResult,model);
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
        ModelAndView modelAndView = searchRecordsController.searchFirst(searchRecordsRequest,bindingResult,model);
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
        ModelAndView modelAndView = searchRecordsController.searchLast(searchRecordsRequest,bindingResult,model);
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
        when(searchRecordsController.newSearch(searchRecordsRequest,bindingResult,model)).thenCallRealMethod();
        ModelAndView modelAndView = searchRecordsController.newSearch(searchRecordsRequest,bindingResult,model);
        assertNotNull(modelAndView);
        assertEquals("searchRecords",modelAndView.getViewName());
    }

    @Test
    public void requestRecords() throws Exception{
        searchRecordsRequest = new SearchRecordsRequest();
        ModelAndView modelAndView1 = new ModelAndView();
        modelAndView1.setViewName("searchRecords");
        when(searchRecordsController.requestRecords(searchRecordsRequest,bindingResult,model, request, redirectAttributes)).thenReturn(modelAndView1);
        ModelAndView modelAndView = searchRecordsController.requestRecords(searchRecordsRequest,bindingResult,model, request, redirectAttributes);
        assertNotNull(modelAndView);
        assertEquals("searchRecords",modelAndView.getViewName());
    }

    @Test
    public void exportRecords() throws Exception {
        byte[] fileContent = searchRecordsControllerWired.exportRecords(buildRequestWithResultRows(), httpServletResponse, bindingResult, model);
        assertNotNull(fileContent);
    }

    @Test
    public void onPageSizeChange() throws Exception{
        SearchRecordsResponse searchRecordsResponse=new SearchRecordsResponse();
        searchRecordsRequest = new SearchRecordsRequest();
        when(searchUtil.requestSearchResults(searchRecordsRequest)).thenReturn(searchRecordsResponse);
        when(searchRecordsController.getSearchUtil()).thenReturn(searchUtil);
        when(searchRecordsController.onPageSizeChange(searchRecordsRequest,bindingResult,model)).thenCallRealMethod();
        ModelAndView modelAndView = searchRecordsController.onPageSizeChange(searchRecordsRequest,bindingResult,model);
        assertNotNull(modelAndView);
        assertEquals("searchRecords",modelAndView.getViewName());
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

}