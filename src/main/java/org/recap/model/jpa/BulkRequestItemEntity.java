package org.recap.model.jpa;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.TemporalType;
import javax.persistence.JoinTable;
import javax.persistence.Temporal;
import javax.persistence.JoinColumn;
import java.util.Date;
import java.util.List;

/**
 * Created by akulak on 21/9/17.
 */
@Entity
@Table(name = "bulk_request_item_t", schema = "recap", catalog = "")
@AttributeOverride(name = "id", column = @Column(name = "BULK_REQUEST_ID"))
@Getter
@Setter
public class BulkRequestItemEntity extends AbstractEntity<Integer>{

    @Column(name = "BULK_REQUEST_NAME")
    private String bulkRequestName;

    @Column(name = "BULK_REQUEST_FILE_NAME")
    private String bulkRequestFileName;

    @Lob
    @Column(name = "BULK_REQUEST_FILE_DATA")
    private byte[] bulkRequestFileData;

    @Column(name = "REQUESTING_INST_ID")
    private Integer requestingInstitutionId;

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

    @Column(name = "NOTES")
    private String notes;

    @Column(name = "PATRON_ID")
    private String patronId;

    @Column(name = "EMAIL_ID")
    private String emailId;

    @Column(name = "REQUEST_STATUS")
    private String bulkRequestStatus;

    @OneToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "bulk_request_t",
            joinColumns = @JoinColumn(name = "BULK_REQUEST_ID"),
            inverseJoinColumns = @JoinColumn(name = "REQUEST_ID"))
    private List<RequestItemEntity> requestItemEntities;
}
