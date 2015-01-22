/**
 * @author: N. Fayolle
 * @date: 17/12/2014
 * @lastRevisionDate: 17/12/2014
 * @content: this class is in charge of sending a message to the queue
 * @comment: 
 */

package jmsconsumer;

import java.io.IOException;
import jmsconsumer.QueueAssociation;
import jmsmainapp.JMSException;

import messaging.Message;

public class ConsumerSender {
	private QueueAssociation queueAssociation;
	
	public ConsumerSender(QueueAssociation queueAssociation){
		this.queueAssociation=queueAssociation;
	}
	
	public synchronized void send(Message message) {
		try {
			this.queueAssociation.getChannel().basicPublish("", queueAssociation.getQUEUE_NAME(), null, message.toJson().getBytes());
			System.out.println(" [x] Sent message.");
		} catch (IOException e) {
			throw new JMSException(e.getMessage());
		}
	}
}
