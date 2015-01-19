package jmsconsumer;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import messaging.MessageConfigurationConsumer;

import org.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;

import esbcomunication.BehaviorScheduler;
import esbcomunication.BehaviorScheduler.BehaviorSchedulerBuilder;

public class MessageHandler {
	
	private String consumerName ;
	private String esbAddr ;
	private BehaviorScheduler scheduler = null ;
	

	public MessageHandler(String name, String esbAddr) {
		this.consumerName = name ;
		this.esbAddr = esbAddr ;
	}

	public void handleMessage(String message) {
		JSONObject jsonMessage = new JSONObject (message) ;
		if (forThisConsumer(jsonMessage)) {
			if (isConfiguration(jsonMessage)) {
				configConsumer(message) ;
			} else if (isStart(jsonMessage)){
				startconsumer() ;
			}
		}
	}

	// à modifier
	private boolean isConfiguration(JSONObject jsonMessage) {
		return (jsonMessage.getJSONObject("type").toString()=="configuration");
	}

	// à modifier
	private boolean isStart(JSONObject jsonMessage) {
		return (jsonMessage.getJSONObject("type").toString()=="start") ;
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
		
		MessageConfigurationConsumer consumerConfiguration = fromJSONtoMessage(message);
		builder.setBehaviors(consumerConfiguration.getConsumerBehaviours());
		builder.setConsumerId(consumerName);
		builder.setESBAddress(esbAddr);
		
		scheduler = builder.build() ;
	}

	private boolean forThisConsumer(JSONObject jsonMessage) {
		return (jsonMessage.getJSONObject("to").toString()==consumerName) ;
	}
	
	// dans la classe message (main app)
	public MessageConfigurationConsumer fromJSONtoMessage(String message) {
        ObjectMapper mapper = new ObjectMapper();
        MessageConfigurationConsumer messageConfiguration = null;
        try {
            messageConfiguration = mapper.readValue(message, MessageConfigurationConsumer.class);
            //producerService.setProcessingTime(messageConfiguration.getDuration());
            //producerService.setResponseSize(messageConfiguration.getDataSize());
        } catch (IOException ex) {
            Logger.getLogger(MessageHandler.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error during reading value");
        }
        return messageConfiguration;
    }

}
