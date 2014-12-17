package com.swim.producer;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Thomas
 */
public class ProducerServiceTest {

    public ProducerServiceTest() {
    }

    private ProducerService instance  = new ProducerService();
    /**
     * Test of getRequest method, of class ProducerService.
     */
    @Test
    public void testMethodGetRequest() throws Exception {
        System.out.println("getRequest");
        int expResult = 2097152; //2 Mo
        int waitTime = 3;
        instance.setResponseSize(expResult);
        instance.setProcessingTime(waitTime);
        String result = instance.getRequest();
        byte[] resultByte = instance.encodeUTF8(result);
        int size = resultByte.length;
        assertEquals(expResult, size);
    }
    
}
