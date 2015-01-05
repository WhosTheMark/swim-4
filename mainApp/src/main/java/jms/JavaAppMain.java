/**
 * @author: N. Fayolle
 * @date: 17/12/2014
 * @lastRevisionDate: 17/12/2014
 * @content: this class represents the main or the communicator in the JavaApp
 * @comment: It will be changed but will be helpful
 */

package jms;

import java.io.IOException;

public class JavaAppMain {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		//Step1 : Association to the topic
		TopicAssociation connection=new TopicAssociation();	
		
		//Step2: publish a message
		JavaAppSender sender= new JavaAppSender();
		sender.send("GOOOOOO!",connection);

		//Step1: Association to the queue
		QueueAssociation association= new QueueAssociation();
		
		//Step2: Wait for a message
		JavaAppReceiverThread rcv= new JavaAppReceiverThread(association);
		rcv.start();
	}

}
