/**
 * @author: N. Fayolle
 * @date: 17/12/2014
 * @lastRevisionDate: 17/12/2014
 * @content: this class is in charge of sending a message to the queue
 * @comment: 
 */

package jms;

import java.io.IOException;

public class ConsumerResultSender {
	
	public ConsumerResultSender(){
		
	}
	
	public void send(String message, QueueAssociation association) throws IOException{
		association.getChannel().basicPublish("", association.getQUEUE_NAME(), null, message.getBytes());
		System.out.println(" [x] Sent '" + message + "'");
		association.getChannel().close();
		association.getConnection().close();
	}
}
