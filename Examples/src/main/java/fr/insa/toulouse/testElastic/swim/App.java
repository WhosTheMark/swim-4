package fr.insa.toulouse.testElastic.swim;
import static org.elasticsearch.node.NodeBuilder.*;

import java.io.IOException;

import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.node.Node;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;

/**
 * Hello world!
 *
 */
public class App 
{ // test ElasticSearch
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );

     // instance a json mapper
     ObjectMapper mapper = new ObjectMapper(); // create once, reuse

     // Initialize client to work with DB
        Node node = nodeBuilder().client(true).node();
        Client client = node.client();

       // Store an object into ElasticSearch 
        Customer c = new Customer("c1", 4.3);
        
        String json = "";
        // generate json
        try {
			json = mapper.writeValueAsString(c);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			System.out.println("Problem.");
			e.printStackTrace();
		}
        
        IndexResponse response = client.prepareIndex("customers", "customer")
                .setSource(json)
                .execute()
                .actionGet();
        
        // Get information about newly stored object
        // Index name
        String _index = response.getIndex();
        System.out.println("index = "+_index);
        // Type name
        String _type = response.getType();
        System.out.println("type = "+_type);
        // Document ID (generated or not)
        String _id = response.getId();
        System.out.println("id = "+_id);
        // Version (if it's the first time you index this document, you will get: 1)
        long _version = response.getVersion();
        System.out.println("version = "+_version);
        
        // get a specific object
        GetResponse responseGet = client.prepareGet("customers", "customer", _id)
                .execute()
                .actionGet();
        System.out.println("get response as String = "+responseGet.getSourceAsString());
        Customer cDB = null;
        try {
			cDB = new ObjectMapper().readValue(responseGet.getSourceAsString(), Customer.class);
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
        System.out.println("Stored Customer name = "+cDB.getName());
        
        DeleteResponse deleteResponse = client.prepareDelete("customers", "customer", _id)
                .execute()
                .actionGet();

        System.out.println("id of deleted object = "+deleteResponse.getId());
     // get a specific object
        responseGet = client.prepareGet("customers", "customer", _id)
                .execute()
                .actionGet();
        if (responseGet.isSourceEmpty()){
        	System.out.println("nothing found ! it worked !");
        }else{
        	System.out.println("get response as String = "+responseGet.getSourceAsString());
        }
        // on shutdown
        node.close();
    }
}
