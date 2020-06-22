package org.recap.filter;

import org.apache.commons.lang3.StringUtils;
import org.recap.RecapConstants;
import org.recap.security.UserInstitutionCache;
import org.recap.util.HelperUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by sheiks on 23/01/17.
 */
public class ReCAPInstitutionFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(ReCAPInstitutionFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String institutionCode = HelperUtil.getInstitutionFromRequest(request);

        UserInstitutionCache userInstitutionCache = HelperUtil.getBean(UserInstitutionCache.class);

        String requestedSessionId = request.getSession().getId();

        String requestURI = request.getRequestURI();
        if(StringUtils.equals(requestURI, "/home")) {

            Cookie[] cookies = request.getCookies();
            cookiesOuter: for(Cookie cookie : cookies) {
                if(StringUtils.equals(cookie.getName(), RecapConstants.IS_USER_AUTHENTICATED) && StringUtils.equals(cookie.getValue(), "Y")) {
                    for(Cookie innerCookies : cookies) {
                        if(StringUtils.equals(innerCookies.getName(), RecapConstants.LOGGED_IN_INSTITUTION)) {
                            institutionCode = innerCookies.getValue();
                            cookie.setValue(null);
                            cookie.setMaxAge(0);
                            response.addCookie(cookie);

                            innerCookies.setValue(null);
                            innerCookies.setMaxAge(0);
                            response.addCookie(innerCookies);

                            break cookiesOuter;
                        }
                    }
                }
            }

            if(StringUtils.isNotBlank(institutionCode)) {
                userInstitutionCache.removeSessionId(requestedSessionId);
                String logoutUrl = HelperUtil.getLogoutUrl(institutionCode);
                try {
                    response.sendRedirect(logoutUrl);
                } catch (IOException e) {
                  log.error("Error While Redirect",e);
                }
            } else {

                forwardChaining(request, response, filterChain, userInstitutionCache, requestedSessionId);

            }

        } else {
            if(StringUtils.isNotBlank(institutionCode)) {
                userInstitutionCache.addRequestSessionId(requestedSessionId, institutionCode);
            }

            forwardChaining(request, response, filterChain, userInstitutionCache, requestedSessionId);
        }
    }

    private void forwardChaining(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain, UserInstitutionCache userInstitutionCache, String requestedSessionId) throws IOException, ServletException {
        String institutionCode = userInstitutionCache.getInstitutionForRequestSessionId(requestedSessionId);
        if(StringUtils.isNotBlank(institutionCode)) {
            request.setAttribute(RecapConstants.RECAP_INSTITUTION_CODE, institutionCode);
        }
        filterChain.doFilter(request, response);
    }

}
