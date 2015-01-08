/**
 * @author: N. Fayolle
 * @date: 17/12/2014
 * @lastRevisionDate: 17/12/2014
 * @content: this class is in charge of sending a message to the topic
 * @comment: 
 */

package jms;

import java.io.IOException;

import messaging.Message;

public class JavaAppSender {
	private TopicAssociation topicAssociation;
	private final String from="JavaApp";
	public JavaAppSender(TopicAssociation topicAssociation) throws IOException{
		this.topicAssociation=topicAssociation;
	}
	
	public void send(Message message) throws java.io.IOException {
		message.setFrom(this.from);
		
		System.out.println("Sending a message");
		this.topicAssociation.getChannel().basicPublish(this.topicAssociation.getExchangeName(), "", null, message.toJson().getBytes());//modif2 publish to the named exchange
		System.out.println(" The Publisher Sent: "+message.toJson());
		this.topicAssociation.getChannel().close();
		this.topicAssociation.getConnection().close();
	}
}
