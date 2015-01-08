/**
 * @author: N. Fayolle
 * @date: 17/12/2014
 * @lastRevisionDate: 17/12/2014
 * @content: this class represents the main or the communicator in the JavaApp
 * @comment: It will be changed but will be helpful
 */

package jms;

import java.io.IOException;

public class JMSManager {
	protected static JMSManager jmsConnection=null;
	private TopicAssociation topicAssociation;
	private ConsumerSender sender;//donner l acces
	private QueueAssociation queueAssociation;
	private ConsumerReceiverThread receiver;//donner l acces
	private String ipAdress="localhost";
	
	/**
	 * Constructor
	 * @throws IOException
	 */
	protected JMSManager() throws IOException{
		this.topicAssociation=new TopicAssociation(this.ipAdress);
		this.queueAssociation=new QueueAssociation(this.ipAdress);
		this.sender=new ConsumerSender(this.queueAssociation);
		
		this.receiver=new ConsumerReceiverThread(this.topicAssociation);
		this.receiver.start();
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


	public ConsumerSender getSender() {
		return sender;
	}


	public ConsumerReceiverThread getReceiver() {
		return receiver;
	}


	
	



	
	
}
