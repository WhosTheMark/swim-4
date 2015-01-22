/**
 * @author: N. Fayolle
 * @date: 17/12/2014
 * @lastRevisionDate: 17/12/2014
 * @content: this class is in charge of waiting and receiving the messages
 * @comment: the ipAdress must change when the VM are created
 */

package jmsconsumer;

import java.util.Date;

import jmsmainapp.JMSException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.QueueingConsumer;

public class ConsumerReceiverThread extends Thread {

    private TopicAssociation association;
    private String nameConsumer;
    private String esbAddr;

    public ConsumerReceiverThread(TopicAssociation association,
            String nameConsumer, String esbAddr) {
        this.nameConsumer = nameConsumer;
        this.esbAddr = esbAddr;
        this.association = association;
    }

    public void run() {

        MessageHandler handler = new MessageHandler(nameConsumer, esbAddr);
        Channel channel = association.getChannel();

        try {

            String queueName = channel.queueDeclare().getQueue();

            // modif4 binding exchange to queue
            channel.queueBind(queueName,association.getExchangeName(), "");

            System.out.println("Queue " + queueName
                    + " is Waiting for messages. To exit press CTRL+C");
            QueueingConsumer consumer = new QueueingConsumer(channel);
            channel.basicConsume(queueName, true, consumer);
            waitForMessages(queueName, handler, consumer);

        } catch (Exception e) {
            throw new JMSException(e.getMessage());
        }
    }

    private void waitForMessages(String queueName, MessageHandler handler,
            QueueingConsumer consumer) throws InterruptedException {

        boolean keepWaiting = true;

        while (keepWaiting) {

            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String message = new String(delivery.getBody());

            System.out.println(queueName
                    + " Je suis un consumer et j ai recu : '" + message
                    + "'" + " at " + new Date());

            keepWaiting = handler.handleMessage(message);
        }
    }
}
