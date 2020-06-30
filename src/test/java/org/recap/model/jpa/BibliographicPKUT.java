package org.recap.model.jpa;

import org.junit.Test;
import org.recap.BaseTestCase;

public class BibliographicPKUT extends BaseTestCase {

    BibliographicPK bibliographicPK = new BibliographicPK();

    @Test
    public void testBibliographic(){
        bibliographicPK.setOwningInstitutionId(1);
        bibliographicPK.setOwningInstitutionBibId("1");
        bibliographicPK.equals(bibliographicPK.getOwningInstitutionId());
        bibliographicPK.hashCode();
    }
    @Test
    public void testGetBibliographic(){
        bibliographicPK.getOwningInstitutionId();
        bibliographicPK.getOwningInstitutionBibId();
    }
}
