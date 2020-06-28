package org.recap.security;

import org.jasig.cas.client.authentication.AttributePrincipal;
import org.jasig.cas.client.proxy.ProxyGrantingTicketStorage;
import org.jasig.cas.client.proxy.ProxyRetriever;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.recap.BaseTestCase;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

import static org.junit.Assert.assertNotNull;

public class ReCAPCas20ServiceTicketValidatorUT extends BaseTestCase {
    @Mock
    ReCAPCas20ServiceTicketValidator reCAPCas20ServiceTicketValidator;
    @Mock
    ProxyGrantingTicketStorage proxyGrantingTicketStorage;
    @Mock
    ProxyRetriever proxyRetriever;
    @Mock
    HttpServletResponse httpServletResponse;

    @Test
    public void parseResponseFromServer() throws Exception{
        String response = httpServletResponse.toString();
        //reCAPCas20ServiceTicketValidator.parseResponseFromServer(response);
    }
    @Test
    public void setProxyCallbackUrl() throws Exception{
        String proxyCallbackUrl = "http://localhost:9095/requestItem/patronValidationBulkRequest";
        reCAPCas20ServiceTicketValidator.setProxyCallbackUrl(proxyCallbackUrl);
    }
    @Test
    public void extractCustomAttributes() throws Exception{
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<note>\n" +
                "  <to>Tove</to>\n" +
                "  <from>Jani</from>\n" +
                "  <heading>Reminder</heading>\n" +
                "  <body>Renew your book before existing due date!</body>\n" +
                "</note>";
        Mockito.doCallRealMethod().when(reCAPCas20ServiceTicketValidator).extractCustomAttributes(xml);
        Map<String, Object> result = reCAPCas20ServiceTicketValidator.extractCustomAttributes(xml);
        assertNotNull(result);
    }

    @Test
    public void setProxyGrantingTicketStorage(){
       // Mockito.doCallRealMethod().when(reCAPCas20ServiceTicketValidator).setProxyGrantingTicketStorage(proxyGrantingTicketStorage);
        reCAPCas20ServiceTicketValidator.setProxyGrantingTicketStorage(proxyGrantingTicketStorage);
    }
    @Test
    public void setProxyRetriever(){
       // Mockito.doCallRealMethod().when(reCAPCas20ServiceTicketValidator).setProxyRetriever(proxyRetriever);
        reCAPCas20ServiceTicketValidator.setProxyRetriever(proxyRetriever);
    }

    @Test
    public void getProxyCallbackUrl(){
        String proxyUrl = reCAPCas20ServiceTicketValidator.getProxyCallbackUrl();
    }
    @Test
    public void getProxyGrantingTicketStorage(){
        ProxyGrantingTicketStorage proxyGrantingTicketStorage = reCAPCas20ServiceTicketValidator.getProxyGrantingTicketStorage();
    }
    @Test
    public void getProxyRetriever(){
        ProxyRetriever proxyRetriever = reCAPCas20ServiceTicketValidator.getProxyRetriever();
    }

}
