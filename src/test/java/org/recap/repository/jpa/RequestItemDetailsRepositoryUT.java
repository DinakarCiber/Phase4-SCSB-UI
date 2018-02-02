package org.recap.repository.jpa;

import org.junit.Test;
import org.recap.BaseTestCase;
import org.recap.RecapConstants;
import org.recap.model.jpa.BibliographicEntity;
import org.recap.model.jpa.HoldingsEntity;
import org.recap.model.jpa.ItemEntity;
import org.recap.model.jpa.RequestItemEntity;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by rajeshbabuk on 28/10/16.
 */
public class RequestItemDetailsRepositoryUT extends BaseTestCase {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    RequestTypeDetailsRepository requestTypeDetailsRepository;

    // Test for Physical Requests

    @Test
    public void checkGetPhysicalPrivateRequestCountsForPUL() throws Exception{
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date fromDate = simpleDateFormat.parse("2016-12-30 00:00:00");
        Date toDate = simpleDateFormat.parse("2020-12-31 23:59:59");
        Integer[] cgdId = {3};
        List<Integer> cgdIdList = new ArrayList<>(Arrays.asList(cgdId));
        long countBefore = requestItemDetailsRepository.getPhysicalAndEDDCounts(fromDate,toDate,Arrays.asList(1),cgdIdList,Arrays.asList(2,3),Arrays.asList(RecapConstants.REQUEST_STATUS_RETRIEVAL_ORDER_PLACED,RecapConstants.REQUEST_STATUS_REFILED,RecapConstants.REQUEST_STATUS_CANCELED),Arrays.asList(RecapConstants.REQUEST_TYPE_RETRIEVAL));
        assertNotNull(countBefore);
        BibliographicEntity bibliographicEntity = saveBibHoldingItemEntity(50000, 1, 3, "PUL");
        ItemEntity itemEntity = bibliographicEntity.getItemEntities().get(0);
        saveRequestEntity(itemEntity.getItemId(),1,1,String.valueOf(new Random().nextInt()));
        long countAfter = requestItemDetailsRepository.getPhysicalAndEDDCounts(fromDate,toDate,Arrays.asList(1),cgdIdList,Arrays.asList(2,3),Arrays.asList(RecapConstants.REQUEST_STATUS_RETRIEVAL_ORDER_PLACED,RecapConstants.REQUEST_STATUS_REFILED,RecapConstants.REQUEST_STATUS_CANCELED),Arrays.asList(RecapConstants.REQUEST_TYPE_RETRIEVAL));
        assertNotNull(countAfter);
        assertEquals(countBefore+1,countAfter);
    }

    @Test
    public void checkGetPhysicalSharedAndOpenRequestCountsForPUL() throws Exception{
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date fromDate = simpleDateFormat.parse("2016-12-30 00:00:00");
        Date toDate = simpleDateFormat.parse("2020-12-31 23:59:59");
        Integer[] cgdId = {1,2};
        List<Integer> cgdIdList = new ArrayList<>(Arrays.asList(cgdId));
        long countBefore = requestItemDetailsRepository.getPhysicalAndEDDCounts(fromDate,toDate,Arrays.asList(1),cgdIdList,Arrays.asList(2,3),Arrays.asList(RecapConstants.REQUEST_STATUS_RETRIEVAL_ORDER_PLACED,RecapConstants.REQUEST_STATUS_REFILED,RecapConstants.REQUEST_STATUS_CANCELED),Arrays.asList(RecapConstants.REQUEST_TYPE_RETRIEVAL));
        assertNotNull(countBefore);
        BibliographicEntity bibliographicEntity = saveBibHoldingItemEntity(50000, 1, 1, "PUL");
        ItemEntity itemEntity = bibliographicEntity.getItemEntities().get(0);
        saveRequestEntity(itemEntity.getItemId(),1,1,String.valueOf(new Random().nextInt()));
        long countAfter = requestItemDetailsRepository.getPhysicalAndEDDCounts(fromDate,toDate,Arrays.asList(1),cgdIdList,Arrays.asList(2,3),Arrays.asList(RecapConstants.REQUEST_STATUS_RETRIEVAL_ORDER_PLACED,RecapConstants.REQUEST_STATUS_REFILED,RecapConstants.REQUEST_STATUS_CANCELED),Arrays.asList(RecapConstants.REQUEST_TYPE_RETRIEVAL));
        assertNotNull(countAfter);
        assertEquals(countBefore+1,countAfter);
    }

    @Test
    public void checkGetPhysicalPrivateRequestCountsForCUL() throws Exception{
        BibliographicEntity bibliographicEntity = saveBibHoldingItemEntity(50000, 2, 3, "CUL");
        ItemEntity itemEntity = bibliographicEntity.getItemEntities().get(0);
        saveRequestEntity(itemEntity.getItemId(),1,2,String.valueOf(new Random().nextInt()));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date fromDate = simpleDateFormat.parse("2016-12-30 00:00:00");
        Date toDate = simpleDateFormat.parse("2020-12-31 23:59:59");
        Integer[] cgdId = {3};
        List<Integer> cgdIdList = new ArrayList<>(Arrays.asList(cgdId));
        long count = requestItemDetailsRepository.getPhysicalAndEDDCounts(fromDate,toDate,Arrays.asList(2),cgdIdList,Arrays.asList(1,3),Arrays.asList(RecapConstants.REQUEST_STATUS_RETRIEVAL_ORDER_PLACED,RecapConstants.REQUEST_STATUS_REFILED,RecapConstants.REQUEST_STATUS_CANCELED),Arrays.asList(RecapConstants.REQUEST_TYPE_RETRIEVAL));
        assertNotNull(count);
        assertEquals(1,count);
    }


    @Test
    public void checkGetPhysicalSharedAndOpenRequestCountsForCUL() throws Exception{
        BibliographicEntity bibliographicEntity = saveBibHoldingItemEntity(50000, 2, 1, "CUL");
        ItemEntity itemEntity = bibliographicEntity.getItemEntities().get(0);
        saveRequestEntity(itemEntity.getItemId(),1,2,String.valueOf(new Random().nextInt()));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date fromDate = simpleDateFormat.parse("2016-12-30 00:00:00");
        Date toDate = simpleDateFormat.parse("2020-12-31 23:59:59");
        Integer[] cgdId = {1,2};
        List<Integer> cgdIdList = new ArrayList<>(Arrays.asList(cgdId));
        long count = requestItemDetailsRepository.getPhysicalAndEDDCounts(fromDate,toDate,Arrays.asList(2),cgdIdList,Arrays.asList(1,3),Arrays.asList(RecapConstants.REQUEST_STATUS_RETRIEVAL_ORDER_PLACED,RecapConstants.REQUEST_STATUS_REFILED,RecapConstants.REQUEST_STATUS_CANCELED),Arrays.asList(RecapConstants.REQUEST_TYPE_RETRIEVAL));
        assertNotNull(count);
        assertEquals(1,count);
    }

    @Test
    public void checkGetPhysicalPrivateRequestCountsForNYPL() throws Exception{
        BibliographicEntity bibliographicEntity = saveBibHoldingItemEntity(50000, 3, 3, "NYPL");
        ItemEntity itemEntity = bibliographicEntity.getItemEntities().get(0);
        saveRequestEntity(itemEntity.getItemId(),1,3,String.valueOf(new Random().nextInt()));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date fromDate = simpleDateFormat.parse("2016-12-30 00:00:00");
        Date toDate = simpleDateFormat.parse("2020-12-31 23:59:59");
        Integer[] cgdId = {3};
        List<Integer> cgdIdList = new ArrayList<>(Arrays.asList(cgdId));
        long count = requestItemDetailsRepository.getPhysicalAndEDDCounts(fromDate,toDate,Arrays.asList(3),cgdIdList,Arrays.asList(1,2),Arrays.asList(RecapConstants.REQUEST_STATUS_RETRIEVAL_ORDER_PLACED,RecapConstants.REQUEST_STATUS_REFILED,RecapConstants.REQUEST_STATUS_CANCELED),Arrays.asList(RecapConstants.REQUEST_TYPE_RETRIEVAL));
        assertNotNull(count);
        assertEquals(1,count);
    }

    @Test
    public void checkGetPhysicalSharedAndOpenRequestCountsForNYPL() throws Exception{
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date fromDate = simpleDateFormat.parse("2016-12-30 00:00:00");
        Date toDate = simpleDateFormat.parse("2020-12-31 23:59:59");
        Integer[] cgdId = {1,2};
        List<Integer> cgdIdList = new ArrayList<>(Arrays.asList(cgdId));
        long countBefore = requestItemDetailsRepository.getPhysicalAndEDDCounts(fromDate,toDate,Arrays.asList(3),cgdIdList,Arrays.asList(1,2),Arrays.asList(RecapConstants.REQUEST_STATUS_RETRIEVAL_ORDER_PLACED,RecapConstants.REQUEST_STATUS_REFILED,RecapConstants.REQUEST_STATUS_CANCELED),Arrays.asList(RecapConstants.REQUEST_TYPE_RETRIEVAL));
        assertNotNull(countBefore);
        BibliographicEntity bibliographicEntity = saveBibHoldingItemEntity(50000, 3, 1, "NYPL");
        ItemEntity itemEntity = bibliographicEntity.getItemEntities().get(0);
        saveRequestEntity(itemEntity.getItemId(),1,3,String.valueOf(new Random().nextInt()));
        long countAfter = requestItemDetailsRepository.getPhysicalAndEDDCounts(fromDate,toDate,Arrays.asList(3),cgdIdList,Arrays.asList(1,2),Arrays.asList(RecapConstants.REQUEST_STATUS_RETRIEVAL_ORDER_PLACED,RecapConstants.REQUEST_STATUS_REFILED,RecapConstants.REQUEST_STATUS_CANCELED),Arrays.asList(RecapConstants.REQUEST_TYPE_RETRIEVAL));
        assertNotNull(countAfter);
        assertEquals(countBefore+1,countAfter);
    }

    // Test for Edd Requests
    @Test
    public void checkGetEDDPrivateRequestCountsForPUL() throws Exception{
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date fromDate = simpleDateFormat.parse("2016-12-30 00:00:00");
        Date toDate = simpleDateFormat.parse("2020-12-31 23:59:59");
        Integer[] cgdId = {3};
        List<Integer> cgdIdList = new ArrayList<>(Arrays.asList(cgdId));
        long countBefore = requestItemDetailsRepository.getPhysicalAndEDDCounts(fromDate,toDate,Arrays.asList(1),cgdIdList,Arrays.asList(2,3),Arrays.asList(RecapConstants.REQUEST_STATUS_EDD,RecapConstants.REQUEST_STATUS_REFILED,RecapConstants.REQUEST_STATUS_CANCELED),Arrays.asList(RecapConstants.EDD));
        assertNotNull(countBefore);
        BibliographicEntity bibliographicEntity = saveBibHoldingItemEntity(50000, 1, 3, "PUL");
        ItemEntity itemEntity = bibliographicEntity.getItemEntities().get(0);
        saveRequestEntity(itemEntity.getItemId(),3,1,String.valueOf(new Random().nextInt()));
        long countAfter = requestItemDetailsRepository.getPhysicalAndEDDCounts(fromDate,toDate,Arrays.asList(1),cgdIdList,Arrays.asList(2,3),Arrays.asList(RecapConstants.REQUEST_STATUS_EDD,RecapConstants.REQUEST_STATUS_REFILED,RecapConstants.REQUEST_STATUS_CANCELED),Arrays.asList(RecapConstants.EDD));
        assertNotNull(countAfter);
        assertEquals(countBefore+1,countAfter);
    }

    @Test
    public void checkGetEDDSharedAndOpenRequestCountsForPUL() throws Exception{
        BibliographicEntity bibliographicEntity = saveBibHoldingItemEntity(50000, 1, 1, "PUL");
        ItemEntity itemEntity = bibliographicEntity.getItemEntities().get(0);
        saveRequestEntity(itemEntity.getItemId(),3,1,String.valueOf(new Random().nextInt()));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date fromDate = simpleDateFormat.parse("2016-12-30 00:00:00");
        Date toDate = simpleDateFormat.parse("2020-12-31 23:59:59");
        Integer[] cgdId = {1,2};
        List<Integer> cgdIdList = new ArrayList<>(Arrays.asList(cgdId));
        long count = requestItemDetailsRepository.getPhysicalAndEDDCounts(fromDate,toDate,Arrays.asList(1),cgdIdList,Arrays.asList(2,3),Arrays.asList(RecapConstants.REQUEST_STATUS_EDD,RecapConstants.REQUEST_STATUS_REFILED,RecapConstants.REQUEST_STATUS_CANCELED),Arrays.asList(RecapConstants.EDD));
        assertNotNull(count);
        assertEquals(1,count);
    }

    @Test
    public void checkGetEDDPrivateRequestCountsForCUL() throws Exception{
        BibliographicEntity bibliographicEntity = saveBibHoldingItemEntity(50000, 2, 3, "CUL");
        ItemEntity itemEntity = bibliographicEntity.getItemEntities().get(0);
        saveRequestEntity(itemEntity.getItemId(),3,2,String.valueOf(new Random().nextInt()));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date fromDate = simpleDateFormat.parse("2016-12-30 00:00:00");
        Date toDate = simpleDateFormat.parse("2020-12-31 23:59:59");
        Integer[] cgdId = {3};
        List<Integer> cgdIdList = new ArrayList<>(Arrays.asList(cgdId));
        long count = requestItemDetailsRepository.getPhysicalAndEDDCounts(fromDate,toDate,Arrays.asList(2),cgdIdList,Arrays.asList(1,3),Arrays.asList(RecapConstants.REQUEST_STATUS_EDD,RecapConstants.REQUEST_STATUS_REFILED,RecapConstants.REQUEST_STATUS_CANCELED),Arrays.asList(RecapConstants.EDD));
        assertNotNull(count);
        assertEquals(1,count);
    }

    @Test
    public void checkGetEDDSharedAndOpenRequestCountsForCUL() throws Exception{
        BibliographicEntity bibliographicEntity = saveBibHoldingItemEntity(50000, 2, 1, "CUL");
        ItemEntity itemEntity = bibliographicEntity.getItemEntities().get(0);
        saveRequestEntity(itemEntity.getItemId(),3,2,String.valueOf(new Random().nextInt()));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date fromDate = simpleDateFormat.parse("2016-12-30 00:00:00");
        Date toDate = simpleDateFormat.parse("2020-12-31 23:59:59");
        Integer[] cgdId = {1,2};
        List<Integer> cgdIdList = new ArrayList<>(Arrays.asList(cgdId));
        long count = requestItemDetailsRepository.getPhysicalAndEDDCounts(fromDate,toDate,Arrays.asList(2),cgdIdList,Arrays.asList(1,3),Arrays.asList(RecapConstants.REQUEST_STATUS_EDD,RecapConstants.REQUEST_STATUS_REFILED,RecapConstants.REQUEST_STATUS_CANCELED),Arrays.asList(RecapConstants.EDD));
        assertNotNull(count);
        assertEquals(1,count);
    }

    @Test
    public void checkGetEDDPrivateRequestCountsForNYPL() throws Exception{
        BibliographicEntity bibliographicEntity = saveBibHoldingItemEntity(50000, 3, 3, "NYPL");
        ItemEntity itemEntity = bibliographicEntity.getItemEntities().get(0);
        saveRequestEntity(itemEntity.getItemId(),3,3,String.valueOf(new Random().nextInt()));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date fromDate = simpleDateFormat.parse("2016-12-30 00:00:00");
        Date toDate = simpleDateFormat.parse("2020-12-31 23:59:59");
        Integer[] cgdId = {3};
        List<Integer> cgdIdList = new ArrayList<>(Arrays.asList(cgdId));
        long count = requestItemDetailsRepository.getPhysicalAndEDDCounts(fromDate,toDate,Arrays.asList(3),cgdIdList,Arrays.asList(2,1),Arrays.asList(RecapConstants.REQUEST_STATUS_EDD,RecapConstants.REQUEST_STATUS_REFILED,RecapConstants.REQUEST_STATUS_CANCELED),Arrays.asList(RecapConstants.EDD));
        assertNotNull(count);
        assertEquals(1,count);
    }

    @Test
    public void checkGetEDDSharedAndOpenRequestCountsForNYPL() throws Exception{
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date fromDate = simpleDateFormat.parse("2016-12-30 00:00:00");
        Date toDate = simpleDateFormat.parse("2020-12-31 23:59:59");
        Integer[] cgdId = {1,2};
        List<Integer> cgdIdList = new ArrayList<>(Arrays.asList(cgdId));
        long countBefore = requestItemDetailsRepository.getPhysicalAndEDDCounts(fromDate,toDate,Arrays.asList(3),cgdIdList,Arrays.asList(2,1),Arrays.asList(RecapConstants.REQUEST_STATUS_EDD,RecapConstants.REQUEST_STATUS_REFILED,RecapConstants.REQUEST_STATUS_CANCELED),Arrays.asList(RecapConstants.EDD));
        assertNotNull(countBefore);
        BibliographicEntity bibliographicEntity = saveBibHoldingItemEntity(50000, 3, 1, "NYPL");
        ItemEntity itemEntity = bibliographicEntity.getItemEntities().get(0);
        saveRequestEntity(itemEntity.getItemId(),3,3,String.valueOf(new Random().nextInt()));
        long countAfter = requestItemDetailsRepository.getPhysicalAndEDDCounts(fromDate,toDate,Arrays.asList(3),cgdIdList,Arrays.asList(2,1),Arrays.asList(RecapConstants.REQUEST_STATUS_EDD,RecapConstants.REQUEST_STATUS_REFILED,RecapConstants.REQUEST_STATUS_CANCELED),Arrays.asList(RecapConstants.EDD));
        assertNotNull(countAfter);
        assertEquals(countBefore+1,countAfter);
    }

    // Recall Requests

    @Test
    public void checkGetRecallRequestCountsForPUL() throws Exception{
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date fromDate = simpleDateFormat.parse("2016-12-30 00:00:00");
        Date toDate = simpleDateFormat.parse("2020-12-31 23:59:59");
        long countBefore = requestItemDetailsRepository.getEDDRecallRetrievalRequestCounts(fromDate,toDate,1,Arrays.asList(RecapConstants.REQUEST_STATUS_RECALLED,RecapConstants.REQUEST_STATUS_REFILED,RecapConstants.REQUEST_STATUS_CANCELED,RecapConstants.REQUEST_STATUS_RETRIEVAL_ORDER_PLACED),Arrays.asList(RecapConstants.REQUEST_TYPE_RECALL));
        assertNotNull(countBefore);
        BibliographicEntity bibliographicEntity = saveBibHoldingItemEntity(50000, 1, 1, "PUL");
        ItemEntity itemEntity = bibliographicEntity.getItemEntities().get(0);
        saveRequestEntity(itemEntity.getItemId(),2,2,String.valueOf(new Random().nextInt()));
        long countAfter = requestItemDetailsRepository.getEDDRecallRetrievalRequestCounts(fromDate,toDate,1, Arrays.asList(RecapConstants.REQUEST_STATUS_RECALLED,RecapConstants.REQUEST_STATUS_REFILED,RecapConstants.REQUEST_STATUS_CANCELED,RecapConstants.REQUEST_STATUS_RETRIEVAL_ORDER_PLACED),Arrays.asList(RecapConstants.REQUEST_TYPE_RECALL));
        assertNotNull(countAfter);
        assertEquals(countBefore+1,countAfter);
    }

    @Test
    public void checkGetRecallRequestCountsForCUL() throws Exception{
        BibliographicEntity bibliographicEntity = saveBibHoldingItemEntity(50000, 2, 1, "CUL");
        ItemEntity itemEntity = bibliographicEntity.getItemEntities().get(0);
        saveRequestEntity(itemEntity.getItemId(),2,1,String.valueOf(new Random().nextInt()));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date fromDate = simpleDateFormat.parse("2016-12-30 00:00:00");
        Date toDate = simpleDateFormat.parse("2020-12-31 23:59:59");
        long count = requestItemDetailsRepository.getEDDRecallRetrievalRequestCounts(fromDate,toDate,2,Arrays.asList(RecapConstants.REQUEST_STATUS_RECALLED,RecapConstants.REQUEST_STATUS_REFILED,RecapConstants.REQUEST_STATUS_CANCELED,RecapConstants.REQUEST_STATUS_RETRIEVAL_ORDER_PLACED),Arrays.asList(RecapConstants.REQUEST_TYPE_RECALL));
        assertNotNull(count);
        assertEquals(1,count);
    }

    @Test
    public void checkGetRecallRequestCountsForNYPL() throws Exception{
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date fromDate = simpleDateFormat.parse("2016-12-30 00:00:00");
        Date toDate = simpleDateFormat.parse("2020-12-31 23:59:59");
        long countBefore = requestItemDetailsRepository.getEDDRecallRetrievalRequestCounts(fromDate,toDate,3, Arrays.asList(RecapConstants.REQUEST_STATUS_RECALLED,RecapConstants.REQUEST_STATUS_REFILED,RecapConstants.REQUEST_STATUS_CANCELED,RecapConstants.REQUEST_STATUS_RETRIEVAL_ORDER_PLACED),Arrays.asList(RecapConstants.REQUEST_TYPE_RECALL));
        assertNotNull(countBefore);
        BibliographicEntity bibliographicEntity = saveBibHoldingItemEntity(50000, 3, 1, "NYPL");
        ItemEntity itemEntity = bibliographicEntity.getItemEntities().get(0);
        saveRequestEntity(itemEntity.getItemId(),2,2,String.valueOf(new Random().nextInt()));
        long countAfter = requestItemDetailsRepository.getEDDRecallRetrievalRequestCounts(fromDate,toDate,3, Arrays.asList(RecapConstants.REQUEST_STATUS_RECALLED,RecapConstants.REQUEST_STATUS_REFILED,RecapConstants.REQUEST_STATUS_CANCELED,RecapConstants.REQUEST_STATUS_RETRIEVAL_ORDER_PLACED),Arrays.asList(RecapConstants.REQUEST_TYPE_RECALL));
        assertNotNull(countAfter);
        assertEquals(countBefore+1,countAfter);
    }

    // Test for Retrieval Requests
    @Test
    public void checkGetRetrievalRequestCountsForPUL() throws Exception{
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date fromDate = simpleDateFormat.parse("2016-12-30 00:00:00");
        Date toDate = simpleDateFormat.parse("2020-12-31 23:59:59");
        long countBefore = requestItemDetailsRepository.getEDDRecallRetrievalRequestCounts(fromDate,toDate,1, Arrays.asList(RecapConstants.REQUEST_STATUS_RETRIEVAL_ORDER_PLACED,RecapConstants.REQUEST_STATUS_INITIAL_LOAD,RecapConstants.REQUEST_STATUS_REFILED,RecapConstants.REQUEST_STATUS_CANCELED),Arrays.asList(RecapConstants.REQUEST_TYPE_RETRIEVAL));
        assertNotNull(countBefore);
        BibliographicEntity bibliographicEntity = saveBibHoldingItemEntity(50000, 1, 1, "PUL");
        ItemEntity itemEntity = bibliographicEntity.getItemEntities().get(0);
        saveRequestEntity(itemEntity.getItemId(),1,2,String.valueOf(new Random().nextInt()));
        long countAfter = requestItemDetailsRepository.getEDDRecallRetrievalRequestCounts(fromDate,toDate,1, Arrays.asList(RecapConstants.REQUEST_STATUS_RETRIEVAL_ORDER_PLACED,RecapConstants.REQUEST_STATUS_INITIAL_LOAD,RecapConstants.REQUEST_STATUS_REFILED,RecapConstants.REQUEST_STATUS_CANCELED),Arrays.asList(RecapConstants.REQUEST_TYPE_RETRIEVAL));
        assertNotNull(countAfter);
        assertEquals(countBefore+1,countAfter);
    }

    @Test
    public void checkGetRetrievalRequestCountsForCUL() throws Exception{
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date fromDate = simpleDateFormat.parse("2016-12-30 00:00:00");
        Date toDate = simpleDateFormat.parse("2020-12-31 23:59:59");
        long countBefore = requestItemDetailsRepository.getEDDRecallRetrievalRequestCounts(fromDate,toDate,2, Arrays.asList(RecapConstants.REQUEST_STATUS_RETRIEVAL_ORDER_PLACED,RecapConstants.REQUEST_STATUS_INITIAL_LOAD,RecapConstants.REQUEST_STATUS_REFILED,RecapConstants.REQUEST_STATUS_CANCELED),Arrays.asList(RecapConstants.REQUEST_TYPE_RETRIEVAL));
        assertNotNull(countBefore);
        BibliographicEntity bibliographicEntity = saveBibHoldingItemEntity(50000, 2, 1, "CUL");
        ItemEntity itemEntity = bibliographicEntity.getItemEntities().get(0);
        saveRequestEntity(itemEntity.getItemId(),1,1,String.valueOf(new Random().nextInt()));
        long countAfter = requestItemDetailsRepository.getEDDRecallRetrievalRequestCounts(fromDate,toDate,2, Arrays.asList(RecapConstants.REQUEST_STATUS_RETRIEVAL_ORDER_PLACED,RecapConstants.REQUEST_STATUS_INITIAL_LOAD,RecapConstants.REQUEST_STATUS_REFILED,RecapConstants.REQUEST_STATUS_CANCELED),Arrays.asList(RecapConstants.REQUEST_TYPE_RETRIEVAL));
        assertNotNull(countAfter);
        assertEquals(countBefore+1,countAfter);
    }

    @Test
    public void checkGetRetrievalRequestCountsForNYPL() throws Exception{
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date fromDate = simpleDateFormat.parse("2016-12-30 00:00:00");
        Date toDate = simpleDateFormat.parse("2020-12-31 23:59:59");
        long countBefore = requestItemDetailsRepository.getEDDRecallRetrievalRequestCounts(fromDate,toDate,3, Arrays.asList(RecapConstants.REQUEST_STATUS_RETRIEVAL_ORDER_PLACED,RecapConstants.REQUEST_STATUS_INITIAL_LOAD,RecapConstants.REQUEST_STATUS_REFILED,RecapConstants.REQUEST_STATUS_CANCELED),Arrays.asList(RecapConstants.REQUEST_TYPE_RETRIEVAL));
        assertNotNull(countBefore);
        BibliographicEntity bibliographicEntity = saveBibHoldingItemEntity(50000, 3, 1, "NYPL");
        ItemEntity itemEntity = bibliographicEntity.getItemEntities().get(0);
        saveRequestEntity(itemEntity.getItemId(),1,2,String.valueOf(new Random().nextInt()));
        long countAfter = requestItemDetailsRepository.getEDDRecallRetrievalRequestCounts(fromDate,toDate,3, Arrays.asList(RecapConstants.REQUEST_STATUS_RETRIEVAL_ORDER_PLACED,RecapConstants.REQUEST_STATUS_INITIAL_LOAD,RecapConstants.REQUEST_STATUS_REFILED,RecapConstants.REQUEST_STATUS_CANCELED),Arrays.asList(RecapConstants.REQUEST_TYPE_RETRIEVAL));
        assertNotNull(countAfter);
        assertEquals(countBefore+1,countAfter);
    }
    // Test for saving Bib , Holding , Item , RequestItem and Patron Entities

    private BibliographicEntity saveBibHoldingItemEntity(Integer itemid, Integer owningInstitutionId, Integer collectionGroupId, String ownInstItemId) throws Exception {
        Random random = new Random();
        String owningInstitutionBibId = String.valueOf(random.nextInt());
        BibliographicEntity bibliographicEntity = new BibliographicEntity();
        bibliographicEntity.setContent("Mock Bib Content".getBytes());
        bibliographicEntity.setCreatedDate(new Date());
        bibliographicEntity.setCreatedBy("ut");
        bibliographicEntity.setLastUpdatedBy("ut");
        bibliographicEntity.setLastUpdatedDate(new Date());
        bibliographicEntity.setOwningInstitutionBibId(owningInstitutionBibId);
        bibliographicEntity.setOwningInstitutionId(owningInstitutionId);

        HoldingsEntity holdingsEntity = new HoldingsEntity();
        holdingsEntity.setContent("mock holdings".getBytes());
        holdingsEntity.setCreatedDate(new Date());
        holdingsEntity.setCreatedBy("ut");
        holdingsEntity.setLastUpdatedDate(new Date());
        holdingsEntity.setLastUpdatedBy("ut");
        holdingsEntity.setOwningInstitutionId(owningInstitutionId);
        holdingsEntity.setOwningInstitutionHoldingsId(String.valueOf(random.nextInt()));

        ItemEntity itemEntity = new ItemEntity();
        itemEntity.setItemId(itemid);
        itemEntity.setBarcode("b3");
        itemEntity.setCustomerCode("c1");
        itemEntity.setCallNumber("cn1");
        itemEntity.setCallNumberType("ct1");
        itemEntity.setItemAvailabilityStatusId(1);
        itemEntity.setCopyNumber(1);
        itemEntity.setOwningInstitutionId(owningInstitutionId);
        itemEntity.setCollectionGroupId(collectionGroupId);
        itemEntity.setCreatedDate(new Date());
        itemEntity.setCreatedBy("ut");
        itemEntity.setLastUpdatedDate(new Date());
        itemEntity.setLastUpdatedBy("ut");
        itemEntity.setUseRestrictions("no");
        itemEntity.setVolumePartYear("v3");
        itemEntity.setOwningInstitutionItemId(ownInstItemId);
        itemEntity.setDeleted(false);

        itemEntity.setHoldingsEntities(Arrays.asList(holdingsEntity));

        bibliographicEntity.setHoldingsEntities(Arrays.asList(holdingsEntity));
        bibliographicEntity.setItemEntities(Arrays.asList(itemEntity));

        BibliographicEntity savedBibliographicEntity = bibliographicDetailsRepository.saveAndFlush(bibliographicEntity);
        entityManager.refresh(savedBibliographicEntity);

        return savedBibliographicEntity;
    }

    private RequestItemEntity saveRequestEntity(Integer itemId, Integer requestTypeId, Integer requestingInstID, String patronID) throws Exception {
        RequestItemEntity requestItemEntity = new RequestItemEntity();
        requestItemEntity.setItemId(itemId);
        requestItemEntity.setRequestTypeId(requestTypeId);
        requestItemEntity.setRequestingInstitutionId(requestingInstID);
        requestItemEntity.setRequestExpirationDate(new Date());
        requestItemEntity.setCreatedDate(new Date());
        requestItemEntity.setLastUpdatedDate(new Date());
        requestItemEntity.setPatronId(patronID);
        requestItemEntity.setStopCode("s1");
        requestItemEntity.setCreatedBy("test");
        requestItemEntity.setCreatedBy("test");
        requestItemEntity.setRequestStatusId(requestTypeId);
        return requestItemDetailsRepository.save(requestItemEntity);
    }
}
