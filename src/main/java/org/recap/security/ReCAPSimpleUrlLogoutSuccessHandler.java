package org.recap.security;

import org.recap.RecapConstants;
import org.recap.util.HelperUtil;
import org.recap.util.UserAuthUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by sheiks on 20/01/17.
 */
public class ReCAPSimpleUrlLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler {


    private UserAuthUtil userAuthUtil;

    /**
     * Instantiates a new ReCAPSimpleUrlLogoutSuccessHandler.
     *
     * @param userAuthUtil the user auth util
     */
    public ReCAPSimpleUrlLogoutSuccessHandler(UserAuthUtil userAuthUtil) {
        this.userAuthUtil = userAuthUtil;
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Object attribute = request.getAttribute(RecapConstants.USER_TOKEN);
        super.onLogoutSuccess(request, response, authentication);
        HelperUtil.logoutFromShiro(attribute);
        request.removeAttribute(RecapConstants.USER_TOKEN);
    }

    @Override
    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response) {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        String institution = (String) ((ServletRequestAttributes) requestAttributes).getRequest().getAttribute(RecapConstants.RECAP_INSTITUTION_CODE);
        return HelperUtil.getLogoutUrl(institution);
    }
}
