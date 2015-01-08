/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swim.messaging;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
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

        // Dummy constructor. Useful for Jackson
        public Message (){
            
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