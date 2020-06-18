package org.recap.repository.jpa;

import org.recap.model.jpa.ItemStatusEntity;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Created by hemalathas on 22/6/16.
 */
@RepositoryRestResource(collectionResourceRel = "itemStatus", path = "itemStatus")
public interface ItemStatusDetailsRepository extends BaseRepository<ItemStatusEntity> {

    /**
     * To get the item status entity for the given status code.
     *
     * @param statusCode the status code
     * @return the item status entity
     */
    ItemStatusEntity findByStatusCode(String statusCode);
}
