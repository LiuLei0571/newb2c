package com.example.b2c.net.exception;

public class RequestFailureException extends Exception {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public RequestFailureException(){
		super();
	}
	
	public RequestFailureException(String message,Throwable  throwable){
		
		super(message,throwable);
	}
	public RequestFailureException(String message){
		
		super(message);
	}
	public RequestFailureException(Throwable  throwable){
		
		super(throwable);
	}
}
