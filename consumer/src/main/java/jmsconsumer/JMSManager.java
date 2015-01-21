/**
 * @author: N. Fayolle
 * @date: 17/12/2014
 * @lastRevisionDate: 17/12/2014
 * @content: this class represents the main or the communicator in the JavaApp
 * @comment: It will be changed but will be helpful
 */

package jmsconsumer;

import jmsconsumer.QueueAssociation;
import jmsconsumer.TopicAssociation;

public class JMSManager {
	
	private static final String IPADDRESS = "10.1.5.160";
	private static JMSManager jmsConnection = null;
	
	private TopicAssociation topicAssociation;
	private ConsumerSender sender;//donner l acces
	private QueueAssociation queueAssociation;
	private ConsumerReceiverThread receiver;//donner l acces

	/**
	 * Constructor
	 * @param nameConsumer 
	 * @param esbAddr
	 */
	protected JMSManager() {		
		topicAssociation=new TopicAssociation(IPADDRESS);		
		queueAssociation=new QueueAssociation(IPADDRESS);
		sender=new ConsumerSender(queueAssociation);
	}
	
	public ConsumerReceiverThread createConsumerThread (String nameConsumer, String esbAddr) {
		return new ConsumerReceiverThread(topicAssociation, nameConsumer, esbAddr);
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


	public ConsumerSender getSender() {
		return sender;
	}


	public ConsumerReceiverThread getReceiver() {
		return receiver;
	}









}
