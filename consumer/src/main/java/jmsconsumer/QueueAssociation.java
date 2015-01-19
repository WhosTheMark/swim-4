/**
 * @author: N. Fayolle
 * @date: 17/12/2014
 * @lastRevisionDate: 17/12/2014
 * @content: this class is in charge of associating the JavaApp to the Queue named "result"
 * @comment: the ipAdress must change when the VM are created
 */

package jmsconsumer;

import java.io.IOException;

import jmsmainapp.JMSException;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

public class QueueAssociation {
	
	private static final  String QUEUE_NAME = "result";
	private String ipAddress ="localhost";
	private static final String USERNAME = "fafa";
	private static final String PASSWORD = "fafa";
	private Connection connection;
	private Channel channel;
	
	
	public QueueAssociation(String ipAddress) {
		try {
			this.ipAddress =ipAddress;
			ConnectionFactory factory = new ConnectionFactory();
			factory.setHost(this.ipAddress);
			factory.setUsername(USERNAME);
			factory.setPassword(PASSWORD);
			connection = factory.newConnection();
			channel = connection.createChannel();
			channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		} catch (IOException e) {
			throw new JMSException("ERROR - queue association's creation failed \n" + e.getMessage());
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