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

        switch (MessageFactory.getInstance().identifyMessage(stringMessage)) {
            case CONFIGURATIONPRODUCER:
            	if (jsonMessage.getJSONObject("to").toString().equals(model.getId())
            	    && model.getState() == Model.State.WAITCONFIG) {
                        MessageConfigurationProducer message=MessageFactory.getInstance().getMessageConfigurationProducerFromJson(stringMessage);
                        model.setProducerBehaviours(message.getProducerBehaviours());
                        model.setDataSize(message.getDatasize());
                        model.setName(message.getName());
                        model.setState(Model.State.WAITSTART);
            	}
            case START:
                if (model.getState() == Model.State.WAITSTART) {
                    model.setState(Model.State.RUN);
                }
        }
        

    }

}
