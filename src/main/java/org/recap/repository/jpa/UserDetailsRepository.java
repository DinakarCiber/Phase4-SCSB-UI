package org.recap.repository.jpa;

import org.recap.model.jpa.InstitutionEntity;
import org.recap.model.jpa.UsersEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by dharmendrag on 29/11/16.
 */
@Repository
public interface UserDetailsRepository extends BaseRepository<UsersEntity>,JpaSpecificationExecutor {


    /**
     * To get the user entity for the given login id.
     *
     * @param loginId the login id
     * @return the users entity
     */
    UsersEntity findByLoginId(String loginId);

    /**
     * To get the user entity for the given login id and institution.
     *
     * @param loginId       the login id
     * @param institutionId the institution id
     * @return the users entity
     */
    UsersEntity findByLoginIdAndInstitutionEntity(String loginId, InstitutionEntity institutionId);


    /**
     *To get pageable users entities.
     *
     *@param pageable the pageable
     * @return the page
     */
    Page<UsersEntity> findAll(Pageable pageable);

    /**
     * To get the pageable users entities for the given institution.
     *
     * @param institutionId the institution id
     * @param pageable      the pageable
     * @return the page
     */
    @Query(value = "select distinct users from UsersEntity users inner join users.userRole role where users.institutionId = :institutionId and role.roleName not in ('Super Admin')")
    Page<UsersEntity> findByInstitutionEntity(@Param("institutionId") Integer institutionId, Pageable pageable);

    /**
     * To get the pageable users entities for the given login id.
     *
     * @param loginId  the login id
     * @param pageable the pageable
     * @return the page
     */
    Page<UsersEntity> findByLoginId(String loginId, Pageable pageable);

    /**
     * To get the pageable users entities for the given login id and institution.
     *
     * @param loginId       the login id
     * @param institutionId the institution id
     * @param pageable      the pageable
     * @return the page
     */
    @Query(value = "select distinct users from UsersEntity users inner join users.userRole role where users.loginId = :loginId and users.institutionId = :institutionId and role.roleName not in ('Super Admin')")
    Page<UsersEntity> findByLoginIdAndInstitutionEntity(@Param("loginId") String loginId, @Param("institutionId") Integer institutionId, Pageable pageable);

    /**
     * To get the users entity for the given network id and institution.
     *
     * @param networkLoginId the network login id
     * @param institutionId  the institution id
     * @return the users entity
     */
    UsersEntity findByLoginIdAndInstitutionId(String networkLoginId, Integer institutionId);

    /**
     * To get the pageable users entities for the given email id.
     *
     * @param userEmailId the user email id
     * @param pageable    the pageable
     * @return the page
     */
    Page<UsersEntity> findByEmailId(String userEmailId, Pageable pageable);

    /**
     * To get the pageable user entities for the given email id and institution.
     *
     * @param userEmailId       the user email id
     * @param institutionEntity the institution entity
     * @param pageable          the pageable
     * @return the page
     */
    Page<UsersEntity> findByEmailIdAndInstitutionEntity(String userEmailId, InstitutionEntity institutionEntity, Pageable pageable);

    /**
     * To get the pageable users entities for the given network id and email id.
     *
     * @param searchNetworkId the search network id
     * @param userEmailId     the user email id
     * @param pageable        the pageable
     * @return the page
     */
    Page<UsersEntity> findByLoginIdAndEmailId(String searchNetworkId, String userEmailId, Pageable pageable);

    /**
     * To get the pageable users entities for the given login id, email id and institution.
     *
     * @param searchNetworkId   the search network id
     * @param userEmailId       the user email id
     * @param institutionEntity the institution entity
     * @param pageable          the pageable
     * @return the page
     */
    Page<UsersEntity> findByLoginIdAndEmailIdAndInstitutionEntity(String searchNetworkId, String userEmailId, InstitutionEntity institutionEntity, Pageable pageable);
}
