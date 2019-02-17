package com.ismailkuyu.stocks;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.ismailkuyu.stocks.exception.StockNotFoundException;

@ControllerAdvice
class StockNotFoundAdvice {

	@ResponseBody
	@ExceptionHandler(StockNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	String stockNotFoundHandler(StockNotFoundException ex) {
		return ex.getMessage();
	}
}
