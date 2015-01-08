package fr.insa.toulouse.tp2g2.mainApp;

import static org.junit.Assert.*;

import messaging.Message;
import messaging.MessageResult;

import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.node.Node;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import database.Database;
import static org.elasticsearch.node.NodeBuilder.*;

public class MessageResultStoredInDatabase {

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
	public void testResultMessageStoredInDatabase() {
		MessageResult message = new MessageResult("me", "you", "c1", "p1", 2, 5, 10, 30);
		String _id = message.store();
		// Message stored. Let's look for it in database.
		
        // get a specific object
	     // Initialize client to work with DB
        Node node = nodeBuilder().client(true).node();
        Client client = node.client();
		GetResponse responseGet = client.prepareGet(Database.DATABASE_NAME, MessageResult.class.toString(), _id)
                .execute()
                .actionGet();
		assertEquals("{\"from\":\"me\",\"to\":\"you\",\"consumerId\":\"c1\",\"producerId\":\"p1\",\"requestTime\":2,\"responseTime\":5,\"requestDataSize\":10,\"responseDataSize\":30}", responseGet.getSourceAsString());
		node.close();
		
		// Clean the database
		Message.delete(MessageResult.class.toString(), _id);
	}

}
