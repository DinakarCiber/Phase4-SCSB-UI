package org.recap.model.reports;

import org.junit.Test;
import org.recap.BaseTestCase;

import java.util.ArrayList;

import static org.junit.Assert.assertNotNull;

/**
 * Created by hemalathas on 30/3/17.
 */
public class ReportsResponseUT extends BaseTestCase {

    @Test
    public void testReportsResponse(){
        ReportsResponse reportsResponse = new ReportsResponse();
        reportsResponse.setAccessionPrivatePulCount(1);
        reportsResponse.setAccessionPrivateCulCount(1);
        reportsResponse.setAccessionPrivateNyplCount(1);
        reportsResponse.setAccessionSharedPulCount(1);
        reportsResponse.setAccessionSharedCulCount(1);
        reportsResponse.setAccessionSharedNyplCount(1);
        reportsResponse.setAccessionOpenPulCount(1);
        reportsResponse.setAccessionOpenCulCount(1);
        reportsResponse.setAccessionOpenNyplCount(1);
        reportsResponse.setDeaccessionPrivatePulCount(1);
        reportsResponse.setDeaccessionPrivateCulCount(1);
        reportsResponse.setDeaccessionPrivateNyplCount(1);
        reportsResponse.setDeaccessionSharedPulCount(1);
        reportsResponse.setDeaccessionSharedCulCount(1);
        reportsResponse.setDeaccessionSharedNyplCount(1);
        reportsResponse.setDeaccessionOpenPulCount(1);
        reportsResponse.setDeaccessionOpenCulCount(1);
        reportsResponse.setDeaccessionOpenNyplCount(1);
        reportsResponse.setOpenPulCgdCount(1);
        reportsResponse.setOpenCulCgdCount(1);
        reportsResponse.setOpenNyplCgdCount(1);
        reportsResponse.setSharedPulCgdCount(1);
        reportsResponse.setSharedCulCgdCount(1);
        reportsResponse.setSharedNyplCgdCount(1);
        reportsResponse.setPrivatePulCgdCount(1);
        reportsResponse.setPrivateCulCgdCount(1);
        reportsResponse.setSharedNyplCgdCount(1);
        reportsResponse.setTotalRecordsCount("1");
        reportsResponse.setTotalPageCount(1);
        reportsResponse.setMessage("Success");
        reportsResponse.setDeaccessionItemResultsRows(new ArrayList<>());
        reportsResponse.setIncompleteTotalRecordsCount("1");
        reportsResponse.setIncompleteTotalPageCount(1);
        reportsResponse.setIncompleteReportResultsRows(new ArrayList<>());

        assertNotNull(reportsResponse.getAccessionPrivatePulCount());
        assertNotNull(reportsResponse.getAccessionPrivateCulCount());
        assertNotNull(reportsResponse.getAccessionPrivateNyplCount());
        assertNotNull(reportsResponse.getAccessionSharedPulCount());
        assertNotNull(reportsResponse.getAccessionSharedCulCount());
        assertNotNull(reportsResponse.getAccessionSharedNyplCount());
        assertNotNull(reportsResponse.getAccessionOpenPulCount());
        assertNotNull(reportsResponse.getAccessionOpenCulCount());
        assertNotNull(reportsResponse.getAccessionOpenNyplCount());
        assertNotNull(reportsResponse.getDeaccessionPrivatePulCount());
        assertNotNull(reportsResponse.getDeaccessionPrivateCulCount());
        assertNotNull(reportsResponse.getDeaccessionPrivateNyplCount());
        assertNotNull(reportsResponse.getDeaccessionSharedPulCount());
        assertNotNull(reportsResponse.getDeaccessionSharedCulCount());
        assertNotNull(reportsResponse.getDeaccessionSharedNyplCount());
        assertNotNull(reportsResponse.getDeaccessionOpenPulCount());
        assertNotNull(reportsResponse.getDeaccessionOpenCulCount());
        assertNotNull(reportsResponse.getDeaccessionOpenNyplCount());
        assertNotNull(reportsResponse.getOpenPulCgdCount());
        assertNotNull(reportsResponse.getOpenCulCgdCount());
        assertNotNull(reportsResponse.getOpenNyplCgdCount());
        assertNotNull(reportsResponse.getSharedPulCgdCount());
        assertNotNull(reportsResponse.getSharedCulCgdCount());
        assertNotNull(reportsResponse.getSharedNyplCgdCount());
        assertNotNull(reportsResponse.getPrivatePulCgdCount());
        assertNotNull(reportsResponse.getPrivateCulCgdCount());
        assertNotNull(reportsResponse.getPrivateNyplCgdCount());
        assertNotNull(reportsResponse.getTotalRecordsCount());
        assertNotNull(reportsResponse.getTotalPageCount());
        assertNotNull(reportsResponse.getMessage());
        assertNotNull(reportsResponse.getDeaccessionItemResultsRows());
        assertNotNull(reportsResponse.getIncompleteTotalRecordsCount());
        assertNotNull(reportsResponse.getIncompleteTotalPageCount());
        assertNotNull(reportsResponse.getIncompleteReportResultsRows());

    }

}