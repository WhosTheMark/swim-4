/**
 * @author: N. Fayolle
 * @date: 17/12/2014
 * @lastRevisionDate: 17/12/2014
 * @content: this class represents the main or the communicator in the JavaApp
 * @comment: It will be changed but will be helpful
 */

package jmsmainapp;


public class JMSManager {
	
	private static JMSManager jmsConnection=null;
	private TopicAssociation topicAssociation;
	private JavaAppSender sender;//donner l acces
	private QueueAssociation queueAssociation;
	private JavaAppReceiverThread receiver;//donner l acces

	private static final String IPADDRESS ="localhost";
	
	/**
	 * Constructor
	 */
	private JMSManager() {
		topicAssociation=new TopicAssociation(IPADDRESS);		
		sender=new JavaAppSender(topicAssociation);
		queueAssociation=new QueueAssociation(IPADDRESS);
		receiver=new JavaAppReceiverThread(queueAssociation);
	}
			
	/**
	 * Singleton de JMS Connection
	 * @return unique JMS manager instance
	 */
	public synchronized static JMSManager getInstance(){	
		if(jmsConnection==null){
			jmsConnection=new JMSManager();
		}
		return jmsConnection;
	}

	public JavaAppSender getSender() {
		return sender;
	}

	public JavaAppReceiverThread getReceiver() {
		return receiver;
	}	
}
