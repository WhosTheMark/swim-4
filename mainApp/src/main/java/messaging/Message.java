package messaging;

import java.util.List;
import java.io.IOException;
import java.util.ArrayList;

import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.node.Node;
import org.elasticsearch.search.SearchHit;

import static org.elasticsearch.index.query.QueryBuilders.termQuery;
import static org.elasticsearch.node.NodeBuilder.*;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import database.Database;

/* All messages sent through our application
 * Described as classes and objects to manage their structure
 * Stored into database in JSON
 */
public abstract class Message {
	private String from;
	private String to;

	public Message(String from, String to) {
		this.from = from;
		this.to = to;
	}
	
	public Message() {}

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

		IndexResponse response = client.prepareIndex(Database.DATABASE_NAME, this.getClass().toString())
				.setSource(this.toJson())
				.execute()
				.actionGet();
		node.close();
		return response.getId();
	}

	public static List<MessageResult> getMessageResults() {
		return search(MessageResult.class, Database.DATABASE_NAME, MessageResult.class.toString());
	}

	public static List<MessageError> getMessageErrors() {
		return search(MessageError.class, Database.DATABASE_NAME, MessageError.class.toString());
	}

	public static List<MessageConfigurationProducer> getMessageConfigurationProducers() {
		return search(MessageConfigurationProducer.class, Database.DATABASE_NAME, MessageConfigurationProducer.class.toString());
	}

	public static List<MessageConfigurationConsumer> getMessageConfigurationConsumers() {
		return search(MessageConfigurationConsumer.class, Database.DATABASE_NAME, MessageConfigurationConsumer.class.toString());
	}

	public static <T> List<T> search(Class T, String collection, String type){
		// Initialize a node, then a client
		// /!\ Important to have a separate client, to be able to search through the "first" client's data
		Node node = nodeBuilder().client(true).node();
		Client client = node.client();
		List<T> result = new ArrayList<T>();
		SearchResponse responseSearch = client.prepareSearch(collection)
				.setQuery(termQuery("_type", type))
				//				.addSort("_id", SortOrder.ASC)
				.execute()
				.actionGet();

		T object = null;

		for (SearchHit s : responseSearch.getHits().getHits()){
			try {
				object = (T) T.newInstance();
			} catch (InstantiationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IllegalAccessException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				object = (T) new ObjectMapper().readValue(s.getSourceAsString(), T);
			} catch (JsonParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JsonMappingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			result.add(object);
		}
		client.close();
		node.close();
		return result;
	}

	@Override
	public String toString(){
		return "{ "+this.getFrom()+" , "+this.getTo()+" }";
	}

	// Coded for testing purposes
	public static void delete(String type, String id){
		Node node = nodeBuilder().client(true).node();
		Client client = node.client();
		DeleteResponse response = client.prepareDelete(Database.DATABASE_NAME, type, id)
				.execute()
				.actionGet();
		client.close();
		node.close();
		return ;
	}
}
