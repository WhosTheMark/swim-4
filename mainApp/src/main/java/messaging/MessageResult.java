package messaging;

import java.util.concurrent.TimeUnit;

public class MessageResult extends Message{
	public static final String STATUS_OK = "STATUS_OK";
	public static final String STATUS_TIMEOUT = "STATUS_TIMEOUT";
	
	private String consumerId;
	private String producerId;
	// always in TimeUnit.milliseconds
	private long requestTime;
	private long responseTime;
	// Later, we will add a time unit, like seconds, milliseconds, nanoseconds...
	private int requestDataSize;
	private int responseDataSize;
	private String status;
	
	public MessageResult() {
		super();
        type = MessageType.RESULT;
	}
	
	public MessageResult(String from, String to) {
		super(from, to);
         type = MessageType.RESULT;
	}
	
	public MessageResult(String from, String to, String consumerId,
			String producerId, long requestTime, long responseTime,
			int requestDataSize, int responseDataSize, final String status) {
		super(from, to);
        type = MessageType.RESULT;
		this.consumerId = consumerId;
		this.producerId = producerId;
		this.requestTime = requestTime;
		this.responseTime = responseTime;
		this.requestDataSize = requestDataSize;
		this.responseDataSize = responseDataSize;
		this.status = status;
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
	public long getRequestTime() {
		return requestTime;
	}
	public void setRequestTime(int requestTime) {
		this.requestTime = requestTime;
	}
	public long getResponseTime() {
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	



}
