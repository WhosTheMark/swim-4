package messaging;

public class MessageResponse extends Message{
	
	private String data;
	
	public MessageResponse(String from, String to) {
		super(from, to);
	}

}
