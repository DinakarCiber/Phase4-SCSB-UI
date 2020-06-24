package org.recap.controller.version;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by hemalathas on 17/10/16.
 */
@Controller
public class VersionNumber {

    @Value("${version.number}")
    private String versionNumberService;

    /**
     * Sets version number service.
     *
     * @param versionNumberService the version number service
     */
    public void setVersionNumberService(String versionNumberService) {
        this.versionNumberService = versionNumberService;
    }

    /**
     * Gets the version number for scsb application which is configured in external application properties file.
     *
     * @return the version number service
     */
    @GetMapping(value = "/getVersionNumberService")
    @ResponseBody
    public String getVersionNumberService() {
        return versionNumberService;
    }
}
