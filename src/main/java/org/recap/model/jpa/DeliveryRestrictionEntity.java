package org.recap.model.jpa;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;

/**
 * Created by harikrishnanv on 3/4/17.
 */
@Getter
@Setter
@Entity
@Table(name="delivery_restriction_cross_partner_t",schema="recap",catalog="")
@AttributeOverride(name = "id", column = @Column(name = "DELIVERY_RESTRICTION_CROSS_PARTNER_ID"))
public class DeliveryRestrictionEntity extends DeliveryRestrictionAbstractEntity {

    @ManyToMany(mappedBy = "deliveryRestrictionEntityList")
    private List<CustomerCodeEntity> customerCodeEntityList;

}
