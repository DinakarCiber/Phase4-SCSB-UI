package org.recap.util;

import org.apache.shiro.authc.UsernamePasswordToken;
import org.recap.RecapCommonConstants;
import org.recap.RecapConstants;
import org.recap.model.usermanagement.UserDetailsForm;
import org.recap.service.RestHeaderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dharmendrag on 4/1/17.
 */
@Service
public class UserAuthUtil {

    private static final Logger logger = LoggerFactory.getLogger(UserAuthUtil.class);

    @Value("${scsb.shiro}")
    private String scsbShiro;

    @Autowired
    RestHeaderService restHeaderService;

    public RestHeaderService getRestHeaderService(){
        return restHeaderService;
    }
    /**
     * Authenticate the used based on the UsernamePasswordToken.
     *
     * @param token the token
     * @return the map of the authorization values
     * @throws Exception the exception
     */
    public Map<String,Object> doAuthentication(UsernamePasswordToken token) throws Exception{
            RestTemplate restTemplate = new RestTemplate();
            HttpEntity<UsernamePasswordToken> requestEntity = new HttpEntity<>(token, getRestHeaderService().getHttpHeaders());
            return restTemplate.postForObject(scsbShiro + RecapConstants.SCSB_SHIRO_AUTHENTICATE_URL, requestEntity, HashMap.class);
    }

    /**
     * Authorize the login user.
     *
     * @param serviceURL the service url
     * @param token      the token
     * @return the boolean
     */
    public boolean authorizedUser(String serviceURL, UsernamePasswordToken token)
    {

        boolean statusResponse = false;
        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpEntity<UsernamePasswordToken> requestEntity = new HttpEntity<>(token,getRestHeaderService().getHttpHeaders());
            statusResponse = restTemplate.postForObject(scsbShiro + serviceURL, requestEntity, Boolean.class);
        }catch(Exception e){
            if(serviceURL.contains(RecapConstants.LOGOUT))
                logger.info(RecapConstants.LOG_USER_LOGOUT_SUCCUSS);
            else
                logger.error(token.getUsername()+RecapConstants.LOG_USER_NOT_VALID,e.getStackTrace());
        }
        return statusResponse;
    }

    /**
     * Gets logged-in user details.
     *
     * @param session         the session
     * @param recapPermission the recap permission
     * @return the user details
     */
    public UserDetailsForm getUserDetails(HttpSession session,String recapPermission)
    {
        UserDetailsForm userDetailsForm=new UserDetailsForm();
        userDetailsForm.setSuperAdmin((Boolean)session.getAttribute(RecapConstants.SUPER_ADMIN_USER));
        userDetailsForm.setRecapUser((Boolean)session.getAttribute(RecapConstants.RECAP_USER));
        userDetailsForm.setLoginInstitutionId((Integer)session.getAttribute(RecapConstants.USER_INSTITUTION));
        userDetailsForm.setRecapPermissionAllowed((Boolean) session.getAttribute(recapPermission));
        return userDetailsForm;
    }

    /**
     * Checks whether the user session is authenticated to support the request.
     * @param httpSession The current session.
     * @param roleUrl  The role url.
     * @return <code>true</code> if the user is successfully authenticated and
     *        <code>false</code> if not
     */
    public boolean isAuthenticated(HttpSession httpSession, String roleUrl) {
        return this.authorizedUser(roleUrl, (UsernamePasswordToken) httpSession.getAttribute(RecapConstants.USER_TOKEN));
    }

    /**
     * Checks whether the request has a valid session to serve it..
     * @param httpServletRequest The current request.
     * @param roleUrl  The role url.
     * @return <code>true</code> if the user is successfully authenticated and
     *        <code>false</code> if not
     */
    public boolean isAuthenticated(HttpServletRequest httpServletRequest, String roleUrl) {
        HttpSession httpSession = httpServletRequest.getSession(false);
        return this.isAuthenticated(httpSession, roleUrl);
    }


}
