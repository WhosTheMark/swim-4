package messaging;

public class MessageError extends Message{

	private String errorMessage;
	
	public MessageError(String from, String to, String errorMessage) {
		super(from, to);
		this.errorMessage = errorMessage;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}
