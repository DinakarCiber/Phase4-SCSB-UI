package org.recap.repository.jpa;

import org.recap.model.jpa.BulkCustomerCodeEntity;

import java.util.List;

/**
 * Created by akulak on 20/10/17.
 */
public interface BulkCustomerCodeDetailsRepository extends BaseRepository<BulkCustomerCodeEntity> {

    List<BulkCustomerCodeEntity> findByOwningInstitutionId(Integer owningInstitutionId);
}
