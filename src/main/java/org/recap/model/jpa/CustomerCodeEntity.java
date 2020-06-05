package org.recap.model.jpa;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by rajeshbabuk on 18/10/16.
 */
@Entity
@Table(name = "customer_code_t", schema = "recap", catalog = "")
@AttributeOverride(name = "id", column = @Column(name = "CUSTOMER_CODE_ID"))
public class CustomerCodeEntity extends AbstractEntity<Integer> implements Comparable<CustomerCodeEntity> {

    @Column(name = "CUSTOMER_CODE")
    private String customerCode;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "OWNING_INST_ID")
    private Integer owningInstitutionId;

    @Column(name = "PWD_DELIVERY_RESTRICTIONS")
    private String pwdDeliveryRestrictions;

    @Column(name = "RECAP_DELIVERY_RESTRICTIONS")
    private String recapDeliveryRestrictions;

    @Column(name = "DELIVERY_RESTRICTIONS")
    private String deliveryRestrictions;


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "OWNING_INST_ID", insertable = false, updatable = false)
    private InstitutionEntity institutionEntity;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "cross_partner_mapping_t", joinColumns = {
            @JoinColumn(name = "CUSTOMER_CODE_ID", referencedColumnName = "CUSTOMER_CODE_ID")},
            inverseJoinColumns = {
                    @JoinColumn(name = "DELIVERY_RESTRICTION_CROSS_PARTNER_ID", referencedColumnName = "DELIVERY_RESTRICTION_CROSS_PARTNER_ID")})
    private List<DeliveryRestrictionEntity> deliveryRestrictionEntityList;

    /**
     * Gets customer code.
     *
     * @return the customer code
     */
    public String getCustomerCode() {
        return customerCode;
    }

    /**
     * Sets customer code.
     *
     * @param customerCode the customer code
     */
    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    /**
     * Gets description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets description.
     *
     * @param description the description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets owning institution id.
     *
     * @return the owning institution id
     */
    public Integer getOwningInstitutionId() {
        return owningInstitutionId;
    }

    /**
     * Sets owning institution id.
     *
     * @param owningInstitutionId the owning institution id
     */
    public void setOwningInstitutionId(Integer owningInstitutionId) {
        this.owningInstitutionId = owningInstitutionId;
    }

    /**
     * Gets delivery restrictions.
     *
     * @return the delivery restrictions
     */
    public String getDeliveryRestrictions() {
        return deliveryRestrictions;
    }

    /**
     * Sets delivery restrictions.
     *
     * @param deliveryRestrictions the delivery restrictions
     */
    public void setDeliveryRestrictions(String deliveryRestrictions) {
        this.deliveryRestrictions = deliveryRestrictions;
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

    @Override
    public int compareTo(CustomerCodeEntity customerCodeEntity) {
        if (null != this.getDescription() && null !=  customerCodeEntity && null != customerCodeEntity.getDescription()) {
            return this.getDescription().compareTo(customerCodeEntity.getDescription());
        }
        return 0;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;
        if (object == null || getClass() != object.getClass())
            return false;

        CustomerCodeEntity customerCodeEntity = (CustomerCodeEntity) object;

        if (id != null ? !id.equals(customerCodeEntity.id) : customerCodeEntity.id != null)
            return false;
        if (customerCode != null ? !customerCode.equals(customerCodeEntity.customerCode) : customerCodeEntity.customerCode != null)
            return false;
        if (description != null ? !description.equals(customerCodeEntity.description) : customerCodeEntity.description != null)
            return false;
        return owningInstitutionId != null ? owningInstitutionId.equals(customerCodeEntity.owningInstitutionId) : customerCodeEntity.owningInstitutionId == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (customerCode != null ? customerCode.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (owningInstitutionId != null ? owningInstitutionId.hashCode() : 0);
        return result;
    }

    /**
     * Gets delivery restriction entity list.
     *
     * @return the delivery restriction entity list
     */
    public List<DeliveryRestrictionEntity> getDeliveryRestrictionEntityList() {
        return deliveryRestrictionEntityList;
    }

    /**
     * Sets delivery restriction entity list.
     *
     * @param deliveryRestrictionEntityList the delivery restriction entity list
     */
    public void setDeliveryRestrictionEntityList(List<DeliveryRestrictionEntity> deliveryRestrictionEntityList) {
        this.deliveryRestrictionEntityList = deliveryRestrictionEntityList;
    }

    /**
     * Gets pwd delivery restrictions.
     *
     * @return the pwd delivery restrictions
     */
    public String getPwdDeliveryRestrictions() {
        return pwdDeliveryRestrictions;
    }

    /**
     * Sets pwd delivery restrictions.
     *
     * @param pwdDeliveryRestrictions the pwd delivery restrictions
     */
    public void setPwdDeliveryRestrictions(String pwdDeliveryRestrictions) {
        this.pwdDeliveryRestrictions = pwdDeliveryRestrictions;
    }

    /**
     * Gets recap delivery restrictions.
     *
     * @return the recap delivery restrictions
     */
    public String getRecapDeliveryRestrictions() {
        return recapDeliveryRestrictions;
    }

    /**
     * Sets recap delivery restrictions.
     *
     * @param recapDeliveryRestrictions the recap delivery restrictions
     */
    public void setRecapDeliveryRestrictions(String recapDeliveryRestrictions) {
        this.recapDeliveryRestrictions = recapDeliveryRestrictions;
    }
}