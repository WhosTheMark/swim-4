package fr.insa.toulouse.tp2g2.mainApp;
import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import messaging.ConsumerBehaviour;
import messaging.Message;
import messaging.MessageConfigurationConsumer;
import messaging.MessageConfigurationProducer;
import messaging.MessageError;
import messaging.MessageResult;
import messaging.ProducerBehaviour;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MessageIsParsedIntoJson {
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}
	@Before
	public void setUp() throws Exception {
	}
	@After
	public void tearDown() throws Exception {
	}
	@Test
	public void testErrorMessageIsConvertedIntoJson() {
		MessageError messageError = new MessageError("me", "you", "NullPointerException");
		assertEquals("{\"from\":\"me\",\"to\":\"you\",\"errorMessage\":\"NullPointerException\"}", messageError.toJson());
	}
	@Test
	public void testConfigurationConsumerMessageIsConvertedIntoJson() {
		List<ConsumerBehaviour> behaviours = new ArrayList<ConsumerBehaviour> ();
		behaviours.add(new ConsumerBehaviour(0, 10, 12, 20));
		MessageConfigurationConsumer message = new MessageConfigurationConsumer("me", "you", "p1", "consumer1", behaviours);
		assertEquals("{\"from\":\"me\",\"to\":\"you\",\"producerId\":\"p1\",\"name\":\"consumer1\",\"consumerBehaviours\":[{\"begin\":0,\"end\":10,\"frequency\":12,\"datasize\":20}]}", message.toJson());
	}
	@Test
	public void testConfigurationProducerMessageIsConvertedIntoJson() {
		List<ProducerBehaviour> behaviours = new ArrayList<ProducerBehaviour> ();
		behaviours.add(new ProducerBehaviour(0, 100, 12));
		Map<String, List<ProducerBehaviour>> behaviorsMap = new HashMap<String, List<ProducerBehaviour>>();
		behaviorsMap.put("consumer1", behaviours);
		MessageConfigurationProducer message = new MessageConfigurationProducer("me", "you", "p1", 10,behaviorsMap);
		assertEquals("{\"from\":\"me\",\"to\":\"you\",\"name\":\"p1\",\"datasize\":10,\"producerBehaviours\":{\"consumer1\":[{\"begin\":0,\"end\":100,\"processingTime\":12}]}}", message.toJson());
	}
	@Test
	public void testResultMessageIsConvertedIntoJson() {
		MessageResult message = new MessageResult("me", "you", "c1", "p1", 2, 5, 10, 30, MessageResult.STATUS_OK);
		assertEquals("{\"from\":\"me\",\"to\":\"you\",\"type\":\"RESULT\",\"consumerId\":\"c1\",\"producerId\":\"p1\",\"requestTime\":2,\"responseTime\":5,\"requestDataSize\":10,\"responseDataSize\":30,\"status\":\""+MessageResult.STATUS_OK+"\"}", message.toJson());
	}
}
