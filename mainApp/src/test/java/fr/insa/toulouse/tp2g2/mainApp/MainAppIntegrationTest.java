package fr.insa.toulouse.tp2g2.mainApp;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

import jmsmainapp.*;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import controller.SWIMController;
import messaging.*;

public class MainAppIntegrationTest {
	
	private SWIMController swimController;
	private JMSManager mockManager;
	private JavaAppSender mockSender;
	private JavaAppReceiverThread mockReceiver;

	@Before
	public void setUp() {
		mockManager = mock(JMSManager.class);
		mockSender = mock(JavaAppSender.class);
		mockReceiver = mock(JavaAppReceiverThread.class);
		when(mockManager.getSender()).thenReturn(mockSender);
		when(mockManager.getReceiver()).thenReturn(mockReceiver);
		swimController = new SWIMController(mockManager);
	}
	
	@Test @Ignore
	public void testIntegrationUntilMessageSending() {	
		doAnswer(new Answer() {
		      public Object answer(InvocationOnMock invocation) {
		    	  try {
					swimController.getMessageHandler().getMessagesList()
					  	.put("{\"from\":\"c1\",\"to\":\"localhost\",\"type\":\"OK\"}");
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    	  return "ok";
		      }})
		  .when(mockReceiver).start();
		swimController.runScenario(ScenarioNames.SMALLSCENARIO);

		System.out.println("verifications");
		verify(mockSender, times(1)).send(createConsumer1Message());
		verify(mockSender, times(1)).send(createProducer1Message());
		verify(mockSender, times(1)).send(createStartMessage());
	}
	
	@Test
	public void testIntegrationFromReceivingMessages() {
		doAnswer(new Answer() {
		      public Object answer(InvocationOnMock invocation) {
		    	  BlockingQueue<String> messages
		    	  	= swimController.getMessageHandler().getMessagesList();
		    	  fillWithMockedMessages(messages);
		    	  return "ok";
		      }})
		  .when(mockReceiver).start();
		swimController.runScenario(ScenarioNames.SMALLSCENARIO);
	}
	
	private void fillWithMockedMessages(BlockingQueue<String> messages) {
		try {
			messages.put("{\"from\":\"c1\",\"to\":\"localhost\",\"type\":\"RESULT\",\"consumerId\":\"c1\",\"producerId\":\"p1\",\"requestTime\":1,\"responseTime\":5,\"requestDataSize\":10,\"responseDataSize\":30,\"status\":\""+MessageResult.STATUS_OK+"\"}");
			messages.put("{\"from\":\"c1\",\"to\":\"localhost\",\"type\":\"RESULT\",\"consumerId\":\"c1\",\"producerId\":\"p1\",\"requestTime\":2,\"responseTime\":-1,\"requestDataSize\":10,\"responseDataSize\":-1,\"status\":\""+MessageResult.STATUS_TIMEOUT+"\"}");
			messages.put("{\"from\":\"c1\",\"to\":\"localhost\",\"type\":\"RESULT\",\"consumerId\":\"c1\",\"producerId\":\"p1\",\"requestTime\":3,\"responseTime\":6,\"requestDataSize\":15,\"responseDataSize\":30,\"status\":\""+MessageResult.STATUS_OK+"\"}");
			messages.put("{\"from\":\"c1\",\"to\":\"localhost\",\"type\":\"RESULT\",\"consumerId\":\"c1\",\"producerId\":\"p1\",\"requestTime\":4,\"responseTime\":3,\"requestDataSize\":15,\"responseDataSize\":30,\"status\":\""+MessageResult.STATUS_OK+"\"}");
			messages.put("{\"from\":\"c1\",\"to\":\"localhost\",\"type\":\"RESULT\",\"consumerId\":\"c1\",\"producerId\":\"p1\",\"requestTime\":5,\"responseTime\":-1,\"requestDataSize\":10,\"responseDataSize\":-1,\"status\":\""+MessageResult.STATUS_TIMEOUT+"\"}");
			messages.put("{\"from\":\"c1\",\"to\":\"localhost\",\"type\":\"RESULT\",\"consumerId\":\"c1\",\"producerId\":\"p1\",\"requestTime\":6,\"responseTime\":4,\"requestDataSize\":10,\"responseDataSize\":30,\"status\":\""+MessageResult.STATUS_OK+"\"}");
			messages.put("{\"from\":\"c1\",\"to\":\"localhost\",\"type\":\"OK\"}");
		} catch(InterruptedException exception) {
			exception.printStackTrace();
		}
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
