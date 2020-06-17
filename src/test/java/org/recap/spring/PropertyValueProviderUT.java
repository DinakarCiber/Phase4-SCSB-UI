package org.recap.spring;

import org.junit.Test;
import org.recap.BaseTestCase;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertNull;

/**
 * Created by hemalathas on 9/3/17.
 */
public class PropertyValueProviderUT extends BaseTestCase{

    @Autowired
    PropertyValueProvider propertyValueProvider;

    @Test
    public void testGetProperty(){
        String key = propertyValueProvider.getProperty("key");
        assertNull(key);
    }

}