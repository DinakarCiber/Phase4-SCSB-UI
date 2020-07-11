package org.recap.util;

import org.recap.RecapCommonConstants;
import org.recap.RecapConstants;
import org.recap.model.reports.ReportsRequest;
import org.recap.model.reports.ReportsResponse;
import org.recap.model.search.ReportsForm;
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
 * Created by rajeshbabuk on 13/1/17.
 */
@Service
public class ReportsServiceUtil {

    private static final Logger logger = LoggerFactory.getLogger(SearchUtil.class);

    @Value("${scsb.url}")
    private String scsbUrl;

    /**
     * This method will call scsb microservice to get the reports response for the accession/deaccession reports.
     *
     * @param reportsForm the reports form
     * @return the reports response
     */
    public ReportsResponse requestAccessionDeaccessionCounts(ReportsForm reportsForm) {
        ReportsRequest reportsRequest = new ReportsRequest();
        reportsRequest.setAccessionDeaccessionFromDate(reportsForm.getAccessionDeaccessionFromDate());
        reportsRequest.setAccessionDeaccessionToDate(reportsForm.getAccessionDeaccessionToDate());
        reportsRequest.setOwningInstitutions(reportsForm.getOwningInstitutions());
        reportsRequest.setCollectionGroupDesignations(reportsForm.getCollectionGroupDesignations());
        return getReportsResponse(reportsRequest, RecapConstants.SCSB_REPORTS_ACCESSION_DEACCESSION_COUNTS_URL);
    }

    /**
     * This method will call scsb microservice to get the reports response for the collection group designation reports.
     *
     * @param reportsForm the reports form
     * @return the reports response
     */
    public ReportsResponse requestCgdItemCounts(ReportsForm reportsForm) {
        ReportsRequest reportsRequest = new ReportsRequest();
        reportsRequest.setOwningInstitutions(reportsForm.getOwningInstitutions());
        reportsRequest.setCollectionGroupDesignations(reportsForm.getCollectionGroupDesignations());
        ReportsResponse reportsResponse = new ReportsResponse();
        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers =  HelperUtil.getSwaggerHeaders();
            HttpEntity<ReportsRequest> httpEntity = new HttpEntity<>(reportsRequest, headers);

            ResponseEntity<ReportsResponse> responseEntity = restTemplate.exchange(scsbUrl + RecapConstants.SCSB_REPORTS_CGD_ITEM_COUNTS_URL, HttpMethod.POST, httpEntity, ReportsResponse.class);
            reportsResponse = responseEntity.getBody();
            return reportsResponse;
        } catch (Exception e) {
            logger.error(RecapCommonConstants.LOG_ERROR, e);
            reportsResponse.setMessage(e.getMessage());
            return reportsResponse;
        }
    }

    /**
     * This method will call scsb microservice to get the deaccessioned item results to display them as rows in the deaccesion reports UI page.
     *
     * @param reportsForm the reports form
     * @return the reports response
     */
    public ReportsResponse requestDeaccessionResults(ReportsForm reportsForm) {
        ReportsRequest reportsRequest = new ReportsRequest();
        reportsRequest.setAccessionDeaccessionFromDate(reportsForm.getAccessionDeaccessionFromDate());
        reportsRequest.setAccessionDeaccessionToDate(reportsForm.getAccessionDeaccessionToDate());
        reportsRequest.setDeaccessionOwningInstitution(reportsForm.getDeaccessionOwnInst());
        reportsRequest.setPageNumber(reportsForm.getPageNumber());
        reportsRequest.setPageSize(reportsForm.getPageSize());
        return getReportsResponse(reportsRequest, RecapConstants.SCSB_REPORTS_DEACCESSION_RESULTS_URL);
    }

    /**
     * This method will call scsb microservice to get the incomplete item results to display them as rows in the incomplete reports UI page.
     *
     * @param reportsForm the reports form
     * @return the reports response
     */
    public ReportsResponse requestIncompleteRecords(ReportsForm reportsForm) {
        ReportsRequest reportsRequest = new ReportsRequest();
        reportsRequest.setIncompleteRequestingInstitution(reportsForm.getIncompleteRequestingInstitution());
        reportsRequest.setIncompletePageNumber(reportsForm.getIncompletePageNumber());
        reportsRequest.setIncompletePageSize(reportsForm.getIncompletePageSize());
        reportsRequest.setExport(reportsForm.isExport());
        return getReportsResponse(reportsRequest, RecapConstants.SCSB_REPORTS_INCOMPLETE_RESULTS_URL);
    }

    private ReportsResponse getReportsResponse(ReportsRequest reportsRequest, String reportUrl) {
        ReportsResponse reportsResponse = new ReportsResponse();
        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = HelperUtil.getSwaggerHeaders();
            HttpEntity<ReportsRequest> httpEntity = new HttpEntity<>(reportsRequest, headers);
            ResponseEntity<ReportsResponse> responseEntity = restTemplate.exchange(scsbUrl + reportUrl, HttpMethod.POST, httpEntity, ReportsResponse.class);
            reportsResponse = responseEntity.getBody();
            return reportsResponse;
        } catch (Exception e) {
            logger.error(RecapCommonConstants.LOG_ERROR, e);
            reportsResponse.setMessage(e.getMessage());
            return reportsResponse;
        }
   }
}

