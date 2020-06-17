package org.recap.model.request;

import org.junit.Test;
import org.recap.BaseTestCase;

import java.util.Arrays;
import java.util.Date;

import static org.junit.Assert.assertNotNull;

/**
 * Created by hemalathas on 29/3/17.
 */
public class ItemRequestInformationUT extends BaseTestCase {

    @Test
    public void testItemRequestInformation(){

        ItemRequestInformation itemRequestInformation = new ItemRequestInformation();
        itemRequestInformation.setItemBarcodes(Arrays.asList("3325685456455546"));
        itemRequestInformation.setTitleIdentifier("test");
        itemRequestInformation.setItemOwningInstitution("PUL");
        itemRequestInformation.setPatronBarcode("452353588");
        itemRequestInformation.setEmailAddress("hemalatha.s@htcindia.com");
        itemRequestInformation.setRequestingInstitution("CUL");
        itemRequestInformation.setRequestType("Recall");
        itemRequestInformation.setDeliveryLocation("PB");
        itemRequestInformation.setRequestNotes("test");
        itemRequestInformation.setTrackingId("125");
        itemRequestInformation.setTitle("test");
        itemRequestInformation.setAuthor("john");
        itemRequestInformation.setCallNumber("X");
        itemRequestInformation.setStartPage("1");
        itemRequestInformation.setEndPage("10");
        itemRequestInformation.setChapterTitle("test");
        itemRequestInformation.setExpirationDate(new Date().toString());
        itemRequestInformation.setBibId("10");
        itemRequestInformation.setUsername("test");
        itemRequestInformation.setIssue("test");
        itemRequestInformation.setVolume("test");

        assertNotNull(itemRequestInformation.getItemBarcodes());
        assertNotNull(itemRequestInformation.getTitleIdentifier());
        assertNotNull(itemRequestInformation.getItemOwningInstitution());
        assertNotNull(itemRequestInformation.getPatronBarcode());
        assertNotNull(itemRequestInformation.getEmailAddress());
        assertNotNull(itemRequestInformation.getRequestingInstitution());
        assertNotNull(itemRequestInformation.getRequestType());
        assertNotNull(itemRequestInformation.getDeliveryLocation());
        assertNotNull(itemRequestInformation.getRequestNotes());
        assertNotNull(itemRequestInformation.getTrackingId());
        assertNotNull(itemRequestInformation.getTitle());
        assertNotNull(itemRequestInformation.getAuthor());
        assertNotNull(itemRequestInformation.getCallNumber());
        assertNotNull(itemRequestInformation.getStartPage());
        assertNotNull(itemRequestInformation.getEndPage());
        assertNotNull(itemRequestInformation.getChapterTitle());
        assertNotNull(itemRequestInformation.getExpirationDate());
        assertNotNull(itemRequestInformation.getBibId());
        assertNotNull(itemRequestInformation.getUsername());
        assertNotNull(itemRequestInformation.getIssue());
        assertNotNull(itemRequestInformation.getVolume());



    }

}