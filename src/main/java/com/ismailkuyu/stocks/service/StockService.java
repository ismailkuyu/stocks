package com.ismailkuyu.stocks.service;

import java.util.List;

import com.ismailkuyu.stocks.bean.Stock;

public interface StockService {

	public List<Stock> all();
	public Stock one(Long id);
	public Stock replaceStock(Stock stock, Long id);
	public Stock newStock(Stock stock);
	public void delete(Long id);
	
	
}
