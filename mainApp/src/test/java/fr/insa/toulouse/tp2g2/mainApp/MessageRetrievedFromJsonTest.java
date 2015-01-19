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

public class MessageRetrievedFromJsonTest {
	String messageErrorJson = "{\"from\":\"me\",\"to\":\"you\",\"errorMessage\":\"NullPointerException\"}";
	String messageConfigurationConsumerJson = "{\"from\":\"me\",\"to\":\"you\",\"type\":\"CONFIGURATIONCONSUMER\",\"producerId\":\"p1\",\"name\":\"consumer1\",\"consumerBehaviours\":[{\"begin\":0,\"end\":10,\"frequency\":12,\"datasize\":20}]}";
	String messageConfigurationProducerJson = "{\"from\":\"me\",\"to\":\"you\",\"name\":\"p1\",\"datasize\":10,\"producerBehaviours\":{\"consumer1\":[{\"begin\":0,\"end\":100,\"processingTime\":12}]}}";
	String messageResultJson = "{\"from\":\"me\",\"to\":\"you\",\"type\":\"RESULT\",\"consumerId\":\"c1\",\"producerId\":\"p1\",\"requestTime\":2,\"responseTime\":5,\"requestDataSize\":10,\"responseDataSize\":30,\"status\":\""+MessageResult.STATUS_OK+"\"}";
	
	@Test
	public void testErrorMessageIsRetrievedFromJson() {
		MessageError messageError = new MessageError("me", "you", "NullPointerException");
		assertEquals(Message.getMessageErrorFromJson(messageErrorJson), messageError);
	}
	@Test
	public void testConfigurationConsumerMessageIsRetrievedFromJson() {
		List<ConsumerBehaviour> behaviours = new ArrayList<ConsumerBehaviour> ();
		behaviours.add(new ConsumerBehaviour(0, 10, 12, 20));
		MessageConfigurationConsumer message = new MessageConfigurationConsumer("me", "you", "p1", "consumer1", behaviours);
		assertEquals(Message.getMessageConfigurationConsumerFromJson(messageConfigurationConsumerJson), message);
	}
	@Test
	public void testConfigurationProducerMessageIsRetrievedFromJson() {
		List<ProducerBehaviour> behaviours = new ArrayList<ProducerBehaviour> ();
		behaviours.add(new ProducerBehaviour(0, 100, 12));
		Map<String, List<ProducerBehaviour>> behaviorsMap = new HashMap<String, List<ProducerBehaviour>>();
		behaviorsMap.put("consumer1", behaviours);
		MessageConfigurationProducer message = new MessageConfigurationProducer("me", "you", "p1", 10,behaviorsMap);
		assertEquals(Message.getMessageConfigurationProducerFromJson(messageConfigurationProducerJson), message);
	}
	@Test
	public void testResultMessageIsRetrievedFromJson() {
		MessageResult message = new MessageResult("me", "you", "c1", "p1", 2, 5, 10, 30, MessageResult.STATUS_OK);
		assertEquals(Message.getMessageResultFromJson(messageResultJson), message);
	}

}
