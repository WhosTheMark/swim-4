package scenario;

import model.Scenario;
import jms.JavaAppSender;

public class Configurator {

	private JavaAppSender sender;
	
	public Configurator(JavaAppSender sender) {
		this.sender = sender;
	}
	
	public void sendConfigurationMessages(Scenario scenario) {
		
	}
}
