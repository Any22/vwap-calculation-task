package com.demo.vwap_calculator.exception;

public class NoDataFoundException extends Exception  {
	
	private static final long serialVersionUID = 1L;
	
	public NoDataFoundException() {		
		super();		
	} 
	
	public NoDataFoundException( String message) {		
		super(message);		
	}
	
}