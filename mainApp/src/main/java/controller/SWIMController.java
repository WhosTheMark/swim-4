package controller;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import jmsmainapp.JMSException;
import jmsmainapp.JMSManager;
import jmsmainapp.JavaAppReceiverThread;
import jmsmainapp.JavaAppSender;
import messaging.Message;
import model.Scenario;
import results.ScenarioResults;
import scenario.Configurator;
import scenario.ScenarioException;
import scenario.ScenarioParser;

/**
 * Controller class : creates the main handlers and coordinates their work
 * @author swim
 */
public class SWIMController {
	
	private static final String ERRORREPORT = "ressources/reports/report.txt";
	private static final String BROADCAST = "broadcast";
	private ScenarioParser scenarioParser;
	private Configurator configurator;
	private JavaAppSender sender;
	private JavaAppReceiverThread receiver;
	private JavaAppMessageHandler messageHandler;
	private String scenarioName;
	private boolean scenarioRunning;
	private ScenarioResults resultsHandler;
	
	public SWIMController() {
		scenarioRunning = false;
		try {
			sender = JMSManager.getInstance().getSender();
			configurator = new Configurator(sender);
			initializeMessageReception();
			scenarioParser = new ScenarioParser();
			resultsHandler = new ScenarioResults();
		} catch(JMSException exception) {
			writeErrorReport(exception.getMessage());
		}
	}
	
	private void initializeMessageReception() {
		BlockingQueue<String> messages = new LinkedBlockingQueue<String>();
		receiver = JMSManager.getInstance().getReceiver();
		receiver.setMessagesList(messages);
		receiver.setSWIMController(this);
		messageHandler = new JavaAppMessageHandler(messages, this);
	}
	
	public boolean keepRunning() {
		return scenarioRunning; 
	}
	
	public String getReportName() {
		return ERRORREPORT;
	}
	
	public void runScenario(String scenarioName) {
		try {
			this.scenarioName = scenarioName;
			Scenario scenario = scenarioParser.parseScenario(scenarioName);
			configurator.sendConfigurationMessages(scenario);
			messageHandler.setConsumersID(configurator.getConsumersID());
			startScenario();
		} catch(ScenarioException exception) {
			writeErrorReport(exception.getMessage());
		} catch (JMSException exception) {
			writeErrorReport(exception.getMessage());
		} catch (SWIMException exception) {
			writeErrorReport(exception.getMessage());
		} 
	}
	
	private void startScenario() {
		scenarioRunning = true;
		sendStartMessage();
		receiver.run();
		messageHandler.run();
	}
	
	private void sendStartMessage() {
		Message start = new Message(null,BROADCAST);
		sender.send(start);
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
        int hour = today.get(Calendar.HOUR);
        int minute = today.get(Calendar.MINUTE);
        int seconde = today.get(Calendar.SECOND);
		writer.write("Ran on: " + day + "/" + month + "/" + year
				   + " at: " + hour + "h" + minute + "min" + seconde + "s\n");
	}
	
	public void handleAllConsumersHaveFinished() {
		scenarioRunning = false;
		resultsHandler.generateXMLresult();
	}
}
