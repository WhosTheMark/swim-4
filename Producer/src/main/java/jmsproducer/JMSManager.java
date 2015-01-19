

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
	protected static JMSManager jmsConnection=null;
	private TopicAssociation topicAssociation;	
	private ProducerReceiverThread receiver;//donner l acces
	private String ipAdress="localhost";
	
	/**
	 * Constructor
	 * 
	 */
	protected JMSManager() {
		
		try {
			this.topicAssociation=new TopicAssociation(this.ipAdress);		
			this.receiver=new ProducerReceiverThread(this.topicAssociation);
			this.receiver.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		
	
	/**
	 * Singleton de JMS Connection
	 * @return
	 * @throws IOException 
	 */
	public synchronized static JMSManager getInstance() throws IOException{
		
		if(jmsConnection==null){
			jmsConnection=new JMSManager();
		}
		return jmsConnection;
	}



	public ProducerReceiverThread getReceiver() {
		return receiver;
	}


	
	



	
	
}
