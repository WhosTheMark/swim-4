package messaging;

/**
 * Exception used to report error when handling message matters
 * i.e message creation, parsing into Json, storing, searching through database...
 * @author stephane
 */
public class MessageException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public MessageException() {
		super();
	}

	public MessageException(String message) {
		super(message);
	}

	public MessageException(Exception exception) {
		super(exception);
	}
}
