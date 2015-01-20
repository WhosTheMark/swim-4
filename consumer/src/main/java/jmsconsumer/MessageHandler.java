package jmsconsumer;


import messaging.MessageConfigurationConsumer;
import messaging.MessageFactory;
import messaging.MessageType;

import esbcomunication.BehaviorScheduler;
import esbcomunication.BehaviorScheduler.BehaviorSchedulerBuilder;

public class MessageHandler {
	
	private String consumerName ;
	private String esbAddr ;
	private BehaviorScheduler scheduler = null ;
	
	/**
	 * MessageHandler Constructor
	 * @param name
	 * @param esbAddr
	 */
	public MessageHandler(String name, String esbAddr) {
		this.consumerName = name ;
		this.esbAddr = esbAddr ;
	}

	/**
	 * Handle consumer behavior according to the message receive
	 * @param message
	 */
	public void handleMessage(String message) {
		
		MessageFactory msg = MessageFactory.getInstance() ;
		if (msg.identifyMessage(message).equals(MessageType.CONFIGURATIONCONSUMER)) {
			if (msg.getMessageFromJson(message).getTo().equals(consumerName)) {
				configConsumer(message);
			}
		} else if (msg.identifyMessage(message).equals(MessageType.START)) {
			startconsumer() ;
		}
	}

	private void startconsumer() {
		if (scheduler!=null) {
			scheduler.scheduleBehaviors();
		} else {
			System.err.println("Start before configuration !!\n");
		}
			
	}

	private void configConsumer(String message) {
		BehaviorSchedulerBuilder builder = new BehaviorSchedulerBuilder() ;

		MessageConfigurationConsumer consumerConfiguration = MessageFactory.getInstance().getMessageConfigurationConsumerFromJson(message);
		builder.setBehaviors(consumerConfiguration.getConsumerBehaviours());
		builder.setConsumerId(consumerName);
		builder.setESBAddress(esbAddr);
		
		scheduler = builder.build() ;
	}

}
