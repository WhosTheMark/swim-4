package messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/* All messages sent through our application
 * Described as classes and objects to manage their structure
 * Stored into database in JSON
 */
public class Message {
	
	private String from;
	private String to;
	
	public Message(String from, String to) {
		super();
		this.from = from;
		this.to = to;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String toJson(){
		// instance a json mapper
		ObjectMapper mapper = new ObjectMapper(); // create once, reuse
		String json = "";
		// generate json
		try {
			json = mapper.writeValueAsString(this);
		} catch (JsonProcessingException e) {
			System.out.println("Problem.");
			e.printStackTrace();
		}
		return json;

	}
}
