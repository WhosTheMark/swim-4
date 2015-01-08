package messaging;

import java.util.List;

public class MessageConfigurationConsumer extends Message {

	private String producerId;
	private String name;
	private List<ConsumerBehaviour> consumerBehaviours;

	public MessageConfigurationConsumer(String from, String to, String producerId,
										String name, List<ConsumerBehaviour> consumerBehaviours) {
		super(from, to);
		this.name = name;
		this.consumerBehaviours = consumerBehaviours;
	}
	
	public String getProducerId() {
		return producerId;
	}
	
	public void setProducerId(String producerId) {
		this.producerId = producerId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<ConsumerBehaviour> getConsumerBehaviours() {
		return consumerBehaviours;
	}

	public void setConsumerBehaviours(List<ConsumerBehaviour> consumerBehaviours) {
		this.consumerBehaviours = consumerBehaviours;
	}
	
}
