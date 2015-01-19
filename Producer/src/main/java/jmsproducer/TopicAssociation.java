/**
 * @author: N. Fayolle
 * @date: 17/12/2014
 * @lastRevisionDate: 17/12/2014
 * @content: this class is in charge of associating the consumers to the Topic named "topic"
 * @comment: the ipAdress must change when the VM are created
 */

package jmsproducer;

import java.io.IOException;

import jmsmainapp.JMSException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class TopicAssociation {
	
	private static final String EXCHANGE_NAME = "topic";
	private static final String USERNAME = "fafa";
	private static final String PASSWORD = "fafa";
	
	private String ipAdress="localhost";
	private Channel channel;
	private Connection connection;
	
	public TopicAssociation(String ipAdress) {
		try {
			this.ipAdress=ipAdress;
			ConnectionFactory factory = new ConnectionFactory();
			factory.setHost(this.ipAdress);
			factory.setUsername(USERNAME);
			factory.setPassword(PASSWORD);
			connection = factory.newConnection();
			channel = connection.createChannel();
			channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
		} catch (IOException e) {
			throw new JMSException("ERROR - topic association's creation failed \n" + e.getMessage());
		}
	}

	public Channel getChannel() {
		return channel;
	}

	public  String getExchangeName() {
		return EXCHANGE_NAME;
	}

	public Connection getConnection() {
		return connection;
	}
	
}