package fr.insa.toulouse.tp2g2.mainApp;

import static org.junit.Assert.*;

import java.util.logging.ErrorManager;

import messaging.Message;
import messaging.MessageConfigurationConsumer;
import messaging.MessageConfigurationProducer;
import messaging.MessageError;
import messaging.MessageResult;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

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
	public void testGenericMessageIsConvertedIntoJson() {
		Message messageGeneric = new Message("me", "you");
		assertEquals("{\"from\":\"me\",\"to\":\"you\"}", messageGeneric.toJson());
	}

	@Test
	public void testErrorMessageIsConvertedIntoJson() {
		MessageError messageError = new MessageError("me", "you", "NullPointerException");
		assertEquals("{\"from\":\"me\",\"to\":\"you\",\"errorMessage\":\"NullPointerException\"}", messageError.toJson());
	}
	
	@Test
	public void testConfigurationConsumerMessageIsConvertedIntoJson() {
		MessageConfigurationConsumer message = new MessageConfigurationConsumer("me", "you", "p1", 0, 10, 12, 2, 20);
		assertEquals("{\"from\":\"me\",\"to\":\"you\",\"producerId\":\"p1\",\"begin\":0,\"end\":10,\"frequency\":12,\"processingTime\":2,\"dataSize\":20}", message.toJson());
	}
	
	@Test
	public void testConfigurationProducerMessageIsConvertedIntoJson() {
		MessageConfigurationProducer message = new MessageConfigurationProducer("me", "you", 2, 10);
		assertEquals("{\"from\":\"me\",\"to\":\"you\",\"duration\":2,\"dataSize\":10}", message.toJson());
	}	
	
	@Test
	public void testResultMessageIsConvertedIntoJson() {
		MessageResult message = new MessageResult("me", "you", "c1", "p1", 2, 5, 10, 30);
		assertEquals("{\"from\":\"me\",\"to\":\"you\",\"consumerId\":\"c1\",\"producerId\":\"p1\",\"requestTime\":2,\"responseTime\":5,\"requestDataSize\":10,\"responseDataSize\":30}", message.toJson());
	}	
}
