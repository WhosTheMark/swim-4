package database;

import static org.elasticsearch.node.NodeBuilder.nodeBuilder;

import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.node.Node;

public class Database {
	public final static String DATABASE_NAME = "swim";
	
	public final static void dropDatabase(){
		
		Node node = nodeBuilder().client(true).node();
		Client client = node.client();
		
		client.prepareDeleteByQuery(DATABASE_NAME)
        	  .setQuery(QueryBuilders.matchAllQuery())
        	  .execute().actionGet();
		
		client.close();
		node.close();
	}
}
