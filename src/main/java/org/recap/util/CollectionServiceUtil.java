package org.recap.util;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.recap.RecapConstants;
import org.recap.model.deaccession.DeAccessionItem;
import org.recap.model.deaccession.DeAccessionRequest;
import org.recap.model.jpa.ItemChangeLogEntity;
import org.recap.model.jpa.ItemEntity;
import org.recap.model.search.BibliographicMarcForm;
import org.recap.repository.jpa.CustomerCodeDetailsRepository;
import org.recap.repository.jpa.ItemChangeLogDetailsRepository;
import org.recap.repository.jpa.ItemDetailsRepository;
import org.recap.service.RestHeaderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by rajeshbabuk on 19/10/16.
 */
@Service
public class CollectionServiceUtil {

    private static final Logger logger = LoggerFactory.getLogger(CollectionServiceUtil.class);

    @Value("${scsb.url}")
    private String scsbUrl;

    @Autowired
    private ItemChangeLogDetailsRepository itemChangeLogDetailsRepository;

    @Autowired
    private CustomerCodeDetailsRepository customerCodeDetailsRepository;

    @Autowired
    private ItemDetailsRepository itemDetailsRepository;

    @Autowired
    private RestHeaderService restHeaderService;

    public RestHeaderService getRestHeaderService(){
        return restHeaderService;
    }
    /**
     * Gets scsb url.
     *
     * @return the scsb url
     */
    public String getScsbUrl() {
        return scsbUrl;
    }

    /**
     * Get rest template rest template.
     *
     * @return the rest template
     */
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }

    /**
     * Gets logger.
     *
     * @return the logger
     */
    public static Logger getLogger() {
        return logger;
    }

    /**
     * Gets customer code details repository.
     *
     * @return the customer code details repository
     */
    public CustomerCodeDetailsRepository getCustomerCodeDetailsRepository() {
        return customerCodeDetailsRepository;
    }

    /**
     * Gets item details repository.
     *
     * @return the item details repository
     */
    public ItemDetailsRepository getItemDetailsRepository() {
        return itemDetailsRepository;
    }

    /**
     * Gets item change log details repository.
     *
     * @return the item change log details repository
     */
    public ItemChangeLogDetailsRepository getItemChangeLogDetailsRepository() {
        return itemChangeLogDetailsRepository;
    }

    /**
     * This method will call scsb microservice and passes the item information in bibliographic marc form  to update collection group designation for that item in scsb.
     *
     * @param bibliographicMarcForm the bibliographic marc form
     */
    public void updateCGDForItem(BibliographicMarcForm bibliographicMarcForm) {
        String statusResponse = null;
        try {
            HttpEntity requestEntity = new HttpEntity<>(getRestHeaderService().getHttpHeaders());

            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getScsbUrl() + RecapConstants.SCSB_UPDATE_CGD_URL)
                    .queryParam(RecapConstants.CGD_UPDATE_ITEM_BARCODE, bibliographicMarcForm.getBarcode())
                    .queryParam(RecapConstants.OWNING_INSTITUTION, bibliographicMarcForm.getOwningInstitution())
                    .queryParam(RecapConstants.OLD_CGD, bibliographicMarcForm.getCollectionGroupDesignation())
                    .queryParam(RecapConstants.NEW_CGD, bibliographicMarcForm.getNewCollectionGroupDesignation())
                    .queryParam(RecapConstants.CGD_CHANGE_NOTES, bibliographicMarcForm.getCgdChangeNotes())
                    .queryParam(RecapConstants.USER_NAME, bibliographicMarcForm.getUsername());

            ResponseEntity<String> responseEntity = getRestTemplate().exchange(builder.build().encode().toUri(), HttpMethod.GET, requestEntity, String.class);
            statusResponse = responseEntity.getBody();
            if (RecapConstants.SUCCESS.equals(statusResponse)) {
                bibliographicMarcForm.setSubmitted(true);
                bibliographicMarcForm.setMessage(RecapConstants.CGD_UPDATE_SUCCESSFUL);
            } else {
                bibliographicMarcForm.setErrorMessage(RecapConstants.CGD_UPDATE_FAILED + "-" + statusResponse.replace(RecapConstants.FAILURE + "-", ""));
            }
        } catch (Exception e) {
            logger.error(RecapConstants.LOG_ERROR,e);
            bibliographicMarcForm.setErrorMessage(RecapConstants.CGD_UPDATE_FAILED + "-" + e.getMessage());
        }
    }

    /**
     * Get de-accession request de-accession request.
     *
     * @return the de accession request
     */
    public DeAccessionRequest getDeAccessionRequest(){
        return new DeAccessionRequest();
    }

    /**
     * This method will call scsb microservice passes the item information in bibliographic marc form to deaccession that item in scsb.
     *
     * @param bibliographicMarcForm the bibliographic marc form
     */
    public void deAccessionItem(BibliographicMarcForm bibliographicMarcForm) {
        try {
            String itemBarcode = bibliographicMarcForm.getBarcode();
            String deliveryLocation = bibliographicMarcForm.getDeliveryLocation();
            String userName = bibliographicMarcForm.getUsername();
            DeAccessionRequest deAccessionRequest = getDeAccessionRequest();
            DeAccessionItem deAccessionItem = new DeAccessionItem();
            deAccessionItem.setItemBarcode(itemBarcode);
            deAccessionItem.setDeliveryLocation(deliveryLocation);
            deAccessionRequest.setDeAccessionItems(Arrays.asList(deAccessionItem));
            deAccessionRequest.setUsername(userName);
            HttpEntity<DeAccessionRequest> requestEntity = new HttpEntity<>(deAccessionRequest, getRestHeaderService().getHttpHeaders());
            Map<String, String> resultMap = getRestTemplate().postForObject(getScsbUrl() + RecapConstants.SCSB_DEACCESSION_URL, requestEntity, Map.class);
            String resultMessage = resultMap.get(itemBarcode);
            if (StringUtils.isNotBlank(resultMessage)) {
                if (resultMessage.contains(RecapConstants.SUCCESS)) {
                    Date lastUpdatedDate = new Date();
                    List<ItemEntity> itemEntities = getItemDetailsRepository().findByBarcode(itemBarcode);
                    saveItemChangeLogEntity(itemEntities, userName, lastUpdatedDate, RecapConstants.DEACCESSION, bibliographicMarcForm.getDeaccessionNotes());
                    bibliographicMarcForm.setSubmitted(true);
                    bibliographicMarcForm.setMessage(RecapConstants.DEACCESSION_SUCCESSFUL);
                } else if (resultMessage.contains(RecapConstants.REQUESTED_ITEM_DEACCESSIONED)) {
                    bibliographicMarcForm.setSubmitted(true);
                    String failureMessage = resultMessage.replace(RecapConstants.FAILURE + " -", "");
                    bibliographicMarcForm.setErrorMessage(RecapConstants.DEACCESSION_FAILED + " - " + failureMessage);
                } else if ((resultMessage.contains(RecapConstants.LAS_REJECTED) || resultMessage.contains(RecapConstants.LAS_SERVER_NOT_REACHABLE)) && StringUtils.isNotBlank(bibliographicMarcForm.getWarningMessage())) {
                    bibliographicMarcForm.setSubmitted(false);
                    String failureMessage = resultMessage.replace(RecapConstants.FAILURE + " -", "");
                    bibliographicMarcForm.setErrorMessage(RecapConstants.DEACCESSION_FAILED + " - " + failureMessage + " " + RecapConstants.DEACCESSION_ERROR_REQUEST_CANCEL);
                } else {
                    String failureMessage = resultMessage.replace(RecapConstants.FAILURE + " -", "");
                    bibliographicMarcForm.setErrorMessage(RecapConstants.DEACCESSION_FAILED + " - " + failureMessage);
                }
            }
        } catch (Exception e) {
            logger.error(RecapConstants.LOG_ERROR,e);
            bibliographicMarcForm.setErrorMessage(RecapConstants.DEACCESSION_FAILED + " - " + e.getMessage());
        }
    }

    private void saveItemChangeLogEntity(List<ItemEntity> itemEntities, String userName, Date lastUpdatedDate, String operationType, String notes) {
        if (CollectionUtils.isNotEmpty(itemEntities)) {
            for (ItemEntity itemEntity : itemEntities) {
                ItemChangeLogEntity itemChangeLogEntity = new ItemChangeLogEntity();
                itemChangeLogEntity.setUpdatedBy(userName);
                itemChangeLogEntity.setUpdatedDate(lastUpdatedDate);
                itemChangeLogEntity.setOperationType(operationType);
                itemChangeLogEntity.setRecordId(itemEntity.getItemId());
                itemChangeLogEntity.setNotes(notes);
                getItemChangeLogDetailsRepository().save(itemChangeLogEntity);
            }
        }
    }

}