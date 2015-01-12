/**
 * @author: N. Fayolle
 * @date: 17/12/2014
 * @lastRevisionDate: 17/12/2014
 * @content: this class is in charge of associating the JavaApp to the Queue named "result"
 * @comment: the ipAdress must change when the VM are created
 */

package jmsconsumer;

import java.io.IOException;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

public class QueueAssociation {
	
	private final  String QUEUE_NAME = "result";
	private String ipAdress="localhost";
	private Connection connection;
	private Channel channel;
	
	
	public QueueAssociation(String ipAdress) throws IOException{
		this.ipAdress=ipAdress;
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(this.ipAdress);
		this.connection = factory.newConnection();
		this.channel = connection.createChannel();
		this.channel.queueDeclare(this.QUEUE_NAME, false, false, false, null);

	}


	public String getQUEUE_NAME() {
		return QUEUE_NAME;
	}


	public String getIpAdress() {
		return ipAdress;
	}


	public Connection getConnection() {
		return connection;
	}


	public Channel getChannel() {
		return channel;
	}
	
}