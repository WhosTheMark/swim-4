package fr.insa.toulouse.tp2g2.mainApp;

import static org.junit.Assert.*;

import java.util.ArrayList;

import messaging.Message;
import messaging.MessageError;
import messaging.MessageResult;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class MessagesAreRetrievedFromDB {

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
	public void testGetValidMessagesFromDB() {
		MessageResult mr1 = new MessageResult("him", "JavaApp", "c1", "p1", 2, 5, 10, 30);
		MessageResult mr2 = new MessageResult("me", "JavaApp", "c2", "p2", 2, 5, 10, 30);
		MessageResult mr3 = new MessageResult("me", "JavaApp", "c1", "p1", 3, 5, 20, 30);
		MessageError me1 = new MessageError("me", "you", "NullPointerException");
		// .store() stores under collection swim/result
		mr1.store();
		mr2.store();
		mr3.store();
		me1.store();
		
		ArrayList<Message> actual = Message.getResults();
		ArrayList<Message> expected = new ArrayList<Message>();
		expected.add(mr1);
		expected.add(mr2);
		expected.add(mr3);
		assertEquals(expected.toString(), actual.toString());
		ArrayList<Message> actualErrors = Message.getErrors();
		ArrayList<Message> expectedErrors = new ArrayList<Message>();
		expectedErrors.add(me1);
		assertEquals(expectedErrors.toString(), actualErrors.toString());
		fail("Not yet implemented");
	}

}
