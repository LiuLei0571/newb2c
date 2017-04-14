package com.example.b2c.net.exception;
/**
 * 自定义 网路异常类
 * @author Administrator
 *
 */
public class NetException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	public NetException(){
		super();
	}
	
	public NetException(String message,Throwable  throwable){
		super(message,throwable);
	}
	public NetException(String message){
		super(message);
	}
	public NetException(Throwable  throwable){
		super(throwable);
	}
	

}
