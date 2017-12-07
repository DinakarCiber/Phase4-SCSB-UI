package org.recap.model.usermanagement;

import org.recap.model.jpa.UsersEntity;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by dharmendrag on 27/12/16.
 */
public interface UserRoleService {

    /**
     * Gets pageable user entities that are in scsb.
     *
     * @param userRoleForm the user role form
     * @param superAdmin   the super admin
     * @return the page
     */
    Page<UsersEntity> searchUsers(UserRoleForm userRoleForm, boolean superAdmin);

    /**
     * Gets pageable user entities based on the given network id.
     *
     * @param userRoleForm the user role form
     * @param superAdmin   the super admin
     * @return the page
     */
    Page<UsersEntity> searchByNetworkId(UserRoleForm userRoleForm, boolean superAdmin);

    /**
     * Gets all the roles that are in scsb except super admin role.
     *
     * @param superAdminRole the super admin role
     * @param superAdmin
     * @return the roles
     */
    List<Object> getRoles(Integer superAdminRole, boolean superAdmin);

    /**
     * Gets all the partners institution for super admin role.
     *
     * @param isSuperAdmin       the is super admin
     * @param loginInstitutionId the login institution id
     * @return the institutions
     */
    List<Object> getInstitutions(boolean isSuperAdmin, Integer loginInstitutionId);

    /**
     * Save a new user with role in scsb.
     *
     * @param userRoleForm the user role form
     * @return the users entity
     */
    UsersEntity saveNewUserToDB(UserRoleForm userRoleForm);

    /**
     * Save the edited user with role in scsb.
     *
     * @param userId          the user id
     * @param networkLoginId  the network login id
     * @param userDescription the user description
     * @param institutionId   the institution id
     * @param roleIds         the role ids
     * @param userEmailId     the user email id
     * @param userRoleForm    the user role form
     * @return the users entity
     */
    UsersEntity saveEditedUserToDB(Integer userId, String networkLoginId, String userDescription, Integer institutionId, List<Integer> roleIds, String userEmailId,UserRoleForm userRoleForm);

    /**
     * Gets pageable user entities based on the given user email id.
     *
     * @param userRoleForm the user role form
     * @param superAdmin   the super admin
     * @return the page
     */
    Page<UsersEntity> searchByUserEmailId(UserRoleForm userRoleForm, boolean superAdmin);

    /**
     * Gets pageable user entities based on the given network id and user email id.
     *
     * @param userRoleForm the user role form
     * @param superAdmin   the super admin
     * @return the page
     */
    Page<UsersEntity> searchByNetworkIdAndUserEmailId(UserRoleForm userRoleForm, boolean superAdmin);
}
