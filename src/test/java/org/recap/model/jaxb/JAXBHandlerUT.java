package org.recap.model.jaxb;


import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.recap.BaseTestCase;
import org.recap.RecapConstants;
import static junit.framework.TestCase.assertNotNull;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import java.util.HashMap;
import java.util.Map;



public class JAXBHandlerUT extends BaseTestCase {


    @Test
    public void unMarshallingTest() throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(RecapConstants.class);
       // Mockito.doCallRealMethod().when(jaxbHandler).unmarshal("unMarshalling",RecapConstants.class);
       // Object result = jaxbHandler.unmarshal("unMarshalling", RecapConstants.class);

    }
    @Test
    public void getUnmarshallerMap(){
        JAXBHandler jaxbHandler = JAXBHandler.getInstance();
        Map<String, Unmarshaller> unmarshallerMap =jaxbHandler.getUnmarshallerMap();
    }
    @Test
    public void getMarshallerMap(){
        JAXBHandler jaxbHandler = JAXBHandler.getInstance();
        Map<String, Marshaller> MarshallerMap =jaxbHandler.getMarshallerMap();
    }
    @Test
    public void setUnmarshallerMap(){
        JAXBHandler jaxbHandler = JAXBHandler.getInstance();
        Map<String, Unmarshaller> unmarshallerMap = new HashMap<>();
        jaxbHandler.setUnmarshallerMap(unmarshallerMap);
    }
    @Test
    public void setMarshallerMap(){
        JAXBHandler jaxbHandler = JAXBHandler.getInstance();
        Map<String, Marshaller> MarshallerMap = new HashMap<>();
        jaxbHandler.setMarshallerMap(MarshallerMap);
    }
    @Test
    public  void marshaller(){
        JAXBHandler jaxbHandler = JAXBHandler.getInstance();
        Object object= "marshaller";
        String marshalling =jaxbHandler.marshal(object);
        assertNotNull(marshalling);
    }
    @Test
    public void unmarshal() throws Exception{
        Bib bib = new Bib();
        JAXBHandler jaxbHandler = JAXBHandler.getInstance();
        String content = "test";
       // jaxbHandler.unmarshal(content,Bib.class);
    }

    }
