package controller;


import messaging.Message;
import jmsmainapp.JMSManager;
import jmsmainapp.JavaAppSender;

public class StartSender extends Thread {

	JavaAppSender sender;
	private boolean activeFlow;
	
	public StartSender() {
		sender = JMSManager.getInstance().getSender();
	}
	
	public void run() {
		Message start = new Message(null,"broadcast");
		sender.send(start);
	}
}
