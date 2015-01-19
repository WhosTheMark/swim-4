package com.swim.producer;

import messaging.ProducerBehaviour;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.lucene.queries.function.valuesource.ProductFloatFunction;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author Thomas
 */
public class ProducerServiceTest {

    private ProducerService instance;

    public ProducerServiceTest() {
    }

    @Before
    public void setUp() {
        instance = new ProducerService();
    }

    @Test
    public void getRequestWhenNotReady() {
        setUp();
        instance.getModel().setState(Model.State.WAITCONFIG);
        String expectedResult = null;
        String result = instance.getRequest("aa", "ppopo");
        assertEquals(expectedResult, result);
    }

    @Test
    public void getRequestWhenNoBehaviour() {
        setUp();
        clearBehaviour();
        String expectedResult = null;
        instance.getModel().setState(Model.State.RUN);
        String result = instance.getRequest("aa", "ppopo");
        assertEquals(expectedResult, result);
    }

    /**
     * Test of getRequest method, of class ProducerService.
     */
    @Test
    public void testMethodGetRequest() {
        setUp();
        instance.getModel().setDataSize(200);
        instance.getModel().setState(Model.State.RUN);
        int expResult = 200; //2 Mo
        int waitTime = 3;
        addBehaviour();
        String result = instance.getRequest("bb", " ");
        byte[] resultByte = instance.getModel().encodeUTF8(result);
        int size = resultByte.length;
        assertEquals(expResult, size);
    }

    public void addBehaviour() {
        List<ProducerBehaviour> producerBehaviourList = new ArrayList<>();
        producerBehaviourList.add(new ProducerBehaviour(0, 3, 10));
        producerBehaviourList.add(new ProducerBehaviour(3, 6, 1));
        HashMap<String, List<ProducerBehaviour>> producerBehaviours = new HashMap<>();
        producerBehaviours.put("aa", producerBehaviourList);
        producerBehaviourList = new ArrayList<>();
        producerBehaviourList.add(new ProducerBehaviour(2, 3000, 2));
        producerBehaviours.put("bb", producerBehaviourList);
        instance.getModel().setProducerBehaviours(producerBehaviours);
    }

    public void clearBehaviour() {
        instance.getModel().setProducerBehaviours(null);
    }
}
