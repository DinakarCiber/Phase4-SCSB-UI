package org.recap.security;

import org.apache.shiro.authc.UsernamePasswordToken;
import org.recap.RecapConstants;
import org.recap.model.jpa.RoleEntity;
import org.recap.repository.jpa.RolesDetailsRepositorty;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

/**
 * Created by dharmendrag on 15/12/16.
 */
@Service
public class UserManagementService {

    /**
     * The Roles details repositorty.
     */
    @Autowired
    RolesDetailsRepositorty rolesDetailsRepositorty;

    /**
     * Un authorized logged-in user.
     *
     * @param session    the session
     * @param moduleName the module name
     * @param logger     the logger
     * @return the string
     */
    public static String unAuthorizedUser(HttpSession session, String moduleName, Logger logger)
    {
        final String loginScreen="redirect:/";
        logger.debug(moduleName+" authorization Rejected for :"+(UsernamePasswordToken)session.getAttribute(RecapConstants.USER_TOKEN));
        if(session!=null){
            session.invalidate();
        }
        return loginScreen;
    }

    /**
     * Get super admin role id integer.
     *
     * @return the integer
     */
    public Integer getSuperAdminRoleId(){
        RoleEntity roleEntity=rolesDetailsRepositorty.findByRoleName("Super Admin");
        return roleEntity.getId();
    }
}
