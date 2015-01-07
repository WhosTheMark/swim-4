package messaging;

import java.util.ArrayList;

import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.node.Node;
import static org.elasticsearch.node.NodeBuilder.*;
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
	
	public String store(){
		 // Initialize client to work with DB
        Node node = nodeBuilder().client(true).node();
        Client client = node.client();
        
		IndexResponse response = client.prepareIndex("swim", "result")
                .setSource(this.toJson())
                .execute()
                .actionGet();
		node.close();
		return response.getId();
	}

	public static ArrayList<Message> getResults() {
		 // Initialize client to work with DB
        Node node = nodeBuilder().client(true).node();
        Client client = node.client();
		SearchResponse response = client.prepareSearch("swim").setTypes("result").execute().actionGet();		
		node.close();
		return null;
	}

	public static ArrayList<Message> getErrors() {
		// TODO Auto-generated method stub
		return null;
	}

}
