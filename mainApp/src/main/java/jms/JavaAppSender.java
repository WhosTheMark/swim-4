/**
 * @author: N. Fayolle
 * @date: 17/12/2014
 * @lastRevisionDate: 17/12/2014
 * @content: this class is in charge of sending a message to the topic
 * @comment: 
 */

package jms;
public class JavaAppSender {
	
	public JavaAppSender(){
		
	}
	
	public void send(String message,TopicAssociation association) throws java.io.IOException {
		System.out.println("Sending a message");
		association.getChannel().basicPublish(association.getExchangeName(), "", null, message.getBytes());//modif2 publish to the named exchange
		System.out.println(" The Publisher Sent: "+message);
		association.getChannel().close();
		association.getConnection().close();
	}
}
