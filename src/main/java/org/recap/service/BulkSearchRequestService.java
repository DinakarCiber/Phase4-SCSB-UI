package org.recap.service;

import org.apache.commons.lang3.StringUtils;
import org.recap.model.jpa.BulkRequestItemEntity;
import org.recap.model.jpa.InstitutionEntity;
import org.recap.model.search.BulkRequestForm;
import org.recap.repository.jpa.BulkRequestDetailsRepository;
import org.recap.repository.jpa.InstitutionDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 * Created by akulak on 25/9/17.
 */
@Service
public class BulkSearchRequestService {

    @Autowired
    private InstitutionDetailsRepository institutionDetailsRepository;

    @Autowired
    private BulkRequestDetailsRepository bulkRequestDetailsRepository;

    public Page<BulkRequestItemEntity> processSearchRequest(BulkRequestForm bulkRequestForm) {
        String requestId = StringUtils.isNotBlank(bulkRequestForm.getRequestIdSearch()) ? bulkRequestForm.getRequestIdSearch().trim() : bulkRequestForm.getRequestIdSearch();
        Integer bulkRequestId = 0;
        if(StringUtils.isNotBlank(requestId)){bulkRequestId = Integer.valueOf(requestId);}
        String bulkRequestName = StringUtils.isNotBlank(bulkRequestForm.getRequestNameSearch()) ? bulkRequestForm.getRequestNameSearch().trim() : bulkRequestForm.getRequestNameSearch();
        String patronId = StringUtils.isNotBlank(bulkRequestForm.getPatronBarcodeSearch()) ? bulkRequestForm.getPatronBarcodeSearch().trim() : bulkRequestForm.getPatronBarcodeSearch();
        String institution = StringUtils.isNotBlank(bulkRequestForm.getInstitution()) ? bulkRequestForm.getInstitution().trim() : bulkRequestForm.getInstitution();
        InstitutionEntity institutionEntity = institutionDetailsRepository.findByInstitutionCode(institution);
        Integer requestingInstitutionId = 0;
        if (institutionEntity != null){
            requestingInstitutionId = institutionEntity.getId();
        }
        Pageable pageable = PageRequest.of(bulkRequestForm.getPageNumber(), bulkRequestForm.getPageSize(), Sort.Direction.DESC,"id");

        if (StringUtils.isNotBlank(requestId) && StringUtils.isBlank(bulkRequestName) && StringUtils.isBlank(patronId)) {
            return StringUtils.isNotBlank(institution) ? bulkRequestDetailsRepository.findByIdAndRequestingInstitutionId(pageable,bulkRequestId,requestingInstitutionId) : bulkRequestDetailsRepository.findById(pageable,bulkRequestId);
        } else if (StringUtils.isBlank(requestId) && StringUtils.isNotBlank(bulkRequestName) && StringUtils.isBlank(patronId)) {
            return StringUtils.isNotBlank(institution) ? bulkRequestDetailsRepository.findByBulkRequestNameAndRequestingInstitutionId(pageable,bulkRequestName,requestingInstitutionId) : bulkRequestDetailsRepository.findByBulkRequestName(pageable,bulkRequestName);
        } else if (StringUtils.isBlank(requestId) && StringUtils.isBlank(bulkRequestName) && StringUtils.isNotBlank(patronId)) {
            return StringUtils.isNotBlank(institution) ? bulkRequestDetailsRepository.findByPatronIdAndRequestingInstitutionId(pageable,patronId,requestingInstitutionId) : bulkRequestDetailsRepository.findByPatronId(pageable,patronId);
        } else if (StringUtils.isNotBlank(requestId) && StringUtils.isNotBlank(bulkRequestName) && StringUtils.isBlank(patronId)) {
            return StringUtils.isNotBlank(institution) ? bulkRequestDetailsRepository.findByIdAndBulkRequestNameAndRequestingInstitutionId(pageable,bulkRequestId,bulkRequestName,requestingInstitutionId) : bulkRequestDetailsRepository.findByIdAndBulkRequestName(pageable,bulkRequestId,bulkRequestName);
        } else if (StringUtils.isBlank(requestId) && StringUtils.isNotBlank(bulkRequestName) && StringUtils.isNotBlank(patronId)) {
            return StringUtils.isNotBlank(institution) ? bulkRequestDetailsRepository.findByBulkRequestNameAndPatronIdAndRequestingInstitutionId(pageable,bulkRequestName,patronId,requestingInstitutionId) : bulkRequestDetailsRepository.findByBulkRequestNameAndPatronId(pageable,bulkRequestName,patronId);
        } else if (StringUtils.isNotBlank(requestId) && StringUtils.isBlank(bulkRequestName) && StringUtils.isNotBlank(patronId)) {
            return StringUtils.isNotBlank(institution) ? bulkRequestDetailsRepository.findByIdAndPatronIdAndRequestingInstitutionId(pageable,bulkRequestId,patronId,requestingInstitutionId) : bulkRequestDetailsRepository.findByIdAndPatronId(pageable,bulkRequestId,patronId);
        } else if (StringUtils.isNotBlank(requestId) && StringUtils.isNotBlank(bulkRequestName) && StringUtils.isNotBlank(patronId)) {
            return StringUtils.isNotBlank(institution) ? bulkRequestDetailsRepository.findByIdAndBulkRequestNameAndPatronIdAndRequestingInstitutionId(pageable,bulkRequestId,bulkRequestName,patronId,requestingInstitutionId) : bulkRequestDetailsRepository.findByIdAndBulkRequestNameAndPatronId(pageable,bulkRequestId,bulkRequestName,patronId);
        } else {
            return StringUtils.isNotBlank(institution) ? bulkRequestDetailsRepository.findByRequestingInstitutionId(pageable,requestingInstitutionId) : bulkRequestDetailsRepository.findAll(pageable);
        }
    }
}
