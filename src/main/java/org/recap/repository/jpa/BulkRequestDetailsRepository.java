package org.recap.repository.jpa;

import org.recap.model.jpa.BulkRequestItemEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by akulak on 22/9/17.
 */

public interface BulkRequestDetailsRepository extends BaseRepository<BulkRequestItemEntity> {
    Page<BulkRequestItemEntity> findByIdAndRequestingInstitutionId(Pageable pageable, Integer id, Integer requestingInstitutionId);

    Page<BulkRequestItemEntity> findById(Pageable pageable, Integer id);

    Page<BulkRequestItemEntity> findByBulkRequestNameAndRequestingInstitutionId(Pageable pageable, String bulkRequestName, Integer requestingInstitutionId);

    Page<BulkRequestItemEntity> findByBulkRequestName(Pageable pageable, String bulkRequestName);

    Page<BulkRequestItemEntity> findByPatronIdAndRequestingInstitutionId(Pageable pageable, String patronId, Integer requestingInstitutionId);

    Page<BulkRequestItemEntity> findByPatronId(Pageable pageable, String patronId);

    Page<BulkRequestItemEntity> findByIdAndBulkRequestNameAndRequestingInstitutionId(Pageable pageable, Integer id, String bulkRequestName, Integer requestingInstitutionId);

    Page<BulkRequestItemEntity> findByIdAndBulkRequestName(Pageable pageable, Integer id, String bulkRequestName);

    Page<BulkRequestItemEntity> findByBulkRequestNameAndPatronIdAndRequestingInstitutionId(Pageable pageable, String bulkRequestName, String patronId, Integer requestingInstitutionId);

    Page<BulkRequestItemEntity> findByBulkRequestNameAndPatronId(Pageable pageable, String bulkRequestName, String patronId);

    Page<BulkRequestItemEntity> findByIdAndPatronIdAndRequestingInstitutionId(Pageable pageable, Integer id, String patronId, Integer requestingInstitutionId);

    Page<BulkRequestItemEntity> findByIdAndPatronId(Pageable pageable, Integer id, String patronId);

    Page<BulkRequestItemEntity> findByIdAndBulkRequestNameAndPatronIdAndRequestingInstitutionId(Pageable pageable, Integer id, String bulkRequestName, String patronId, Integer requestingInstitutionId);

    Page<BulkRequestItemEntity> findByIdAndBulkRequestNameAndPatronId(Pageable pageable, Integer id, String bulkRequestName, String patronId);

    Page<BulkRequestItemEntity> findByRequestingInstitutionId(Pageable pageable, Integer requestingInstitutionId);

}
