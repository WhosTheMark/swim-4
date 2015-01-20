package controller;

import java.util.Iterator;
import java.util.List;

import messaging.MessageError;
import messaging.MessageFactory;
import messaging.MessageResult;
import messaging.MessageType;

public class JavaAppMessageHandler extends Thread {

	private MessageFactory factory;
	private List<String> messages;
	private SWIMController controller;
	
	public JavaAppMessageHandler(List<String> messages, SWIMController controller) {
		factory = MessageFactory.getInstance();
		this.messages = messages;
		this.controller = controller;
	}
	
	public void run() {
		while(controller.keepRunning()) {
			Iterator<String> it = messages.iterator();
			while(it.hasNext()) {
				handleMessage(it.next());
				it.remove();
			}
		}
	}
	
	private void handleMessage(String json) {
		MessageType type = factory.identifyMessage(json);
		switch(type) {
		case RESULT: 
			MessageResult result = factory.getMessageResultFromJson(json);
			result.store();
			break;
		case ERROR:
			MessageError error = factory.getMessageErrorFromJson(json);
			error.store();
			break;
		case OK:
			handleTerminatedConsumerMessage();
	    default:
	    	throw new SWIMException("ERROR - message of type " + type + " not expected" );
		}
		
	}
	
	private void handleTerminatedConsumerMessage() {
		
	}
	
	
}
