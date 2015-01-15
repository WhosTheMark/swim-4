package com.swim.producer;

import messaging.ProducerBehaviour;
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
        ProducerService instance = new ProducerService(200);
        System.out.println("getRequest");
        int expResult = 200; //2 Mo
        int waitTime = 3;
        
        List<ProducerBehaviour> producerBehaviourList = new ArrayList<>();
        producerBehaviourList.add(new ProducerBehaviour(0, 3, 10));
        producerBehaviourList.add(new ProducerBehaviour(3, 6, 1));
        HashMap<String, List<ProducerBehaviour>> producerBehaviours = new HashMap<>();
        producerBehaviours.put("aa", producerBehaviourList);
        producerBehaviourList = new ArrayList<>();
        producerBehaviourList.add(new ProducerBehaviour(2, 3000, 2));
        producerBehaviours.put("bb", producerBehaviourList);
        System.out.println(instance.getModel().getData());
        instance.getModel().setProducerBehaviours(producerBehaviours);
        String result = instance.getRequest("bb"," ");
        byte[] resultByte = instance.getModel().encodeUTF8(result);
        int size = resultByte.length;
        assertEquals(expResult, size);
    }

}
