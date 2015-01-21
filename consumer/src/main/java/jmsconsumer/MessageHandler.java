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
	public boolean handleMessage(String message) {
		
		MessageFactory msg = MessageFactory.getInstance() ;
		if (msg.identifyMessage(message).equals(MessageType.CONFIGURATIONCONSUMER)) {
			if (msg.getMessageConfigurationConsumerFromJson(message).getTo().equals(consumerName)) {
				configConsumer(message);
			}
		} else if (msg.identifyMessage(message).equals(MessageType.START)) {
			startconsumer() ;
			return false;
		}
		
		return true;
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
		builder.setProducerId(consumerConfiguration.getProducerId());
		builder.setESBAddress(esbAddr);
		
		System.out.println("Consumer Configuration");
		
		scheduler = builder.build() ;
	}

}
