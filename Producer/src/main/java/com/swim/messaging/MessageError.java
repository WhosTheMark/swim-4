package com.swim.messaging;

public class MessageError extends Message{

	public MessageError() {
		super();
	}

	private String errorMessage;
	
	public MessageError(String from, String to, String errorMessage) {
		super(from, to);
		this.errorMessage = errorMessage;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}
