package org.recap.controller;

import lombok.Getter;
import lombok.Setter;
import org.recap.service.RestHeaderService;
import org.recap.util.RequestServiceUtil;
import org.recap.util.UserAuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

@Getter
@Setter
public class AbstractController {

    @Value("${scsb.url}")
    private String scsbUrl;

    @Value("${scsb.shiro}")
    private String scsbShiro;

    @Autowired
    private RestHeaderService restHeaderService;

    @Autowired
    private RequestServiceUtil requestServiceUtil;

    @Autowired
    private UserAuthUtil userAuthUtil;

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
