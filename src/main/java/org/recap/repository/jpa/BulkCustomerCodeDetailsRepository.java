package org.recap.repository.jpa;

import org.recap.model.jpa.BulkCustomerCodeEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by akulak on 20/10/17.
 */
public interface BulkCustomerCodeDetailsRepository extends CrudRepository<BulkCustomerCodeEntity,Integer> {

    List<BulkCustomerCodeEntity> findByOwningInstitutionId(Integer owningInstitutionId);
}
