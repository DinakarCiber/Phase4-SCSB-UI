package org.recap.model.jpa;

import org.junit.Test;
import org.recap.BaseTestCase;

public class HoldingsPKUT extends BaseTestCase {
    HoldingsPK holdingsPK = new HoldingsPK();
    @Test
    public void testHoldingsPK(){
        holdingsPK.setOwningInstitutionId(1);
        holdingsPK.setOwningInstitutionHoldingsId("1");
        holdingsPK.equals(holdingsPK.getOwningInstitutionId());
        holdingsPK.hashCode();
    }
    @Test
    public void testgetHoldings(){
        holdingsPK.getOwningInstitutionId();
        holdingsPK.getOwningInstitutionHoldingsId();

    }
}
