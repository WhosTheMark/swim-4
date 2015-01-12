package com.swim.messaging;

public class MessageResult extends Message{

	private String consumerId;
	private String producerId;
	private int requestTime;
	private int responseTime;
	// Later, we will add a time unit, like seconds, milliseconds, nanoseconds...
	private int requestDataSize;
	private int responseDataSize;
	
	public MessageResult() {
		super();
	}
	public MessageResult(String from, String to) {
		super(from, to);
	}
	public String getConsumerId() {
		return consumerId;
	}
	public void setConsumerId(String consumerId) {
		this.consumerId = consumerId;
	}
	public String getProducerId() {
		return producerId;
	}
	public void setProducerId(String producerId) {
		this.producerId = producerId;
	}
	public int getRequestTime() {
		return requestTime;
	}
	public void setRequestTime(int requestTime) {
		this.requestTime = requestTime;
	}
	public int getResponseTime() {
		return responseTime;
	}
	public void setResponseTime(int responseTime) {
		this.responseTime = responseTime;
	}
	public int getRequestDataSize() {
		return requestDataSize;
	}
	public void setRequestDataSize(int requestDataSize) {
		this.requestDataSize = requestDataSize;
	}
	public int getResponseDataSize() {
		return responseDataSize;
	}
	public void setResponseDataSize(int responseDataSize) {
		this.responseDataSize = responseDataSize;
	}
	public MessageResult(String from, String to, String consumerId,
			String producerId, int requestTime, int responseTime,
			int requestDataSize, int responseDataSize) {
		super(from, to);
		this.consumerId = consumerId;
		this.producerId = producerId;
		this.requestTime = requestTime;
		this.responseTime = responseTime;
		this.requestDataSize = requestDataSize;
		this.responseDataSize = responseDataSize;
	}



}
