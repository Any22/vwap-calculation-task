package com.demo.vwap_calculator.exception;

public class DuplicateRecordExist extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public DuplicateRecordExist() {
		super();
	}

	public DuplicateRecordExist(String message) {
		super(message);
	}
}
