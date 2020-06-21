package org.recap.util;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.recap.BaseTestCase;
import org.recap.RecapCommonConstants;
import org.recap.RecapConstants;
import org.recap.model.deaccession.DeAccessionItem;
import org.recap.model.deaccession.DeAccessionRequest;
import org.recap.model.jpa.BibliographicEntity;
import org.recap.model.jpa.CustomerCodeEntity;
import org.recap.model.jpa.HoldingsEntity;
import org.recap.model.jpa.ItemEntity;
import org.recap.model.search.BibliographicMarcForm;
import org.recap.repository.jpa.BibliographicDetailsRepository;
import org.recap.repository.jpa.CustomerCodeDetailsRepository;
import org.recap.repository.jpa.ItemChangeLogDetailsRepository;
import org.recap.repository.jpa.ItemDetailsRepository;
import org.recap.service.RestHeaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Date;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * Created by rajeshbabuk on 19/10/16.
 */
public class CollectionServiceUtilUT extends BaseTestCase {

    private MockMvc mockMvc;

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private CollectionServiceUtil collectionServiceUtil;

    @Autowired
    private RestHeaderService restHeaderService;

    @Mock
    private CollectionServiceUtil mockedCollectionServiceUtil;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private BibliographicDetailsRepository bibliographicDetailsRepository;

    @Mock
    private ItemChangeLogDetailsRepository mockedItemChangeLogDetailsRepository;

    @Mock
    private ItemDetailsRepository mockedItemDetailsRepository;

    @Mock
    private CustomerCodeDetailsRepository customerCodeDetailsRepository;

    @Value("${scsb.url}")
    String scsbUrl;

    @Mock
    private RestTemplate restTemplate;

    @Before
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void updateCGDForItem() throws Exception {
        Mockito.when(mockedItemChangeLogDetailsRepository.count()).thenReturn(new Long(0));
        long beforeCountForChangeLog = mockedItemChangeLogDetailsRepository.count();

        BibliographicEntity bibliographicEntity = getBibEntityWithHoldingsAndItem(1,false);
        BibliographicEntity savedBibliographicEntity = bibliographicDetailsRepository.saveAndFlush(bibliographicEntity);
        entityManager.refresh(savedBibliographicEntity);
        assertNotNull(savedBibliographicEntity);
        assertNotNull(savedBibliographicEntity.getBibliographicId());
        assertNotNull(savedBibliographicEntity.getHoldingsEntities());
        assertNotNull(savedBibliographicEntity.getItemEntities());
        assertNotNull(savedBibliographicEntity.getItemEntities().get(0));
        assertNotNull(savedBibliographicEntity.getItemEntities().get(0).getItemId());
        assertEquals("Shared", savedBibliographicEntity.getItemEntities().get(0).getCollectionGroupEntity().getCollectionGroupCode());

        String itemBarcode = savedBibliographicEntity.getItemEntities().get(0).getBarcode();

        BibliographicMarcForm bibliographicMarcForm = new BibliographicMarcForm();
        bibliographicMarcForm.setBibId(savedBibliographicEntity.getBibliographicId());
        bibliographicMarcForm.setBarcode(itemBarcode);
        bibliographicMarcForm.setOwningInstitution("PUL");
        bibliographicMarcForm.setCollectionGroupDesignation("Shared");
        bibliographicMarcForm.setNewCollectionGroupDesignation("Private");
        bibliographicMarcForm.setCgdChangeNotes("Notes for updating CGD");
        HttpEntity requestEntity = new HttpEntity<>(restHeaderService.getHttpHeaders());
        ResponseEntity responseEntity = new ResponseEntity("Success", HttpStatus.OK);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(scsbUrl + RecapConstants.SCSB_UPDATE_CGD_URL)
                .queryParam(RecapCommonConstants.CGD_UPDATE_ITEM_BARCODE, bibliographicMarcForm.getBarcode())
                .queryParam(RecapCommonConstants.OWNING_INSTITUTION, bibliographicMarcForm.getOwningInstitution())
                .queryParam(RecapCommonConstants.OLD_CGD, bibliographicMarcForm.getCollectionGroupDesignation())
                .queryParam(RecapCommonConstants.NEW_CGD, bibliographicMarcForm.getNewCollectionGroupDesignation())
                .queryParam(RecapCommonConstants.CGD_CHANGE_NOTES, bibliographicMarcForm.getCgdChangeNotes());
        collectionServiceUtil = Mockito.mock(CollectionServiceUtil.class);
        Mockito.when(collectionServiceUtil.getRestTemplate()).thenReturn(restTemplate);
        Mockito.when(collectionServiceUtil.getRestHeaderService()).thenReturn(restHeaderService);
        Mockito.when(collectionServiceUtil.getScsbUrl()).thenReturn(scsbUrl);
        Mockito.when(restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.GET, requestEntity, String.class)).thenReturn(responseEntity);
        Mockito.doCallRealMethod().when(collectionServiceUtil).updateCGDForItem(bibliographicMarcForm);
        collectionServiceUtil.updateCGDForItem(bibliographicMarcForm);

        //Mockito.when(mockedItemDetailsRepository.findByBarcode(itemBarcode)).thenReturn(getBibEntityWithHoldingsAndItem(3,false).getItemEntities());
        List<ItemEntity> fetchedItemEntities = itemDetailsRepository.findByBarcode(itemBarcode);
        assertNotNull(fetchedItemEntities);
        assertTrue(fetchedItemEntities.size() > 0);
        for (ItemEntity fetchedItemEntity : fetchedItemEntities) {
            entityManager.refresh(fetchedItemEntity);
            assertNotNull(fetchedItemEntity);
            assertNotNull(fetchedItemEntity.getItemId());
            assertEquals(itemBarcode, fetchedItemEntity.getBarcode());
            //assertEquals("Private", fetchedItemEntity.getCollectionGroupEntity().getCollectionGroupCode());
        }

        Mockito.when(mockedItemChangeLogDetailsRepository.count()).thenReturn(new Long(1));
        long afterCountForChangeLog = mockedItemChangeLogDetailsRepository.count();

        assertEquals(afterCountForChangeLog, beforeCountForChangeLog + 1);
    }


    @Test
    public void deaccessionItem() throws Exception {
        BibliographicEntity bibliographicEntity = getBibEntityWithHoldingsAndItem(1,false);

        BibliographicEntity savedBibliographicEntity = bibliographicDetailsRepository.saveAndFlush(bibliographicEntity);
        entityManager.refresh(savedBibliographicEntity);
        assertNotNull(savedBibliographicEntity);
        Integer bibliographicId = savedBibliographicEntity.getBibliographicId();
        assertNotNull(bibliographicId);

        BibliographicEntity fetchedBibliographicEntity = bibliographicDetailsRepository.findByBibliographicId(bibliographicId);
        entityManager.refresh(fetchedBibliographicEntity);
        assertNotNull(fetchedBibliographicEntity);
        assertNotNull(fetchedBibliographicEntity.getBibliographicId());
        assertEquals(bibliographicId, fetchedBibliographicEntity.getBibliographicId());
        assertNotNull(fetchedBibliographicEntity.getItemEntities());
        assertTrue(fetchedBibliographicEntity.getItemEntities().size() > 0);
        assertNotNull(fetchedBibliographicEntity.getItemEntities().get(0));
        assertNotNull(fetchedBibliographicEntity.getItemEntities().get(0).getItemId());

        CustomerCodeEntity customerCodeEntity = new CustomerCodeEntity();
        customerCodeEntity.setCustomerCode("PB");

        Integer itemId = fetchedBibliographicEntity.getItemEntities().get(0).getItemId();
        ItemEntity fetchedItemEntity = itemDetailsRepository.findByItemId(itemId);
        entityManager.refresh(fetchedItemEntity);
        assertNotNull(fetchedItemEntity);
        assertNotNull(fetchedItemEntity.getItemId());
        assertEquals(itemId, fetchedItemEntity.getItemId());
        assertEquals(Boolean.FALSE, fetchedItemEntity.isDeleted());

        DeAccessionRequest deAccessionRequest = new DeAccessionRequest();
        DeAccessionItem deAccessionItem = new DeAccessionItem();
        deAccessionItem.setDeliveryLocation("PB");
        deAccessionItem.setItemBarcode("3324565886843358");
        deAccessionRequest.setUsername("john");
        deAccessionRequest.setDeAccessionItems(Arrays.asList(deAccessionItem));

        String itemBarcode = fetchedBibliographicEntity.getItemEntities().get(0).getBarcode();
        BibliographicMarcForm bibliographicMarcForm = new BibliographicMarcForm();
        bibliographicMarcForm.setItemId(itemId);
        bibliographicMarcForm.setUsername("test");
        bibliographicMarcForm.setBarcode(itemBarcode);
        bibliographicMarcForm.setCgdChangeNotes("Notes for deaccession");
        Map<String,String> map = new HashMap<>();
        map.put(itemBarcode,RecapCommonConstants.SUCCESS);
        collectionServiceUtil = Mockito.mock(CollectionServiceUtil.class);
        Mockito.when(collectionServiceUtil.getDeAccessionRequest()).thenReturn(deAccessionRequest);
        Mockito.when(collectionServiceUtil.getCustomerCodeDetailsRepository()).thenReturn(customerCodeDetailsRepository);
        Mockito.when(collectionServiceUtil.getCustomerCodeDetailsRepository().findByDescription(bibliographicMarcForm.getDeliveryLocation())).thenReturn(customerCodeEntity);
        HttpEntity<DeAccessionRequest> requestEntity = new HttpEntity<>(deAccessionRequest, restHeaderService.getHttpHeaders());
        Mockito.when(collectionServiceUtil.getRestTemplate()).thenReturn(restTemplate);
        Mockito.when(collectionServiceUtil.getScsbUrl()).thenReturn(scsbUrl);
        Mockito.when(collectionServiceUtil.getRestHeaderService()).thenReturn(restHeaderService);
        Mockito.when(collectionServiceUtil.getItemDetailsRepository()).thenReturn(mockedItemDetailsRepository);
        Mockito.when(collectionServiceUtil.getItemDetailsRepository().findByBarcode(itemBarcode)).thenReturn(Arrays.asList(fetchedItemEntity));
        Mockito.when(collectionServiceUtil.getItemChangeLogDetailsRepository()).thenReturn(mockedItemChangeLogDetailsRepository);
        Mockito.when(restTemplate.postForObject(scsbUrl + RecapConstants.SCSB_DEACCESSION_URL, requestEntity, Map.class)).thenReturn(map);
        Mockito.doCallRealMethod().when(collectionServiceUtil).deAccessionItem(bibliographicMarcForm);
        collectionServiceUtil.deAccessionItem(bibliographicMarcForm);

        ItemEntity fetchedItemEntityAfterDeaccession = itemDetailsRepository.findByItemId(itemId);
        entityManager.refresh(fetchedItemEntityAfterDeaccession);
        assertNotNull(fetchedItemEntityAfterDeaccession);
        assertNotNull(fetchedItemEntityAfterDeaccession.getItemId());
        assertEquals(itemId, fetchedItemEntityAfterDeaccession.getItemId());
        assertNotNull(deAccessionItem.getDeliveryLocation());
        assertNotNull(deAccessionItem.getItemBarcode());
        assertNotNull(deAccessionRequest.getUsername());
        assertNotNull(deAccessionRequest.getDeAccessionItems());
    }

    @Test
    public void checkGetterServices(){

        Mockito.when(mockedCollectionServiceUtil.getScsbUrl()).thenCallRealMethod();
        Mockito.when(mockedCollectionServiceUtil.getRestTemplate()).thenCallRealMethod();
        Mockito.when(mockedCollectionServiceUtil.getCustomerCodeDetailsRepository()).thenCallRealMethod();
        Mockito.when(mockedCollectionServiceUtil.getItemDetailsRepository()).thenCallRealMethod();
        Mockito.when(mockedCollectionServiceUtil.getItemChangeLogDetailsRepository()).thenCallRealMethod();


        assertNotEquals(mockedCollectionServiceUtil.getScsbUrl(),scsbUrl);
        assertNotEquals(mockedCollectionServiceUtil.getRestTemplate(),restTemplate);
        assertNotEquals(mockedCollectionServiceUtil.getCustomerCodeDetailsRepository(),customerCodeDetailsRepository);
        assertNotEquals(mockedCollectionServiceUtil.getItemChangeLogDetailsRepository(),mockedItemChangeLogDetailsRepository);
        assertNotEquals(mockedCollectionServiceUtil.getItemDetailsRepository(),mockedItemDetailsRepository);

    }



    public BibliographicEntity getBibEntityWithHoldingsAndItem(int cgd,boolean isDelete) throws Exception {
        Random random = new Random();
        File bibContentFile = getBibContentFile();
        File holdingsContentFile = getHoldingsContentFile();
        String sourceBibContent = FileUtils.readFileToString(bibContentFile, "UTF-8");
        String sourceHoldingsContent = FileUtils.readFileToString(holdingsContentFile, "UTF-8");

        BibliographicEntity bibliographicEntity = new BibliographicEntity();
        bibliographicEntity.setContent(sourceBibContent.getBytes());
        bibliographicEntity.setCreatedDate(new Date());
        bibliographicEntity.setLastUpdatedDate(new Date());
        bibliographicEntity.setCreatedBy("tst");
        bibliographicEntity.setLastUpdatedBy("tst");
        bibliographicEntity.setOwningInstitutionId(1);
        bibliographicEntity.setOwningInstitutionBibId(String.valueOf(random.nextInt()));
        bibliographicEntity.setDeleted(isDelete);

        HoldingsEntity holdingsEntity = new HoldingsEntity();
        holdingsEntity.setContent(sourceHoldingsContent.getBytes());
        holdingsEntity.setCreatedDate(new Date());
        holdingsEntity.setLastUpdatedDate(new Date());
        holdingsEntity.setCreatedBy("tst");
        holdingsEntity.setLastUpdatedBy("tst");
        holdingsEntity.setOwningInstitutionId(1);
        holdingsEntity.setOwningInstitutionHoldingsId(String.valueOf(random.nextInt()));
        holdingsEntity.setDeleted(isDelete);

        ItemEntity itemEntity = new ItemEntity();
        itemEntity.setLastUpdatedDate(new Date());
        itemEntity.setOwningInstitutionItemId(String.valueOf(random.nextInt()));
        itemEntity.setOwningInstitutionId(1);
        itemEntity.setBarcode("12345");
        itemEntity.setCallNumber("x.12321");
        itemEntity.setCollectionGroupId(cgd);
        itemEntity.setCallNumberType("1");
        itemEntity.setCustomerCode("123");
        itemEntity.setCreatedDate(new Date());
        itemEntity.setCreatedBy("tst");
        itemEntity.setLastUpdatedBy("tst");
        itemEntity.setItemAvailabilityStatusId(1);
        //itemEntity.setHoldingsEntities(Arrays.asList(holdingsEntity));
        itemEntity.setDeleted(isDelete);

        holdingsEntity.setItemEntities(Arrays.asList(itemEntity));
        bibliographicEntity.setHoldingsEntities(Arrays.asList(holdingsEntity));
        bibliographicEntity.setItemEntities(Arrays.asList(itemEntity));

        return bibliographicEntity;
    }

    public File getBibContentFile() throws URISyntaxException {
        URL resource = getClass().getResource("BibContent.xml");
        return new File(resource.toURI());
    }

    public File getHoldingsContentFile() throws URISyntaxException {
        URL resource = getClass().getResource("HoldingsContent.xml");
        return new File(resource.toURI());
    }
}