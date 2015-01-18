package messaging;

public class MessageError extends Message{

	public MessageError() {
		super();
        type = MessageType.ERROR;
	}

	private String errorMessage;
	
	public MessageError(String from, String to, String errorMessage) {
		super(from, to);
		this.errorMessage = errorMessage;
        type = MessageType.ERROR;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}
