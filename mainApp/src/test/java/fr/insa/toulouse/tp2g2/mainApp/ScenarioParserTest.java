package fr.insa.toulouse.tp2g2.mainApp;
import static org.junit.Assert.*;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import scenario.ScenarioException;
import scenario.ScenarioParser;

import model.*;


public class ScenarioParserTest {

	private ScenarioParser scenarioParser;
	
	@Before
	public void setUp() {
		scenarioParser = new ScenarioParser();
	}
	
	
	@Test(expected=ScenarioException.class)
	public void fileDoesNotExist() {
		Scenario scenario = null;
		String scenarioName = "ressources/notExistingFile.txt";
		try {
			scenario = scenarioParser.parseScenario(scenarioName);
		} catch(ScenarioException exception) {
			assertEquals("ERROR - file " + scenarioName + " does not exist", exception.getMessage());
			assertNull(scenario);
			throw new ScenarioException(exception);
		}
	}
	
	@Test(expected=ScenarioException.class)
	public void inputIsNotAnXMLFile() {
		Scenario scenario = null;
		String scenarioName = "ressources/notXMLInput.txt";
		try {
			scenario = scenarioParser.parseScenario(scenarioName);
		} catch(ScenarioException exception) {
			assertEquals("ERROR - file " + scenarioName + " is not an XML file", exception.getMessage());
			assertNull(scenario);
			throw new ScenarioException(exception);
		}
	}
	
	@Test(expected=ScenarioException.class)
	public void noConsumersInScenario() {
		Scenario scenario = null;
		String scenarioName = "ressources/scenarioWithoutConsumer.xml";
		try {
			scenario = scenarioParser.parseScenario(scenarioName);
		} catch(ScenarioException exception) {
			assertNull(scenario);
			throw new ScenarioException(exception);
		}
	}
	
	@Test(expected=ScenarioException.class)
	public void validStructureButWrongValues() {
		Scenario scenario = null;
		String scenarioName = "ressources/scenarioWithWrongValues.xml";
		try {
			scenario = scenarioParser.parseScenario(scenarioName);
		} catch(ScenarioException exception) {
			assertNull(scenario);
			throw new ScenarioException(exception);
		}
	}
	
	@Test(expected=ScenarioException.class)
	public void nonExistingProducerReferenced() {
		Scenario scenario = null;
		String scenarioName = "ressources/wrongProducerReferenced.xml";
		try {
			scenario = scenarioParser.parseScenario(scenarioName);
		} catch(ScenarioException exception) {
			assertNull(scenario);
			throw new ScenarioException(exception);
		}
	}
	@Test
	public void inputIsValid() {
		String scenarioName = "ressources/xml/scenario1.xml";
		Scenario scenario = scenarioParser.parseScenario(scenarioName);
		assertNotNull(scenario);
		Scenario expectedScenario = buildExpectedScenario1();
		assertTrue(expectedScenario.equals(scenario));
	}
	
	@Test(expected=ScenarioException.class)
	public void scenarioIsIllFormated() {
		Scenario scenario = null;
		try {
			String scenarioName = "ressources/xml/notValidScenario.xml";
			scenario = scenarioParser.parseScenario(scenarioName);
		} catch(ScenarioException exception) {
			assertNull(scenario);
			throw new ScenarioException(exception);
		}
	}

	@Test
	public void veryLargeValidScenario() {
		String scenarioName = "ressources/xml/largeScenario.xml";
		Scenario scenario = scenarioParser.parseScenario(scenarioName);
		Scenario expectedScenario = buildExpectedScenario2();
		assertEquals(expectedScenario, scenario);
	}
	
	private Scenario buildExpectedScenario1() {

		List<ProducerT> producers = new ArrayList<ProducerT>();
		producers.add(createProducer("p1", "producer1", 40));
		producers.add(createProducer("p2", "producer2", 50));
		
		List<BehaviourT> behaviours1 = new ArrayList<BehaviourT>();
		behaviours1.add(createBehaviour(1, 10, 45, 125, 1));
		
		List<BehaviourT> behaviours2 = new ArrayList<BehaviourT>();
		behaviours2.add(createBehaviour(1, 10, 12, 20, 5));
		
		List<ConsumerT> consumers = new ArrayList<ConsumerT>();
		consumers.add(createConsumer("c1", "consumer1", producers.get(0), behaviours1));
		consumers.add(createConsumer("c2", "consumer2", producers.get(1), behaviours2));

		return createScenario(155, consumers, producers);
	}
	
	private Scenario buildExpectedScenario2() {

		List<ProducerT> producersList = new ArrayList<ProducerT>();
		producersList.add(createProducer("p1", "producer1", 40));
		producersList.add(createProducer("p2", "producer2", 50));
		producersList.add(createProducer("p3", "producer3", 40));
		producersList.add(createProducer("p4", "producer4", 50));
		producersList.add(createProducer("p5", "producer5", 40));
		producersList.add(createProducer("p6", "producer6", 50));
		producersList.add(createProducer("p7", "producer7", 40));
		producersList.add(createProducer("p8", "producer8", 50));
		producersList.add(createProducer("p9", "producer9", 40));
		producersList.add(createProducer("p10", "producer10", 50));

		List<BehaviourT> behaviours = new ArrayList<BehaviourT> ();
		behaviours.add(createBehaviour(1, 10, 45, 125, 1));
		behaviours.add(createBehaviour(11, 20, 60, 100, 2));
		behaviours.add(createBehaviour(21, 30, 30, 150, 4));
		behaviours.add(createBehaviour(31, 40, 20, 100, 1));
		behaviours.add(createBehaviour(41, 50, 50, 90, 2));
		behaviours.add(createBehaviour(51, 60, 80, 110, 1));
		behaviours.add(createBehaviour(61, 70, 60, 100, 2));

		List<ConsumerT> consumersList = new ArrayList<ConsumerT>();
		
		consumersList.add(createConsumer("c1", "consumer1", producersList.get(0), behaviours));
		consumersList.add(createConsumer("c2", "consumer2", producersList.get(1), behaviours));
		consumersList.add(createConsumer("c3", "consumer3", producersList.get(2), behaviours));
		consumersList.add(createConsumer("c4", "consumer4", producersList.get(3), behaviours));
		consumersList.add(createConsumer("c5", "consumer5", producersList.get(4), behaviours));
		consumersList.add(createConsumer("c6", "consumer6", producersList.get(5), behaviours));
		consumersList.add(createConsumer("c7", "consumer7", producersList.get(6), behaviours));
		consumersList.add(createConsumer("c8", "consumer8", producersList.get(7), behaviours));
		consumersList.add(createConsumer("c9", "consumer9", producersList.get(8), behaviours));
		consumersList.add(createConsumer("c10", "consumer10", producersList.get(9), behaviours));
		
		return createScenario(155,consumersList,producersList);
	}
	
	private Scenario createScenario(long timeDuration, List<ConsumerT> consumers, List<ProducerT> producers) {
		Scenario scenario = new Scenario();
		Consumers cons = new Consumers();
		cons.setConsumers(consumers);
		scenario.setConsumers(cons);
		Producers prod = new Producers();
		prod.setProducers(producers);
		scenario.setProducers(prod);
		scenario.setDuration(createTimeDuration(timeDuration));
		return scenario;
	}
	
	private TimeT createTimeDuration(long time) {
		TimeT duration = new TimeT();
		duration.setValue(BigInteger.valueOf(time));
		duration.setTimeUnit(TimeUnitType.MS);
		return duration;
	}
	
	private ProducerT createProducer(String id, String name, long datasizeValue) {
		ProducerT producer = new ProducerT();
		producer.setId(id);
		producer.setName(name);
		producer.setDatasize(createDatasize(datasizeValue));
		return producer;
	}
	
	private DatasizeT createDatasize(long value) {
		DatasizeT datasize = new DatasizeT();
		datasize.setValue(BigInteger.valueOf(value));
		datasize.setSizeUnit(SizeUnitType.BYTES);
		return datasize;
	}
	
	private BehaviourT createBehaviour(long begin, long end, long frequency, long dValue, long pTime) {
		BehaviourT behaviour = new BehaviourT();
		behaviour.setTimeUnit(TimeUnitType.MS);
		behaviour.setBegin(BigInteger.valueOf(begin));
		behaviour.setEnd(BigInteger.valueOf(end));
		behaviour.setFrequency(BigInteger.valueOf(frequency));
		behaviour.setDatasize(createDatasize(dValue));
		behaviour.setProcessingTime(createTimeDuration(pTime));
		return behaviour;
	}
	
	private ConsumerT createConsumer(String id, String name, ProducerT producer, List<BehaviourT> behaviours) {
		ConsumerT consumer = new ConsumerT();
		consumer.setId(id);
		consumer.setProducerId(producer);
		consumer.setName(name);
		consumer.setBehaviours(behaviours);
		return consumer;
	}
}
