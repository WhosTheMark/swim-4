package messaging;

import java.util.List;
import java.util.Map;

public class MessageConfigurationProducer extends Message {

	private String name;
	private Map<String, List<ProducerBehaviour>> producerBehaviours;

	public MessageConfigurationProducer(String from, String to, String name,
										Map<String, List<ProducerBehaviour>> producerBehaviours) {
		super(from, to);
		this.name = name;
		this.producerBehaviours = producerBehaviours;
	}
	
	public MessageConfigurationProducer() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Map<String, List<ProducerBehaviour>> getProducerBehaviour() {
		return producerBehaviours;
	}

	public void setProducerBehaviour(Map<String, List<ProducerBehaviour>> producerBehaviour) {
		this.producerBehaviours = producerBehaviour;
	}
	
}
