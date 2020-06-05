package org.recap.repository.jpa;

import org.junit.Test;
import org.recap.BaseTestCase;
import org.recap.model.jpa.BibliographicEntity;
import org.recap.model.jpa.HoldingsEntity;
import org.recap.model.jpa.InstitutionEntity;
import org.recap.model.jpa.ItemEntity;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by dharmendrag on 6/2/17.
 */
public class HoldingsDetailsRepositoryUT extends BaseTestCase {

    Random random = new Random();

    @Autowired
    BibliographicDetailsRepository bibliographicDetailsRepository;

    @Autowired
    HoldingsDetailsRepository holdingsDetailsRepository;

    @Autowired
    ItemDetailsRepository itemDetailsRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    public void findById(){
        String owningInstitutionBibId = String.valueOf(random.nextInt());
        int owningInstitutionId = 1;

        HoldingsEntity holdingsEntity=new HoldingsEntity();
        InstitutionEntity institutionEntity=new InstitutionEntity();
        institutionEntity.setId(1);


        holdingsEntity.setContent("mock holdings".getBytes());
        holdingsEntity.setCreatedDate(new Date());
        holdingsEntity.setCreatedBy("etl");
        holdingsEntity.setLastUpdatedDate(new Date());
        holdingsEntity.setLastUpdatedBy("etl");
        holdingsEntity.setOwningInstitutionId(owningInstitutionId);
        holdingsEntity.setOwningInstitutionHoldingsId(String.valueOf(random.nextInt()));


        setBibliographicEntity(owningInstitutionBibId,owningInstitutionId,holdingsEntity);

        setItemEntity(owningInstitutionBibId,owningInstitutionId,holdingsEntity);

        HoldingsEntity savedHoldings=holdingsDetailsRepository.saveAndFlush(holdingsEntity);
        entityManager.refresh(savedHoldings);

        assertNotNull(savedHoldings.getHoldingsId());
        assertEquals(holdingsEntity.getContent().length,savedHoldings.getContent().length);
        assertTrue(holdingsEntity.getLastUpdatedDate().before(new Date()));
        assertTrue(holdingsEntity.getCreatedDate().before(new Date()));
        assertEquals(holdingsEntity.getLastUpdatedBy(),savedHoldings.getLastUpdatedBy());
       // assertEquals(holdingsEntity.getBibliographicEntities(),savedHoldings.getBibliographicEntities());
        assertEquals(holdingsEntity.getItemEntities().get(0).getBarcode(),savedHoldings.getItemEntities().get(0).getBarcode());
        assertEquals(holdingsEntity.getCreatedBy(),savedHoldings.getCreatedBy());
        assertEquals(holdingsEntity.getOwningInstitutionHoldingsId(),savedHoldings.getOwningInstitutionHoldingsId());
        assertEquals(holdingsEntity.getOwningInstitutionId(),savedHoldings.getOwningInstitutionId());


    }

    private void setBibliographicEntity(String owningInstitutionBibId,int owningInstitutionId,HoldingsEntity holdingsEntity){
        BibliographicEntity bibliographicEntity = new BibliographicEntity();
        bibliographicEntity.setContent("Mock Bib Content".getBytes());
        bibliographicEntity.setCreatedDate(new Date());
        bibliographicEntity.setCreatedBy("etl");
        bibliographicEntity.setLastUpdatedBy("etl");
        bibliographicEntity.setLastUpdatedDate(new Date());
        bibliographicEntity.setOwningInstitutionBibId(owningInstitutionBibId);
        bibliographicEntity.setOwningInstitutionId(owningInstitutionId);

        BibliographicEntity savedBibliographicEntity=bibliographicDetailsRepository.saveAndFlush(bibliographicEntity);
        entityManager.refresh(savedBibliographicEntity);

        holdingsEntity.setBibliographicEntities(Arrays.asList(savedBibliographicEntity));
    }

    private void setItemEntity(String owningInstitutionBibId,int owningInstitutionId,HoldingsEntity holdingsEntity){

        ItemEntity itemEntity = new ItemEntity();
        itemEntity.setCallNumberType("0");
        itemEntity.setCallNumber("callNum");
        itemEntity.setCreatedDate(new Date());
        itemEntity.setCreatedBy("etl");
        itemEntity.setLastUpdatedDate(new Date());
        itemEntity.setLastUpdatedBy("etl");
        itemEntity.setBarcode("1231");
        itemEntity.setOwningInstitutionItemId(".i1231");
        itemEntity.setOwningInstitutionId(1);
        itemEntity.setCollectionGroupId(1);
        itemEntity.setCustomerCode("PA");
        itemEntity.setItemAvailabilityStatusId(1);

        holdingsEntity.setItemEntities(Arrays.asList(itemEntity));

    }
}
