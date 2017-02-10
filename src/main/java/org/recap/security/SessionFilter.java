package org.recap.security;

import org.apache.shiro.authc.UsernamePasswordToken;
import org.recap.RecapConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ComponentScan;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by dharmendrag on 11/1/17.
 */
@ComponentScan
public class SessionFilter implements Filter{

    Logger logger = LoggerFactory.getLogger(SessionFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //Do nothing
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse httpResponse=(HttpServletResponse)response;
        try
            {
                HttpServletRequest httpRequest=(HttpServletRequest)request;
                HttpSession session=httpRequest.getSession();
                UsernamePasswordToken token=(UsernamePasswordToken) session.getAttribute(UserManagement.USER_TOKEN);
                if(token==null || token.getUsername()==null)
                {
                    throw new Exception("User Session Expired");
                }
                chain.doFilter(request,response);
            }catch(Exception e)
            {//if session time out redirects to login screen
                logger.error(RecapConstants.LOG_ERROR,e);
                httpResponse.sendRedirect("/");
            }
    }

    @Override
    public void destroy() {
        //Do nothing
    }
}
