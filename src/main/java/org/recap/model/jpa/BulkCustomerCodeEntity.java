package org.recap.model.jpa;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by akulak on 20/10/17.
 */
@Entity
@Table(name = "bulk_customer_code_t", schema = "recap", catalog = "")
@AttributeOverride(name = "id", column = @Column(name = "BULK_CUSTOMER_CODE_ID"))
public class BulkCustomerCodeEntity extends AbstractEntity<Integer> {

    @Column(name = "CUSTOMER_CODE")
    private String customerCode;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "OWNING_INST_ID")
    private Integer owningInstitutionId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "OWNING_INST_ID", insertable = false, updatable = false)
    private InstitutionEntity institutionEntity;

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getOwningInstitutionId() {
        return owningInstitutionId;
    }

    public void setOwningInstitutionId(Integer owningInstitutionId) {
        this.owningInstitutionId = owningInstitutionId;
    }

    public InstitutionEntity getInstitutionEntity() {
        return institutionEntity;
    }

    public void setInstitutionEntity(InstitutionEntity institutionEntity) {
        this.institutionEntity = institutionEntity;
    }
}
