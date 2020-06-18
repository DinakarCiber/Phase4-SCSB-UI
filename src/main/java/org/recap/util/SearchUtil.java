package org.recap.util;

import org.recap.RecapConstants;
import org.recap.model.search.SearchRecordsRequest;
import org.recap.model.search.SearchRecordsResponse;
import org.recap.spring.SwaggerAPIProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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

            HttpHeaders headers = new HttpHeaders();
            headers.set(RecapConstants.API_KEY, SwaggerAPIProvider.getInstance().getSwaggerApiKey());
            HttpEntity<SearchRecordsRequest> httpEntity = new HttpEntity<>(searchRecordsRequest, headers);

            ResponseEntity<SearchRecordsResponse> responseEntity = restTemplate.exchange(scsbUrl + RecapConstants.SCSB_SEARCH_SERVICE_URL, HttpMethod.POST, httpEntity, SearchRecordsResponse.class);
            searchRecordsResponse = responseEntity.getBody();
            return searchRecordsResponse;
        } catch (Exception e) {
            logger.error(RecapConstants.LOG_ERROR,e);
            return searchRecordsResponse;
        }
    }
}
