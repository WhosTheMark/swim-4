package scenario;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import messaging.*;
import model.*;
import jms.JavaAppSender;

public class Configurator {

	private JavaAppSender sender;
	
	public Configurator(JavaAppSender sender) {
		this.sender = sender;
	}
	
	public void sendConfigurationMessages(Scenario scenario) {
		sendConfigurationMessageToConsumers(scenario.getConsumers());
		sendConfigurationMessageToProducers(scenario);
	}
	
	private void sendConfigurationMessageToConsumers(Consumers consumers) {
		for(ConsumerT consumer: consumers.getConsumers()) {
			sendConfigurationMessageToConsumer(consumer);
		}
	}
	
	private void sendConfigurationMessageToConsumer(ConsumerT consumer) {
		MessageConfigurationConsumer message = createConsumerConfigurationMessage(consumer);
		try {
			sender.send(message);
		} catch(IOException exception) {
			System.out.println("ouch consumer");
		}
	}
	
	private MessageConfigurationConsumer createConsumerConfigurationMessage(ConsumerT consumer) {
		MessageConfigurationConsumer message = new MessageConfigurationConsumer();
		message.setName(consumer.getName());
		message.setTo(consumer.getId());
		message.setProducerId(((ProducerT)consumer.getProducerId()).getId());
		message.setConsumerBehaviours(determineConsumerBehaviours(consumer.getBehaviours()));
		return message;
	}
	
	private List<ConsumerBehaviour> determineConsumerBehaviours(List<BehaviourT> behaviours) {
		List<ConsumerBehaviour> consumerBehaviours = new ArrayList<ConsumerBehaviour> ();
		for(BehaviourT behaviour: behaviours) {
			consumerBehaviours.add(determineConsumerBehaviour(behaviour));
		}
		return consumerBehaviours;
	}
	
	private ConsumerBehaviour determineConsumerBehaviour(BehaviourT behaviour) {
		ConsumerBehaviour consumerBehaviour = null;
		if(isOKBehaviourConfiguration(behaviour)) {
			consumerBehaviour = new ConsumerBehaviour(behaviour.getBeginInMs(),
													  behaviour.getEndInMs(),
													  behaviour.getFrequency().intValue(),
													  behaviour.getDatasizeInBytes());
		} else {
			throw new ScenarioException("ERROR - Impossible consumer behaviour " + behaviour.toString());
		}
		return consumerBehaviour;
	}

	private boolean isOKBehaviourConfiguration(BehaviourT behaviour) {
		return true; //TODO
	}
	
	private void sendConfigurationMessageToProducers(Scenario scenario) {
		for(ProducerT producer: scenario.getProducers().getProducers()) {
			sendConfigurationMessageToProducer(producer,scenario);
		}
	}
	
	private void sendConfigurationMessageToProducer(ProducerT producer, Scenario scenario) {
		MessageConfigurationProducer message = createProducerConfigurationMessage(producer, scenario);
		try {
			sender.send(message);
		} catch(IOException exceptio) {
			System.out.println("ouch");
		}
	}
	
	private MessageConfigurationProducer createProducerConfigurationMessage(ProducerT producer, Scenario scenario) {
		Map<String, List<ProducerBehaviour>> pBehaviours
			= determineProducerBehaviours(producer.getId(), scenario.getConsumers().getConsumers());
		return new MessageConfigurationProducer(producer.getId(),
												producer.getName(),
												producer.getDatasizeInBytes(),
												pBehaviours);
	}
	
	private Map<String, List<ProducerBehaviour>> determineProducerBehaviours(String producerId, List<ConsumerT> consumers) {
		Map<String, List<ProducerBehaviour>> behaviours = new HashMap<String, List<ProducerBehaviour>>();
		for(ConsumerT consumer: consumers) {
			if(isProducerResponsibleForConsumer(producerId, consumer)) {
				behaviours.put(consumer.getId(),determineSpecificProducerBehaviour(consumer.getBehaviours()));
			}
		}
		return behaviours;
	}
	
	private boolean isProducerResponsibleForConsumer(String producerId, ConsumerT consumer) {
		return producerId.equals(((ProducerT) consumer.getProducerId()).getId());
	}
	
	private List<ProducerBehaviour> determineSpecificProducerBehaviour(List<BehaviourT> scenarioBehaviours) {
		List<ProducerBehaviour> pBehaviours = new ArrayList<ProducerBehaviour>();
		for(BehaviourT sBehaviour : scenarioBehaviours) {
			//No need to verify the behaviour's validity
			//it was done in the consumers' configuration step
			pBehaviours.add(new ProducerBehaviour(sBehaviour.getBeginInMs(),
												  sBehaviour.getEndInMs(),
												  sBehaviour.getProcessingTimeInMs()));
		}
		return pBehaviours;
	}
}
