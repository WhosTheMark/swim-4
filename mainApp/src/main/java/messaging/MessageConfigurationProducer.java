package messaging;

import java.util.List;
import java.util.Map;

public class MessageConfigurationProducer extends Message {

	private String name;
	private int datasize;
	
	private Map<String, List<ProducerBehaviour>> producerBehaviours;

	public MessageConfigurationProducer() {
		super();
        type = MessageType.CONFIGURATIONPRODUCER;
	}

	public MessageConfigurationProducer(String from, String to, String name, int datasize,
										Map<String, List<ProducerBehaviour>> producerBehaviours) {
		super(from, to);
        initializeMessage(name, datasize, producerBehaviours);      
	}
	
	public MessageConfigurationProducer(String to, String name, int datasize,
										Map<String, List<ProducerBehaviour>> producerBehaviours) {
		super(null, to);
        initializeMessage(name, datasize, producerBehaviours);
	}
	
	private void initializeMessage(String name, int datasize, Map<String, List<ProducerBehaviour>> producerBehaviours) {
		type = MessageType.CONFIGURATIONPRODUCER;
		this.name = name;
		this.datasize = datasize;
		this.producerBehaviours = producerBehaviours;
		
	}

	public int getDatasize() {
		return datasize;
	}

	public void setDatasize(int datasize) {
		this.datasize = datasize;
	}

	public Map<String, List<ProducerBehaviour>> getProducerBehaviours() {
		return producerBehaviours;
	}

	public void setProducerBehaviours(
			Map<String, List<ProducerBehaviour>> producerBehaviours) {
		this.producerBehaviours = producerBehaviours;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public boolean equals(Object o) {
		if(o.getClass() == MessageConfigurationProducer.class) {
			MessageConfigurationProducer aux = (MessageConfigurationProducer) o;
			return super.equals(aux)
					&& name.equals(aux.getName())
					&& datasize == aux.getDatasize()
					&& producerBehaviours.equals(aux.getProducerBehaviours());
		}
		return false;
	}
}
