/**
 * @author: N. Fayolle
 * @date: 17/12/2014
 * @lastRevisionDate: 17/12/2014
 * @content: this class creates a thread to wait for the messages
 * @comment: the ipAdress must change when the VM are created
 */

package jms;

import java.io.IOException;

public class WebServiceMain {

	
	public static void main(String[] args) throws IOException {
		//step1: subscription to the topic
		TopicAssociation association= new TopicAssociation();
		
		//step2: waiting for messages
		WebServiceReceiverThread receiveMessage= new WebServiceReceiverThread("consumer2", association);
		receiveMessage.start();
	}

}
