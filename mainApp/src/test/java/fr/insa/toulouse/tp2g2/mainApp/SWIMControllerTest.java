package fr.insa.toulouse.tp2g2.mainApp;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import controller.SWIMController;

public class SWIMControllerTest {

	private SWIMController swimController;
	
	@Before
	public void setUp() {
		swimController = new SWIMController();
	}
	
	@Test
	public void reportCreatedAfterNoXMLFileScenario() {
		swimController.runScenario(ScenarioNames.NOTXMLINPUT);
	}

}
