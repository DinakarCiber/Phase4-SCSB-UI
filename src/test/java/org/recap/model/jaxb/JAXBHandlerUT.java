package org.recap.model.jaxb;


import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.recap.BaseTestCase;
import org.recap.RecapConstants;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import static junit.framework.TestCase.assertNotNull;


public class JAXBHandlerUT extends BaseTestCase {

    @Mock
    JAXBHandler jaxbHandler;
    @Test
    public void unMarshallingTest() throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(RecapConstants.class);
        Mockito.doCallRealMethod().when(jaxbHandler).unmarshal("unMarshalling",RecapConstants.class);
       // Object result = jaxbHandler.unmarshal("unMarshalling", RecapConstants.class);

    }

    }
