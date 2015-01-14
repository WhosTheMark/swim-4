package com.swim.messaging;


import com.swim.producer.Model;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Thomas
 */
public class MessageHandlerTest {

    public MessageHandlerTest() {
    }

    /**
     * Test of handleMessage method, of class MessageHandler.
     */
    @Test
    public void testFromJSONtoMessage() {
//        System.out.println("handleMessage");
//        String messageJson = "{\"from\":\"P\",\"to\":\"D\",\"name\":\"name\",\"producerBehaviour\":{\"aa\":[{\"begin\":0,\"end\":3,\"datasize\":12,\"processingTime\":10}]}}";
//        MessageConfigurationProducer expectedMessage = new MessageConfigurationProducer("P", "D", "name", null)
//        MessageHandler instance = new MessageHandler();
//        MessageConfigurationProducer message = instance.fromJSONtoMessage(messageJson);
//        assertNotNull(message);
//        assertEquals(expectedMessage.getDataSize(), message.getDataSize());
//        assertEquals(expectedMessage.getDuration(), message.getDuration());
//        assertEquals(expectedMessage.getFrom(), message.getFrom());
//        assertEquals(expectedMessage.getTo(), message.getTo());
    }

    /**
     * Test of handleMessage method, of class MessageHandler.
     */
    @Test
    public void testFromMessageToJSON() {
        System.out.println("handleMessage");
        MessageHandler instance = new MessageHandler(new Model(20));
         List<ProducerBehaviour> producerBehaviourList = new ArrayList<>();
        producerBehaviourList.add(new ProducerBehaviour(0, 3, 12, 10));
      
        HashMap<String, List<ProducerBehaviour>> producerBehaviours = new HashMap<>();
        producerBehaviours.put("aa", producerBehaviourList);
        MessageConfigurationProducer message = new MessageConfigurationProducer("P", "D", "name", producerBehaviours);
        String expectedMessage = "{\"from\":\"P\",\"to\":\"D\",\"name\":\"name\",\"producerBehaviour\":{\"aa\":[{\"begin\":0,\"end\":3,\"datasize\":12,\"processingTime\":10}]}}";
        System.out.println(message.toJson());
        assertEquals(expectedMessage, message.toJson());
    }
}
