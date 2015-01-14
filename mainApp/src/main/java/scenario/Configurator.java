package scenario;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import messaging.*;
import model.*;
import jmsmainapp.JavaAppSender;

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
		List<MessageConfigurationConsumer> messages
			= createConsumersConfigurationMessage(consumers.getConsumers());
		for(MessageConfigurationConsumer message: messages) {
			sender.send(message);
		}
	}
	
	private List<MessageConfigurationConsumer> createConsumersConfigurationMessage(List<ConsumerT> consumers) {
		List<MessageConfigurationConsumer> messages = new ArrayList<MessageConfigurationConsumer> ();
		for(ConsumerT consumer: consumers) {
			messages.add(createConsumerConfigurationMessage(consumer));
		}
		return messages;
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
		checkIfBehavioursOK(behaviours);
		for(BehaviourT behaviour: behaviours) {
			consumerBehaviours.add(determineConsumerBehaviour(behaviour));
		}
		return consumerBehaviours;
	}
	
	/**
	 * @throws ScenarioException if behaviours overlap or are impossible
	 */
	private void checkIfBehavioursOK(List<BehaviourT> behaviours) {
		Collections.sort(behaviours);
		for(int i=0; i < behaviours.size(); i++) {
			if(!behaviours.get(i).isPossibleBehaviour()) {
				throw new ScenarioException("ERROR - Behaviour "
						+ behaviours.get(i).toString()
						+ " is impossible to achieve");
				
			} else if(i>0 && doBehavioursOverlap(behaviours.get(i),behaviours.get(i-1))) {
				throw new ScenarioException("ERROR - Behaviours "
						+ behaviours.get(i-1).toString() + " and "
						+ behaviours.get(i).toString()
						+ " overlap");
			}
		}
	}
	
	private boolean doBehavioursOverlap(BehaviourT b1, BehaviourT b2) {
		return b1.doesBehaviourOverlapWith(b2);
	}
	
	private ConsumerBehaviour determineConsumerBehaviour(BehaviourT behaviour) {
		return new ConsumerBehaviour(behaviour.getBeginInMs(),
									 behaviour.getEndInMs(),
									 behaviour.getFrequency().intValue(),
									 behaviour.getDatasizeInBytes());
	}
	
	private void sendConfigurationMessageToProducers(Scenario scenario) {
		for(ProducerT producer: scenario.getProducers().getProducers()) {
			sendConfigurationMessageToProducer(producer,scenario);
		}
	}
	
	private void sendConfigurationMessageToProducer(ProducerT producer, Scenario scenario) {
		MessageConfigurationProducer message = createProducerConfigurationMessage(producer, scenario);
		sender.send(message);
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
