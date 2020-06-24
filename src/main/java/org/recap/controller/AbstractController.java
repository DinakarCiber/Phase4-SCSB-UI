package org.recap.controller;

import org.recap.service.RestHeaderService;
import org.recap.util.RequestServiceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

public class AbstractController {

    @Value("${scsb.url}")
    private String scsbUrl;

    @Value("${scsb.shiro}")
    private String scsbShiro;

    @Autowired
    RestHeaderService restHeaderService;

    @Autowired
    private RequestServiceUtil requestServiceUtil;
    

    public RestHeaderService getRestHeaderService(){return restHeaderService;}

    public RequestServiceUtil getRequestServiceUtil() {
        return requestServiceUtil;
    }

    /**
     * Get rest template rest template.
     *
     * @return the rest template
     */
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }

    /**
     * Gets scsb shiro.
     *
     * @return the scsb shiro
     */
    public String getScsbShiro() {
        return scsbShiro;
    }

    /**
     * Gets scsb url.
     *
     * @return the scsb url
     */
    public String getScsbUrl() {
        return scsbUrl;
    }


}
