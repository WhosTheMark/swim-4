/**
 * @author: N. Fayolle
 * @date: 17/12/2014
 * @lastRevisionDate: 17/12/2014
 * @content: this class is in charge of sending a message to the queue
 * @comment: 
 */

package jmsconsumer;

import java.io.IOException;

import javax.xml.ws.handler.MessageContext;

import jmsconsumer.QueueAssociation;

import messaging.Message;

public class ConsumerSender {
	private QueueAssociation queueAssociation;
	
	public ConsumerSender(QueueAssociation queueAssociation){
		this.queueAssociation=queueAssociation;
	}
	
	public synchronized void send(Message message) throws IOException{
		this.queueAssociation.getChannel().basicPublish("", this.queueAssociation.getQUEUE_NAME(), null, message.toJson().getBytes());
		System.out.println(" [x] Sent '" + message + "'");
		this.queueAssociation.getChannel().close();
		this.queueAssociation.getConnection().close();
	}
}
