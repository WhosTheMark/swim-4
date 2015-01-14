/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swim.producer;

import com.swim.messaging.ProducerBehaviour;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Thomas
 */
public class ModelTest {
    
    public ModelTest() {
    }
    

    
    @Before
    public void setUp() {
    }
  

    /**
     * Test of getProcessingTime method, of class Model.
     */
    @Test
    public void testGetProcessingTime() {
        System.out.println("getProcessingTime");
        Model instance = new Model(2048);
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
        int result = instance.getProcessingTime("aa", 1);
        int expResult = 10;
        assertEquals(expResult, result);
    }    
}
