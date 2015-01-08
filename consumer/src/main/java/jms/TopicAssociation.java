/**
 * @author: N. Fayolle
 * @date: 17/12/2014
 * @lastRevisionDate: 17/12/2014
 * @content: this class is in charge of associating the consumers to the Topic named "topic"
 * @comment: the ipAdress must change when the VM are created
 */

package jms;

import java.io.IOException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class TopicAssociation {
	private String ipAdress="localhost";
	private  final String EXCHANGE_NAME = "topic";
	private Channel channel;
	private Connection connection;
	
	public TopicAssociation(String ipAdress) throws IOException{
		this.ipAdress=ipAdress;
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(this.ipAdress);
		this.connection = factory.newConnection();
		this.channel = this.connection.createChannel();
		this.channel.exchangeDeclare(this.EXCHANGE_NAME, "fanout");
	}

	public Channel getChannel() {
		return channel;
	}

	public  String getExchangeName() {
		return this.EXCHANGE_NAME;
	}

	public Connection getConnection() {
		return connection;
	}
	
}
