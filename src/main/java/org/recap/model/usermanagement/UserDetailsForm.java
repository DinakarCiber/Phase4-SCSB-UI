package org.recap.model.usermanagement;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by dharmendrag on 28/12/16.
 */
@Setter
public class UserDetailsForm {

    @Getter(AccessLevel.PUBLIC) private Integer loginInstitutionId;

    private boolean superAdmin;

    private boolean recapUser;

    private boolean recapPermissionAllowed;

    /**
     * Gets recap permission allowed.
     *
     * @return the boolean
     */
    public boolean isRecapPermissionAllowed() {
        return recapPermissionAllowed;
    }

    /**
     * Gets super admin.
     *
     * @return the boolean
     */
    public boolean isSuperAdmin() {
        return superAdmin;
    }

    /**
     * Gets recap user.
     *
     * @return the boolean
     */
    public boolean isRecapUser() {
        return recapUser;
    }

}
