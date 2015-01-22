package main;

import java.io.IOException;

import jmsconsumer.ConsumerReceiverThread;
import jmsconsumer.JMSManager;

public class MainConsumer {
	
	private static String consumerName = "c1" ;
	private static String esbAddr = "http://10.1.5.162:9000" ; // à remplir
	
	public static void main(String[] args) throws IOException, InterruptedException {
		JMSManager manager = JMSManager.getInstance();
		ConsumerReceiverThread receiver = manager.createConsumerThread(consumerName, esbAddr);
		receiver.start();
		receiver.join();

	}
}
