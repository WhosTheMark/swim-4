package com.swim.messaging;


import com.swim.producer.Model;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.junit.Test;

import com.swim.producer.ProducerService;
import java.util.Map;

import messaging.Message;
import messaging.MessageConfigurationProducer;
import messaging.ProducerBehaviour;

import static org.junit.Assert.*;

/**
 *
 * @author Thomas
 */
public class MessageHandlerTest {
	MessageConfigurationProducer message;
	public MessageHandlerTest() {
		List<ProducerBehaviour> behaviours = new ArrayList<> ();
		behaviours.add(new ProducerBehaviour(0, 100, 12));
		Map<String, List<ProducerBehaviour>> behaviorsMap = new HashMap<String, List<ProducerBehaviour>>();
		behaviorsMap.put("producer1", behaviours);
		message = new MessageConfigurationProducer("me", "you", "p1", 10,behaviorsMap);
	}
	/**
	 * Test of handleMessage method, of class MessageHandler.
	 */
	@Test
	public void testFromJSONtoMessage() {
		String messageJson = "{\"from\":\"me\",\"to\":\"you\",\"name\":\"p1\",\"datasize\":10,\"producerBehaviours\":{\"producer1\":[{\"begin\":0,\"end\":100,\"processingTime\":12}]}}";
		MessageHandler instance = new MessageHandler();
		MessageConfigurationProducer messageGenerated = instance.fromJSONtoMessage(messageJson);
		//MessageConfigurationProducer messageGenerated = Message.getMessageConfigurationProducerFromJson(messageJson);
		assertNotNull(messageGenerated);
		assertEquals(message.getDatasize(), messageGenerated.getDatasize());
		System.out.println(message.getProducerBehaviours().get("producer1"));
		System.out.println(messageGenerated.getProducerBehaviours().get("producer1"));
//		assertEquals(message.getProducerBehaviours().get("producer1"),
//				messageGenerated.getProducerBehaviours().get("producer1"));
		assertEquals(message.getFrom(), messageGenerated.getFrom());
		assertEquals(message.getTo(), messageGenerated.getTo());
		assertEquals(message.getName(), messageGenerated.getName());
	}

	/**
	 * Test of handleMessage method, of class MessageHandler.
	 */
	@Test
	public void testFromMessageToJSON() {
		String expectedMessage = "{\"from\":\"me\",\"to\":\"you\",\"type\":\"CONFIGURATIONPRODUCER\",\"name\":\"p1\",\"datasize\":10,\"producerBehaviours\":{\"producer1\":[{\"begin\":0,\"end\":100,\"processingTime\":12}]}}";
		assertEquals(expectedMessage, message.toJson());
	}
}
