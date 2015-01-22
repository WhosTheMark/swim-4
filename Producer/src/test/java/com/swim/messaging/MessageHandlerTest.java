package com.swim.messaging;


import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.swim.producer.Model;

import java.awt.TrayIcon.MessageType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.swim.producer.ProducerService;
import java.util.Map;

import jmsproducer.JMSManager;

import messaging.Message;
import messaging.MessageConfigurationConsumer;
import messaging.MessageConfigurationProducer;
import messaging.MessageFactory;
import messaging.ProducerBehaviour;

import static org.junit.Assert.*;

/**
 *
 * @author Thomas
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(JMSManager.class)


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
		MessageConfigurationProducer messageGenerated = MessageFactory.getInstance().getMessageConfigurationProducerFromJson(messageJson);
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
	
	/**
	 * Configuration->equal==OK
	 */
	@Test
	public void testHandleMessage(){
		String messageJSON = "{\"from\":\"me\",\"to\":\"you\",\"type\":\"CONFIGURATIONPRODUCER\",\"name\":\"p1\",\"datasize\":10,\"producerBehaviours\"" +
				":{\"producer1\":[{\"begin\":0,\"end\":100,\"processingTime\":12}]}}";
		MessageConfigurationProducer messageGenerated = MessageFactory.getInstance().getMessageConfigurationProducerFromJson(messageJSON);
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
	 * Type!=(Start||ConfigurationProducer)
	 */
	@Test
	public void testHandleMessage2(){
		

		String messageJSON2 = "{\"from\":\"me\",\"to\":\"you\",\"type\":\"CONFIGURATIONPRODUCER\",\"name\":\"p1\",\"datasize\":10,\"producerBehaviours\"" +
				":{\"producer1\":[{\"begin\":0,\"end\":100,\"processingTime\":12}]}}";
		MessageConfigurationProducer messageGenerated2 = MessageFactory.getInstance().getMessageConfigurationProducerFromJson(messageJSON2);
		assertNotNull(messageGenerated2);
		assertEquals(message.getDatasize(), messageGenerated2.getDatasize());
		System.out.println(message.getProducerBehaviours().get("producer1"));
		System.out.println(messageGenerated2.getProducerBehaviours().get("producer1"));
//		assertEquals(message.getProducerBehaviours().get("producer1"),
//				messageGenerated.getProducerBehaviours().get("producer1"));
		String messageJSON = "{\"from\":\"me\",\"to\":\"you\",\"type\":\"CONFIGURATIONCONSUMER\",\"producerId\":\"p1\",\"name\":\"consumer1\",\"consumerBehaviours\":" +
				"[{\"begin\":0,\"end\":10,\"frequency\":12,\"datasize\":20}]}";
		MessageConfigurationConsumer messageGenerated=MessageFactory.getInstance().getMessageConfigurationConsumerFromJson(messageJSON);
		assertEquals(message.getFrom(), messageGenerated2.getFrom());
		assertEquals(message.getTo(), messageGenerated2.getTo());
		assertEquals(message.getName(), messageGenerated2.getName());
		
	}
	

	
	/**
	 * Start->state==WAIT
	 */
	@Test
	public void testHandleMessage4(){
		String messageJSON = "{\"from\":\"me\",\"to\":\"you\",\"type\":\"START\"}";
		Model.getInstance().setState(Model.State.WAITSTART);
		//Message messageGenerated = MessageFactory.getInstance().getMessageFromJson(messageJSON);
		MessageHandler handler=new MessageHandler();
		handler.handleMessage(messageJSON);
		//assertNotNull(messageGenerated);
		assertEquals(messaging.MessageType.START, MessageFactory.getInstance().identifyMessage(messageJSON));
		assertEquals(Model.State.RUN, Model.getInstance().getState());
		
	}
	/**
	 * Start->state==WAIT
	 */
	@Test
	public void testHandleMessage5(){
		String messageJSON = "{\"from\":\"me\",\"to\":\"you\",\"type\":\"START\"}";
		Message messageGenerated = MessageFactory.getInstance().getMessageConfigurationProducerFromJson(messageJSON);
	

		
	}
}
