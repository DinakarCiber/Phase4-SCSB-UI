package org.recap.util;

import org.apache.commons.lang3.StringUtils;
import org.recap.RecapConstants;
import org.recap.model.jpa.InstitutionEntity;
import org.recap.model.jpa.RequestItemEntity;
import org.recap.model.search.RequestForm;
import org.recap.repository.jpa.InstitutionDetailsRepository;
import org.recap.repository.jpa.RequestItemDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Created by rajeshbabuk on 29/10/16.
 */
@Service
public class RequestServiceUtil {

    @Autowired
    private RequestItemDetailsRepository requestItemDetailsRepository;

    @Autowired
    private InstitutionDetailsRepository institutionDetailsRepository;

    /**
     * Based on the given search criteria in the request search UI page, this method builds the request search results to show them as rows in the request search UI page.
     *
     * @param requestForm the request form
     * @return the page
     */
    public Page<RequestItemEntity> searchRequests(RequestForm requestForm) {
        String patronBarcode = StringUtils.isNotBlank(requestForm.getPatronBarcode()) ? requestForm.getPatronBarcode().trim() : requestForm.getPatronBarcode();
        String itemBarcode = StringUtils.isNotBlank(requestForm.getItemBarcode()) ? requestForm.getItemBarcode().trim() : requestForm.getItemBarcode();
        String status = StringUtils.isNotBlank(requestForm.getStatus()) ? requestForm.getStatus().trim() : requestForm.getStatus();
        String institution = StringUtils.isNotBlank(requestForm.getInstitution()) ? requestForm.getInstitution().trim() : requestForm.getInstitution();
        InstitutionEntity institutionEntity = institutionDetailsRepository.findByInstitutionCode(institution);
        Optional<InstitutionEntity> institutionEntityOptional = Optional.ofNullable(institutionEntity);
        if(!institutionEntityOptional.isPresent()){
            institutionEntity=new InstitutionEntity();
            institutionEntity.setInstitutionId(0);
        }
        Pageable pageable = new PageRequest(requestForm.getPageNumber(), requestForm.getPageSize(), Sort.Direction.DESC, RecapConstants.REQUEST_ID);

        Page<RequestItemEntity> requestItemEntities;
        if (StringUtils.isNotBlank(patronBarcode) && StringUtils.isNotBlank(itemBarcode) && StringUtils.isNotBlank(status) && StringUtils.isNotBlank(institution)) {
            if (status.equals(RecapConstants.SEARCH_REQUEST_ACTIVE)) {
                requestItemEntities = requestItemDetailsRepository.findByPatronBarcodeAndItemBarcodeAndActiveAndInstitution(pageable, patronBarcode, itemBarcode,institutionEntity.getInstitutionId());
            }
            else {
                requestItemEntities = requestItemDetailsRepository.findByPatronBarcodeAndItemBarcodeAndStatusAndInstitution(pageable, patronBarcode, itemBarcode, status,institutionEntity.getInstitutionId());
            }
        }
        else if (StringUtils.isNotBlank(patronBarcode) && StringUtils.isNotBlank(itemBarcode) && StringUtils.isNotBlank(status) && StringUtils.isBlank(institution)) {
            if (status.equals(RecapConstants.SEARCH_REQUEST_ACTIVE)) {
                requestItemEntities = requestItemDetailsRepository.findByPatronBarcodeAndItemBarcodeAndActive(pageable, patronBarcode, itemBarcode,institutionEntity.getInstitutionId());
            }
            else {
                requestItemEntities = requestItemDetailsRepository.findByPatronBarcodeAndItemBarcodeAndStatus(pageable, patronBarcode, itemBarcode,status,institutionEntity.getInstitutionId());
            }
        }
        else if (StringUtils.isNotBlank(patronBarcode) && StringUtils.isNotBlank(itemBarcode) && StringUtils.isBlank(status) && StringUtils.isBlank(institution)) {
            requestItemEntities = requestItemDetailsRepository.findByPatronBarcodeAndItemBarcode(pageable, patronBarcode, itemBarcode,institutionEntity.getInstitutionId());
        }
        else if (StringUtils.isNotBlank(patronBarcode) && StringUtils.isNotBlank(itemBarcode) && StringUtils.isBlank(status) && StringUtils.isNotBlank(institution)) {
            requestItemEntities = requestItemDetailsRepository.findByPatronBarcodeAndItemBarcodeAndInstitution(pageable, patronBarcode, itemBarcode,institutionEntity.getInstitutionId());
        }
        else if (StringUtils.isNotBlank(patronBarcode) && StringUtils.isBlank(itemBarcode) && StringUtils.isNotBlank(status) && StringUtils.isBlank(institution)) {
            if (status.equals(RecapConstants.SEARCH_REQUEST_ACTIVE)) {
                requestItemEntities = requestItemDetailsRepository.findByPatronBarcodeAndActive(pageable, patronBarcode,institutionEntity.getInstitutionId());
            }
            else {
                requestItemEntities = requestItemDetailsRepository.findByPatronBarcodeAndStatus(pageable, patronBarcode, status,institutionEntity.getInstitutionId());
            }
        } else if (StringUtils.isNotBlank(patronBarcode) && StringUtils.isBlank(itemBarcode) && StringUtils.isNotBlank(status) && StringUtils.isNotBlank(institution)) {
            if (status.equals(RecapConstants.SEARCH_REQUEST_ACTIVE)) {
                requestItemEntities = requestItemDetailsRepository.findByPatronBarcodeAndActiveAndInstitution(pageable, patronBarcode,institutionEntity.getInstitutionId());
            }
            else {
                requestItemEntities = requestItemDetailsRepository.findByPatronBarcodeAndStatusAndInstitution(pageable, patronBarcode, status,institutionEntity.getInstitutionId());
            }
        }
        else if (StringUtils.isBlank(patronBarcode) && StringUtils.isNotBlank(itemBarcode) && StringUtils.isNotBlank(status) && StringUtils.isBlank(institution)) {
            if (status.equals(RecapConstants.SEARCH_REQUEST_ACTIVE)) {
                requestItemEntities = requestItemDetailsRepository.findByItemBarcodeAndActive(pageable, itemBarcode,institutionEntity.getInstitutionId());
            }
            else {
                requestItemEntities = requestItemDetailsRepository.findByItemBarcodeAndStatus(pageable, itemBarcode, status,institutionEntity.getInstitutionId());
            }
        }
        else if (StringUtils.isBlank(patronBarcode) && StringUtils.isNotBlank(itemBarcode) && StringUtils.isNotBlank(status) && StringUtils.isNotBlank(institution)) {
            if (status.equals(RecapConstants.SEARCH_REQUEST_ACTIVE)) {
                requestItemEntities = requestItemDetailsRepository.findByItemBarcodeAndActiveAndInstitution(pageable, itemBarcode,institutionEntity.getInstitutionId());
            }
            else {
                requestItemEntities = requestItemDetailsRepository.findByItemBarcodeAndStatusAndInstitution(pageable, itemBarcode, status,institutionEntity.getInstitutionId());
            }
        }
        else if (StringUtils.isNotBlank(patronBarcode) && StringUtils.isBlank(itemBarcode) && StringUtils.isBlank(status) && StringUtils.isBlank(institution)) {
            requestItemEntities = requestItemDetailsRepository.findByPatronBarcode(pageable, patronBarcode);
        }
        else if (StringUtils.isNotBlank(patronBarcode) && StringUtils.isBlank(itemBarcode) && StringUtils.isBlank(status) && StringUtils.isNotBlank(institution)) {
            requestItemEntities = requestItemDetailsRepository.findByPatronBarcodeAndInstitution(pageable, patronBarcode,institutionEntity.getInstitutionId());
        }
        else if (StringUtils.isBlank(patronBarcode) && StringUtils.isNotBlank(itemBarcode) && StringUtils.isBlank(status) && StringUtils.isBlank(institution)) {
            requestItemEntities = requestItemDetailsRepository.findByItemBarcode(pageable, itemBarcode,institutionEntity.getInstitutionId());
        }
        else if (StringUtils.isBlank(patronBarcode) && StringUtils.isNotBlank(itemBarcode) && StringUtils.isBlank(status) && StringUtils.isNotBlank(institution)) {
            requestItemEntities = requestItemDetailsRepository.findByItemBarcodeAndInstitution(pageable, itemBarcode,institutionEntity.getInstitutionId());
        }
        else if (StringUtils.isBlank(patronBarcode) && StringUtils.isBlank(itemBarcode) && StringUtils.isNotBlank(status) && StringUtils.isBlank(institution)) {
            if (status.equals(RecapConstants.SEARCH_REQUEST_ACTIVE)) {
                requestItemEntities = requestItemDetailsRepository.findAllActive(pageable);
            }
            else {
                requestItemEntities = requestItemDetailsRepository.findByStatus(pageable, status);
            }
        }
        else if (StringUtils.isBlank(patronBarcode) && StringUtils.isBlank(itemBarcode) && StringUtils.isNotBlank(status) && StringUtils.isNotBlank(institution)) {
            if (status.equals(RecapConstants.SEARCH_REQUEST_ACTIVE)) {
                requestItemEntities = requestItemDetailsRepository.findAllActiveAndInstitution(pageable,institutionEntity.getInstitutionId());
            }
            else {
                requestItemEntities = requestItemDetailsRepository.findByStatusAndInstitution(pageable, status,institutionEntity.getInstitutionId());
            }
        }
        else if(StringUtils.isBlank(patronBarcode) && StringUtils.isBlank(itemBarcode) && StringUtils.isBlank(status) && StringUtils.isNotBlank(institution)){
            requestItemEntities = requestItemDetailsRepository.findAllActiveAndInstitution(pageable,institutionEntity.getInstitutionId());
        }
        else{
            requestItemEntities = requestItemDetailsRepository.findAllActive(pageable);
        }
        return requestItemEntities;
    }
}
