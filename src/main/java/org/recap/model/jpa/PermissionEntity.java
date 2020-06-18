package org.recap.model.jpa;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Cacheable;
import javax.persistence.ManyToMany;
import java.util.List;

/**
 * Created by dharmendrag on 29/11/16.
 */
@Cacheable(true)
@Entity
@Table(name="permissions_t",schema="recap",catalog="")
@AttributeOverride(name = "id", column = @Column(name = "permission_id"))
@Getter
@Setter
public class PermissionEntity extends AbstractEntity<Integer>{
    @Column(name="permission_name")
    private String permissionName;

    @Column(name="permission_description")
    private String permissionDesc;

    @ManyToMany(mappedBy ="permissions")
    private List<RoleEntity> roleEntityList;

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
