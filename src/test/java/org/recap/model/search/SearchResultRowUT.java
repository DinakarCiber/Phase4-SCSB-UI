package org.recap.model.search;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

/**
 * Created by premkb on 2/8/16.
 */
public class SearchResultRowUT {

    @Test
    public void testSearchResultRow() throws Exception {
        SearchResultRow searchResultRow = new SearchResultRow();
        setSearchResultRow(searchResultRow);
        assertNotNull(searchResultRow);
        assertEquals("TestTitle", searchResultRow.getTitle());
        assertEquals("TestAuthor", searchResultRow.getAuthor());
        assertEquals("NYPL", searchResultRow.getOwningInstitution());
        assertNotNull(searchResultRow.getBibId());
        assertNotNull(searchResultRow.getPublisher());
        assertNotNull(searchResultRow.getPublisherDate());
        assertNotNull(searchResultRow.getCustomerCode());
        assertNotNull(searchResultRow.getCollectionGroupDesignation());
        assertNotNull(searchResultRow.getUseRestriction());
        assertNotNull(searchResultRow.getBarcode());
        assertNotNull(searchResultRow.getSummaryHoldings());
        assertNotNull(searchResultRow.getAvailability());
        assertNotNull(searchResultRow.getLeaderMaterialType());
        assertNotNull(searchResultRow.isSelected());
        assertNotNull(searchResultRow.isShowItems());
        assertNotNull(searchResultRow.getSearchItemResultRows());
        assertNotNull(searchResultRow.isSelectAllItems());
        assertNotNull(searchResultRow.getItemId());
        assertNotNull(searchResultRow.getPatronBarcode());
        assertNotNull(searchResultRow.getRequestingInstitution());
        assertNotNull(searchResultRow.getDeliveryLocation());
        assertNotNull(searchResultRow.getRequestType());
        assertNotNull(searchResultRow.getRequestNotes());
        assertNotNull(searchResultRow.getRequestCreatedBy());
        assertNotNull(searchResultRow.getPatronEmailId());
        assertNotNull(searchResultRow.getCreatedDate());
        assertNotNull(searchResultRow.getStatus());
        assertNotNull(searchResultRow.getRequestId());
        assertNotNull(searchResultRow.isShowAllItems());

    }

    private void setSearchResultRow(SearchResultRow searchResultRow) {
        searchResultRow.setTitle("TestTitle");
        searchResultRow.setAuthor("TestAuthor");
        searchResultRow.setOwningInstitution("NYPL");
        searchResultRow.setBibId(1);
        searchResultRow.setPublisher("test");
        searchResultRow.setPublisherDate(new Date().toString());
        searchResultRow.setCustomerCode("PB");
        searchResultRow.setCollectionGroupDesignation("Open");
        searchResultRow.setUseRestriction("others");
        searchResultRow.setBarcode("332455546936368");
        searchResultRow.setSummaryHoldings("test");
        searchResultRow.setAvailability("available");
        searchResultRow.setLeaderMaterialType("test");
        searchResultRow.setSelected(true);
        searchResultRow.setShowItems(false);
        searchResultRow.setSelectAllItems(false);
        searchResultRow.setItemId(1);
        searchResultRow.setSearchItemResultRows(new ArrayList<>());
        searchResultRow.setShowAllItems(false);
        searchResultRow.setRequestId(1);
        searchResultRow.setPatronBarcode("452356654");
        searchResultRow.setRequestingInstitution("PUL");
        searchResultRow.setDeliveryLocation("PB");
        searchResultRow.setRequestType("Recall");
        searchResultRow.setRequestNotes("test");
        searchResultRow.setRequestCreatedBy("test");
        searchResultRow.setPatronEmailId("hemalatha.s@htcindia.com");
        searchResultRow.setCreatedDate(new Date());
        searchResultRow.setStatus("success");


    }
}
