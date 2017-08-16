package org.recap.security;

import org.apache.shiro.authc.UsernamePasswordToken;
import org.recap.RecapConstants;
import org.recap.spring.ApplicationContextProvider;
import org.recap.util.HelperUtil;
import org.recap.util.UserAuthUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by dharmendrag on 11/1/17.
 */
@ComponentScan
public class SessionFilter implements Filter{


    UserAuthUtil userAuthUtil;

    private static final Logger logger = LoggerFactory.getLogger(SessionFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //Do nothing
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

        SecurityContext context = SecurityContextHolder.getContext();
        if(null != context) {
            Authentication authentication = context.getAuthentication();
            if(null != authentication && !HelperUtil.isAnonymousUser(authentication)) {
                HttpServletRequest request = (HttpServletRequest) req;
                HttpServletResponse response = (HttpServletResponse) res;
                Cookie cookie = new Cookie(RecapConstants.IS_USER_AUTHENTICATED, "Y");
                cookie.setMaxAge(-1);
                cookie.setHttpOnly(false);
                cookie.setPath("/");
                response.addCookie(cookie);

                UserInstitutionCache userInstitutionCache = HelperUtil.getBean(UserInstitutionCache.class);

                String requestedSessionId = request.getRequestedSessionId();

                String institutionCode = userInstitutionCache.getInstitutionForRequestSessionId(requestedSessionId);

                Cookie institutionCodeCookies = new Cookie(RecapConstants.LOGGED_IN_INSTITUTION, institutionCode);
                institutionCodeCookies.setMaxAge(-1);
                institutionCodeCookies.setHttpOnly(false);
                institutionCodeCookies.setPath("/");
                response.addCookie(institutionCodeCookies);

                HttpSession session=request.getSession(false);
                UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) session.getAttribute(RecapConstants.USER_TOKEN);
                if(usernamePasswordToken != null) {
                    boolean authenticated=getUserAuthUtil().authorizedUser(RecapConstants.SCSB_SHIRO_TOUCH_EXISTIN_SESSION_URL, usernamePasswordToken);
                }

            }
        }
        chain.doFilter(req, res);
    }

    @Override
    public void destroy() {
        //Do nothing
    }

    public UserAuthUtil getUserAuthUtil() {
        if(userAuthUtil == null) {
            userAuthUtil = ApplicationContextProvider.getInstance().getApplicationContext().getBean(UserAuthUtil.class);
        }
        return userAuthUtil;
    }
}
