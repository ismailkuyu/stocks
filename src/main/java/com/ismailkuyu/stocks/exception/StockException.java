package com.ismailkuyu.stocks.exception;

public class StockException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5141099826524372956L;

	private String errorMessage;

	public String getErrorMessage() {
		return errorMessage;
	}

	public StockException(String errorMessage) {
		super(errorMessage);
		this.errorMessage = errorMessage;
	}

	public StockException() {
		super();
	}
}
