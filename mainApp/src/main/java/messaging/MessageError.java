package messaging;

public class MessageError extends Message{

	public MessageError() {
		super();
                this.setType(Type.ERROR);
	}

	private String errorMessage;
	
	public MessageError(String from, String to, String errorMessage) {
		super(from, to);
		this.errorMessage = errorMessage;
                this.setType(Type.ERROR);
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}
