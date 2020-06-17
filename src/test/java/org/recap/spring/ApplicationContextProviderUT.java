package org.recap.spring;

import org.junit.Test;
import org.recap.BaseTestCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import static org.junit.Assert.assertNotNull;

/**
 * Created by hemalathas on 31/3/17.
 */
public class ApplicationContextProviderUT extends BaseTestCase{

    @Autowired
    ApplicationContextProvider applicationContextProvider;

    @Autowired
    ApplicationContext applicationContext;

    @Test
    public void testApplicationContextProvider(){
        applicationContextProvider.setApplicationContext(applicationContext);
        ApplicationContextProvider applicationContextProvider1 = applicationContextProvider.getInstance();
        assertNotNull(applicationContextProvider1);

    }

}