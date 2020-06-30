package org.recap.security;

import org.junit.Test;
import org.recap.BaseTestCase;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertNotNull;


public class ReCAPAbstractUrlBasedTicketValidatorUT extends BaseTestCase {

    @Autowired
    ReCAPAbstractUrlBasedTicketValidator reCAPAbstractUrlBasedTicketValidator;

    @Test
    public void constructValidationUrl(){
        String ticket = "ticket";
        String serviceUrl = "http://localhost:9095/requestItem/";
        String validateUrl = reCAPAbstractUrlBasedTicketValidator.constructValidationUrl(ticket,serviceUrl);
        assertNotNull(validateUrl);
    }
    @Test
    public void getReCAPAbstractUrlBasedTicketValidator(){
        Map<String,String> customParameters = new HashMap<>();
        customParameters.put("b", "b");
        reCAPAbstractUrlBasedTicketValidator.setCasServerUrlPrefix("http");
        reCAPAbstractUrlBasedTicketValidator.setCustomParameters(customParameters);
        reCAPAbstractUrlBasedTicketValidator.setRenew(true);
        reCAPAbstractUrlBasedTicketValidator.setEncoding("Test");
        assertNotNull(reCAPAbstractUrlBasedTicketValidator.getCasServerUrlPrefix());
        assertNotNull(reCAPAbstractUrlBasedTicketValidator.getCustomParameters());
        assertNotNull(reCAPAbstractUrlBasedTicketValidator.getEncoding());
        assertNotNull(reCAPAbstractUrlBasedTicketValidator.getUrlSuffix());
    }
}
