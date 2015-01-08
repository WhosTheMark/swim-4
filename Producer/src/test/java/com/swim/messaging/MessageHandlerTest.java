package com.swim.messaging;


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
        System.out.println("handleMessage");
        String messageJson = "{\"from\":\"JavaApp\",\"to\":\"producer\",\"duration\":3000,\"dataSize\":2048}";
        MessageConfigurationProducer expectedMessage = new MessageConfigurationProducer("JavaApp", "producer", 3000, 2048);
        MessageHandler instance = new MessageHandler();
        MessageConfigurationProducer message = instance.fromJSONtoMessage(messageJson);
        assertNotNull(message);
        assertEquals(expectedMessage.getDataSize(), message.getDataSize());
        assertEquals(expectedMessage.getDuration(), message.getDuration());
        assertEquals(expectedMessage.getFrom(), message.getFrom());
        assertEquals(expectedMessage.getTo(), message.getTo());
    }

    /**
     * Test of handleMessage method, of class MessageHandler.
     */
    @Test
    public void testFromMessageToJSON() {
        System.out.println("handleMessage");
        MessageHandler instance = new MessageHandler();
        MessageConfigurationProducer message = new MessageConfigurationProducer("P", "D", 3, 4);
        String expectedMessage = "{\"from\":\"P\",\"to\":\"D\",\"duration\":3,\"dataSize\":4}";
        assertEquals(expectedMessage, message.toJson());
    }
}
