package com.swim.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.xml.ws.security.policy.MessageLayout;
import com.swim.producer.Model;
import com.swim.producer.ProducerService;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import messaging.Message;
import messaging.MessageFactory;
import messaging.MessageConfigurationProducer;

import messaging.MessageConfigurationProducer;
import messaging.MessageType;
import org.json.JSONObject;

/**
 *
 * @author Thomas
 */
public class MessageHandler {

    private Model model;

    public MessageHandler() {
        super();
        this.model = Model.getInstance();
    }

    public void handleMessage(String stringMessage) {
        JSONObject jsonMessage = new JSONObject(stringMessage);
        
            switch (MessageFactory.getInstance().identifyMessage(stringMessage)) {
                case CONFIGURATIONPRODUCER:
                	if (jsonMessage.getJSONObject("to").toString().equals(model.getId())) {
	                    if (model.getState() == Model.State.WAITCONFIG) {
	                        MessageConfigurationProducer message=MessageFactory.getInstance().getMessageConfigurationProducerFromJson(stringMessage);
	                        model.setProducerBehaviours(message.getProducerBehaviours());
	                        model.setDataSize(message.getDatasize());
	                        model.setName(message.getName());
	                        model.setState(Model.State.WAITSTART);
	                    }
                	}
                case START:
                    if (model.getState() == Model.State.WAITSTART) {
                        model.setState(Model.State.RUN);
                    }
            }
        

    }

//    /**
//     *
//     * @param message : the JSON message
//     * @return the object Message created or null if error
//     */
//    public MessageConfigurationProducer fromJSONtoMessage(String message) {
//        ObjectMapper mapper = new ObjectMapper();
//        MessageConfigurationProducer messageConfiguration = null;
//        try {
//            messageConfiguration = mapper.readValue(message, MessageConfigurationProducer.class);
//            //producerService.setProcessingTime(messageConfiguration.getDuration());
//            //producerService.setResponseSize(messageConfiguration.getDataSize());
//        } catch (IOException ex) {
//            Logger.getLogger(MessageHandler.class.getName()).log(Level.SEVERE, null, ex);
//            System.out.println("Error during reading value");
//        }
//        return messageConfiguration;
//    }
}
