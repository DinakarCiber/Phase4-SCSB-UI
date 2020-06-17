package org.recap.model.jpa;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.TemporalType;
import javax.persistence.Temporal;
import javax.persistence.JoinColumn;
import java.util.Date;

/**
 * Created by pvsubrah on 6/11/16.
 */
@Entity
@Table(name = "request_item_t", schema = "recap", catalog = "")
@AttributeOverride(name = "id", column = @Column(name = "REQUEST_ID"))
@Getter
@Setter
public class RequestItemEntity extends AbstractEntity<Integer> {

    @Column(name = "ITEM_ID")
    private Integer itemId;

    @Column(name = "REQUEST_TYPE_ID")
    private Integer requestTypeId;

    @Column(name = "REQUESTING_INST_ID")
    private Integer requestingInstitutionId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "REQ_EXP_DATE")
    private Date requestExpirationDate;

    @Column(name = "CREATED_BY")
    private String createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "LAST_UPDATED_DATE")
    private Date lastUpdatedDate;

    @Column(name = "STOP_CODE")
    private String stopCode;

    @Column(name = "REQUEST_STATUS_ID")
    private Integer requestStatusId;

    @Column(name = "NOTES")
    private String notes;

    @Column(name = "PATRON_ID")
    private String patronId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "REQUESTING_INST_ID", insertable = false, updatable = false)
    private InstitutionEntity institutionEntity;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "REQUEST_TYPE_ID", insertable = false, updatable = false)
    private RequestTypeEntity requestTypeEntity;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ITEM_ID", referencedColumnName = "ITEM_ID", insertable = false, updatable = false)
    private ItemEntity itemEntity;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "REQUEST_STATUS_ID", insertable = false, updatable = false)
    private RequestStatusEntity requestStatusEntity;

    @Column(name = "EMAIL_ID")
    private String emailId;
}
