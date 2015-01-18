package com.swim.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.swim.producer.Model;
import com.swim.producer.ProducerService;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import messaging.MessageConfigurationProducer;


import messaging.MessageConfigurationProducer;

/**
 *
 * @author Thomas
 */
public class MessageHandler {

    private Model model ;
    public MessageHandler(Model model) {
        super();
        this.model = model;
    }

    public void handleMessage(String jsonMessage) {
        MessageConfigurationProducer message = fromJSONtoMessage(jsonMessage);
        
    }

    /**
     *
     * @param  message : the JSON message
     * @return the object Message created or null if error
     */
    public MessageConfigurationProducer fromJSONtoMessage(String message) {
        ObjectMapper mapper = new ObjectMapper();
        MessageConfigurationProducer messageConfiguration = null;
        try {
            messageConfiguration = mapper.readValue(message, MessageConfigurationProducer.class);
            //producerService.setProcessingTime(messageConfiguration.getDuration());
            //producerService.setResponseSize(messageConfiguration.getDataSize());
        } catch (IOException ex) {
            Logger.getLogger(MessageHandler.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error during reading value");
        }
        return messageConfiguration;
    }
}
