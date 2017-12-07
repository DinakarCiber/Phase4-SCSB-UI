package org.recap.repository.jpa;

import org.recap.model.jpa.RoleEntity;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


/**
 * Created by dharmendrag on 13/12/16.
 */
public interface RolesDetailsRepositorty extends JpaRepository<RoleEntity, Integer> {

    /**
     * To get pageable role entities for the given role name.
     *
     * @param roleName the role name
     * @param pageable the pageable
     * @return the page
     */
    Page<RoleEntity> findByRoleName(String roleName, Pageable pageable);

    /**
     * To get role entity for the given role name.
     *
     * @param roleName the role name
     * @return the role entity
     */
    RoleEntity findByRoleName(String roleName);

    /**
     * To get pageable role entities expect super admin role.
     *
     * @param pageable the pageable
     * @return the roles without super admin
     */
    @Query(value = "select roles from RoleEntity roles where roles.roleName not in ('Super Admin')")
    Page<RoleEntity> getRolesWithoutSuperAdmin(Pageable pageable);

    /**
     * To get pageable role entities for the given role name.
     *
     * @param pageable the pageable
     * @param roleName the role name
     * @return the page
     */
    @Query(value = "select roles from RoleEntity roles where roles.roleName = :roleName")
    Page<RoleEntity> findByRoleName(Pageable pageable, @Param("roleName") String roleName);

    /**
     * To get the list role ids for the given permission id.
     *
     * @param permissionID the permission id
     * @return the role i dfor permission name
     */
    @Query(value = "select distinct(ROLE_ID) from role_permission_t where PERMISSION_ID = :permissionID",nativeQuery = true)
    List<Integer> getRoleIDforPermissionName(@Param("permissionID") Integer permissionID);

    /**
     *To get the pageable role entity for the given list of role ids.
     *
     * @param pageable the pageable
     * @param roleID   the role id
     * @return the page
     */
    @Query(value="select roles from RoleEntity roles where roles.roleId In(:roleID) and roles.roleName not in ('Super Admin')")
    Page<RoleEntity> findByRoleIDs(Pageable pageable, @Param("roleID") List<Integer> roleID);

    /**
     * To get the list of role entity for the given list of role ids.
     *
     * @param roleIds the role ids
     * @return the list
     */
    List<RoleEntity> findByRoleIdIn(List<Integer> roleIds);

    /**
     * To get the role entity for the given role id.
     *
     * @param roleId the role id
     * @return the role entity
     */
    RoleEntity findByRoleId(Integer roleId);

    /**
     * Get all roles except 'ReSubmit Request'.
     *
     * @return
     */
    @Query("select roles from RoleEntity roles where roles.roleName not in ('ReSubmit Request')")
    List<RoleEntity> findAllExceptReSubmitRole();
}
