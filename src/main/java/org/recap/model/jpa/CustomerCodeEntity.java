package org.recap.model.jpa;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by rajeshbabuk on 18/10/16.
 */
@Entity
@Table(name = "customer_code_t", schema = "recap", catalog = "")
@AttributeOverride(name = "id", column = @Column(name = "CUSTOMER_CODE_ID"))
@Getter
@Setter
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
}