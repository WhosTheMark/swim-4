package fr.insa.toulouse.tp2g2.mainApp;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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
		String report = retrieveReportContent();
		System.out.println(report);
		assertTrue(report.contains("ERROR - file "
								 + ScenarioNames.NOTXMLINPUT
								 + " is not an XML file"));
	}
	
	@Test(expected=SWIMException.class)
	public void reportCreatedAfterNotExistingFileScenario() {
		swimController.runScenario(ScenarioNames.NOTEXISTINGFILE);
		String report = retrieveReportContent();
		System.out.println(report);
		assertTrue(report.contains("ERROR - file "
								 + ScenarioNames.NOTEXISTINGFILE
								 + " does not exist"));
	}
	
	@Test(expected=SWIMException.class)
	public void reportCreatedAfterNotValidScenario() {
		swimController.runScenario(ScenarioNames.NOTVALIDSCENARIO);
		String report = retrieveReportContent();
		System.out.println(report);
		assertTrue(report.contains("ERROR - file "
								 + ScenarioNames.NOTVALIDSCENARIO
								 + " does not correspond to model"));
	}

	@Test(expected=SWIMException.class)
	public void reportCreatedAfterWrongBehaviour() {
		swimController.runScenario(ScenarioNames.WRONGBEHAVIOURSCENARIO);
		String report = retrieveReportContent();
		System.out.println(report);
		TestScenarioFactory factory = new TestScenarioFactory();
		List<BehaviourT> behaviours = factory.getOverlappingBehaviour();
		assertTrue(report.contains(ScenarioNames.WRONGBEHAVIOURSCENARIO));
		assertTrue(report.contains("ERROR - Behaviours "
								 + behaviours.get(0).toString() + " and "
								 + behaviours.get(1).toString()
								 + " overlap"));
	}
	
	private String retrieveReportContent() {
		String reportName = swimController.getReportName();
		String content = "";
		BufferedReader input = null;
	    try {
	    	input = new BufferedReader(new FileReader(reportName));
	        StringBuilder sb = new StringBuilder();
	        String line = input.readLine();

	        while (line != null) {
	            sb.append(line);
	            sb.append(System.lineSeparator());
	            line = input.readLine();
	        }
	        content = sb.toString();
	    }catch(IOException exception) {
	    	
	    } finally {
	    	if(input != null) {
	    		try {
					input.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    	}
	    }
	    return content;
	}
}
