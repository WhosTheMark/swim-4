package fr.insa.toulouse.tp2g2.mainApp;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jms.JavaAppSender;

import messaging.ConsumerBehaviour;
import messaging.MessageConfigurationConsumer;
import messaging.MessageConfigurationProducer;
import messaging.ProducerBehaviour;
import model.Scenario;

import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;

import scenario.Configurator;

public class ConfiguratorTest {

	private Configurator configurator;
	private TestScenarioFactory scenarioFactory;
	
	private JavaAppSender sender;
	

	@Before
	public void setUp() {
		configurator = new Configurator(sender);
		scenarioFactory = new TestScenarioFactory();
		sender = mock(JavaAppSender.class);
	}
	
	@Test
	public void scenarioDescribesRightConfiguration() throws IOException {
		Scenario scenario = scenarioFactory.buildExpectedNormalScenario();
		configurator.sendConfigurationMessages(scenario);
		List<MessageConfigurationProducer> pMessages = getProducerMessages();
		for(MessageConfigurationProducer message: pMessages) {
			verify(sender,times(1)).send(message);
		}
		List<MessageConfigurationConsumer> cMessages = getConsumerMessages();
		for(MessageConfigurationConsumer message: cMessages) {
			verify(sender,times(1)).send(message);
		}
	}
	
	private List<MessageConfigurationProducer> getProducerMessages() {
		List<MessageConfigurationProducer> messages = new ArrayList<MessageConfigurationProducer>();
		messages.add(createProducerMessage("p1", "producer1", "c1", 125, 1, 10, 1));
		messages.add(createProducerMessage("p2", "producer2", "c2", 50, 1, 10, 5));
		return messages;
	}
	
	private MessageConfigurationProducer createProducerMessage(String id, String name,String consumerId,
															   int datasize, int begin, int end, int processingTime) {
		Map<String, List<ProducerBehaviour>> pBehaviour = new HashMap<String, List<ProducerBehaviour>>();
		List<ProducerBehaviour> pList = new ArrayList<ProducerBehaviour>();
		pList.add(new ProducerBehaviour(begin, end, processingTime));
		pBehaviour.put(consumerId, pList);
		return new MessageConfigurationProducer(null, id, name, datasize, pBehaviour);
	}
	
	private List<MessageConfigurationConsumer> getConsumerMessages() {
		List<MessageConfigurationConsumer> messages = new ArrayList<MessageConfigurationConsumer>();
		messages.add(createConsumerMessage("consumer1", "c1", "p1", 1, 10, 45, 125));
		messages.add(createConsumerMessage("consumer2", "c2", "p2", 1, 10, 12, 20));
		return messages;
	}
	
	private MessageConfigurationConsumer createConsumerMessage(String name, String id, String producerId,
															   int begin, int end, int frequency, int datasize) {
		List<ConsumerBehaviour> behaviours = new ArrayList<ConsumerBehaviour>();
		behaviours.add(new ConsumerBehaviour(begin, end, frequency, datasize));
		return new MessageConfigurationConsumer(null, id, producerId, name, behaviours);
	}
	
	/*<consumer id="c2">
			<name>consumer2</name>
			<producerId>p2</producerId>

			<behaviour timeUnit="ms" begin="1" end="10">
    			<frequency>12</frequency>
    			<processingTime timeUnit="ms">5</processingTime>
    			<datasize sizeUnit="bytes">20</datasize>
			</behaviour>    
		</consumer>
	 * */
}
