package org.recap.util;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.recap.BaseTestCase;
import org.recap.model.search.SearchRecordsRequest;
import org.recap.model.search.SearchRecordsResponse;
import org.recap.model.search.SearchResultRow;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.validation.support.BindingAwareModelMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.spy;

/**
 * Created by rajeshbabuk on 3/1/17.
 */
@RunWith(MockitoJUnitRunner.class)

public class SearchUtilUT extends BaseTestCase {

    @InjectMocks
    private SearchUtil searchUtil = new SearchUtil();
    @Mock
    private RestTemplate restTemplate = new RestTemplate();
    @Mock
    private HelperUtil helperUtil;


    private String scsbUrl;

    @Before
    public void setUp() {
        ReflectionTestUtils.setField(searchUtil, "scsbUrl", "http://localhost:9090");
    }

    @Test
    public void requestSearchResults() throws Exception {
        SearchRecordsRequest searchRecordsRequest = new SearchRecordsRequest();
        searchRecordsRequest.setFieldName("");
        searchRecordsRequest.setFieldValue("");
        searchRecordsRequest.setTotalBibRecordsCount("1");
        searchRecordsRequest.setTotalItemRecordsCount("1");
        searchRecordsRequest.setTotalRecordsCount("1");
        searchRecordsRequest.setTotalPageCount(1);
        SearchRecordsResponse searchRecordsResponse = searchUtil.requestSearchResults(searchRecordsRequest);
        assertNotNull(searchRecordsResponse);
        List<SearchResultRow> searchResultRows = searchRecordsResponse.getSearchResultRows();
        assertNotNull(searchResultRows);
    }


    @Test
    public void test_method_searchRecord_should_return_ModelAndView() throws Exception {
        BindingAwareModelMap model = new BindingAwareModelMap();
        SearchRecordsRequest searchRecordsRequest = new SearchRecordsRequest();

        SearchRecordsResponse searchRecordsResponses = new SearchRecordsResponse();
        searchRecordsResponses.setSearchResultRows(buildSearchResultRow(5));

        ModelAndView view = new ModelAndView();
        Mockito.when(
                restTemplate.exchange(
                        ArgumentMatchers.anyString(),
                        ArgumentMatchers.any(HttpMethod.class),
                        ArgumentMatchers.any(),
                        ArgumentMatchers.<Class<SearchRecordsResponse>>any()))
                .thenReturn(ResponseEntity.ok().body(searchRecordsResponses));

        Mockito.when(searchUtil.searchRecord(searchRecordsRequest, model))
                .thenReturn(view);

        Mockito.when(searchUtil.requestSearchResults(searchRecordsRequest))
                .thenReturn(searchRecordsResponses);

        ModelAndView modelAndView = searchUtil.searchRecord(searchRecordsRequest,model);
        Assert.assertEquals(view, modelAndView);




    }


    public List<SearchResultRow> buildSearchResultRow(int count) {
        if(count > 25) {
            count = 25;
        }
        List<SearchResultRow> searchResultRows = new ArrayList<>();

        for (int i = 0; i <  count; i++ ) {
            SearchResultRow resultRow = new SearchResultRow();
            resultRow.setTitle("Groups in Process: An Introduction Vol " +  i);
            resultRow.setPublisher("Prentice-Hall");
            resultRow.setOwningInstitution("PUL");
            resultRow.setUseRestriction("No Restrictions");
            resultRow.setBarcode(randomBarCodeGenerator() + "");
            resultRow.setAvailability("Available");
            resultRow.setPublisherDate("1979");
            resultRow.setAuthor("Brooks");
            resultRow.setItemId(3);
            searchResultRows.add(resultRow);
        }
        return searchResultRows;


/*
        SearchItemResultRow itemResultRow = new SearchItemResultRow();
        itemResultRow.setCallNumber("K22.U83");
        itemResultRow.setChronologyAndEnum("v.7(1979-80");
        itemResultRow.setCustomerCode("PA");
        itemResultRow.setBarcode("329090");
        itemResultRow.setAvailability("Available");
        itemResultRow.setUseRestriction("No Restrictions");
        */

    }

    public int randomBarCodeGenerator() {
        Random randomNumGen  = new  Random();
        return randomNumGen.nextInt(2000 + 1) + 3000;
    }


}
