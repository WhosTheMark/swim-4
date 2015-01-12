/**
 * @author: N. Fayolle
 * @date: 17/12/2014
 * @lastRevisionDate: 17/12/2014
 * @content: this class represents the main or the communicator in the JavaApp
 * @comment: It will be changed but will be helpful
 */

package jmsmainapp;

import java.io.IOException;

public class JMSManager {
	protected static JMSManager jmsConnection=null;
	private TopicAssociation topicAssociation;
	private JavaAppSender sender;//donner l acces
	private QueueAssociation queueAssociation;
	private JavaAppReceiverThread receiver;//donner l acces
	private String ipAdress="localhost";
	
	/**
	 * Constructor
	 * @throws IOException
	 */
	protected JMSManager() {
		
		try {
			this.topicAssociation=new TopicAssociation(this.ipAdress);		
			this.sender=new JavaAppSender(this.topicAssociation);
			this.queueAssociation=new QueueAssociation(this.ipAdress);
			this.receiver=new JavaAppReceiverThread(this.queueAssociation);
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
	public static JMSManager getInstance() throws IOException{
		
		if(jmsConnection==null){
			jmsConnection=new JMSManager();
		}
		return jmsConnection;
	}


	
	
	////GETTERS & SETTERS
	public JavaAppSender getSender() {
		return sender;
	}


	public JavaAppReceiverThread getReceiver() {
		return receiver;
	}



	
	
}
