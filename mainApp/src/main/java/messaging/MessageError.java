package messaging;

public class MessageError extends Message{

	private String errorMessage;
	
	public MessageError(String from, String to, String errorMessage) {
		super(from, to);
		this.errorMessage = errorMessage;
	}

	public String getError() {
		return errorMessage;
	}

	public void setError(String error) {
		this.errorMessage = error;
	}
}
