package org.recap.repository.jpa;

import org.recap.model.jpa.CustomerCodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

/**
 * Created by rajeshbabuk on 18/10/16.
 */
@RepositoryRestResource(collectionResourceRel = "customerCode", path = "customerCode")
public interface CustomerCodeDetailsRepository extends JpaRepository<CustomerCodeEntity, Integer> {

    /**
     * To get the customer code entity for the given customer code.
     *
     * @param customerCode the customer code
     * @return the customer code entity
     */
    CustomerCodeEntity findByCustomerCode(@Param("customerCode") String customerCode);

    /**
     * To get the customer code entity for the given description.
     *
     * @param description the description
     * @return the customer code entity
     */
    CustomerCodeEntity findByDescription(@Param("description") String description);

    /**
     * To get list of customer code entities for the given list of customer codes.
     *
     * @param customerCodes the customer codes
     * @return the list
     */
    List<CustomerCodeEntity> findByCustomerCodeIn(List<String> customerCodes);

    /**
     * To get the customer code entity for the given customer code and owning institution.
     *
     * @param customerCode        the customer code
     * @param owningInstitutionId the owning institution id
     * @return the customer code entity
     */
    CustomerCodeEntity findByCustomerCodeAndOwningInstitutionId(@Param("customerCode") String customerCode,@Param("owningInstitutionId") Integer owningInstitutionId);

    /**
     * To get the customer code entity which has recap delivery restrictions EDD for the given customer code.
     *
     * @param customerCode the customer code
     * @return the customer code entity
     */
    @Query(value="select customerCode from CustomerCodeEntity customerCode where customerCode.customerCode =:customerCode and customerCode.recapDeliveryRestrictions LIKE ('%EDD%')")
    CustomerCodeEntity findByCustomerCodeAndRecapDeliveryRestrictionLikeEDD(@Param("customerCode") String customerCode);

    List<CustomerCodeEntity> findByOwningInstitutionId(Integer owningInstitutionId);
}
