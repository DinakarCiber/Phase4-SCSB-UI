package org.recap.controller;

import org.apache.shiro.authc.UsernamePasswordToken;
import org.recap.RecapConstants;
import org.recap.model.usermanagement.UserDetailsForm;
import org.recap.model.usermanagement.UserRoleService;
import org.recap.security.UserManagementService;
import org.recap.util.UserAuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import javax.servlet.http.HttpSession;
import java.util.List;

public class AuthenticationController extends AbstractController {

    @Autowired
    private UserAuthUtil userAuthUtil;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private UserManagementService userManagementService;


    /**
     * Gets user auth util.
     *
     * @return the user auth util
     */
    public UserAuthUtil getUserAuthUtil() {
        return userAuthUtil;
    }

    /**
     * Sets user auth util.
     *
     * @param userAuthUtil the user auth util
     */
    public void setUserAuthUtil(UserAuthUtil userAuthUtil) {
        this.userAuthUtil = userAuthUtil;
    }

    /**
     * Gets user role service.
     *
     * @return the user role service
     */
    public UserRoleService getUserRoleService() {
        return userRoleService;
    }

    /**
     * Gets user management service.
     *
     * @return the user management service
     */
    public UserManagementService getUserManagementService() {
        return userManagementService;
    }


    public boolean isAuthenticated(HttpSession session, String roleURL) {
        return getUserAuthUtil().authorizedUser(roleURL, (UsernamePasswordToken) session.getAttribute(RecapConstants.USER_TOKEN));
    }

    public UserDetailsForm getUserDetails(HttpSession session, String privilege) {
        return userAuthUtil.getUserDetails(session, privilege);
    }

    public List<Object> getRoles(UserDetailsForm userDetailsForm) {
        return userRoleService.getRoles(getUserManagementService().getSuperAdminRoleId(), userDetailsForm.isSuperAdmin());
    }

    public List<Object> getInstitutions(UserDetailsForm userDetailsForm) {
        return userRoleService.getInstitutions(userDetailsForm.isSuperAdmin(), userDetailsForm.getLoginInstitutionId());
    }

}
