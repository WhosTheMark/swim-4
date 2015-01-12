package fr.insa.toulouse.tp2g2.mainApp;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import scenario.ScenarioException;
import scenario.ScenarioParser;

import model.*;


public class ScenarioParserTest {

	private ScenarioParser scenarioParser;
	private TestScenarioFactory scenarioFactory;

	
	@Before
	public void setUp() {
		scenarioParser = new ScenarioParser();
		scenarioFactory = new TestScenarioFactory();
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
		Scenario expectedScenario = scenarioFactory.buildExpectedNormalScenario();
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
		Scenario expectedScenario = scenarioFactory.buildExpectedLargeScenario();
		assertEquals(expectedScenario, scenario);
	}
	

}
