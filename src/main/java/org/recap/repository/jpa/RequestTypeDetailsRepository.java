package org.recap.repository.jpa;

import org.recap.model.jpa.RequestTypeEntity;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by rajeshbabuk on 28/10/16.
 */
public interface RequestTypeDetailsRepository extends BaseRepository<RequestTypeEntity> {

    /**
     * To get the request type entity for the given request type code.
     *
     * @param requestTypeCode the request type code
     * @return the request type entity
     */
    RequestTypeEntity findByRequestTypeCode(String requestTypeCode);

    /**
     * To get all the request type entities in scsb expect edd and borrow direct request type.
     *
     * @return the list
     */
    @Query(value="select requestType from RequestTypeEntity requestType where requestType.requestTypeCode NOT IN ('EDD','BORROW DIRECT')")
    List<RequestTypeEntity> findAllExceptEDDAndBorrowDirect();

    /**
     *To get all the request type entities in scsb expect borrow direct request type.
     *
     * @return the list
     */
    @Query(value="select requestType from RequestTypeEntity requestType where requestType.requestTypeCode NOT IN ('BORROW DIRECT')")
    List<RequestTypeEntity> findAllExceptBorrowDirect();
}
