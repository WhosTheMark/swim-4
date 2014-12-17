package messaging;

public class MessageRequest extends Message{
	
	private String data; 
	
	public MessageRequest(String from, String to) {
		super(from, to);
	}
}
