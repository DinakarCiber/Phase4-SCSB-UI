package org.recap.model.jpa;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.CascadeType;
import javax.persistence.IdClass;
import javax.persistence.NamedNativeQuery;
import javax.persistence.ManyToOne;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import java.util.List;

/**
 * Created by pvsubrah on 6/10/16.
 */
@Getter
@Setter
@Entity
@Table(name = "bibliographic_t", schema = "recap", catalog = "")
@IdClass(BibliographicPK.class)
        @NamedNativeQuery(
                name = "BibliographicEntity.getNonDeletedHoldingsEntities",
                query = "SELECT HOLDINGS_T.* FROM HOLDINGS_T, BIBLIOGRAPHIC_HOLDINGS_T WHERE BIBLIOGRAPHIC_HOLDINGS_T.HOLDINGS_INST_ID = HOLDINGS_T.OWNING_INST_ID " +
                        "AND BIBLIOGRAPHIC_HOLDINGS_T.OWNING_INST_HOLDINGS_ID = HOLDINGS_T.OWNING_INST_HOLDINGS_ID AND HOLDINGS_T.IS_DELETED = 0 AND " +
                        "BIBLIOGRAPHIC_HOLDINGS_T.OWNING_INST_BIB_ID = :owningInstitutionBibId AND BIBLIOGRAPHIC_HOLDINGS_T.BIB_INST_ID = :owningInstitutionId",
                resultClass = HoldingsEntity.class)
        @NamedNativeQuery(
                name = "BibliographicEntity.getNonDeletedItemEntities",
                query = "SELECT ITEM_T.* FROM ITEM_T, BIBLIOGRAPHIC_ITEM_T WHERE BIBLIOGRAPHIC_ITEM_T.ITEM_INST_ID = ITEM_T.OWNING_INST_ID " +
                        "AND BIBLIOGRAPHIC_ITEM_T.OWNING_INST_ITEM_ID = ITEM_T.OWNING_INST_ITEM_ID AND ITEM_T.IS_DELETED = 0 AND ITEM_T.CATALOGING_STATUS = :catalogingStatus AND " +
                        "BIBLIOGRAPHIC_ITEM_T.OWNING_INST_BIB_ID = :owningInstitutionBibId AND BIBLIOGRAPHIC_ITEM_T.BIB_INST_ID = :owningInstitutionId",
                resultClass = ItemEntity.class)
        
public class BibliographicEntity extends BibliographicAbstractEntity {

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "OWNING_INST_ID", insertable = false, updatable = false)
    private InstitutionEntity institutionEntity;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "bibliographic_holdings_t", joinColumns = {
            @JoinColumn(name = "OWNING_INST_BIB_ID", referencedColumnName = "OWNING_INST_BIB_ID"),
            @JoinColumn(name = "BIB_INST_ID", referencedColumnName = "OWNING_INST_ID")},
            inverseJoinColumns = {
                    @JoinColumn(name = "OWNING_INST_HOLDINGS_ID", referencedColumnName = "OWNING_INST_HOLDINGS_ID"),
                    @JoinColumn(name = "HOLDINGS_INST_ID", referencedColumnName = "OWNING_INST_ID")})
    private List<HoldingsEntity> holdingsEntities;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "bibliographic_item_t", joinColumns = {
            @JoinColumn(name = "OWNING_INST_BIB_ID", referencedColumnName = "OWNING_INST_BIB_ID"),
            @JoinColumn(name = "BIB_INST_ID", referencedColumnName = "OWNING_INST_ID")},
            inverseJoinColumns = {
                    @JoinColumn(name = "OWNING_INST_ITEM_ID", referencedColumnName = "OWNING_INST_ITEM_ID"),
                    @JoinColumn(name = "ITEM_INST_ID", referencedColumnName = "OWNING_INST_ID")})
    private List<ItemEntity> itemEntities;

    /**
     * Instantiates a new Bibliographic entity object.
     */
    public BibliographicEntity() {
        super();
    }
}

