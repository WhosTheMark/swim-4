package com.swim.producer;

import com.swim.messaging.ProducerBehaviour;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.lucene.queries.function.valuesource.ProductFloatFunction;
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
    public void testMethodGetRequest() throws Exception {
        ProducerService instance = new ProducerService();
        System.out.println("getRequest");
        int expResult = 200; //2 Mo
        int waitTime = 3;
        
        List<ProducerBehaviour> producerBehaviourList = new ArrayList<>();
        producerBehaviourList.add(new ProducerBehaviour(0, 3, 12, 10));
        producerBehaviourList.add(new ProducerBehaviour(3, 6, 20, 1));
        HashMap<String, List<ProducerBehaviour>> producerBehaviours = new HashMap<>();
        producerBehaviours.put("aa", producerBehaviourList);
        producerBehaviourList = new ArrayList<>();
        producerBehaviourList.add(new ProducerBehaviour(2, 3000, 200, 2));
        producerBehaviours.put("bb", producerBehaviourList);
        instance.setProducerBehaviours(producerBehaviours);
        instance.setProcessingTime(waitTime);
        String result = instance.getRequest("bb"," ");
        byte[] resultByte = instance.encodeUTF8(result);
        int size = resultByte.length;
        assertEquals(expResult, size);
    }

    /**
     * Test of getDataSize method, of class ProducerService.
     */
    @Test
    public void testGetDataSize() throws Exception {
        System.out.println("getDataSize");
        ProducerService instance = new ProducerService();
        List<ProducerBehaviour> producerBehaviourList = new ArrayList<>();
        producerBehaviourList.add(new ProducerBehaviour(0, 3, 12, 10));
        producerBehaviourList.add(new ProducerBehaviour(3, 6, 20, 1));
        HashMap<String, List<ProducerBehaviour>> producerBehaviours = new HashMap<>();
        producerBehaviours.put("aa", producerBehaviourList);
        producerBehaviourList = new ArrayList<>();
        producerBehaviourList.add(new ProducerBehaviour(2, 35, 1, 2));
        producerBehaviourList.add(new ProducerBehaviour(0, 2, 3, 4));
        producerBehaviours.put("bb", producerBehaviourList);
        instance.setProducerBehaviours(producerBehaviours);
        int result = instance.getDataSize("bb", 2);
        int expResult = 3;
        assertEquals(expResult, result);
    }

}
