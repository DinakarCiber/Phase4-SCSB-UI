package org.recap.model.usermanagement;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by dharmendrag on 28/12/16.
 */
@Getter
@Setter
public class UserDetailsForm {
    private Integer loginInstitutionId;
    private boolean superAdmin;
    private boolean recapUser;
    private boolean recapPermissionAllowed;
}
