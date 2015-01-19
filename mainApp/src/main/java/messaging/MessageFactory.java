package messaging;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MessageFactory {

	private static MessageFactory instance = null;
	
	private MessageFactory() {}
	
	public static synchronized MessageFactory getInstance() {
		if(instance == null) {
			instance = new MessageFactory();
		}
		return instance;
	}
	
	public MessageType identifyMessage(String json){
		ObjectMapper mapper = new ObjectMapper();
		JsonNode root;
		try {
			root = mapper.readTree(json);
		} catch (JsonProcessingException e) {
			throw new MessageException("ERROR - Problem when identifying a message "+e.getMessage());
		} catch (IOException e) {
			throw new MessageException("ERROR - Problem when identifying a message "+e.getMessage());
		}
		String type = root.get("type").asText();		
		return MessageType.valueOf(type);
	}
	
	public Message getMessageFromJson(String json){
		return fromJson(Message.class, json);
	}
	
	public MessageResult getMessageResultFromJson(String json){
		return fromJson(MessageResult.class, json);
	}
	
	public MessageError getMessageErrorFromJson(String json){
		return fromJson(MessageError.class, json);
	}
	
	public MessageConfigurationProducer getMessageConfigurationProducerFromJson(String json){
		return fromJson(MessageConfigurationProducer.class, json);
	}
	
	public MessageConfigurationConsumer getMessageConfigurationConsumerFromJson(String json){
		return fromJson(MessageConfigurationConsumer.class, json);
	}
	
	private <T> T fromJson(Class T, String json){
		T message = null;
		try {
			message = (T) T.newInstance();
		} catch (InstantiationException e) {
			throw new MessageException("ERROR - Problem when searching a message "+e.getMessage());
		} catch (IllegalAccessException e) {
			throw new MessageException("ERROR - Problem when searching a message "+e.getMessage());
		}
		ObjectMapper mapper = new ObjectMapper();
		try {
			message = (T) mapper.readValue(json, T);
		} catch (IOException e) {
			throw new MessageException("ERROR - Problem when retrieving a message from json "+e.getMessage());
		}
		return message;
	}
}
