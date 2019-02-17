package com.ismailkuyu.stocks.exception;

public class StockNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5141099826524372956L;

	public StockNotFoundException(Long id) {
		super("Could not find stock: " + id);
	}
}
