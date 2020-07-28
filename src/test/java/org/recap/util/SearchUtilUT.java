package org.recap.util;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.recap.model.search.SearchRecordsRequest;
import org.recap.model.search.SearchRecordsResponse;
import org.recap.model.search.SearchResultRow;

import org.recap.spring.ApplicationContextProvider;
import org.springframework.context.ApplicationContext;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.validation.support.BindingAwareModelMap;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertNotNull;


/**
 * Created by rajeshbabuk on 3/1/17.
 */
@RunWith(MockitoJUnitRunner.class)
public class SearchUtilUT {

    @InjectMocks
    private SearchUtil searchUtil;

    @Mock
    ApplicationContext applicationContext;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(searchUtil, "scsbUrl", "http://localhost:9090");
        ApplicationContextProvider.getInstance().setApplicationContext(applicationContext);
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
        ModelAndView modelAndView = searchUtil.searchRecord(searchRecordsRequest,model);
        Assert.assertNotNull(modelAndView);
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
    }

    public int randomBarCodeGenerator() {
        Random randomNumGen  = new  Random();
        return randomNumGen.nextInt(2000 + 1) + 3000;
    }

}
