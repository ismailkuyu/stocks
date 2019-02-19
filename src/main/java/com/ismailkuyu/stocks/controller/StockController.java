package com.ismailkuyu.stocks.controller;


import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ismailkuyu.stocks.bean.Stock;
import com.ismailkuyu.stocks.exception.StockException;
import com.ismailkuyu.stocks.service.StockService;

@RestController
public class StockController {


	@Autowired
	private StockService service;
	
	
	@GetMapping("/api/stocks")
	public List<Stock> all() {
		return service.all();
	}
	
	@GetMapping("/api/stocks/{id}")
	public Stock one(@PathVariable Long id) {
		return service.one(id);
	}
	

	@PutMapping("/api/stocks/{id}")
	public Stock replaceStock(@RequestBody Stock newStock, @PathVariable Long id) {
		return service.replaceStock(newStock, id);
		
	}

	@PostMapping("/api/stocks")
	public Stock newStock(@RequestBody Stock newStock) {
		newStock.setLastUpdate(new Date());
		return service.newStock(newStock);
	}


	@DeleteMapping("/api/stocks/{id}")
	public String deleteStock(@PathVariable Long id) {
		Stock stock = service.one(id);
		if (stock == null || stock.getId() <= 0){
            throw new StockException("Stock ["+id+"] doesn´t exist, couldn't be deleted.");
    	}
		
		service.delete(id);
		return "Stock ["+id+"] is deleted.";

	}
}
