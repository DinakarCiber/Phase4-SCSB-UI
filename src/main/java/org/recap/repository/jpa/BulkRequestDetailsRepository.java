package org.recap.repository.jpa;

import org.recap.model.jpa.BulkRequestItemEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by akulak on 22/9/17.
 */

public interface BulkRequestDetailsRepository extends JpaRepository<BulkRequestItemEntity,Integer> {
    Page<BulkRequestItemEntity> findByBulkRequestIdAndRequestingInstitutionId(Pageable pageable, Integer bulkRequestId, Integer requestingInstitutionId);

    Page<BulkRequestItemEntity> findByBulkRequestId(Pageable pageable, Integer bulkRequestId);

    Page<BulkRequestItemEntity> findByBulkRequestNameAndRequestingInstitutionId(Pageable pageable, String bulkRequestName, Integer requestingInstitutionId);

    Page<BulkRequestItemEntity> findByBulkRequestName(Pageable pageable, String bulkRequestName);

    Page<BulkRequestItemEntity> findByPatronIdAndRequestingInstitutionId(Pageable pageable, String patronId, Integer requestingInstitutionId);

    Page<BulkRequestItemEntity> findByPatronId(Pageable pageable, String patronId);

    Page<BulkRequestItemEntity> findByBulkRequestIdAndBulkRequestNameAndRequestingInstitutionId(Pageable pageable, Integer bulkRequestId, String bulkRequestName, Integer requestingInstitutionId);

    Page<BulkRequestItemEntity> findByBulkRequestIdAndBulkRequestName(Pageable pageable, Integer bulkRequestId, String bulkRequestName);

    Page<BulkRequestItemEntity> findByBulkRequestNameAndPatronIdAndRequestingInstitutionId(Pageable pageable, String bulkRequestName, String patronId, Integer requestingInstitutionId);

    Page<BulkRequestItemEntity> findByBulkRequestNameAndPatronId(Pageable pageable, String bulkRequestName, String patronId);

    Page<BulkRequestItemEntity> findByBulkRequestIdAndPatronIdAndRequestingInstitutionId(Pageable pageable, Integer bulkRequestId, String patronId, Integer requestingInstitutionId);

    Page<BulkRequestItemEntity> findByBulkRequestIdAndPatronId(Pageable pageable, Integer bulkRequestId, String patronId);

    Page<BulkRequestItemEntity> findByBulkRequestIdAndBulkRequestNameAndPatronIdAndRequestingInstitutionId(Pageable pageable, Integer bulkRequestId, String bulkRequestName, String patronId, Integer requestingInstitutionId);

    Page<BulkRequestItemEntity> findByBulkRequestIdAndBulkRequestNameAndPatronId(Pageable pageable, Integer bulkRequestId, String bulkRequestName, String patronId);

    Page<BulkRequestItemEntity> findByRequestingInstitutionId(Pageable pageable, Integer requestingInstitutionId);

}
