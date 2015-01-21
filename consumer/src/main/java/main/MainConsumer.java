package main;

import java.io.IOException;

import jmsconsumer.ConsumerReceiverThread;
import jmsconsumer.JMSManager;

public class MainConsumer {
	
    // TODO Set name of consumer and esb address
	private static String consumerName = "consumer" ;
	private static String esbAddr = null ;
	
	public static void main(String[] args) throws IOException, InterruptedException {
		JMSManager manager = JMSManager.getInstance();
		ConsumerReceiverThread receiver = manager.createConsumerThread(consumerName, esbAddr);
		receiver.start();
		receiver.join();
	}
}
