package fr.insa.toulouse.tp2g2.mainApp;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

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
	public void testGetValidResultMessagesFromDB() {
		MessageResult mr1 = new MessageResult("him", "JavaApp", "c1", "p1", 2, 5, 10, 30);
		MessageResult mr2 = new MessageResult("me", "JavaApp", "c2", "p2", 2, 5, 10, 30);
		MessageResult mr3 = new MessageResult("me", "JavaApp", "c1", "p1", 3, 5, 20, 30);
		MessageError me1 = new MessageError("me", "you", "NullPointerException");
		
		// .store() stores under collection swim/result
		ArrayList<String> idToDelete = new ArrayList<String>();
		idToDelete.add(mr1.store());
		idToDelete.add(mr2.store());
		idToDelete.add(mr3.store());
		idToDelete.add(me1.store());
		
		List<MessageResult> actual = Message.getMessageResults();
		List<MessageResult> expected = new ArrayList<MessageResult>();
		expected.add(mr1);
		expected.add(mr2);
		expected.add(mr3);
		System.out.println("Result Messages");
		System.out.println("Expected = "+expected.toString());
		System.out.println("Actual = "+actual.toString());
		assertEquals(expected.size(), actual.size());
	
		List<MessageError> actualErrors = Message.getMessageErrors();
		List<MessageError> expectedErrors = new ArrayList<MessageError>();
		expectedErrors.add(me1);
		System.out.println("Error Messages");
		System.out.println("Expected = "+expected.toString());
		System.out.println("Actual = "+actual.toString());
		assertEquals(expectedErrors.size(), actualErrors.size());
		
		// Let's clean up the mess !
		for (int i = 0; i < 3; i++){
			Message.delete(MessageResult.class.toString(), idToDelete.get(i));
		}
		Message.delete(MessageError.class.toString(), idToDelete.get(3));

	}
	
}
