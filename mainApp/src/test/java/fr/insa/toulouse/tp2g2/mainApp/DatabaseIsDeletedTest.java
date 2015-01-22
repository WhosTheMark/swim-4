package fr.insa.toulouse.tp2g2.mainApp;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import messaging.Message;
import messaging.MessageConfigurationProducer;
import messaging.MessageResult;
import messaging.ProducerBehaviour;

import org.junit.Before;
import org.junit.Test;

import database.Database;

public class DatabaseIsDeletedTest {

	@Before
	public void setUp() throws Exception {
		
	}
	
	@Test
	public void testDropEmptyDatabase() throws IOException {
		Database.dropDatabase();
		assertEquals(0,Message.getMessageResults().size());
		assertEquals(0,Message.getMessageConfigurationConsumers().size());
	}
	
	@Test
	public void testDropDatabase() throws IOException {
		putThingsIntoDB();
		Database.dropDatabase();
		assertEquals(0,Message.getMessageResults().size());
		assertEquals(0,Message.getMessageConfigurationConsumers().size());
	}
	
	private void putThingsIntoDB(){
		MessageResult messageR = new MessageResult("me", "you", "c1", "p1", 2, 5, 10, 30, MessageResult.STATUS_OK);
		List<ProducerBehaviour> behaviours = new ArrayList<ProducerBehaviour> ();
		behaviours.add(new ProducerBehaviour(0, 100, 12));
		Map<String, List<ProducerBehaviour>> behaviorsMap = new HashMap<String, List<ProducerBehaviour>>();
		behaviorsMap.put("consumer1", behaviours);
		MessageConfigurationProducer messageC = new MessageConfigurationProducer("me", "you", "p1", 10,behaviorsMap);
		
		messageR.store();
		messageC.store();
	}

}
