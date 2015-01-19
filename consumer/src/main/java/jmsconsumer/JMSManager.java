/**
 * @author: N. Fayolle
 * @date: 17/12/2014
 * @lastRevisionDate: 17/12/2014
 * @content: this class represents the main or the communicator in the JavaApp
 * @comment: It will be changed but will be helpful
 */

package jmsconsumer;

import java.io.IOException;





import jmsconsumer.QueueAssociation;
import jmsconsumer.TopicAssociation;

public class JMSManager {
	protected static JMSManager jmsConnection=null;
	private TopicAssociation topicAssociation;
	private ConsumerSender sender;//donner l acces
	private QueueAssociation queueAssociation;
	private ConsumerReceiverThread receiver;//donner l acces
	private String ipAdress="localhost";

	/**
	 * Constructor
	 * @param nameConsumer 
	 * @param esbAddr 
	 * @throws IOException
	 */
	protected JMSManager() {
		
		try {
			this.topicAssociation=new TopicAssociation(this.ipAdress);		
			this.queueAssociation=new QueueAssociation(this.ipAdress);
			this.sender=new ConsumerSender(this.queueAssociation);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public ConsumerReceiverThread createConsumerThread (String nameConsumer, String esbAddr) {
		return new ConsumerReceiverThread(this.topicAssociation, nameConsumer, esbAddr);
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
