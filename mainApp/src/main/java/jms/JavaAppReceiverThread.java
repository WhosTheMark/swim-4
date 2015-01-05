/**
 * @author: N. Fayolle
 * @date: 17/12/2014
 * @lastRevisionDate: 17/12/2014
 * @content: this class represents the thread which waits for and read messages
 * @comment: 
 */

package jms;

import java.io.IOException;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.ShutdownSignalException;

public class JavaAppReceiverThread extends Thread{
	private QueueAssociation association;
	
	
	
	public JavaAppReceiverThread(QueueAssociation association){
		this.association=association;
	}
	
	
	public void run(){
		System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
		QueueingConsumer consumer = new QueueingConsumer(this.association.getChannel());
		try {
			this.association.getChannel().basicConsume(this.association.getQUEUE_NAME(), true, consumer);	
			//Wait and read messages
			while (true) {
				QueueingConsumer.Delivery delivery = consumer.nextDelivery();
				String message = new String(delivery.getBody());
				System.out.println(" [x] Received '" + message + "'");
			}
			
		} catch (IOException e) {			
			e.printStackTrace();
		} catch (ShutdownSignalException e) {			
			e.printStackTrace();
		} catch (ConsumerCancelledException e) {			
			e.printStackTrace();
		} catch (InterruptedException e) {			
			e.printStackTrace();
		}
	}
}
