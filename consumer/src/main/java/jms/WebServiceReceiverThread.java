/**
 * @author: N. Fayolle
 * @date: 17/12/2014
 * @lastRevisionDate: 17/12/2014
 * @content: this class is in charge of waiting and receiving the messages
 * @comment: the ipAdress must change when the VM are created
 */

package jms;

import java.io.IOException ;
import java.util.Date;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.ShutdownSignalException;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.QueueingConsumer;


public class WebServiceReceiverThread extends Thread{
	
	private  String name;
	private TopicAssociation association;
	
	public WebServiceReceiverThread(String name,TopicAssociation association){
		this.name=name;		
		this.association=association;
		
	}
	
	
	
	public void run() {
		
		String queueName;
		try {
			queueName = this.association.getChannel().queueDeclare().getQueue();
		
			this.association.getChannel().queueBind(queueName, this.association.getExchangeName(), "");//modif4 binding exchange to queue
			System.out.println("Queue " + queueName + " is Waiting for messages. To exit press CTRL+C");
			QueueingConsumer consumer = new QueueingConsumer(this.association.getChannel());
			this.association.getChannel().basicConsume(queueName, true, consumer);
			
			while (true) {
				QueueingConsumer.Delivery delivery = consumer.nextDelivery();
				String message = new String(delivery.getBody());				
				System.out.println(queueName + " Je suis "+ name+ " et j ai recu : '" + message + "'" +" at "+ new Date());
				sayThx();//TEST: to be deleted
			}
		} catch (IOException | ShutdownSignalException | ConsumerCancelledException | InterruptedException e) {
			
			e.printStackTrace();
		}
	}
	//TEST: send Merci to the JavaApp when it receives a message
	private void sayThx() throws IOException{
		QueueAssociation association = new QueueAssociation();
		ConsumerResultSender sender= new ConsumerResultSender();
		sender.send("Merci :)", association);
	}
}
