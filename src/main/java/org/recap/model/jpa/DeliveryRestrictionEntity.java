package org.recap.model.jpa;

import javax.persistence.*;
import java.util.List;

/**
 * Created by harikrishnanv on 3/4/17.
 */
@Entity
@Table(name="delivery_restriction_cross_partner_t",schema="recap",catalog="")
public class DeliveryRestrictionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="DELIVERY_RESTRICTION_CROSS_PARTNER_ID")
    private Integer deliveryRestrictionId;

    @Column(name="DELIVERY_RESTRICTIONS")
    private String deliveryRestriction;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "INSTITUTION_ID", insertable = false, updatable = false)
    private InstitutionEntity institutionEntity;

    @ManyToMany(mappedBy = "deliveryRestrictionEntityList")
    private List<CustomerCodeEntity> customerCodeEntityList;

    /**
     * Gets delivery restriction id.
     *
     * @return the delivery restriction id
     */
    public Integer getDeliveryRestrictionId() {
        return deliveryRestrictionId;
    }

    /**
     * Sets delivery restriction id.
     *
     * @param deliveryRestrictionId the delivery restriction id
     */
    public void setDeliveryRestrictionId(Integer deliveryRestrictionId) {
        this.deliveryRestrictionId = deliveryRestrictionId;
    }

    /**
     * Gets delivery restriction.
     *
     * @return the delivery restriction
     */
    public String getDeliveryRestriction() {
        return deliveryRestriction;
    }

    /**
     * Sets delivery restriction.
     *
     * @param deliveryRestriction the delivery restriction
     */
    public void setDeliveryRestriction(String deliveryRestriction) {
        this.deliveryRestriction = deliveryRestriction;
    }

    /**
     * Gets institution entity.
     *
     * @return the institution entity
     */
    public InstitutionEntity getInstitutionEntity() {
        return institutionEntity;
    }

    /**
     * Sets institution entity.
     *
     * @param institutionEntity the institution entity
     */
    public void setInstitutionEntity(InstitutionEntity institutionEntity) {
        this.institutionEntity = institutionEntity;
    }

    /**
     * Gets customer code entity list.
     *
     * @return the customer code entity list
     */
    public List<CustomerCodeEntity> getCustomerCodeEntityList() {
        return customerCodeEntityList;
    }

    /**
     * Sets customer code entity list.
     *
     * @param customerCodeEntityList the customer code entity list
     */
    public void setCustomerCodeEntityList(List<CustomerCodeEntity> customerCodeEntityList) {
        this.customerCodeEntityList = customerCodeEntityList;
    }
}
