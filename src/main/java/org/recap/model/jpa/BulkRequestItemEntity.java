package org.recap.model.jpa;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import java.util.List;

/**
 * Created by akulak on 21/9/17.
 */
@Entity
@Table(name = "bulk_request_item_t", schema = "recap", catalog = "")
@AttributeOverride(name = "id", column = @Column(name = "BULK_REQUEST_ID"))
@Getter
@Setter
public class BulkRequestItemEntity extends BulkRequestItemAbstractEntity {

    @OneToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "bulk_request_t",
            joinColumns = @JoinColumn(name = "BULK_REQUEST_ID"),
            inverseJoinColumns = @JoinColumn(name = "REQUEST_ID"))
    private List<RequestItemEntity> requestItemEntities;
}
