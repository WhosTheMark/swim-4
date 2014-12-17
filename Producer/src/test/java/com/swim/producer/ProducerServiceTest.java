package com.swim.producer;

import java.nio.charset.Charset;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Thomas
 */
public class ProducerServiceTest {

    public ProducerServiceTest() {
    }

    /**
     * Test of getRequest method, of class ProducerService.
     */
    @Test
    public void testGetRequest() throws Exception {
        System.out.println("getRequest");
        //EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        //ProducerService instance = (ProducerService)container.getContext().lookup("java:global/classes/ProducerService");
        int expResult = 2097152; //2 Mo
        ProducerService instance = new ProducerService(expResult, 2);
        String result = instance.getRequest();
        byte[] resultByte = instance.encodeUTF8(result);
        int size = resultByte.length;
        assertEquals(expResult, size);
        
        expResult = 0; 
        instance = new ProducerService(expResult, 2);
        result = instance.getRequest();
        resultByte = instance.encodeUTF8(result);
        //byte[] resultByte = result.getBytes();
        size = resultByte.length;
        assertEquals(expResult, size);
        //container.close();
    }

}
