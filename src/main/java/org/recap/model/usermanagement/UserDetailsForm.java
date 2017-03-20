package org.recap.model.usermanagement;

/**
 * Created by dharmendrag on 28/12/16.
 */
public class UserDetailsForm {

    private Integer loginInstitutionId;

    private boolean superAdmin;

    private boolean recapUser;

    private boolean recapPermissionAllowed;

    public boolean isRecapPermissionAllowed() {
        return recapPermissionAllowed;
    }

    public void setRecapPermissionAllowed(boolean recapPermissionAllowed) {
        this.recapPermissionAllowed = recapPermissionAllowed;
    }

    public Integer getLoginInstitutionId() {
        return loginInstitutionId;
    }

    public void setLoginInstitutionId(Integer loginInstitutionId) {
        this.loginInstitutionId = loginInstitutionId;
    }

    public boolean isSuperAdmin() {
        return superAdmin;
    }

    public void setSuperAdmin(boolean superAdmin) {
        this.superAdmin = superAdmin;
    }

    public boolean isRecapUser() {
        return recapUser;
    }

    public void setRecapUser(boolean recapUser) {
        this.recapUser = recapUser;
    }
}
