package org.recap.model.request;

import org.junit.Test;
import org.recap.BaseTestCase;

import java.util.Date;

import static org.junit.Assert.assertNotNull;

/**
 * Created by hemalathas on 29/3/17.
 */
public class ItemResponseInformationUT extends BaseTestCase {

    @Test
    public void testItemResponseInformation(){

        ItemResponseInformation itemResponseInformation = new ItemResponseInformation();
        itemResponseInformation.setPatronBarcode("485536469");
        itemResponseInformation.setItemBarcode("33245545455745466");
        itemResponseInformation.setRequestType("Recall");
        itemResponseInformation.setDeliveryLocation("PB");
        itemResponseInformation.setRequestingInstitution("CUL");
        itemResponseInformation.setBibliographicId("1235");
        itemResponseInformation.setExpirationDate(new Date().toString());
        itemResponseInformation.setItemId("123");
        itemResponseInformation.setScreenMessage("success");
        itemResponseInformation.setSuccess(true);
        itemResponseInformation.setEmailAddress("hemalatha.s@htcindia.com");
        itemResponseInformation.setStartPage(1);
        itemResponseInformation.setEndPage(10);
        itemResponseInformation.setTitleIdentifier("test");
        itemResponseInformation.setBibiid("748647");
        itemResponseInformation.setDueDate(new Date().toString());

        assertNotNull(itemResponseInformation.getPatronBarcode());
        assertNotNull(itemResponseInformation.getItemBarcode());
        assertNotNull(itemResponseInformation.getRequestType());
        assertNotNull(itemResponseInformation.getDeliveryLocation());
        assertNotNull(itemResponseInformation.getRequestingInstitution());
        assertNotNull(itemResponseInformation.getBibliographicId());
        assertNotNull(itemResponseInformation.getExpirationDate());
        assertNotNull(itemResponseInformation.getItemId());
        assertNotNull(itemResponseInformation.getScreenMessage());
        assertNotNull(itemResponseInformation.isSuccess());
        assertNotNull(itemResponseInformation.getEmailAddress());
        assertNotNull(itemResponseInformation.getStartPage());
        assertNotNull(itemResponseInformation.getEndPage());
        assertNotNull(itemResponseInformation.getTitleIdentifier());
        assertNotNull(itemResponseInformation.getBibiid());
        assertNotNull(itemResponseInformation.getDueDate());

    }

}