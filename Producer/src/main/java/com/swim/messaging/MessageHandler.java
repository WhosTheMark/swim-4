package com.swim.messaging;

import com.swim.producer.Model;
import messaging.MessageFactory;
import messaging.MessageConfigurationProducer;

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
        MessageFactory msg = MessageFactory.getInstance();

        switch (msg.identifyMessage(stringMessage)) {
            case CONFIGURATIONPRODUCER:
                if (msg.getMessageConfigurationProducerFromJson(stringMessage).getTo().equals(model.getId())
                        && model.getState() == Model.State.WAITCONFIG) {
                    MessageConfigurationProducer message=msg.getMessageConfigurationProducerFromJson(stringMessage);
                        model.setProducerBehaviours(message.getProducerBehaviours());
                        model.setDataSize(message.getDatasize());
                        model.setName(message.getName());
                        model.setState(Model.State.WAITSTART);
                    System.out.println("Producer configure");
                }
                
            case START:
                System.out.println("start");
                if (model.getState() == Model.State.WAITSTART) {
                    model.setState(Model.State.RUN);
                }
        }
        

    }

}
