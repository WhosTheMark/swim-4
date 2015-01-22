package fr.insa.toulouse.tp2g2.mainApp;

import static org.junit.Assert.*;

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

import org.junit.Test;


public class MessageIsParsedIntoJsonTest {

	private final static String MESSAGE_ERROR_JSON = "{\"from\":\"me\",\"to\":\"you\",\"type\":\"ERROR\",\"errorMessage\":\"NullPointerException\"}";
	private final static String MESSAGE_CONFIGURATION_CONSUMER_JSON = "{\"from\":\"me\",\"to\":\"you\",\"type\":\"CONFIGURATIONCONSUMER\",\"producerId\":\"p1\",\"name\":\"consumer1\",\"consumerBehaviours\":[{\"begin\":0,\"end\":10,\"frequency\":12,\"datasize\":20}]}";
	private final static String MESSAGE_CONFIGURATION_PRODUCER_JSON = "{\"from\":\"me\",\"to\":\"you\",\"type\":\"CONFIGURATIONPRODUCER\",\"name\":\"p1\",\"datasize\":10,\"producerBehaviours\":{\"consumer1\":[{\"begin\":0,\"end\":100,\"processingTime\":12}]}}";
	private final static String MESSAGE_RESULT_JSON = "{\"from\":\"me\",\"to\":\"you\",\"type\":\"RESULT\",\"consumerId\":\"c1\",\"producerId\":\"p1\",\"requestTime\":2,\"responseTime\":5,\"requestDataSize\":10,\"responseDataSize\":30,\"status\":\""+MessageResult.STATUS_OK+"\"}";
    private static final String MESSAGE_JSON = "{\"from\":\"me\",\"to\":\"you\",\"type\":\"START\"}";


	@Test
	public void testErrorMessageIsConvertedIntoJson() {
		MessageError messageError = new MessageError("me", "you", "NullPointerException");
		assertEquals(messageError.toJson(), MESSAGE_ERROR_JSON);
	}
	
	@Test
	public void testConfigurationConsumerMessageIsConvertedIntoJson() {
		List<ConsumerBehaviour> behaviours = new ArrayList<ConsumerBehaviour> ();
		behaviours.add(new ConsumerBehaviour(0, 10, 12, 20));
		MessageConfigurationConsumer message = new MessageConfigurationConsumer("me", "you", "p1", "consumer1", behaviours);
		assertEquals(message.toJson(),MESSAGE_CONFIGURATION_CONSUMER_JSON);
	}
	
	@Test
	public void testConfigurationProducerMessageIsConvertedIntoJson() {
		List<ProducerBehaviour> behaviours = new ArrayList<ProducerBehaviour> ();
		behaviours.add(new ProducerBehaviour(0, 100, 12));
		Map<String, List<ProducerBehaviour>> behaviorsMap = new HashMap<String, List<ProducerBehaviour>>();
		behaviorsMap.put("consumer1", behaviours);
		MessageConfigurationProducer message = new MessageConfigurationProducer("me", "you", "p1", 10,behaviorsMap);
		assertEquals( message.toJson(), MESSAGE_CONFIGURATION_PRODUCER_JSON);
	}
	
	@Test
	public void testResultMessageIsConvertedIntoJson() {
		MessageResult message = new MessageResult("me", "you", "c1", "p1", 2, 5, 10, 30, MessageResult.STATUS_OK);
		assertEquals(message.toJson(), MESSAGE_RESULT_JSON);
	}
	
	@Test
	public void testMessageToJson(){
	    Message message = new Message("me", "you");
	    assertEquals(message.toJson(), MESSAGE_JSON);
	}
	
}
