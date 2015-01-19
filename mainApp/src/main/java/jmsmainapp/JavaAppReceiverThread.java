/**
 * @author: N. Fayolle
 * @date: 17/12/2014
 * @lastRevisionDate: 17/12/2014
 * @content: this class represents the thread which waits for and read messages
 * @comment: 
 */

package jmsmainapp;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.QueueingConsumer;

public class JavaAppReceiverThread extends Thread {
    private QueueAssociation association;

    public JavaAppReceiverThread(QueueAssociation association) {
        this.association = association;
    }

    public void run() {
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
        Channel channel = association.getChannel();
        QueueingConsumer consumer = new QueueingConsumer(channel);

        try {

            channel.basicConsume(QueueAssociation.QUEUE_NAME, true, consumer);
            readMessages(consumer);

        } catch (Exception e) {
            throw new JMSException(e);
        }
    }

    private void readMessages(QueueingConsumer consumer) throws InterruptedException {

        while (true) {
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String message = new String(delivery.getBody());
            System.out.println(" [x] Received '" + message + "'");
        }
    }
}
