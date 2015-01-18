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
		try {
			scenario = scenarioParser.parseScenario(ScenarioNames.NOTEXISTINGFILE);
		} catch(ScenarioException exception) {
			assertEquals("ERROR - file " + ScenarioNames.NOTEXISTINGFILE + " does not exist", exception.getMessage());
			assertNull(scenario);
			throw new ScenarioException(exception);
		}
	}
	
	@Test(expected=ScenarioException.class)
	public void inputIsNotAnXMLFile() {
		Scenario scenario = null;
		try {
			scenario = scenarioParser.parseScenario(ScenarioNames.NOTXMLINPUT);
		} catch(ScenarioException exception) {
			assertEquals("ERROR - file " + ScenarioNames.NOTXMLINPUT + " is not an XML file", exception.getMessage());
			assertNull(scenario);
			throw new ScenarioException(exception);
		}
	}
	
	@Test(expected=ScenarioException.class)
	public void noConsumersInScenario() {
		runFailingScenario(ScenarioNames.SCENARIOWITHOUTCONSUMER);
	}
	
	@Test(expected=ScenarioException.class)
	public void validStructureButWrongValues() {
		runFailingScenario(ScenarioNames.SCENARIOWITHWRONGVALUES);
	}
	
	@Test(expected=ScenarioException.class)
	public void nonExistingProducerReferenced() {
		runFailingScenario(ScenarioNames.WRONGPRODUCERREFERENCED);
	}
	
	@Test(expected=ScenarioException.class)
	public void scenarioIsIllFormated() {
		runFailingScenario(ScenarioNames.NOTVALIDSCENARIO);
	}
	
	private void runFailingScenario(String scenarioName) {
		Scenario scenario = null;
		try {
			scenario = scenarioParser.parseScenario(scenarioName);
		} catch(ScenarioException exception) {
			assertNull(scenario);
			throw new ScenarioException(exception);
		}
	}
	
	@Test
	public void inputIsValid() {
		Scenario scenario = scenarioParser.parseScenario(ScenarioNames.VALIDNORMALSCENARIO);
		assertNotNull(scenario);
		Scenario expectedScenario = scenarioFactory.buildExpectedNormalScenario();
		assertTrue(expectedScenario.equals(scenario));
	}

	@Test
	public void veryLargeValidScenario() {
		Scenario scenario = scenarioParser.parseScenario(ScenarioNames.VALIDLARGESCENARIO);
		Scenario expectedScenario = scenarioFactory.buildExpectedLargeScenario();
		assertEquals(expectedScenario, scenario);
	}
	

}
