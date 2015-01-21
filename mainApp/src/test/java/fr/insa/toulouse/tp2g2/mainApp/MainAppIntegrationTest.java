package fr.insa.toulouse.tp2g2.mainApp;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jmsmainapp.*;

import org.junit.Test;

import controller.SWIMController;
import messaging.*;

public class MainAppIntegrationTest {
	
	private SWIMController swimController;

	@Test
	public void testIntegrationUntilMessageSending() {
		JMSManager mockManager = mock(JMSManager.class);
		JavaAppSender mockSender = mock(JavaAppSender.class);
		JavaAppReceiverThread mockReceiver = mock(JavaAppReceiverThread.class);
		when(mockManager.getSender()).thenReturn(mockSender);
		when(mockManager.getReceiver()).thenReturn(mockReceiver);
		swimController = new SWIMController(mockManager);
		swimController.runScenario(ScenarioNames.SMALLSCENARIO);
		verify(mockSender, times(1)).send(createConsumer1Message());
		verify(mockSender, times(1)).send(createProducer1Message());
		verify(mockSender, times(1)).send(createStartMessage());
	}
	
	private Message createStartMessage() {
		return new Message(null,swimController.getBroadcastValue());
	}
	
	private MessageConfigurationConsumer createConsumer1Message() {
		List<ConsumerBehaviour> behaviours = new ArrayList<ConsumerBehaviour>();
		behaviours.add(new ConsumerBehaviour(1, 10, 1, 125));
		return new MessageConfigurationConsumer("c1","p1","consumer1", behaviours);
	}
	
	private MessageConfigurationProducer createProducer1Message() {
		List<ProducerBehaviour> behaviours = new  ArrayList<ProducerBehaviour>();
		behaviours.add(new ProducerBehaviour(1,10,2));
		Map<String, List<ProducerBehaviour>> pBehaviours = new HashMap<String,List<ProducerBehaviour>>();
		pBehaviours.put("c1",behaviours);
		return new MessageConfigurationProducer("p1", "producer1", 40, pBehaviours);
	}

}
