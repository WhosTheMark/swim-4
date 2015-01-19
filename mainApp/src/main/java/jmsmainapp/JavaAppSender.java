/**
 * @author: N. Fayolle
 * @date: 17/12/2014
 * @lastRevisionDate: 17/12/2014
 * @content: this class is in charge of sending a message to the topic
 * @comment: 
 */

package jmsmainapp;

import java.io.IOException;

import messaging.Message;

public class JavaAppSender {
	
	private TopicAssociation topicAssociation;
	private static final String FROMCONTENT ="JavaApp";
	
	public JavaAppSender(TopicAssociation topicAssociation) {
		this.topicAssociation=topicAssociation;
	}
	
	public void send(Message message) {
		try {
			message.setFrom(FROMCONTENT);
			System.out.println("Sending a message");
			this.topicAssociation.getChannel().basicPublish(topicAssociation.getExchangeName(), "", null, message.toJson().getBytes());//modif2 publish to the named exchange
			System.out.println(" The Publisher Sent: "+message.toJson());
		} catch(IOException exception) {
			throw new JMSException("ERROR - Problem when sending message via topic "
					+ exception.getMessage());
		}
	}
}
