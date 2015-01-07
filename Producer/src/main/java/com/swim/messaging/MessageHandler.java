package com.swim.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Thomas
 */
public class MessageHandler {

    public MessageHandler() {
        super();
    }

    public void handleMessage(String jsonMessage) {

    }

    /**
     *
     * @param the Json message
     * @return the object Message created or null if error
     */
    public MessageConfigurationProducer fromJSONtoMessage(String message) {
        ObjectMapper mapper = new ObjectMapper();
        MessageConfigurationProducer messageConfiguration = null;
        try {
            messageConfiguration = mapper.readValue(message, MessageConfigurationProducer.class);
        } catch (IOException ex) {
            Logger.getLogger(MessageHandler.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error during reading value");
        }
        return messageConfiguration;
    }
}
