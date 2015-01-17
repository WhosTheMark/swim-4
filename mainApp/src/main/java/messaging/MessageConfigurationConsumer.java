package messaging;

import java.util.List;

public class MessageConfigurationConsumer extends Message {

	public MessageConfigurationConsumer() {
		super();
                this.setType(Type.CONFIGURATIONCONSUMER);
	}
	private String producerId;
	private String name;
	private List<ConsumerBehaviour> consumerBehaviours;

	public MessageConfigurationConsumer(String from, String to, String producerId,
										String name, List<ConsumerBehaviour> consumerBehaviours) {
		super(from, to);
                this.setType(Type.CONFIGURATIONCONSUMER);
		this.name = name;
		this.producerId = producerId;
		this.consumerBehaviours = consumerBehaviours;
	}
	
	public MessageConfigurationConsumer(String to, String producerId, String name,
										List<ConsumerBehaviour> consumerBehaviours) {
		super(null, to);
                this.setType(Type.CONFIGURATIONCONSUMER);
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
	
	public boolean equals(Object o) {
		if(o.getClass() == MessageConfigurationConsumer.class) {
			MessageConfigurationConsumer aux = (MessageConfigurationConsumer) o;
			return super.equals(aux)
					&& producerId.equals(aux.getProducerId())
					&& name.equals(aux.getName())
					&&consumerBehaviours.equals(aux.getConsumerBehaviours());
		}
		return false;
	}
}
