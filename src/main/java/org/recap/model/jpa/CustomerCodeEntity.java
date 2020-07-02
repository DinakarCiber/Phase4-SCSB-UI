package org.recap.model.jpa;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import java.util.List;

/**
 * Created by rajeshbabuk on 18/10/16.
 */
@Entity
@Table(name = "customer_code_t", schema = "recap", catalog = "")
@AttributeOverride(name = "id", column = @Column(name = "CUSTOMER_CODE_ID"))
@Getter
@Setter
public class CustomerCodeEntity extends CustomerCodeAbstractEntity implements Comparable<CustomerCodeEntity> {

    @Column(name = "PWD_DELIVERY_RESTRICTIONS")
    private String pwdDeliveryRestrictions;

    @Column(name = "RECAP_DELIVERY_RESTRICTIONS")
    private String recapDeliveryRestrictions;

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
        if (getCustomerCode() != null ? !getCustomerCode().equals(customerCodeEntity.getCustomerCode()) : customerCodeEntity.getCustomerCode() != null)
            return false;
        if (getDescription() != null ? !getDescription().equals(customerCodeEntity.getDescription()) : customerCodeEntity.getDescription() != null)
            return false;
        return getOwningInstitutionId() != null ? getOwningInstitutionId().equals(customerCodeEntity.getOwningInstitutionId()) : customerCodeEntity.getOwningInstitutionId() == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (getCustomerCode() != null ? getCustomerCode().hashCode() : 0);
        result = 31 * result + (getDescription() != null ? getDescription().hashCode() : 0);
        result = 31 * result + (getOwningInstitutionId() != null ? getOwningInstitutionId().hashCode() : 0);
        return result;
    }
}