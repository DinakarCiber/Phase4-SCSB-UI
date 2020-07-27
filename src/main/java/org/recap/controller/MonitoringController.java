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
     * @param request the request
     * @return the string
     */
    @GetMapping("/monitoring")
    public String monitoring(Model model) {
        Monitoring monitoring = new Monitoring();
        monitoring.setLoggingUrl(RecapConstants.MONITORING_URL);
        model.addAttribute(RecapConstants.MONITORING_FORM, monitoring);
        model.addAttribute(RecapCommonConstants.TEMPLATE, RecapConstants.APP_MONITORING);
        return RecapConstants.VIEW_SEARCH_RECORDS;
    }

    @GetMapping("/logging")
    public String logging(Model model) {
        Monitoring monitoring = new Monitoring();
        monitoring.setMonitoringUrl(RecapConstants.LOGGING_URL);
        model.addAttribute(RecapConstants.LOGGING_FORM, monitoring);
        model.addAttribute(RecapCommonConstants.TEMPLATE, RecapConstants.APP_LOGGING);
        return RecapConstants.VIEW_SEARCH_RECORDS;
    }
}