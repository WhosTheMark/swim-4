package controller;

public class SWIMException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public SWIMException() {
		super();
	}
	
	public SWIMException(String message) {
		super(message);
	}
	
	public SWIMException(Exception exception) {
		super(exception);
	}

}
