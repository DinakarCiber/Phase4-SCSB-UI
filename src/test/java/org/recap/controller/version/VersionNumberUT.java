package org.recap.controller.version;

import org.junit.Test;
import org.recap.BaseTestCase;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertNotNull;

/**
 * Created by hemalathas on 21/2/17.
 */
public class VersionNumberUT extends BaseTestCase{

    @Autowired
    VersionNumber versionNumber;

    @Test
    public void testVersionNumber(){
        versionNumber.setVersionNumberService("1.0.0");
        String version = versionNumber.getVersionNumberService();
        assertNotNull(version);
    }

}