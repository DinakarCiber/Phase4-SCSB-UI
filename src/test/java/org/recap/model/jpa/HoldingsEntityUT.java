package org.recap.model.jpa;

import org.junit.Test;
import org.recap.BaseTestCase;

import java.util.Date;

public class HoldingsEntityUT extends BaseTestCase {
    HoldingsEntity holdingsEntity = new HoldingsEntity();
    @Test
    public void testHoldingsEntity(){
        holdingsEntity.setCreatedBy("test");
        holdingsEntity.setCreatedDate(new Date());
        holdingsEntity.setDeleted(false);
        holdingsEntity.setHoldingsId(1);
        holdingsEntity.setLastUpdatedBy("test");
        holdingsEntity.setLastUpdatedDate(new Date());
        holdingsEntity.setOwningInstitutionHoldingsId("1");
        holdingsEntity.setOwningInstitutionId(1);
        holdingsEntity.equals(holdingsEntity.getHoldingsId());
        holdingsEntity.hashCode();
    }
}
