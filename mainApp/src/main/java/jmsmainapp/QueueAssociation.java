/**
 * @author: N. Fayolle
 * @date: 17/12/2014
 * @lastRevisionDate: 17/12/2014
 * @content: this class is in charge of associating the JavaApp to the Queue named "result"
 * @comment: the ipAdress must change when the VM are created
 */
package jmsmainapp;

import java.io.IOException;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

public class QueueAssociation {
	
	private final  String QUEUE_NAME = "result";
	private String ipAddress="localhost";
	private Connection connection;
	private Channel channel;
	
	public QueueAssociation(String ipAddress) {
		try {
			this.ipAddress=ipAddress;
			ConnectionFactory factory = new ConnectionFactory();
			factory.setHost(ipAddress);
			this.connection = factory.newConnection();
			this.channel = connection.createChannel();
			this.channel.queueDeclare(this.QUEUE_NAME, false, false, false, null);
		} catch(IOException exception) {
			throw new JMSException("ERROR - Problem at creation of queue with IP address " + ipAddress + " "
									+ exception.getMessage());
		}
	}

	public String getQUEUE_NAME() {
		return QUEUE_NAME;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public Connection getConnection() {
		return connection;
	}

	public Channel getChannel() {
		return channel;
	}
}