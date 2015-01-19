package jmsmainapp;

/**
 * Exception used to report error when handling jms matters
 * i.e queues and topics creation, sending and receiving messages, etc.
 * @author swim
 */
public class JMSException extends RuntimeException{


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JMSException() {
		super();
	}

	public JMSException(String message) {
		super(message);
	}

	public JMSException(Exception exception) {
		super(exception);
	}
}
