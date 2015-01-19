/**
 * @author: N. Fayolle
 * @date: 17/12/2014
 * @lastRevisionDate: 17/12/2014
 * @content: this class is in charge of waiting and receiving the messages
 * @comment: the ipAdress must change when the VM are created
 */
package jmsproducer;


import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConsumerCancelledException;
import java.util.Date;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.ShutdownSignalException;
import com.swim.messaging.MessageHandler;

public class ProducerReceiverThread extends Thread {

    private TopicAssociation association;
    private MessageHandler handler;

    public ProducerReceiverThread(TopicAssociation association) {
        this.association = association;
    }

    public void run() {

        String queueName;
        handler = new MessageHandler();
        Channel channel = association.getChannel();
        
        try {
            queueName = channel.queueDeclare().getQueue();

            channel.queueBind(queueName, association.getExchangeName(), "");//modif4 binding exchange to queue
            System.out.println("Queue " + queueName
                             + " is Waiting for messages. To exit press CTRL+C");
            QueueingConsumer consumer = new QueueingConsumer(channel);
            channel.basicConsume(queueName, true, consumer);

            waitForMessages(consumer, queueName);
            
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    private void waitForMessages(QueueingConsumer consumer, String queueName)
            throws ShutdownSignalException, ConsumerCancelledException, InterruptedException {
        
        while (true) {
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String message = new String(delivery.getBody());
            System.out.println(queueName + " Je suis un producer et j ai recu : '"
                    + message + "'" + " at " + new Date());
            handler.handleMessage(message);
        }
    }
}
