package org.recap.repository.jpa;

import org.recap.model.jpa.RequestStatusEntity;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by rajeshbabuk on 8/2/17.
 */
public interface RequestStatusDetailsRepository extends BaseRepository<RequestStatusEntity> {
    /**
     * To get all the request status entity in scsb expect processing request status.
     *
     * @return the request status entity list
     */
    @Query(value="select requestStatus from RequestStatusEntity requestStatus where requestStatus.requestStatusDescription NOT IN ('PROCESSING ...')")
    List<RequestStatusEntity> findAllExceptProcessing();

    /**
     * To get all the request status description in scsb expect processing request status.
     *
     * @return the list
     */
    @Query(value="select requestStatusDescription from RequestStatusEntity requestStatus where requestStatus.requestStatusDescription NOT IN ('PROCESSING ...')")
    List<String> findAllRequestStatusDescExceptProcessing();
}

