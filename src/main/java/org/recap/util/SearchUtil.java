package org.recap.util;

import org.apache.commons.collections.CollectionUtils;
import org.recap.RecapCommonConstants;
import org.recap.RecapConstants;
import org.recap.model.search.SearchRecordsRequest;
import org.recap.model.search.SearchRecordsResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by rajeshbabuk on 2/1/17.
 */
@Service
public class SearchUtil {

    private static final Logger logger = LoggerFactory.getLogger(SearchUtil.class);

    @Value("${scsb.url}")
    private String scsbUrl;

    /**
     *  This method makes a call to scsb microservice to get search results response for the given search criteria in the search UI page.
     *
     * @param searchRecordsRequest the search records request
     * @return the search records response
     */
    public SearchRecordsResponse requestSearchResults(SearchRecordsRequest searchRecordsRequest) {
        SearchRecordsResponse searchRecordsResponse = new SearchRecordsResponse();
        try {
            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders headers  = HelperUtil.getSwaggerHeaders();
            HttpEntity<SearchRecordsRequest> httpEntity = new HttpEntity<>(searchRecordsRequest, headers);

            ResponseEntity<SearchRecordsResponse> responseEntity = restTemplate.exchange(scsbUrl + RecapConstants.SCSB_SEARCH_SERVICE_URL, HttpMethod.POST, httpEntity, SearchRecordsResponse.class);
            searchRecordsResponse = responseEntity.getBody();
            return searchRecordsResponse;
        } catch (Exception e) {
            logger.error(RecapCommonConstants.LOG_ERROR,e);
            return searchRecordsResponse;
        }
    }

    public ModelAndView searchRecord(SearchRecordsRequest searchRecordsRequest, Model model) {
        searchRecordsRequest.resetPageNumber();
        searchAndSetResults(searchRecordsRequest);
        model.addAttribute( RecapCommonConstants.TEMPLATE,  RecapCommonConstants.SEARCH);
        return new ModelAndView(RecapConstants.VIEW_SEARCH_RECORDS, RecapConstants.VIEW_SEARCH_RECORDS_REQUEST, searchRecordsRequest);
    }

    public void searchAndSetResults(SearchRecordsRequest searchRecordsRequest) {
        searchRecordsRequest.reset();
        searchRecordsRequest.setSearchResultRows(null);
        searchRecordsRequest.setShowResults(true);
        searchRecordsRequest.setSelectAll(false);

        SearchRecordsResponse searchRecordsResponse = requestSearchResults(searchRecordsRequest);
        searchRecordsRequest.setSearchResultRows(searchRecordsResponse.getSearchResultRows());
        searchRecordsRequest.setTotalRecordsCount(searchRecordsResponse.getTotalRecordsCount());
        searchRecordsRequest.setTotalBibRecordsCount(searchRecordsResponse.getTotalBibRecordsCount());
        searchRecordsRequest.setTotalItemRecordsCount(searchRecordsResponse.getTotalItemRecordsCount());
        searchRecordsRequest.setTotalPageCount(searchRecordsResponse.getTotalPageCount());
        searchRecordsRequest.setShowTotalCount(searchRecordsResponse.isShowTotalCount());
        searchRecordsRequest.setErrorMessage(searchRecordsResponse.getErrorMessage());

        if(CollectionUtils.isEmpty(searchRecordsRequest.getSearchResultRows())) {
            searchRecordsRequest.setTotalRecordsCount(String.valueOf(0));
            searchRecordsRequest.setTotalBibRecordsCount(String.valueOf(0));
            searchRecordsRequest.setTotalItemRecordsCount(String.valueOf(0));
            if (searchRecordsRequest.getErrorMessage() == null) {
                searchRecordsRequest.setErrorMessage(RecapCommonConstants.SEARCH_RESULT_ERROR_NO_RECORDS_FOUND);
            }
        }

    }

}
