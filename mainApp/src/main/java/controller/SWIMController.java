package controller;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import model.Scenario;
import jmsmainapp.JMSException;
import jmsmainapp.JMSManager;
import jmsmainapp.JavaAppSender;
import scenario.Configurator;
import scenario.ScenarioException;
import scenario.ScenarioParser;


public class SWIMController {
	
	private static final String ERRORREPORT = "ressources/reports/report.txt";
	
	private ScenarioParser scenarioParser;
	private Configurator configurator;
	private String scenarioName;
	
	public SWIMController() {
		try {
		JavaAppSender sender = JMSManager.getInstance().getSender();
		scenarioParser = new ScenarioParser();
		configurator = new Configurator(sender);
		} catch(JMSException exception) {
			writeErrorReport(exception.getMessage());
		}
	}
	
	public void runScenario(String scenarioName) {
		try {
			this.scenarioName = scenarioName;
			Scenario scenario = scenarioParser.parseScenario(scenarioName);
			configurator.sendConfigurationMessages(scenario);
			//TODO continue to run the entire scenario
		} catch(ScenarioException exception) {
			writeErrorReport(exception.getMessage());
		} catch(JMSException exception) {
			writeErrorReport(exception.getMessage());
		}
	}
	
	private void writeErrorReport(String errorMsg) {
		try {
          File file = new File(ERRORREPORT);
          BufferedWriter output = new BufferedWriter(new FileWriter(file));
          output.write("Scenario " + scenarioName + " \n");
          writeDate(output);
          output.write(errorMsg + "\n");
          output.close();
        } catch ( IOException e ) {
           e.printStackTrace();
        } 
	}
	
	private void writeDate(BufferedWriter writer) throws IOException {
		Calendar today = Calendar.getInstance();
		int year = today.get(Calendar.YEAR);
        int month = today.get(Calendar.MONTH)+1;
        int day = today.get(Calendar.DAY_OF_MONTH);
		writer.write("Ran on : " + day + "/" + month + "/" + year + "\n");
	}
}
