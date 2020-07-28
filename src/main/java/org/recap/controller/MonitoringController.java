package org.recap.controller;

import org.recap.RecapCommonConstants;
import org.recap.RecapConstants;
import org.recap.model.search.Monitoring;
import org.recap.model.search.MonitoringForm;
import org.recap.security.UserManagementService;
import org.recap.util.MonitoringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class MonitoringController extends AbstractController {

    private static final Logger logger = LoggerFactory.getLogger(MonitoringController.class);

    @Autowired
    private MonitoringUtil monitoringUtil;

    /**
     * Display All Monitoring url's
     *
     * @param model   the model
     * @return the string
     */
    @GetMapping("/monitoring")
    public String monitoring(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        boolean authenticated = getUserAuthUtil().isAuthenticated(request, RecapConstants.SCSB_SHIRO_MONITORING_URL);
        if (authenticated) {
            Monitoring monitoring = new Monitoring();
            model.addAttribute(RecapConstants.MONITORING_FORM, monitoring);
            model.addAttribute(RecapCommonConstants.TEMPLATE, RecapConstants.APP_MONITORING);
            return RecapConstants.VIEW_SEARCH_RECORDS;
        }
        return UserManagementService.unAuthorizedUser(session, "Monitoring", logger);
    }

    @GetMapping("/logging")
    public String logging(Model model,HttpServletRequest request) {
        boolean authenticated = getUserAuthUtil().isAuthenticated(request, RecapConstants.SCSB_SHIRO_LOGGING_URL);
        HttpSession session = request.getSession(false);
        if (authenticated) {
            Monitoring monitoring = new Monitoring();
            model.addAttribute(RecapConstants.LOGGING_FORM, monitoring);
            model.addAttribute(RecapCommonConstants.TEMPLATE, RecapConstants.APP_LOGGING);
            return RecapConstants.VIEW_SEARCH_RECORDS;
        }
        return UserManagementService.unAuthorizedUser(session, "Logging", logger);
    }
}