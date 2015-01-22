package fr.insa.toulouse.tp2g2.mainApp;

import static org.junit.Assert.*;
import java.util.List;

import model.BehaviourT;

import org.junit.Before;
import org.junit.Test;

import controller.SWIMController;
import controller.SWIMException;
import jmsmainapp.JMSManager;

public class SWIMControllerTest {

	private SWIMController swimController;
	
	@Before
	public void setUp() {
		swimController = new SWIMController(JMSManager.getInstance());
	}
	
	@Test(expected=SWIMException.class)
	public void reportCreatedAfterNoXMLFileScenario() {
		swimController.runScenario(ScenarioNames.NOTXMLINPUT);
		checkReportContent("ERROR - file "
						  + ScenarioNames.NOTXMLINPUT
						  + " is not an XML file");
	}
	
	@Test(expected=SWIMException.class)
	public void reportCreatedAfterNotExistingFileScenario() {
		swimController.runScenario(ScenarioNames.NOTEXISTINGFILE);
		checkReportContent("ERROR - file "
						 + ScenarioNames.NOTEXISTINGFILE
						 + " does not exist");
	}
	
	private void checkReportContent(String expectedContent) {
		String filename = swimController.getReportName();
		String report = TestUtilities.retrieveFileContent(filename);
		System.out.println(report);
		assertTrue(report.contains(expectedContent));
	}
	
	@Test(expected=SWIMException.class)
	public void reportCreatedAfterNotValidScenario() {
		swimController.runScenario(ScenarioNames.NOTVALIDSCENARIO);
		checkReportContent("ERROR - file "
						 + ScenarioNames.NOTVALIDSCENARIO
					     + " does not correspond to model");
	}

	@Test(expected=SWIMException.class)
	public void reportCreatedAfterWrongBehaviour() {
		swimController.runScenario(ScenarioNames.WRONGBEHAVIOURSCENARIO);
		String filename = swimController.getReportName();
		String report = TestUtilities.retrieveFileContent(filename);
		System.out.println(report);
		TestScenarioFactory factory = new TestScenarioFactory();
		List<BehaviourT> behaviours = factory.getOverlappingBehaviour();
		assertTrue(report.contains(ScenarioNames.WRONGBEHAVIOURSCENARIO));
		assertTrue(report.contains("ERROR - Behaviours "
								 + behaviours.get(0).toString() + " and "
								 + behaviours.get(1).toString()
								 + " overlap"));
	}
}
