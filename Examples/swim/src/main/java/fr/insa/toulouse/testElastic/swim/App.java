package fr.insa.toulouse.testElastic.swim;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import static org.elasticsearch.index.query.QueryBuilders.termQuery;
import static org.elasticsearch.index.query.FilterBuilders.*;
import static org.elasticsearch.index.query.QueryBuilders.*;

import static org.elasticsearch.node.NodeBuilder.nodeBuilder;

import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.deletebyquery.DeleteByQueryResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.node.Node;
import org.elasticsearch.search.SearchHit;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
		Node node = nodeBuilder().clusterName("elasticsearch").client(true).node();
		Client client = node.client();
		//        Client client = new TransportClient()
		//        .addTransportAddress(new InetSocketTransportAddress("", 9200));

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

		IndexResponse response = client.prepareIndex("customers", "aa")
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

		//        // get a specific object
		//        GetResponse responseGet = client.prepareGet("customers", "customer_pokemon", _id)
		//                .execute()
		//                .actionGet();
		//        System.out.println("get response as String = "+responseGet.getSourceAsString());
		//        Customer cDB = null;
		//        try {
		//			cDB = new ObjectMapper().readValue(responseGet.getSourceAsString(), Customer.class);
		//		} catch (JsonParseException e) {
		//			// TODO Auto-generated catch block
		//			e.printStackTrace();
		//		} catch (JsonMappingException e) {
		//			// TODO Auto-generated catch block
		//			e.printStackTrace();
		//		} catch (IOException e) {
		//			// TODO Auto-generated catch block
		//			e.printStackTrace();
		//		}
		//        System.out.println("Stored Customer name = "+cDB.getName());

		// store more objects
		Customer c2 = new Customer("c2", 4.3);
		Customer c3 = new Customer("c3", 4.3);
		// generate json
		try {
			json = mapper.writeValueAsString(c2);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			System.out.println("Problem.");
			e.printStackTrace();
		}

		IndexResponse response2 = client.prepareIndex("customers", "customer_pokemon")
				.setSource(json)
				.execute()
				.actionGet();

		// generate json
		try {
			json = mapper.writeValueAsString(c3);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			System.out.println("Problem.");
			e.printStackTrace();
		}

		IndexResponse response3 = client.prepareIndex("customers", "pikachu")
				.setSource(json)
				.execute()
				.actionGet();
		
		System.out.println("J'ai trouvé "+search("customers", "aa").get(0).getName()+" résultats aa.");

		System.out.println("J'ai trouvé "+search("customers", "pikachu").get(0).getName()+" résultats pikachu.");

		System.out.println("J'ai trouvé "+search("customers", "customer_pokemon").get(0).getName()+" résultats customer_pokemon.");
		//        DeleteResponse deleteResponse = client.prepareDelete("customers", "customer", _id)
		//                .execute()
		//                .actionGet();
		try {
			System.in.read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DeleteByQueryResponse deleteResponse = client.prepareDeleteByQuery("customers")
				.setQuery(termQuery("_type", "pikachu"))
				.execute()
				.actionGet();

		System.out.println("number of deleted object(s) = "+deleteResponse.getIndices().size());
		//        System.out.println("id of deleted object = "+deleteResponse.getId());
		//     // get a specific object
		//        responseGet = client.prepareGet("customers", "customer", _id)
		//                .execute()
		//                .actionGet();
		//        if (responseGet.isSourceEmpty()){
		//        	System.out.println("nothing found ! it worked !");
		//        }else{
		//        	System.out.println("get response as String = "+responseGet.getSourceAsString());
		//        }
		// on shutdown
		//        
		client.close();
		node.close();
	}

	//	public static void searchDocument(Client client, String index, String type,
	//			String field, String value){
	//
	//		SearchResponse response = client.prepareSearch(index)
	//				.setTypes(type)
	//				.setSearchType(SearchType.QUERY_AND_FETCH)
	//				.setQuery(fieldQuery(field, value))
	//				.setFrom(0).setSize(60).setExplain(true)
	//				.execute()
	//				.actionGet();
	//
	//		SearchHit[] results = response.getHits().getHits();
	//
	//		System.out.println("Current results: " + results.length);
	//		for (SearchHit hit : results) {
	//			System.out.println("------------------------------");
	//			Map<String,Object> result = hit.getSource();   
	//			System.out.println(result);
	//		}
	//
	//	}
	public static ArrayList<Customer> search(String collection, String type){
		Node node = nodeBuilder().client(true).node();
		Client client = node.client();
		ArrayList<Customer> result = new ArrayList<Customer>();
		SearchResponse responseSearch = client.prepareSearch(collection)
				//				.setTypes("aa")
				//				.setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
				.setQuery(termQuery("_type", type))
				.execute().
				actionGet();
		
        Customer cDB = new Customer();

		for (SearchHit s : responseSearch.getHits().getHits()){
			cDB = new Customer();
			        try {
						cDB = new ObjectMapper().readValue(s.getSourceAsString(), Customer.class);
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
			result.add(cDB);
		}
		client.close();
		node.close();
		return result;
	}
}
