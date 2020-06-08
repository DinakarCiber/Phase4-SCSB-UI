package org.recap.model.jpa;



import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by dharmendrag on 29/11/16.
 */
@Cacheable(true)
@Entity
@Table(name="permissions_t",schema="recap",catalog="")
@AttributeOverride(name = "id", column = @Column(name = "permission_id"))
public class PermissionEntity extends AbstractEntity<Integer>{
    @Column(name="permission_name")
    private String permissionName;

    @Column(name="permission_description")
    private String permissionDesc;

    @ManyToMany(mappedBy ="permissions")
    private List<RoleEntity> roleEntityList;

    /**
     * Gets permission name.
     *
     * @return the permission name
     */
    public String getPermissionName() {
        return permissionName;
    }

    /**
     * Sets permission name.
     *
     * @param permissionName the permission name
     */
    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    /**
     * Gets permission desc.
     *
     * @return the permission desc
     */
    public String getPermissionDesc() {
        return permissionDesc;
    }

    /**
     * Sets permission desc.
     *
     * @param permissionDesc the permission desc
     */
    public void setPermissionDesc(String permissionDesc) {
        this.permissionDesc = permissionDesc;
    }

    /**
     * Gets role entity list.
     *
     * @return the role entity list
     */
    public List<RoleEntity> getRoleEntityList() {
        return roleEntityList;
    }

    /**
     * Sets role entity list.
     *
     * @param roleEntityList the role entity list
     */
    public void setRoleEntityList(List<RoleEntity> roleEntityList) {
        this.roleEntityList = roleEntityList;
    }





}
