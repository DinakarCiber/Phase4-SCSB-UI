package org.recap.model.jpa;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * Created by dharmendrag on 29/11/16.
 */
@Entity
@Table(name="roles_t",schema="recap",catalog="")
@AttributeOverride(name = "id", column = @Column(name = "role_id"))
public class RoleEntity extends AbstractEntity<Integer>{

    @Column(name="role_name")
    private String roleName;

    @Column(name="role_description")
    private String roleDescription;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @Column(name = "CREATED_BY")
    private String createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "LAST_UPDATED_DATE")
    private Date lastUpdatedDate;

    @Column(name = "LAST_UPDATED_BY")
    private String lastUpdatedBy;

    @ElementCollection(targetClass = PermissionEntity.class)
    @JoinTable(name="role_permission_t",joinColumns = {
            @JoinColumn(name="role_id",referencedColumnName = "role_id")},
            inverseJoinColumns = {
                    @JoinColumn(name="permission_id",referencedColumnName = "permission_id")
            })
    private Set<PermissionEntity> permissions;


    @ElementCollection(targetClass = UsersEntity.class)
    @JoinTable(name="user_role_t",joinColumns = {
           @JoinColumn(name="role_id",referencedColumnName = "role_id")},
    inverseJoinColumns = {@JoinColumn(name="user_id",referencedColumnName = "user_id")})
    private Set<UsersEntity> users;

    /**
     * Gets users.
     *
     * @return the users
     */
    public Set<UsersEntity> getUsers() {
        return users;
    }

    /**
     * Sets users.
     *
     * @param users the users
     */
    public void setUsers(Set<UsersEntity> users) {
        this.users = users;
    }

    /**
     * Gets role name.
     *
     * @return the role name
     */
    public String getRoleName() {
        return roleName;
    }

    /**
     * Sets role name.
     *
     * @param roleName the role name
     */
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    /**
     * Gets role description.
     *
     * @return the role description
     */
    public String getRoleDescription() {
        return roleDescription;
    }

    /**
     * Sets role description.
     *
     * @param roleDescription the role description
     */
    public void setRoleDescription(String roleDescription) {
        this.roleDescription = roleDescription;
    }

    /**
     * Gets permissions.
     *
     * @return the permissions
     */
    public Set<PermissionEntity> getPermissions() {
        return permissions;
    }

    /**
     * Sets permissions.
     *
     * @param permissions the permissions
     */
    public void setPermissions(Set<PermissionEntity> permissions) {
        this.permissions = permissions;
    }

    /**
     * Gets created date.
     *
     * @return the created date
     */
    public Date getCreatedDate() {
        return createdDate;
    }

    /**
     * Sets created date.
     *
     * @param createdDate the created date
     */
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * Gets created by.
     *
     * @return the created by
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * Sets created by.
     *
     * @param createdBy the created by
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * Gets last updated date.
     *
     * @return the last updated date
     */
    public Date getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    /**
     * Sets last updated date.
     *
     * @param lastUpdatedDate the last updated date
     */
    public void setLastUpdatedDate(Date lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    /**
     * Gets last updated by.
     *
     * @return the last updated by
     */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /**
     * Sets last updated by.
     *
     * @param lastUpdatedBy the last updated by
     */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }
}
