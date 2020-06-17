package org.recap.repository.jpa;

import org.recap.model.jpa.PermissionEntity;


/**
 * Created by dharmendrag on 13/12/16.
 */
public interface PermissionsDetailsRepository extends BaseRepository<PermissionEntity> {

    /**
     * To get the permission entity for the given permission name.
     *
     * @param permissionName the permission name
     * @return the permission entity
     */
    PermissionEntity findByPermissionName(String permissionName);

}
