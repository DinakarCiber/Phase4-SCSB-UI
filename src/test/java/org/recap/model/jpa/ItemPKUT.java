package org.recap.model.jpa;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.recap.BaseTestCase;

import java.awt.*;
import java.util.Arrays;
import java.util.Collection;

public class ItemPKUT extends BaseTestCase {

    ItemPK itemPK = new ItemPK();

        private Integer owningInstitutionId = 1;
        private String owningInstitutionItemId = "1";
        private boolean equal;
        @Test
        public  void getItemPK(){
            itemPK.getOwningInstitutionId();
            itemPK.getOwningInstitutionItemId();
        }

    @Test
    public void testEqualsAndHashcode() {
        itemPK.setOwningInstitutionId(owningInstitutionId);
        itemPK.setOwningInstitutionItemId(owningInstitutionItemId);

        itemPK.equals(itemPK.getOwningInstitutionId());
        itemPK.hashCode();
    }

}
