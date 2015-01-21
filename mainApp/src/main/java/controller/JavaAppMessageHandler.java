package controller;

import java.util.List;
import java.util.concurrent.BlockingQueue;

import messaging.Message;
import messaging.MessageError;
import messaging.MessageFactory;
import messaging.MessageResult;
import messaging.MessageType;

public class JavaAppMessageHandler {

	private MessageFactory factory;
	private BlockingQueue<String> messages;
	private SWIMController controller;
	private List<String> consumers;
	
	public JavaAppMessageHandler(BlockingQueue<String> messages, SWIMController controller) {
		factory = MessageFactory.getInstance();
		this.messages = messages;
		this.controller = controller;
	}
	
	public void setConsumersID(List<String> consumers) {
		this.consumers = consumers;
	}
	
	public BlockingQueue<String> getMessagesList() {
		return messages;
	}
	
	public void handleMessages() {
		while(controller.keepRunning()) {
			System.out.println("hey");
			try {
                String json = messages.take();
                handleMessage(json);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
			System.out.println(controller.keepRunning());
		}
	}
	
	private void handleMessage(String json) {
		System.out.println(json);
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
			handleTerminatedConsumerMessage(json);
			break;
	    default:
	    	throw new SWIMException("ERROR - message of type " + type + " not expected" );
		}
		
	}
	
	private void handleTerminatedConsumerMessage(String json) {
		
	    Message msg = factory.getMessageFromJson(json);
		String consumerName = msg.getFrom();
		consumers.remove(consumerName);
		
		if(consumers.isEmpty()){
		    controller.handleAllConsumersHaveFinished();
		}
	}
	
	
}
