/**
 * @author: N. Fayolle
 * @date: 17/12/2014
 * @lastRevisionDate: 17/12/2014
 * @content: this class is in charge of waiting and receiving the messages
 * @comment: the ipAdress must change when the VM are created
 */

package jmsproducer;

import java.io.IOException ;
import java.util.Date;

import jmsmainapp.JMSException;

import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.ShutdownSignalException;
import com.rabbitmq.client.QueueingConsumer;


public class ProducerReceiverThread extends Thread{
	
	private TopicAssociation association;
	
	public ProducerReceiverThread(TopicAssociation association){		
		this.association=association;
	}
	
	
	
	public void run() {
		
		String queueName;
		try {
			queueName = association.getChannel().queueDeclare().getQueue();
		
			association.getChannel().queueBind(queueName, association.getExchangeName(), "");//modif4 binding exchange to queue
			System.out.println("Queue " + queueName + " is Waiting for messages. To exit press CTRL+C");
			QueueingConsumer consumer = new QueueingConsumer(association.getChannel());
			association.getChannel().basicConsume(queueName, true, consumer);
			
			while (true) {
				QueueingConsumer.Delivery delivery = consumer.nextDelivery();
				String message = new String(delivery.getBody());				
				System.out.println(queueName + " Je suis un producer et j ai recu : '" + message + "'" +" at "+ new Date());
			}
		} catch (IOException | ShutdownSignalException | ConsumerCancelledException | InterruptedException e) {		
			throw new JMSException(e.getMessage());
		}
	}

}