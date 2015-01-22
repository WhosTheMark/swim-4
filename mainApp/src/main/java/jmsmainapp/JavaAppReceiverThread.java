/**
 * @author: N. Fayolle
 * @date: 17/12/2014
 * @lastRevisionDate: 17/12/2014
 * @content: this class represents the thread which waits for and read messages
 * @comment: 
 */

package jmsmainapp;

import java.util.concurrent.BlockingQueue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.QueueingConsumer;

import controller.SWIMController;

public class JavaAppReceiverThread extends Thread {
    private QueueAssociation association;

    private QueueingConsumer consumer;
    private BlockingQueue<String> messages;
    private SWIMController controller;
    
    public JavaAppReceiverThread(QueueAssociation association) {
        this.association = association;
        messages = null;
    }
    
    public void setMessagesList(BlockingQueue<String> messages) {
    	this.messages = messages;
    }
    
    public BlockingQueue<String> getMessagesList() {
    	return messages;
    }
    
    public void setSWIMController(SWIMController controller) {
    	this.controller = controller;
    }

    public void run() {
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
        Channel channel = association.getChannel();
        consumer = new QueueingConsumer(channel);
        try {
            channel.basicConsume(QueueAssociation.QUEUE_NAME, true, consumer);
            readMessages();
        } catch (Exception e) {
            throw new JMSException(e);
        }
    }

    private void readMessages() throws InterruptedException {
        while (controller.keepRunning()) {
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String message = new String(delivery.getBody());
            if(!message.isEmpty()) {
            	messages.put(message);
            }
        }
    }
}
