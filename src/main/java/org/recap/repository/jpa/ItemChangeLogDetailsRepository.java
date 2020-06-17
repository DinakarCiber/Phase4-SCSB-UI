package org.recap.repository.jpa;

import org.recap.model.jpa.ItemChangeLogEntity;

/**
 * Created by rajeshbabuk on 18/10/16.
 */
public interface ItemChangeLogDetailsRepository extends BaseRepository<ItemChangeLogEntity> {

     /**
      * To get the item change log entity for the given record id and operation type.
      *
      * @param recordId      the record id
      * @param operationType the operation type
      * @return the item change log entity
      */
     ItemChangeLogEntity findByRecordIdAndOperationType(Integer recordId, String operationType);
}
