

/**
 * @author: N. Fayolle
 * @date: 17/12/2014
 * @lastRevisionDate: 17/12/2014
 * @content: this class represents the main or the communicator in the JavaApp
 * @comment: It will be changed but will be helpful
 */
package jmsproducer;

import java.io.IOException;

public class JMSManager {
	private static JMSManager jmsConnection=null;
	private TopicAssociation topicAssociation;	
	private ProducerReceiverThread receiver;
	private static final String IPADDRESS = "10.1.5.160";
	
	/**
	 * Constructor
	 * 
	 */
	protected JMSManager() {
		topicAssociation=new TopicAssociation(IPADDRESS);		
		receiver=new ProducerReceiverThread(topicAssociation);
		receiver.start();
	}
		
	
	/**
	 * Singleton de JMS Connection
	 * @return 
	 */
	public synchronized static JMSManager getInstance() {
		if(jmsConnection==null){
			jmsConnection=new JMSManager();
		}
		return jmsConnection;
	}



	public ProducerReceiverThread getReceiver() {
		return receiver;
	}


	
	



	
	
}
